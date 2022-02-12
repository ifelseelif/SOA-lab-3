package com.ifelseelif.soaback1.service.impl;


import com.ifelseelif.soaback1.dao.OrganizationDao;
import com.ifelseelif.soaback1.dao.ProductDao;
import com.ifelseelif.soaback1.exceptions.HttpException;
import com.ifelseelif.soaback1.model.*;
import com.ifelseelif.soaback1.service.ProductService;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.lang.reflect.Field;
import java.util.*;

@Stateless
@Remote(ProductService.class)
public class ProductServiceImp implements ProductService {
    private ProductDao productDao;
    private OrganizationDao organizationDao;
    private List<String> productPropertiesNames;


    public ProductServiceImp() {
        productDao = new ProductDao();
        organizationDao = new OrganizationDao();
    }

    public void save(Product product) throws HttpException {
        validate(product);

        int manufacturerId = product.getManufacturer().getId();
        Organization organization = organizationDao.findById(manufacturerId);
        if (organization == null)
            throw new HttpException("organization with id:=" + manufacturerId + " not found", 404);

        product.setManufacturer(organization);
        product.setCreationDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        productDao.save(product);
    }

    public void updateById(Long productId, Product product) throws HttpException {
        if (productId == null) throw new HttpException("provide manufacture cost", 400);
        product.setId(productId);
        validate(product);
        Organization organization = organizationDao.findById(product.getManufacturer().getId());
        if (organization == null)
            throw new HttpException("organization with id:=" + product.getManufacturer().getId() + " not found", 404);

        product.setId(productId);
        product.setManufacturer(organization);

        if (productDao.findById(productId) == null) {
            throw new HttpException("product with id:=" + product.getId() + " not found", 404);
        }

        productDao.update(product);
    }

    public void deleteByManufactureCost(Long manufactureCost) throws HttpException {
        if (manufactureCost == null) throw new HttpException("provide manufacture cost", 400);
        productDao.deleteByManufactureCost(manufactureCost);
    }


    public List<Product> getAll(Map<String, String[]> parameterMap) throws HttpException {
        int pageIndex = getIntValue(parameterMap, "pageIndex", 0);
        int pageSize = getIntValue(parameterMap, "pageSize", 10);
        List<String> sortingParams = getSortParams(parameterMap.getOrDefault("sort", new String[]{}));
        Map<String, String[]> filters = getFilters(parameterMap);
        Filter filter = new Filter(pageIndex, pageSize, sortingParams, filters);

        System.out.println("return ok");
        return productDao.findAllFiltering(filter);
    }

    public Product getById(Long id) throws HttpException {
        if (id == null) throw new HttpException("provide manufacture cost", 400);
        Product product = productDao.findById(id);
        if (product == null) throw new HttpException("product with id:=" + id + " not found", 400);
        return product;
    }

    public void deleteById(Long id) throws HttpException {
        if (id == null) throw new HttpException("provide manufacture cost", 400);
        Product product = getById(id);
        productDao.delete(product);
    }

    private List<String> getPropertiesName() {
        if (productPropertiesNames != null) {
            return productPropertiesNames;
        }

        productPropertiesNames = new ArrayList<>();
        for (Field field : Product.class.getDeclaredFields()) {
            productPropertiesNames.add(field.getName());
        }

        productPropertiesNames.remove("coordinates");
        productPropertiesNames.remove("manufacturer");
        for (Field field : Organization.class.getDeclaredFields()) {
            productPropertiesNames.add(Constants.manufacturerPrefix + field.getName());
        }
        for (Field field : Coordinates.class.getDeclaredFields()) {
            productPropertiesNames.add(Constants.coordinatesPrefix + field.getName());
        }

        return productPropertiesNames;
    }

    private List<String> getSortParams(String[] sorts) throws HttpException {
        List<String> result = new ArrayList<>();
        List<String> propertiesName = getPropertiesName();
        for (String order : sorts) {
            String[] values = order.split(Constants.divider);
            if (values.length == 0) throw new HttpException("Invalid sort param, it can not be zero", 400);
            if (propertiesName.contains(values[0])) {
                result.add(order);
            } else {
                throw new HttpException("Invalid name sort" + values[0], 400);
            }
        }

        return result;
    }

    private Map<String, String[]> getFilters(Map<String, String[]> parameterMap) throws HttpException {
        Map<String, String[]> filters = new HashMap<>();
        List<String> propertiesName = getPropertiesName();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            if (entry.getKey().equals("sort") || entry.getKey().equals("pageIndex") || entry.getKey().equals("pageSize"))
                continue;
            if (!propertiesName.contains(entry.getKey())) {
                throw new HttpException("Invalid name filter " + entry.getKey(), 400);
            }
            filters.put(entry.getKey(), entry.getValue());
        }

        return filters;
    }

    private int getIntValue(Map<String, String[]> parameterMap, String name, int defaultValue) throws HttpException {
        int value = defaultValue;
        if (parameterMap.containsKey(name)) {
            try {
                value = Integer.parseInt(String.join("", parameterMap.get(name)));
            } catch (Exception ignored) {
                throw new HttpException("Parameters is invalid", 400);
            }
        }

        return value;
    }

    private void validate(Product product) throws HttpException {
        String errorMessage = null;
        if (product.getName() == null || product.getName().isEmpty()) errorMessage = "Name should not be empty";
        if (product.getCoordinates() == null || product.getCoordinates().getX() == null)
            errorMessage = "Coordinate x should not be empty";
        if (product.getPrice() == null || product.getPrice() <= 0)
            errorMessage = "Price should not be empty and be greater than 0";
        if (product.getManufactureCost() == null) errorMessage = "Manufacture cost should not be empty";
        if (product.getUnitOfMeasure() == null) errorMessage = "Unit of measure should not be empty";
        if (product.getManufacturer() == null || product.getManufacturer().getId() == 0)
            errorMessage = "Manufacture id should not be empty";

        if (errorMessage != null) {
            throw new HttpException(errorMessage, 400);
        }
    }
}
