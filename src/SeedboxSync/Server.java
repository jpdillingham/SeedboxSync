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
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.FileInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.io.CopyStreamAdapter;

/**
 * Represents an FTP server.
 */
public class Server implements IServer {
    /**
     * The logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

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
     * The currently downloading file.
     */
    private String currentDownload;

    /**
     * The size of the currently downloading file, in bytes.
     */
    private Long currentDownloadSize;

    /**
     * The percentage of completion of the currently downloading file.
     */
    private Double currentDownloadPercent;

    /**
     * The time of the last received progress update
     */
    private Long lastProgressUpdate;

    /**
     * The total progress of the current download at the last check.
     */
    private Long lastProgressTotal;

    /**
     * The interval between progress checks, in nanoseconds.
     */
    private Long timeBetweenUpdates = 10000000000L;

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
        logger.info("Configuring FTP client...");

        this.address = address;
        this.username = username;
        this.password = password;
        this.port = port;

        this.server = new FTPClient();

        logger.debug("Binding transfer stream listener...");
        configureStreamListener();

        logger.info("FTP client is ready.");
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
        logger.info("Connecting to '" + address + "'...");
        server.connect(address, port);

        logger.info("Logging in with credentials '" + username + "', '<hidden>'");
        server.login(username, password);

        logger.info("Configuring connection...");
        server.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
        server.setBufferSize(1024 * 1024);
        server.setAutodetectUTF8(true);
        server.setControlKeepAliveTimeout(300);

        logger.info("Connection established.");
    }

    /**
     * Restablishes the Server connection.
     * @throws Exception Thrown if an exception is encountered during the reconnect.
     */
    public void reconnect() throws Exception {
        if (isConnected()) {
            disconnect();
        }
        logger.debug("Connection to server lost.  Reconnecting...");
        connect();
    }

    /**
     * Closes the Server connection.
     * @throws Exception Thrown if an exception is encountered during the disconnect operation.
     */
    public void disconnect() throws Exception {
        logger.info("Disconnecting from '" + address + "'...");
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
        // reconnect to the server to reset active/passive mode settings
        reconnect();

        List<FTPFile> retVal = new ArrayList<FTPFile>();

        server.enterLocalPassiveMode();

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
     * @param sourceFile The filename of the file to download.
     * @param destinationFile The file to which the downloaded file should be saved.
     * @return A value indicating whether the download completed successfully.
     * @throws Exception Thrown if an exception is encountered during the download.
     */
    public void download(String sourceFile, String destinationFile, Long size) throws Exception {
        // reconnect to the server to reset active/passive mode settings
        reconnect();

        File file = new File(sourceFile);
        currentDownload = file.getName();
        currentDownloadSize = size;
        currentDownloadPercent = 0.0;

        lastProgressUpdate = System.nanoTime();
        lastProgressTotal = 0L;

        logger.info("Retrieving file " + sourceFile);

        server.enterLocalActiveMode();

        FileOutputStream out = new FileOutputStream(destinationFile);
        server.retrieveFile(sourceFile, out);

        // force a final progress update
        lastProgressUpdate = 0L;
        progressUpdate(size, size.intValue(), 0L, true);

        logger.info("Transfer complete.");
        out.close();
    }

    /**
     * Downloads the specified file.
     * @param sourceFile The file to download.
     * @param destinationFile The file to which the downloaded file should be saved.
     * @return A value indicating whether the download completed successfully.
     * @throws Exception Thrown if an exception is encountered during the download.
     */
    public void download(String sourceFile, File destinationFile, Long size) throws Exception {
        download(sourceFile, destinationFile.getAbsolutePath(), size);
    }

    /**
     * Uploads the specified file.
     * @param sourceFile The filename of the file to upload.
     * @param destinationFile The destination filename.
     * @throws Exception Thrown if an exception is encountered during the upload.
     */
    public void upload(String sourceFile, String destinationFile) throws Exception {
        // reconnect to the server to reset active/passive mode settings
        reconnect();

        logger.info("Uploading file '" + sourceFile + "' to '" + destinationFile + "'...");

        FileInputStream inputStream = new FileInputStream(sourceFile);

        OutputStream outputStream = server.storeFileStream(destinationFile);

        byte[] bytesIn = new byte[4096];
        int read = 0;

        while ((read = inputStream.read(bytesIn)) != -1) {
            outputStream.write(bytesIn, 0, read);
        }

        inputStream.close();
        outputStream.close();

        server.completePendingCommand();

        logger.info("Upload complete.");
    }

    /**
     * Uploads the specified file.
     * @param sourceFile The file to upload.
     * @param destinationFile The destination filename.
     * @return A value indicating whether the upload completed successfully.
     * @throws Exception Thrown if an exception is encountered during the upload.
     */
    public void upload(File sourceFile, String destinationFile) throws Exception {
        upload(sourceFile.getAbsolutePath(), destinationFile);
    }

    /**
     * Configures and attaches a listener to the server's copy stream, so that downloads can be monitored
     * for progress.
     */
    private void configureStreamListener() {
        CopyStreamAdapter streamListener = new CopyStreamAdapter() {
            @Override
            public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
                progressUpdate(totalBytesTransferred, bytesTransferred, streamSize);
            }
        };

        server.setCopyStreamListener(streamListener);
    }

    /**
     * Report download progress.
     * @param totalBytesTransferred The total number of bytes transferred during the download.
     * @param bytesTransferred Bytes transferred during the immediate period.
     * @param streamSize The size of the stream.
     */
    private void progressUpdate(long totalBytesTransferred, int bytesTransferred, long streamSize) {
        progressUpdate(totalBytesTransferred, bytesTransferred, streamSize, false);
    }

    /**
     * Report download progress.
     * @param totalBytesTransferred The total number of bytes transferred during the download.
     * @param bytesTransferred Bytes transferred during the immediate period.
     * @param streamSize The size of the stream.
     * @param transferComplete Indicates whether the transfer is complete.
     */
    private void progressUpdate(long totalBytesTransferred, int bytesTransferred, long streamSize, boolean transferComplete) {
        Double currentDownloadPercent = totalBytesTransferred / currentDownloadSize.doubleValue();

        if (System.nanoTime() - lastProgressUpdate >= timeBetweenUpdates) {
            // calculate the number of seconds since the last update
            Long timeSinceLastUpdate = (System.nanoTime() - lastProgressUpdate) / 1000000000;
            // calculate the number of bytes transferred since the last update
            Long bytesSinceLastUpdate = totalBytesTransferred - lastProgressTotal;
            Double bytesPerSecond = bytesSinceLastUpdate / timeSinceLastUpdate.doubleValue();

            lastProgressUpdate = System.nanoTime();
            lastProgressTotal = totalBytesTransferred;

            String reportString = "Downloading '" + currentDownload + "'; " +
                    String.format("%.2f", totalBytesTransferred / 1024 / 1024.0) + " of " +
                    String.format("%.2f", currentDownloadSize / 1024 / 1024.0) +
                    " MB (" + String.format("%.2f", currentDownloadPercent * 100) + "%)";

            // report the transfer speed if it isn't complete
            if (!transferComplete) {
                reportString += ", " + String.format("%.2f", (bytesPerSecond / 1024 / 1024.0)) + " MB/sec";
            }

            logger.info(reportString);
        }
    }
}
