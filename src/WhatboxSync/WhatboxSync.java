package WhatboxSync;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhatboxSync {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(WhatboxSync.class);

	    logger.info("Hello world!");

        Server server = new Server("127.0.0.1");

        if (server.connect()) {
            System.out.println("Connected!");

            List<File> files = server.list("blah");

            for (File file : files) {
                System.out.println(file.getName());
            }
        }
    }
}
