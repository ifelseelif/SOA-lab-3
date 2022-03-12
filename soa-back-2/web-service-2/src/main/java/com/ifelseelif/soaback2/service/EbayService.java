package com.ifelseelif.soaback2.service;

import com.ifelseelif.soaback2.exceptions.HttpException;
import com.ifelseelif.soaback2.model.Product;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.IOException;
import java.util.List;

@WebService
public interface EbayService {
    @WebMethod
    List<Product> getAllProducts(int manufacturerId) throws HttpException, IOException;

    @WebMethod
    void increasePrice(int percent) throws HttpException, IOException;
}
