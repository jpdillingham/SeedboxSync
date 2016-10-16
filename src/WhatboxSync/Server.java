/****************************************************************************
 *
 * Server.java
 *
 * Represents an FTP server.
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

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.io.File;
import java.io.FileOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

/**
 * Represents an FTP server.
 */
public class Server implements IServer {
    /**
     * The logger for this class.
     */
    Logger logger = LoggerFactory.getLogger(Server.class);

    /**
     * The default port.
     */
    private static final Integer defaultPort = 21;

    /**
     * The FTPClient driving the server
     */
    private static FTPClient server;

    /**
     * The Server address.
     */
    private String address;

    /**
     * The Server port.
     */
    private Integer port;

    /**
     * The username to use when connecting to the Server.
     */
    private String username;

    /**
     * The password to use when connecting to the Server.
     */
    private String password;

    /**
     * Initializes a new instance of the Server class with the specified IP address, username and password.
     * @param address The Server address.
     * @param username The username to use when connecting to the Server.
     * @param password The password to use when connecting to the Server.
     */
    Server(String address, String username, String password) {
        this(address, username, password, defaultPort);
    }

    /**
     * Initializes a new instance of the Server class with the specified IP address, username, password and port.
     * @param address The Server address.
     * @param username The username to use when connecting to the Server.
     * @param password The password to use when connecting to the Server.
     * @param port The Server port.
     */
    public Server(String address, String username, String password, Integer port)
    {
        this.address = address;
        this.username = username;
        this.password = password;
        this.port = port;

        this.server = new FTPClient();
    }

    /**
     * Gets the Server address.
     * @return The Server address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the username used when connecting to the Server.
     * @return The username used when connecting to the Server.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password used when connecting to the Server.
     * @return The password used when connecting to the Server.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the Server port.
     * @return The Server port.
     */
    public Integer getPort() {
        return port;
    }

     /**
      * Opens the Server connection using the specified IP address and the default port.
      * @throws Exception Thrown if an exception is encountered during the connect or login operations.
      */
    public void connect() throws Exception {
        logger.debug("Connecting to '" + address + "'...");
        server.connect(address, port);

        logger.debug("Logging in with credentials '" + username + "', '<hidden>'");
        server.login(username, password);
    }

    /**
     * Closes the Server connection.
     * @throws Exception Thrown if an exception is encountered during the disconnect operation.
     */
    public void disconnect() throws Exception {
        logger.debug("Disconnecting from '" + address + "'...");
        server.disconnect();
    }

    /**
     * Returns a value indicating whether the Server is connected.
     * @return A value indicating whether the Server is connected.
     */
    public Boolean isConnected() {
        return server.isConnected();
    }

    /**
     * Returns a list of files contained within the specified directory.
     * @param directory The directory for which to return the file list.
     * @return A list of files contained within the directory.
     * @throws Exception Thrown if an exception is encountered during the listFiles operation.
     */
    public List<FTPFile> list(String directory) throws Exception {
        List<FTPFile> retVal = new ArrayList<FTPFile>();

        logger.debug("Fetching file list from '" + directory + "'...");
        FTPFile[] files = server.listFiles(directory);
        logger.debug("Fetched " + files.length + " files.");

        for (FTPFile f : files) {
            retVal.add(f);
        }

        return retVal;
    }

    /**
     * Downloads the specified file.
     * @param file The file to download.
     * @param destinationFile The file to which the downloaded file should be saved.
     * @return A value indicating whether the download completed successfully.
     * @throws Exception Thrown if an exception is encountered during the download.
     */
    @Async
    public Future<Boolean> download(String file, String destinationFile) throws Exception {
        server.retrieveFile(file, new FileOutputStream(destinationFile));
        return new AsyncResult<Boolean>(true);
    }

    /**
     * Downloads the specified file.
     * @param file The file to download.
     * @param destinationFile The file to which the downloaded file should be saved.
     * @return A value indicating whether the download completed successfully.
     * @throws Exception Thrown if an exception is encountered during the download.
     */
    @Async
    public Future<Boolean> download(String file, File destinationFile) throws Exception {
        return download(file, destinationFile.getAbsolutePath());
    }
}
