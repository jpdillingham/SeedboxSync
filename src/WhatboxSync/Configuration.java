/****************************************************************************
 *
 * Configuration.java
 *
 * Represents the application Configuration models.
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the application Configuration model.
 */
public class Configuration implements IConfiguration {
    /**
     * The logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

    /**
     * The configured server address.
     */
    private String server;

    /**
     * The configured server port.
     */
    private Integer port;

    /**
     * The configured username for the server.
     */
    private String username;

    /**
     * The configured password for the server.
     */
    private String password;

    /**
     * The configured interval on which the synchronization should take place.
     */
    private Integer interval;

    /**
     * The configured remote directory which is to be synchronized.
     */
    private String remoteDirectory;

    /**
     * The configured local directory to which files are to be downloaded.
     */
    private String localDirectory;

    /**
     * Initializes a new instance of the Configuration class with the specified parameters.
     * @param server The server address.
     * @param port The server port.
     * @param username The username for the server.
     * @param password The password for the server.
     * @param interval The interval on which synchronization should take place.
     * @param remoteDirectory The remote directory which is to be synchronized.
     * @param localDirectory The local directory to which files are to be downloaded.
     */
    public Configuration(String server, Integer port, String username, String password, Integer interval, String remoteDirectory, String localDirectory) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.password = password;
        this.interval = interval;
        this.remoteDirectory = remoteDirectory;
        this.localDirectory = localDirectory;
    }

    /**
     * Gets the configured server address.
     * @return The configured server address.
     */
    public String getServer() {
        return server;
    }

    /**
     * Gets the configured server port.
     * @return The configured server port.
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Gets the configured username for the server.
     * @return The configured username for the server.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the configured password for the server.
     * @return The configured password for the server.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the configured interval on which the synchronization should take place.
     * @return The configured interval on which the synchronization should take place.
     */
    public Integer getInterval() {
        return interval;
    }

    /**
     * Gets the remote directory which is to be synchronized.
     * @return The remote directory which is to be synchronized.
     */
    public String getRemoteDirectory() {
        return remoteDirectory;
    }

    /**
     * Gets the local directory to which files are to be downloaded.
     * @return The local directory to which files are to be downloaded.
     */
    public String getLocalDirectory() {
        return localDirectory;
    }

    /**
     * Returns a value indicating whether the configuration is valid.
     * @return A value indicating whether the configuration is valid.
     */
    public Boolean isValid() {
        String err = "";

        if (server == null || server == "") {
            err = "Server is missing or blank.";
        }
        else if (port == null || port <= 0) {
            err = "Server port is null or invalid.";
        }
        else if (username == null || username == "") {
            err = "Username is missing or blank.";
        }
        else if (password == null) {
            err = "Password is missing.";
        }
        else if (interval == null || interval <= 0) {
            err = "Interval is missing or too low.";
        }
        else if (remoteDirectory == null || remoteDirectory == "") {
            err = "Remote directory is missing or blank.";
        }
        else if (localDirectory == null || localDirectory == "") {
            err = "Local directory is missing or blank.";
        }

        if (err != "") {
            return false;
        }

        return true;
    }
}
