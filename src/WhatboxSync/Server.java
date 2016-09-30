package WhatboxSync;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents an FTP server.
 */
class Server implements IServer {
    /** The logger for this class. */
    Logger logger = LoggerFactory.getLogger(Server.class);

    /** The default port. */
    private static final Integer defaultPort = 21;

    /** The FTPClient driving the server */
    private static FTPClient server;

    /** The Server IP address. */
    private String ipAddress;

    /** The Server port. */
    private Integer port;

    /** Initializes a new instance of the Server class with the specified IP address. */
    Server(String ipAddress) {
        this(ipAddress, defaultPort);
    }

    /** Initializes a new instance of the Server class with the specified IP address and port. */
    Server(String ipAddress, Integer port)
    {
        this.ipAddress = ipAddress;
        this.port = port;

        this.server = new FTPClient();
    }
     /** Opens the Server connection using the specified IP address and the default port. */
    public Boolean connect() {
        try {
            server.connect(ipAddress, port);
        }
        catch (IOException ex) {
            logger.error("Exception thrown while connecting to server at '" + ipAddress + ":" + port + "': " + ex.getMessage());
        }

        return true;
    }

    /** Closes the Server connection. */
    public Boolean disconnect() {
        return true;
    }

    /** Returns a value indicating whether the Server connection is open. */
    public Boolean isConnected() {
        return true;
    }

    /** Lists the files in the specified directory. */
    public List<File> list(String directory) {
        List<File> retVal = new ArrayList<File>();
        retVal.add(new File("test", 0L, new java.util.Date(2016,9,13)));
        retVal.add(new File("test2", 2L, new java.util.Date(2016,9,13)));

        return retVal;
    }

    /** Downloads the specified file. */
    public Boolean download(String file) {
        return true;
    }
}
