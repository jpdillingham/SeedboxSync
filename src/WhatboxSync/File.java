/****************************************************************************
 *
 * File.java
 *
 * Represents a remote File.
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

import java.util.Calendar;

/**
 * Represents a remote File.
 */
public class File implements IFile {
    /**
     * The name of the File.
     */
    private String name;

    /**
     * The size of the File.
     */
    private Long size;

    /**
     * The remote timestamp of the File.
     */
    private Calendar timestamp;

    /**
     * The timestamp at which the File was added to the Database.
     */
    private Calendar added;

    /**
     * The timestamp at which the File was downloaded successfully.
     */
    private Calendar downloaded;

    /**
     * Initializes a new instance of the File class with the specified name, size and timestamp.
     * @param name The name of the File.
     * @param size The size of the File.
     * @param timestamp The remote timestamp of the File.
     */
    public File(String name, Long size, Calendar timestamp) { this(name, size, timestamp, null, null); }

    /**
     * Initializes a new instance of the File class.
     * @param name The name of the File.
     * @param size The size of the File.
     * @param timestamp The remote timestamp of the File.
     * @param added The timestamp at which the File was added to the Database.
     * @param downloaded The timestamp at which the File was downloaded successfully.
     */
    public File(String name, Long size, Calendar timestamp, Calendar added, Calendar downloaded) {
        this.name = name;
        this.size = size;
        this.timestamp = timestamp;
        this.added = added;
        this.downloaded = downloaded;
    }

    /**
     * Gets the File's name.
     * @return The File's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the File's size.
     * @return The File's size.
     */
    public Long getSize() {
        return size;
    }

    /**
     * Gets the File's remote timestamp.
     * @return The File's remote timestamp.
     */
    public Calendar getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the timestamp at which the File was added to the Database.
     * @return The timestamp at which the File was added to the Database.
     */
    public Calendar getAdded() { return added; }

    /**
     * Gets the timestamp at which the File was downloaded successfully.
     * @return The timestamp at which the File was downloaded successfully.
     */
    public Calendar getDownloaded() { return downloaded; }

    /**
     * Gets a value indicating whether the File has been downloaded successfully.
     * @return A value indicating whether the File has been downloaded successfully.
     */
    public Boolean isDownloaded() {
        return (downloaded != null);
    }
}
