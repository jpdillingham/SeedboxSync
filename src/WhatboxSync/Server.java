package WhatboxSync;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents an FTP server.
 */
class Server implements IServer {
    /** The default port. */
    private static final Integer defaultPort = 21;

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
    }
     /** Opens the Server connection using the specified IP address and the default port. */
    public Boolean connect() {
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
