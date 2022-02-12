package com.ifelseelif.soaback2.util;

import com.ifelseelif.soaback2.exceptions.HttpException;
import com.ifelseelif.soaback2.service.EbayService;
import org.wildfly.naming.client.WildFlyInitialContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class RemoteBeanLookupUtil {
    private static Properties properties = ConfigReader.getProperties();

    public static EbayService lookupEbayBean() throws HttpException {
        try {
            return (EbayService) getContext().lookup(properties.getProperty("e_jndi"));
        } catch (NamingException e) {
            System.out.println("--------");
            System.out.println(e.getMessage());
            System.out.println("-----------");
            throw new HttpException("Cant' lookup EbayService", 500);
        }
    }

    private static Context getContext() throws NamingException {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        return new InitialContext(props);
    }


}
