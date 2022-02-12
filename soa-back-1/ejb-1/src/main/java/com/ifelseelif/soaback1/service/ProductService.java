package com.ifelseelif.soaback1.service;

import com.ifelseelif.soaback1.exceptions.HttpException;
import com.ifelseelif.soaback1.model.Product;

import javax.ejb.Remote;
import java.util.List;
import java.util.Map;

@Remote
public interface ProductService {
    Product getById(Long id) throws HttpException;

    List<Product> getAll(Map<String, String[]> parameterMap) throws HttpException;

    void save(Product product) throws HttpException;

    void updateById(Long id, Product product) throws HttpException;

    void deleteById(Long id) throws HttpException;

    void deleteByManufactureCost(Long id) throws HttpException;
}
