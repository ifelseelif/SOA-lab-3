package com.ifelseelif.soaback1.util;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    public static Properties getProperties() {
        Properties prop = new Properties();
        String fileName = "app.config";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return prop;
    }
}
