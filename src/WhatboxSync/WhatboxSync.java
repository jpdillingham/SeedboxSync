package WhatboxSync;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhatboxSync {
    private static Configuration config;
    private static Logger logger = LoggerFactory.getLogger(WhatboxSync.class);

    public static void main(String[] args) {
	    logger.info("Retrieving configuration...");

        try {
            config = new Configuration("/Users/JP.WHATNET/config.json");
            logger.info("Configuration retrieved successfully.");
        }
        catch (Exception ex) {
            logger.error("Error retrieving configuration: " + ex.getMessage());
        }

        logger.info("Connecting to server '" + config.getServer() + "' on port " + config.getPort() + "...");

        Server server = new Server(config.getServer(), config.getUsername(), config.getPassword(), config.getPort());

        if (server.connect()) {
            System.out.println("Connected!");

            List<File> files = server.list("files");

            for (File file : files) {
                System.out.println(file.getName());
            }
        }
    }
}
