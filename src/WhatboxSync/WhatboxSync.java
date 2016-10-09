package WhatboxSync;

import java.util.List;
import org.apache.commons.net.ftp.FTPFile;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhatboxSync {
    private static Logger logger = LoggerFactory.getLogger(WhatboxSync.class);

    private static ConfigurationLoader configLoader;
    private static IConfiguration config;

    public static void main(String[] args) {
        String configFile = System.getProperty("user.dir") + File.separator + "config.json";

	    logger.info("Retrieving configuration from '" + configFile + "'...");

        if ((new File(configFile)).exists()) {
            try {
                configLoader = new ConfigurationLoader();
                config = configLoader.Load(configFile);

                logger.info("Configuration retrieved successfully.");
            } catch (Exception ex) {
                logger.error("Error retrieving configuration: " + ex.getMessage());
            }
        }
        else {
            logger.error("Configuration file '" + configFile + "' was not found.");
        }

        logger.info("Connecting to server '" + config.getServer() + "' on port " + config.getPort() + "...");


    }
}
