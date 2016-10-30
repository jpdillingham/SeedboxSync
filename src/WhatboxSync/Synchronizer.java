import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.util.List;
import java.sql.Timestamp;

/**
 * Created by JP on 10/8/2016.
 */
public class Synchronizer implements ISynchronizer {
    /**
     * The logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

    /** The configuration for the Synchronizer. */
    private Configuration config;

    private IServer server;

    private IDatabase database;

    private Boolean synchronizing;

    /** Initializes a new instance of the Synchronizer class with the specified Configuration.
     * @param config The Configuration instance with which the Synchronizer should be configured. */
    public Synchronizer(Configuration config, IServer server, IDatabase database) {
        this.config = config;
        this.server = server;
        this.database = database;
    }

    public void synchronize() throws Exception {
        logger.info("Connecting to server '" + config.getServer() + "' on port " + config.getPort() + "...");

        if (!server.isConnected()) {
            try {
                server.connect();
            }
            catch (Exception ex)
            {
                logger.error("Exception thrown while connecting to server '" + config.getServer() + "': " + ex.getMessage());
            }
        }

        list(config.getRemoteDownloadDirectory());
    }


    private void list(String directory) throws Exception {
        logger.info("Listing files for directory '" + directory + "'...");

        List<FTPFile> files;

        try {
            files = server.list(directory);
        }
        catch (Exception ex)
        {
            logger.error("Exception thrown while retrieving file list for '" + directory + "': " + ex.getMessage());
            throw ex;
        }

        for (FTPFile file : files) {
            try {
                database.addFile(new File(directory + "/" + file.getName(), file.getSize(), new Timestamp(0L)));
                //server.download(directory + "/" + file.getName(), file.getName());
            }
            catch (SQLException ex) {

            }

            if (file.isDirectory()) {
                logger.info("Recursing directory '" + file.getName());
                list(directory + "/" + file.getName());
            }
        }
    }
}
