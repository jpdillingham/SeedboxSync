package WhatboxSync;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhatboxSync {
    private static Logger logger = LoggerFactory.getLogger(WhatboxSync.class);

    private static ConfigurationLoader configLoader;
    private static IConfiguration config;

    public static void main(String[] args) {
	    logger.info("Retrieving configuration...");

        try {
            configLoader = new ConfigurationLoader();
            config = configLoader.Load("/Users/JP.WHATNET/config.json");

            logger.info("Configuration retrieved successfully.");
        }
        catch (Exception ex) {
            logger.error("Error retrieving configuration: " + ex.getMessage());
        }

        logger.info("Connecting to server '" + config.getServer() + "' on port " + config.getPort() + "...");

        Server server = new Server(config.getServer(), config.getUsername(), config.getPassword(), config.getPort());

        if (server.connect()) {
            System.out.println("Connected!");

            List<File> files = server.list(config.getRemoteDirectory());

            for (File file : files) {
                System.out.println(file.getName());
            }
        }
    }
}
