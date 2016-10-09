package WhatboxSync;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;

public class ConfigurationLoader {
    /** The logger for this class. */
    Logger logger = LoggerFactory.getLogger(Server.class);

    public IConfiguration Load(String file) {
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

        String server = (String)jsonObject.get("server");
        Integer port = ((Long)jsonObject.get("port")).intValue();
        String username = (String)jsonObject.get("username");
        String password = (String)jsonObject.get("password");
        Integer interval = ((Long)jsonObject.get("interval")).intValue();
        String remoteDirectory = (String)jsonObject.get("remoteDirectory");
        String localDirectory = (String)jsonObject.get("localDirectory");

        return new Configuration(server, port, username, password, interval, remoteDirectory, localDirectory);
    }
}
