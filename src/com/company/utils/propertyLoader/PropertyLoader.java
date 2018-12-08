package com.company.utils.propertyLoader;

import com.company.utils.exceptions.PropertyNotFoundException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {

    private static PropertyLoader instance;
    private String path;

    public String loadProperty(String name){
        String value = null;
        Properties properties = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            properties.load(fileInputStream);
            value = properties.getProperty(name);
            if (value == null) {
                throw new PropertyNotFoundException("No such property with name \'" + name + "\' in " + path);
            }
        } catch (PropertyNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static PropertyLoader getInstance(String path) {
        if (instance == null) {
            instance = new PropertyLoader(path);
        }
        return instance;
    }
    private PropertyLoader(String path) {
        this.path = path;
    }
}