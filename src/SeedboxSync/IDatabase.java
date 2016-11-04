/****************************************************************************
 *
 * IDatabase.java
 *
 * Defines the interface for Database objects.
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

import java.sql.SQLException;

/**
 * Defines the interface for Database objects.
 */
public interface IDatabase {
    /**
     * Retrieves the record matching the specified File.
     * @param file The File to retrieve.
     * @return The retrieved record.
     * @throws SQLException Thrown if an exception is encountered while retrieving the record.
     */
    File getFile(File file) throws SQLException;

    /**
     * Retrieves the record matching the specified name.
     * @param fileName The name of the File to retrieve.
     * @return The record matching the specified name.
     * @throws SQLException Thrown if an exception is encountered while retrieving the record.
     */
    File getFile(String fileName) throws SQLException;

    /**
     * Gets the list of Files stored in the database.
     * @return The list of Files.
     * @throws SQLException Thrown if an exception is encountered while retrieving the list.
     */
    List<File> getFiles() throws SQLException;

    /**
     * Adds the specified File to the database.
     * @param file The File to add.
     * @throws SQLException Thrown if an exception is encountered while adding the record.
     */
    void addFile(File file) throws SQLException;

    /**
     * Sets the downloaded column of the specified File to the current timestamp, indicating that the file
     * was successfully downloaded.
     * @param file The File to update.
     * @throws SQLException Thrown if an exception is encountered while updating the record.
     */
    void setDownloadedTimestamp(File file) throws SQLException;

    /**
     * Sets the downloaded column of the record matching the specified name to the current timestamp,
     * indicating that the file was successfully downloaded.
     * @param name The name of the File to update.
     * @throws SQLException Thrown if an exception is encountered while updating the record.
     */
    void setDownloadedTimestamp(String name) throws SQLException;

    /**
     * Closes the database connection.
     * @throws SQLException Thrown if an exception is encountered while closing the connection.
     */
    void close() throws SQLException;

}
