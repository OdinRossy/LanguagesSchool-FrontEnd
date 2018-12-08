package com.company.transport.configuration;

import com.company.utils.propertyLoader.PropertyLoader;

public interface ServerConfiguration {

    String PATH_TO_PROPERTY_FILE = "src/resources/Configuration.properties";
    PropertyLoader propertyLoader = PropertyLoader.getInstance(PATH_TO_PROPERTY_FILE);

    int TCP_SERVER_PORT = Integer.parseInt(propertyLoader.loadProperty("server.port"));
    String TCP_SERVER_ADDRESS = propertyLoader.loadProperty("server.address");
}
