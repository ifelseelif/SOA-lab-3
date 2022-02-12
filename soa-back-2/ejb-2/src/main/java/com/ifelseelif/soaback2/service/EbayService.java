package com.ifelseelif.soaback2.service;

import com.ifelseelif.soaback2.exceptions.HttpException;
import com.ifelseelif.soaback2.model.Product;

import javax.ejb.Remote;
import java.io.IOException;
import java.util.List;

@Remote
public interface EbayService {
    List<Product> getAllProducts(int manufacturerId) throws HttpException, IOException;

    void increasePrice(int percent) throws HttpException, IOException;
}
