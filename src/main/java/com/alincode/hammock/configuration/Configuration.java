package com.alincode.hammock.configuration;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Configuration {
    /**
     * This is a list of socket:pair -> filename mappings.
     */
    public List<Map<String, String>> sockets;

    public static Configuration buildConfiguration(String fileLocation) {
        Configuration config = null;
        try(InputStream inputStream = new FileInputStream(new File(fileLocation));
        ) {
            Yaml yaml = new Yaml();
            config = yaml.loadAs(inputStream, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }
}
