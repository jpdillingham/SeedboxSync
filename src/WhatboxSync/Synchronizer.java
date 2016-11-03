import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.util.List;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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

    private Uploader uploader;

    private Downloader downloader;

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

        uploader = new Uploader(server, config.getLocalUploadDirectory(), config.getRemoteUploadDirectory());
        downloader = new Downloader(server, config.getLocalDownloadDirectory(), config.getRemoteDownloadDirectory(), database);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                try {
                    uploader.process();
                }
                catch (Exception ex) {
                    logger.error("Exception in Uploader: " + ex.getMessage());
                }

                try {
                    downloader.process();
                }
                catch (Exception ex) {
                    logger.error("Exception in Downloader: " + ex.getMessage());
                }
            }
        }, 0, 5000);
    }
}
