/****************************************************************************
 *
 * Processor.java
 *
 * Coordinates the processing of files.
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

import java.util.Collections;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

/**
 * Coordinates the processing of files.
 */
public abstract class Processor {
    /**
     * The IServer instance enabling file transfers.
     */
    protected IServer server;

    /**
     * The local directory to/from which files are transferred.
     */
    protected String localDirectory;

    /**
     * The remote directory to/from which files are transferred.
     */
    protected String remoteDirectory;

    /**
     * The file queue.
     */
    protected Queue<String> queue;

    /**
     * A value indicating whether a transfer is currently in progress.
     */
    protected Boolean transferInProgress;

    /**
     * Initializes a new instance of the Processor class.
     * @param server The IServer instance enabling file transfers.
     * @param localDirectory The local directory to/from which files are transferred.
     * @param remoteDirectory The remote directory to/from which files are transferred.
     */
    public Processor(IServer server, String localDirectory, String remoteDirectory) {
        this.server = server;
        this.localDirectory = localDirectory;
        this.remoteDirectory = remoteDirectory;
        this.queue = new LinkedList<String>();

        transferInProgress = false;
    }

    /**
     * Adds the specified file to the queue.
     * @param file The file to add.
     */
    public void enqueue(String file) {
        if (!queue.contains(file)) {
            queue.add(file);
        }
    }

    /**
     * Removes the specified file from the queue.
     * @param file The file to remove.
     */
    public void dequeue(String file) {
        if (queue.contains(file)) {
            queue.remove(file);
        }
    }

    /**
     * Gets the list of queued files to be uploaded.
     * @return The list of queued files to be uploaded.
     */
    public List<String> getQueue() {
        return Collections.unmodifiableList((List)queue);
    }

    /**
     * Processes.
     */
    public abstract void process() throws Exception;
}
