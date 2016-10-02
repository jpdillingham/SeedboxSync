package WhatboxSync;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhatboxSync {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(WhatboxSync.class);

	    logger.info("Hello world!");

        // TODO: put this in an external config file
        Server server = new Server("sushi.whatbox.ca", "", "");

        if (server.connect()) {
            System.out.println("Connected!");

            List<File> files = server.list("files");

            for (File file : files) {
                System.out.println(file.getName());
            }
        }
    }
}
