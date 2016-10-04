package WhatboxSync;

import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by JP on 10/3/2016.
 */
public class Configuration {
    Logger logger = LoggerFactory.getLogger(WhatboxSync.class);
    private String file;
    private String server;
    private Integer port;
    private String username;
    private String password;
    private Boolean loaded = false;
    private String exceptionText = "The configuration has not been loaded.";

    public Configuration(String file) {
        this.file = file;

        try {
            loadConfiguration();
            loaded = true;
        }
        catch (Exception ex) {
            logger.error("Exception thrown while attempting to load configuration: " + ex.getMessage());
        }


    }

    public String getServer() {
        if (loaded) {
            return server;
        }
        else {
            throw new RuntimeException(exceptionText);
        }
    }

    public Integer getPort() {
        if (loaded) {
            return port;
        }
        else {
            throw new RuntimeException(exceptionText);
        }
    }

    public String getUsername() {
        if (loaded) {
            return username;
        }
        else {
            throw new RuntimeException(exceptionText);
        }
    }

    public String getPassword() {
        if (loaded) {
            return password;
        }
        else {
            throw new RuntimeException(exceptionText);
        }
    }

    private void loadConfiguration() {
        JSONParser parser = new JSONParser();
        Object obj;
        try {
            obj = parser.parse(new FileReader(file));
        }
        catch (Exception ex) {
            logger.error("Exception parsing configuration file: " + ex.getMessage());
            throw (RuntimeException)ex;
        }

        JSONObject jsonObject = (JSONObject)obj;

        server = (String)jsonObject.get("server");
        port = ((Long)jsonObject.get("port")).intValue();
        username = (String)jsonObject.get("username");
        password = (String)jsonObject.get("password");
    }
}
