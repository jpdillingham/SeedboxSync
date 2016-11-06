/****************************************************************************
 *
 * Uploader.java
 *
 * Coordinates the uploading of files.
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

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Coordinates the uploading of files.
 */
public class Uploader extends Processor {
    /**
     * The logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

    /**
     * Initializes a new instance of the Uploader class with the specified server and directories.
     * @param server
     * @param localDirectory
     * @param remoteDirectory
     */
    public Uploader(IServer server, String localDirectory, String remoteDirectory) {
        super(server, localDirectory, remoteDirectory);
    }

    /**
     * Scans the local upload directory and enqueues new files, then uploads the next file in the queue, if applicable.
     */
    public void process() throws Exception {
        logger.info("Processing uploads...");

        scanDirectory();

        if (queue.size() > 0) {
            logger.info("Processing upload queue of " + queue.size() + " file(s)...");

            upload();

            logger.info("Upload queue processed.");
        }

        logger.info("Uploads processed.");
    }

    /**
     * Scans the local upload directory for new files and adds them to the queue if not already present.
     */
    private void scanDirectory() {
        File dir = new File(localDirectory);

        logger.info("Scanning local upload directory '" + localDirectory + "'...");

        if (!dir.exists()) {
            logger.warn("Directory '" + localDirectory + "' does not exist.  Aborting scan.");
        }
        else {
            for (java.io.File file : dir.listFiles()) {
                if (file.isFile() && (!file.getName().contains("[Uploaded]"))) {
                    if (!queue.contains(file.getAbsolutePath())) {
                        logger.info("Adding file '" + file.getAbsolutePath() + "' to the uploader queue.");
                        enqueue(file.getAbsolutePath());
                    }
                }
            }
        }

        logger.info("Scan complete.");
    }

    /**
     * Uploads the next file in the queue, if a transfer is not already in progress.
     * @throws Exception Thrown if an exception is encountered during the upload.
     */
    private void upload() throws Exception {
        if (!queue.isEmpty()) {
            transferInProgress = true;

            File file = new File(queue.peek());

            logger.info("Uploading file '" + file.getName() + "' to remote directory '" + remoteDirectory + "'...");

            try {
                server.upload(file, remoteDirectory + "/" + file.getName());

                logger.info("Transfer complete.");

                File newName = new File(file.getParent() + "/[Uploaded] " + file.getName());

                logger.info("Renaming file to '" + newName.getAbsolutePath() + "'...");

                if (file.renameTo(newName)) {
                    logger.info("Rename successful.  Removing file from the queue...");
                    queue.remove(file.getAbsolutePath());
                } else {
                    logger.warn("Rename failed.  The file will remain in the queue.");
                }

                dequeue(file.getName());

                logger.info("Upload complete.");

                upload();
            }
            catch (Exception ex) {
                logger.error("Error uploading '" + file.getName() + "': " + ex.getMessage());
            }
            finally {
                transferInProgress = false;
            }
        }
    }
}
