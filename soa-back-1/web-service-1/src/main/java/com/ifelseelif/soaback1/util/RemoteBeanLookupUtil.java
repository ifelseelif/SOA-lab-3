package com.ifelseelif.soaback1.util;

import com.ifelseelif.soaback1.exceptions.HttpException;
import com.ifelseelif.soaback1.service.OrganizationService;
import com.ifelseelif.soaback1.service.ProductService;
import org.wildfly.naming.client.WildFlyInitialContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class RemoteBeanLookupUtil {
    private static final Properties properties = ConfigReader.getProperties();

    public static ProductService lookupProductBean() throws HttpException {
        try {
            return (ProductService) getContext().lookup(properties.getProperty("p_jndi"));
        } catch (NamingException e) {
            System.out.println("--------");
            System.out.println(e.getMessage());
            System.out.println("-----------");
            throw new HttpException("Can't lookup LabWorkBean", 400);
        }
    }

    public static OrganizationService lookupOrganizationBean() throws HttpException {
        try {
            return (OrganizationService) getContext().lookup(properties.getProperty("o_jndi"));
        } catch (NamingException e) {
            System.out.println("--------");
            System.out.println(e.getMessage());
            System.out.println("-----------");
            throw new HttpException("Can't lookup DisciplineBean", 400);
        }
    }

    private static Context getContext() throws NamingException {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        return new InitialContext(props);
    }


}
