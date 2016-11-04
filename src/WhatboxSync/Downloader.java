/****************************************************************************
 *
 * Downloader.java
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

import java.util.List;
import java.nio.file.Paths;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.net.ftp.FTPFile;

/**
 * Coordinates the downloading of files.
 */
public class Downloader extends Processor {
    /**
     * The logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

    /**
     * The IDatabase instance in which to store file information.
     */
    private IDatabase database;

    /**
     * Initializes a new instance of the Downloader class with the specified server and directories.
     * @param server
     * @param localDirectory
     * @param remoteDirectory
     */
    public Downloader(IServer server, String localDirectory, String remoteDirectory, IDatabase database) {
        super(server, localDirectory, remoteDirectory);

        this.database = database;
    }

    /**
     * Scans the remote download directory and enqueues new files, then uploads the next file in the queue, if applicable.
     */
    public void process() throws Exception {
        scanDirectory(remoteDirectory);
        download();
    }

    private void scanDirectory(String directory) throws Exception {
        logger.debug("Listing files for directory '" + directory + "'...");

        List<FTPFile> files;

        try {
            files = server.list(directory);
        }
        catch (Exception ex)
        {
            logger.error("Exception thrown while retrieving file list for '" + directory + "': " + ex.getMessage());
            throw ex;
        }

        logger.debug("Listing complete.  " + files.size() + " " + (files.size() == 1 ? "record" : "records") + " retrieved.");

        for (FTPFile file : files) {
            if (!file.isDirectory()) {
                String fullFileName = directory + "/" + file.getName();
                String relativeFileName = fullFileName.replace(remoteDirectory, "");
                String relativeFileNameWithSize = relativeFileName + ":" + file.getSize();

                // enqueue the file for downloading only if it doesn't exist in the database
                if (database.getFile(relativeFileName) == null) {
                    if (!queue.contains(relativeFileNameWithSize)) {
                        enqueue(relativeFileNameWithSize);
                        logger.info("Added file '" + relativeFileNameWithSize + "' to the download queue.");
                    }
                }
            }
            // if the file is a directory, recursively list the files within it
            else {
                scanDirectory(directory + "/" + file.getName());
            }
        }
    }

    /**
     * Downloads the next file in the queue, if a download is not already in progress.
     * @throws Exception Thrown if an exception is encountered during the download.
     */
    private void download() throws Exception {
        if (transferInProgress) {
            logger.debug("Download is already in progress.  Skipping.");
        }
        else {
            if (!queue.isEmpty()) {
                transferInProgress = true;

                logger.debug("Preparing to download " + queue.peek());

                // fetch the next file from the queue
                String file = queue.peek();

                // split the queue entry and retrieve the filename and size
                String[] fileParts = file.split(":");
                String fileName = fileParts[0];
                Long fileSize = Long.parseLong(fileParts[1]);

                String remoteFileName = remoteDirectory + fileName;
                String localFileName = localDirectory + fileName;

                logger.debug("Remote filename: " + remoteFileName);
                logger.debug("Local filename: " + localFileName);

                try {
                    // create the target directory structure
                    (new java.io.File(localFileName)).getParentFile().mkdirs();

                    logger.info("Downloading file '" + fileName + "' from remote directory '" + remoteDirectory + "'...");

                    server.download(remoteFileName, localFileName, fileSize);

                    logger.debug("Transfer complete.");

                    File newFile = new File(fileName, fileSize, new Timestamp(System.currentTimeMillis()));
                    database.addFile(newFile);
                    database.setDownloadedTimestamp(newFile);

                    logger.debug("File '" + fileName + "' added to the completed file database.");

                    dequeue(file);

                    logger.debug("File '" + fileName + "' removed from the download queue.");

                    logger.info("Download complete.");
                }
                catch (Exception ex) {
                    logger.error("Error downloading '" + fileName + "': " + ex.getMessage());
                }
                finally {
                    transferInProgress = false;
                }
            }

            if (!queue.isEmpty()) {
                download();
            }
        }
    }
}
