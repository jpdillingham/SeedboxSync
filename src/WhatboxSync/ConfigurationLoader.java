package WhatboxSync;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class ConfigurationLoader {
    /** The logger for this class. */
    private Logger logger = LoggerFactory.getLogger(Server.class);

    public Configuration Load(String file) throws IOException, ParseException {
        if (!(new File(file)).exists()) {
            throw new FileNotFoundException("File not found: '" + file + "'");
        }

        JSONParser parser = new JSONParser();
        Object obj;

        try {
            obj = parser.parse(new FileReader(file));
        }
        catch (ParseException ex) {
            logger.error("File parsing error: " + ex.getMessage());
            throw ex;
        }

        JSONObject jsonObject = (JSONObject)obj;

        String server = this.<String>fetch("server", jsonObject);
        Integer port = this.<Long>fetch("port", jsonObject).intValue();
        String username = this.<String>fetch("username", jsonObject);

        String password = this.<String>fetch("password", jsonObject);
        Integer interval = this.<Long>fetch("interval", jsonObject).intValue();
        String remoteDirectory = this.<String>fetch("remoteDirectory", jsonObject);
        String localDirectory = this.<String>fetch("localDirectory", jsonObject);

        return new Configuration(server, port, username, password, interval, remoteDirectory, localDirectory);
    }

    @SuppressWarnings("unchecked")
    private <T> T fetch(String fieldName, JSONObject object) {
        T retVal = (T)object.get(fieldName);

        if (retVal == null) {
            badConfiguration(fieldName);
        }

        return retVal;
    }

    private void badConfiguration(String fieldName) {
        throw new RuntimeException("Bad configuration; field '" + fieldName + "' is missing or contains an invalid value.");
    }
}
