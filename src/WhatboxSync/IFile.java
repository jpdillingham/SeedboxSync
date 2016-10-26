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

import java.sql.Timestamp;

/**
 * Represents a remote File.
 */
public interface IFile {
    /**
     * Gets the File's name.
     * @return The File's name.
     */
    String getName();

    /**
     * Gets the File's size.
     * @return The File's size.
     */
    Long getSize();

    /**
     * Gets the File's remote timestamp.
     * @return The File's remote timestamp.
     */
    Timestamp getTimestamp();

    /**
     * Gets the timestamp at which the File was added to the Database.
     * @return The timestamp at which the File was added to the Database.
     */
    Timestamp getAddedTimestamp();

    /**
     * Gets the timestamp at which the File was downloaded successfully.
     * @return The timestamp at which the File was downloaded successfully.
     */
    Timestamp getDownloadedTimestamp();

    /**
     * Gets a value indicating whether the File has been downloaded successfully.
     * @return A value indicating whether the File has been downloaded successfully.
     */
    Boolean isDownloaded();
}
