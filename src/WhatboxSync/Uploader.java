/****************************************************************************
 *
 * Uploader.java
 *
 * Coordinates the uploading of files to the remote FTP server.
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
 * Coordinates the uploading of files to the remote FTP server.
 */
public class Uploader {
    private IServer server;
    private Queue<String> queue;

    /**
     * Initializes a new instance of the Uploader class.
     * @param server The IServer instance providing the upload functionality.
     */
    public Uploader(IServer server) {
        this.server = server;
        this.queue = new LinkedList<String>();
    }

    /**
     * Adds the specified file to the queue.
     * @param file The file to add.
     */
    public void enqueue(String file) {
        queue.add(file);
    }

    /**
     * Removes the specified file from the queue.
     * @param file The file to remove.
     */
    public void dequeue(String file) {
        queue.remove(file);
    }

    /**
     * Gets the list of queued files to be uploaded.
     * @return The list of queued files to be uploaded.
     */
    public List<String> getQueue() {
        return Collections.unmodifiableList((List)queue);
    }
}
