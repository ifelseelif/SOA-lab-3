package com.ifelseelif.soaback1;

import com.ifelseelif.soaback1.util.ConfigReader;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegCheck;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Properties;

@ApplicationPath("")
public class App extends Application {

    public App() {
        super();
        System.out.println("Start");

        Properties properties = ConfigReader.getProperties();
        String port = properties.getProperty("server_port");

        Consul client = Consul.builder().build();
        AgentClient agentClient = client.agentClient();

        String serviceId = properties.getProperty("consul_server_id");
        Registration service = ImmutableRegistration.builder()
                .id(serviceId)
                .name(properties.getProperty("consul_server_name"))
                .address(properties.getProperty("server_address"))
                .port(Integer.parseInt(port))
                .check(ImmutableRegCheck.builder()
                        .tlsSkipVerify(true)
                        .http(properties.getProperty("server_address") + ":" + port + "/soa-back-1/products")
                        .interval("30s")
                        .build())
                .build();

        agentClient.register(service);

    }
}
