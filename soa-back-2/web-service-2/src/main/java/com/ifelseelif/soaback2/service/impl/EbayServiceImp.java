package com.ifelseelif.soaback2.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifelseelif.soaback2.exceptions.HttpException;
import com.ifelseelif.soaback2.model.Product;
import com.ifelseelif.soaback2.service.EbayService;
import com.ifelseelif.soaback2.util.ConfigReader;
import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.health.Service;
import com.orbitz.consul.model.health.ServiceHealth;

import javax.jws.WebService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@WebService(serviceName = "ebay_service", endpointInterface = "com.ifelseelif.soaback2.service.EbayService")
public class EbayServiceImp implements EbayService {
    private Properties properties = ConfigReader.getProperties();

    @Override
    public List<Product> getAllProducts(int manufacturerId) throws HttpException, IOException {
        if (manufacturerId < 0) {
            throw new HttpException("manufacturerId can't be less than zero", 400);
        }

        ArrayList<Product> products = new ArrayList<>();
        long skip = 0;


        WebTarget target = getTarget();

        if (target == null) {
            throw new HttpException("Consul can't find active serivce", 400);
        }

        while (true) {
            String json = target.path("products").queryParam("manufacturer.id", "=;" + manufacturerId)
                    .queryParam("pageIndex", skip).request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Product> productList = objectMapper.readValue(json, new TypeReference<List<Product>>() {
            });
            products.addAll(productList);

            skip += productList.size();
            if (productList.size() == 0) {
                break;
            }
        }
        if (products.isEmpty()) {
            throw new HttpException("продукты не найдены", 404);
        }

        return products;
    }

    @Override
    public void increasePrice(int percent) throws HttpException, IOException {
        if (percent < 0) {
            throw new HttpException("manufacturerId can't be less than zero", 400);
        }

        WebTarget target = getTarget();
        if (target == null) {
            throw new HttpException("Consul can't find active serivce", 400);
        }

        long skip = 0;
        while (true) {
            String json = target.path("products")
                    .queryParam("pageIndex", skip).request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Product> productList = objectMapper.readValue(json, new TypeReference<List<Product>>() {
            });
            for (Product p :
                    productList) {
                WebTarget target2 = getTarget();
                if (p.getPrice() == null) continue;
                p.setPrice(p.getPrice() + (p.getPrice() * (percent / 100f)));
                target2.path("products").path("/" + p.getId()).request().put(Entity.entity(p, MediaType.APPLICATION_JSON));
            }
            skip += productList.size();
            if (productList.size() == 0) {
                break;
            }
        }
    }


    private WebTarget getTarget() throws HttpException {
        Consul consulClient = Consul.builder().build();
        HealthClient healthClient = consulClient.healthClient();

        List<ServiceHealth> nodes = healthClient.getHealthyServiceInstances(properties.getProperty("consul_server_name")).getResponse();

        if (nodes == null || nodes.size() == 0) {
            throw new HttpException("Service soa1 not found", 404);
        }

        Service service = nodes.get(0).getService();

        String serviceAddress = "https://localhost";
        if (!service.getAddress().isEmpty()) {
            serviceAddress = service.getAddress();
            System.out.println("It's okay " + serviceAddress);
        }
        String BACK_2_URI = serviceAddress + ":" + service.getPort() + "/" + properties.getProperty("server_1_path") + "/";
        System.out.println(BACK_2_URI);

        try {
            FileInputStream is = new FileInputStream("truststore.jks");
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(is, "secret".toCharArray());
            Client client = ClientBuilder.newBuilder().trustStore(keystore).build();
            return client.target(BACK_2_URI);
        } catch (IOException e) {
            System.out.println("Can't find truststore file");
        } catch (Exception e) {
            System.out.println("Can't setup SSL");
            System.out.println(e.getMessage());
        }
        return null;
    }
}

