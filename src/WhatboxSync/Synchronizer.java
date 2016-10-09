package WhatboxSync;

import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by JP on 10/8/2016.
 */
public class Synchronizer implements ISynchronizer {
    /** The logger for this class. */
    private Logger logger = LoggerFactory.getLogger(WhatboxSync.class);

    /** The configuration for the Synchronizer. */
    private IConfiguration config;

    /** Initializes a new instance of the Synchronizer class with the specified Configuration.
     * @param config The Configuration instance with which the Synchronizer should be configured. */
    public Synchronizer(IConfiguration config) {
        this.config = config;
    }

    public void Synchronize() {
        Server server = new Server(config.getServer(), config.getUsername(), config.getPassword(), config.getPort());

        logger.info("Connecting to server '" + config.getServer() + "' on port " + config.getPort() + "...");

        if (server.connect()) {
            System.out.println("Connected!");

            List<FTPFile> files = server.list(config.getRemoteDirectory());

            for (FTPFile file : files) {
                System.out.println(file.getName());
            }
        }
    }
}
