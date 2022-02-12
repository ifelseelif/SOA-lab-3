package com.ifelseelif.soaback1.util;

import com.ifelseelif.soaback1.model.Organization;
import com.ifelseelif.soaback1.model.Product;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties properties = ConfigReader.getProperties();
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Product.class);
                configuration.addAnnotatedClass(Organization.class);
                configuration.setProperty(Environment.URL, properties.getProperty("db_url"));
                configuration.setProperty(Environment.USER, properties.getProperty("db_username"));
                configuration.setProperty(Environment.PASS, properties.getProperty("db_password"));
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                System.out.println("Ошибка  !!!! " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
