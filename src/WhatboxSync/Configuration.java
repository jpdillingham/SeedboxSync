package WhatboxSync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the application Configuration.
 */
public class Configuration implements IConfiguration {

    /** The logger for this class. */
    Logger logger = LoggerFactory.getLogger(WhatboxSync.class);

    /** The file containing the configuration information. */
    private String file;

    /** The configured server address. */
    private String server;

    /** The configured server port. */
    private Integer port;

    /** The configured username for the server. */
    private String username;

    /** The configured password for the server. */
    private String password;

    /** The configured remote directory which is to be synchronized. */
    private String remoteDirectory;

    /** The configured local directory to which files are to be downloaded. */
    private String localDirectory;

    public Configuration(String server, Integer port, String username, String password, String remoteDirectory, String localDirectory) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.password = password;
        this.remoteDirectory = remoteDirectory;
        this.localDirectory = localDirectory;
    }

    public String getServer() {
        return server;
    }

    public Integer getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRemoteDirectory() {
        return remoteDirectory;
    }

    public String getLocalDirectory() {
        return localDirectory;
    }
}
