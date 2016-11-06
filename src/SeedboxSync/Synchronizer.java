/****************************************************************************
 *
 * Synchronizer.java
 *
 * Coordinates the synchronization of the local and remote directories.
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Coordinates the synchronization of the local and remote directories.
 */
public class Synchronizer implements ISynchronizer {
    /**
     * The logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

    /**
     * The configuration for the Synchronizer.
     */
    private Configuration configuration;

    /**
     * The IServer instance for the Synchronizer.
     */
    private IServer server;

    /**
     * The IDatabase instance for the Synchronizer.
     */
    private IDatabase database;

    /**
     * The Uploader for the Synchronizer.
     */
    private Uploader uploader;

    /**
     * The Downloader for the Synchronizer.
     */
    private Downloader downloader;

    /** Initializes a new instance of the Synchronizer class with the specified Configuration.
     * @param configuration The Configuration instance with which the Synchronizer should be configured.
     * @param server The IServer instance for the Synchronizer.
     * @param database The IDatabase instance for the Synchronizer.
     */
    public Synchronizer(Configuration configuration, IServer server, IDatabase database) {
        this.configuration = configuration;
        this.server = server;
        this.database = database;

        uploader = new Uploader(server, configuration.getLocalUploadDirectory(), configuration.getRemoteUploadDirectory());
        downloader = new Downloader(server, configuration.getLocalDownloadDirectory(), configuration.getRemoteDownloadDirectory(), database);
    }

    /**
     * Synchronizes the local and remote directories.
     * @throws Exception Thrown if an exception is encountered during the synchronization.
     */
    public void synchronize() throws Exception {
        uploader.process();
        downloader.process();
    }
}
