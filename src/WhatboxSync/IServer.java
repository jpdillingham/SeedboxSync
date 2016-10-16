import java.util.List;
import java.util.concurrent.Future;
import java.io.File;

import org.apache.commons.net.ftp.FTPFile;

/**
 * Defines the interface for Server objects.
 */
interface IServer {
    /** Opens the Server connection. */
    void connect() throws Exception;

    /** Closes the Server connection. */
    void disconnect() throws Exception;

    /** Returns a value indicating whether the Server connection is open.
     * @return */
    Boolean isConnected();

    /** Lists the files in the specified directory. */
    List<FTPFile> list(String directory) throws Exception;

    /** Downloads the specified file. */
    Future<Boolean> download(String file, String destinationFile) throws Exception;
    Future<Boolean> download(String file, File destinationFile) throws Exception;

}
