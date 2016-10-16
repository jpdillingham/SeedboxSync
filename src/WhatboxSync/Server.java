import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Future;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

/**
 * Represents an FTP server.
 */
public class Server implements IServer {
    /** The logger for this class. */
    Logger logger = LoggerFactory.getLogger(Server.class);

    /** The default port. */
    private static final Integer defaultPort = 21;

    /** The FTPClient driving the server */
    private static FTPClient server;

    /** The Server address. */
    private String address;

    /** The Server port. */
    private Integer port;

    /** The username to use when connecting to the Server. */
    private String username;

    /** The password to use when connecting to the Server. */
    private String password;

    /** Initializes a new instance of the Server class with the specified IP address, username and password.
     * @param address The Server address.
     * @param username The username to use when connecting to the Server.
     * @param password The password to use when connecting to the Server.
     */
    Server(String address, String username, String password) {
        this(address, username, password, defaultPort);
    }

    /** Initializes a new instance of the Server class with the specified IP address, username, password and port.
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

    /** Gets the Server address.
     * @return The Server address.
     */
    public String getAddress() {
        return address;
    }

    /** Gets the username used when connecting to the Server.
     * @return The username used when connecting to the Server.
     */
    public String getUsername() {
        return username;
    }

    /** Gets the password used when connecting to the Server.
     * @return The password used when connecting to the Server.
     */
    public String getPassword() {
        return password;
    }

    /** Gets the Server port.
     * @return The Server port.
     */
    public Integer getPort() {
        return port;
    }

     /** Opens the Server connection using the specified IP address and the default port. */
    public void connect() throws Exception {
        try {
            server.connect(address, port);
            server.login(username, password);
        }
        catch (Exception ex) {
            logger.error("Exception thrown while connecting to server at '" + address + ":" + port + "': " + ex.getMessage());
            throw ex;
        }
    }

    /** Closes the Server connection. */
    public void disconnect() throws Exception {
        try {
            server.disconnect();
        }
        catch (Exception ex) {
            logger.error("Exception thrown while disconnecting from server: " + ex.getMessage());
            throw ex;
        }
    }

    /** Returns a value indicating whether the Server is connected.
     * @return A value indicating whether the Server is connected.
     */
    public Boolean isConnected() {
        return true;
    }

    /** Returns a list of files contained within the specified directory.
     * @param directory The directory for which to return the file list.
     * @return A list of files contained within the directory.
     */
    public List<FTPFile> list(String directory) throws Exception {
        List<FTPFile> retVal = new ArrayList<FTPFile>();

        try {
            FTPFile[] files = server.listFiles(directory);

            for (FTPFile f : files) {
                retVal.add(f);
            }
        }
        catch (Exception ex)
        {
            logger.error("Exception thrown while retrieving file list for directory '" + directory + "': " + ex.getMessage());
            throw ex;
        }

        return retVal;
    }

    /** Downloads the specified file.
     * @param file The file to download.
     * @return A value indicating whether the download completed successfully.
     * @throws Exception Thrown if an exception is encountered during the download.
     */
    @Async
    public Future<Boolean> download(String file) throws Exception {
        // TODO: Implement this
        return new AsyncResult<Boolean>(true);
    }
}
