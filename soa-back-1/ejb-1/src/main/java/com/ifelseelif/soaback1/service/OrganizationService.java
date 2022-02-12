package com.ifelseelif.soaback1.service;

import com.ifelseelif.soaback1.exceptions.HttpException;
import com.ifelseelif.soaback1.model.Organization;

import javax.ejb.Remote;
import java.util.List;
import java.util.Map;

@Remote
public interface OrganizationService {
    Organization getById(int id) throws HttpException;

    List<Organization> getAll(Map<String, String[]> parameterMap) throws HttpException;

    void save(Organization organization) throws HttpException;

    void updateById(int id, Organization organization) throws HttpException;

    void deleteById(int id) throws HttpException;
}
