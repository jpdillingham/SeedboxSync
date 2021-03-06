/****************************************************************************
 *
 * SeedboxSync.java
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The main application class.
 */
public class SeedboxSync {
    /**
     * The logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

    /**
     * The Configuraiton instance containing the application configuratiom.
     */
    private static Configuration config;

    /**
     * The ISynchronizer instance containing the Synchronizer for the application.
     */
    private static ISynchronizer synchronizer;

    /**
     * The ScheduledExecutaorService instance which handles scheduled synchronization.
     */
    private static ScheduledExecutorService service;

    /**
     * The main entry point for the application.
     * @param args The command-line arguments passed to the application when starting.
     * @throws Exception Thrown on any uncaught Exception.
     */
    public static void main(String[] args) throws Exception {
        configureLogging();

        logger.info("Starting SeedboxSync...");

        // determine the filename of the config file.  if a command line argument was supplied, use that.
        // otherwise, use the default "config.json".
        String configFile;

        if (args.length > 0) {
            configFile = args[0];
        }
        else {
            configFile = "config.json";
        }

        // load the configuration from the config file.
        // if an exception is raised or the config is invalid an exception is thrown and we will log it and exit.
        logger.info("Retrieving configuration from '" + configFile + "'...");

        try {
            config = loadConfiguration(configFile);

            logger.info("Configuration loaded and validated successfully.");
        }
        catch (Exception ex) {
            logger.error("Error loading configuration: " + ex.getMessage());
            throw ex;
        }

        // config was loaded and validated.  create the synchronizer.
        logger.info("Creating Synchronizer...");

        try {
            Server server = ServerFactory.createServer(config);
            Database database = DatabaseLoader.load(new java.io.File("database.db"));
            synchronizer = new Synchronizer(config, server, database);

            logger.info("Synchronizer created successfully.");
        }
        catch (Exception ex) {
            logger.error("Error creating Synchronizer: " + ex.getMessage());
            throw ex;
        }

        // start the application.
        start();
    }

    /**
     * Starts a repeating task to invoke the synchronizer.
     */
    private static void start() {
        // create an executor to handle the synchronization
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                boolean err;
                logger.info("Starting Synchronization...");

                try {
                    err = false;
                    synchronizer.synchronize();
                }
                catch (Exception ex) {
                    // report the error but keep the application running.
                    logger.error("Exception thrown during synchronization: " + ex.getMessage());
                    err = true;
                }

                logger.info("Synchronization " + (err ? "failed." : "complete."));
                logger.info("Synchronization will repeat in " + config.getInterval() / 60 + " minute(s).");
            }
        }, 0, config.getInterval(), TimeUnit.SECONDS);
    }

    /**
     * Configures the logger.
     */
    private static void configureLogging() {
        PatternLayout layout = new PatternLayout("%d{yyyy-MM-dd' 'HH:mm:ss.SSS} [%-5p] [%c] - %m%n");

        ConsoleAppender console = new ConsoleAppender();
        console.setLayout(layout);
        console.setThreshold(Level.INFO);
        console.activateOptions();

        DailyRollingFileAppender file = new DailyRollingFileAppender();
        file.setFile("log/app.log");
        file.setDatePattern("'.'yyyy-MM-dd");
        file.setLayout(layout);
        file.activateOptions();

        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getRootLogger();
        logger.addAppender(console);
        logger.addAppender(file);
    }

    /**
     * Loads the application configuration from file.
     * @returns The loaded Configuration
     */
    private static Configuration loadConfiguration(String file) throws Exception {
        Configuration config;
        String configFile = System.getProperty("user.dir") + File.separator + file;

        if (!(new File(configFile)).exists()) {
            throw new Exception("Configuration file '" + configFile + "' was not found.");
        }
        else {
            return new ConfigurationLoader().load(configFile);
        }
    }
}
