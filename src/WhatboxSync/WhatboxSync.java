/****************************************************************************
 *
 * WhatboxSync.java
 *
 * The main application class.
 *
 ***************************************************************************
 *
 * Copyright (C) 2016 JP Dillingham (jp@dillingham.ws)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 ****************************************************************************/

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;

/** The main application class. */
public class WhatboxSync {
    /**
     * The logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

    /** The IConfiguraiton instance containing the application configuratiom. */
    private static IConfiguration config;

    /** The ISynchronizer instance containing the Synchronizer for the application. */
    private static ISynchronizer syncher;

    /** The ScheduledExecutaorService instance which handles scheduled synchronization. */
    private static ScheduledExecutorService service;

    /** The main entry point for the application.
     * @param args The command-line arguments passed to the application when starting. */
    public static void main(String[] args) {
        ConsoleAppender console = new ConsoleAppender(); //create appender
        //configure the appender
        String PATTERN = "%d{yyyy-MM-dd' 'HH:mm:ss.SSS} [%-5p] [%c] - %m%n";
        console.setLayout(new PatternLayout(PATTERN));
        console.setThreshold(Level.INFO);
        console.activateOptions();

        //add appender to any Logger (here is root)
        org.apache.log4j.Logger.getRootLogger().addAppender(console);

        String configFile = System.getProperty("user.dir") + File.separator + "config.json";

	    logger.info("Retrieving configuration from '" + configFile + "'...");

        // attempt to find and load the configuration file.
        // the file (config.json) needs to be located in the root of the application directory.
        if ((new File(configFile)).exists()) {
            try {
                config = new ConfigurationLoader().load(configFile);

                logger.info("Configuration retrieved successfully.");
            } catch (Exception ex) {
                logger.error("Error retrieving configuration: " + ex.getMessage());
            }
        }
        else {
            logger.error("Configuration file '" + configFile + "' was not found.");
        }

        logger.info("Creating a Synchronizer...");

        // instantiate a Synchronizer with the fetched configuration
        try {
            IServer server = new ServerFactory().createServer(config);
            syncher = new Synchronizer(config, server, new Database());
        }
        catch (Exception ex) {
            logger.error("Error creating Synchronizer: " + ex.getMessage());
        }

        logger.info("Synchronizer created successfully.");

        // create an executor to handle the synchronization
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                logger.info("Starting Synchronization...");
                try {
                    syncher.synchronize();
                } catch (Exception ex) {  }
            }
        }, 0, config.getInterval(), TimeUnit.SECONDS);
    }
}
