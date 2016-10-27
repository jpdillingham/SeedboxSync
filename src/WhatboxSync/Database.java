/****************************************************************************
 *
 * Database.java
 *
 * Represents the application Database.
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
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Represents the application Database.
 */
public class Database implements IDatabase {
    /**
     * The logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

    /**
     * The file backing the Database.
     */
    private String file;

    /**
     * The SQLite database connection.
     */
    private Connection connection;

    /**
     * Initializes a new instance of the Database class with the specified file.
     * @param file
     * @throws SQLException Thrown if the database can't be initialized.
     */
    public Database(String file) throws SQLException {
        this.file = file;

        logger.info("Establishing database connection to '" + file + "'...");

        createConnection();

        logger.info("Connection established successfully.");
        logger.info("Verifying schema...");

        if (!schemaExists()) {
            logger.info("Application schema is missing from '" + file + "'... creating...");

            initializeSchema();

            logger.info("Schema created successfully.");
        }

        logger.info("Schema verified; database connection is ready.");
    }

    /**
     * Retrieves the record matching the specified File.
     * @param file The File to retrieve.
     * @return The retrieved record.
     * @throws SQLException Thrown if an exception is encountered while retrieving the record.
     */
    public File getFile(File file) throws SQLException {
        return getFile(file.getName());
    }

    /**
     * Retrieves the record matching the specified name.
     * @param fileName The name of the File to retrieve.
     * @return The record matching the specified name.
     * @throws SQLException Thrown if an exception is encountered while retrieving the record.
     */
    public File getFile(String fileName) throws SQLException {
        logger.info("Fetching list of files from the database...");

        String query = "SELECT Name, Size, Timestamp, AddedTimestamp, DownloadedTimestamp FROM Files " +
                "WHERE Name = '" + fileName + "'";

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            String name = result.getString("Name");
            Long size = result.getLong("Size");
            Timestamp timestamp = result.getTimestamp("Timestamp");
            Timestamp addedTimestamp = result.getTimestamp("AddedTimestamp");
            Timestamp downloadedTimestamp = result.getTimestamp("DownloadedTimestamp");

            return new File(name, size, timestamp, addedTimestamp, downloadedTimestamp);
        }

        return null;
    }

    /**
     * Gets the list of Files stored in the database.
     * @return The list of Files.
     * @throws SQLException Thrown if an exception is encountered while retrieving the list.
     */
    public List<File> getFiles() throws SQLException {
        logger.info("Fetching list of files from the database...");

        List<File> retVal = new ArrayList<File>();

        String query = "SELECT Name, Size, Timestamp, AddedTimestamp, DownloadedTimestamp FROM Files";

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            String name = result.getString("Name");
            Long size = result.getLong("Size");
            Timestamp timestamp = result.getTimestamp("Timestamp");
            Timestamp addedTimestamp = result.getTimestamp("AddedTimestamp");
            Timestamp downloadedTimestamp = result.getTimestamp("DownloadedTimestamp");

            retVal.add(new File(name, size, timestamp, addedTimestamp, downloadedTimestamp));
        }

        return retVal;
    }

    /**
     * Adds the specified File to the database.
     * @param file The File to add.
     * @throws SQLException Thrown if an exception is encountered while adding the record.
     */
    public void addFile(File file) throws SQLException {
        logger.info("Adding file '" + file.getName() + "' to database...");

        String query = "INSERT INTO Files (Name, Size, Timestamp, AddedTimestamp)" +
                " VALUES('" + file.getName() + "', " +
                file.getSize() + ", '" + file.getTimestamp() + "', '" +
                new Timestamp((new Date()).getTime()).toString() + "')";

        Statement statement = connection.createStatement();

        try {
            statement.executeUpdate(query);
        }
        catch (SQLException ex) {
            if (ex.getErrorCode() == 19) {
                // constraint violated, reasonably certain it is the unique constraint on name.  ignore it.
            }
            else {
                throw ex;
            }
        }

        statement.close();
    }

    /**
     * Sets the downloaded column of the specified File to the current timestamp, indicating that the file
     * was successfully downloaded.
     * @param file The File to update.
     * @throws SQLException Thrown if an exception is encountered while updating the record.
     */
    public void setDownloadedTimestamp(File file) throws SQLException {
        setDownloadedTimestamp(file.getName());
    }

    /**
     * Sets the downloaded column of the record matching the specified name to the current timestamp,
     * indicating that the file was successfully downloaded.
     * @param name The name of the File to update.
     * @throws SQLException Thrown if an exception is encountered while updating the record.
     */
    public void setDownloadedTimestamp(String name) throws SQLException {
        logger.info("Updating download timestamp for file '" + name + "'...");

        String query = "UPDATE Files SET DownloadedTimestamp = '"  +
                new Timestamp((new Date()).getTime()).toString() + "' WHERE Name = '" +
                name + "'";

        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }

    /**
     * Closes the database connection.
     * @throws SQLException Thrown if an exception is encountered while closing the connection.
     */
    public void close() throws SQLException {
        connection.close();
    }

    /**
     * Returns a value indicating whether the schema exists within the database.
     * @return A value indicating whether the schema exists within the database.
     * @throws SQLException Thrown if an exception is encountered while querying the schema.
     */
    private Boolean schemaExists() throws SQLException {
        Boolean retVal = false;
        String query = "SELECT count(*) FROM sqlite_master WHERE type='table' AND name='Files'";

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);

        result.next();

        if (result.getInt(1) == 1) {
            retVal = true;
        }
        else {
            retVal = false;
        }

        return retVal;
    }

    /**
     * Initializes the database schema.
     * @throws SQLException Thrown if an exception is encountered while creating the schema.
     */
    private void initializeSchema() throws SQLException {
        String query = "CREATE TABLE Files (" +
                "Name TEXT PRIMARY KEY NOT NULL, " +
                "Size BIGINT NOT NULL, " +
                "Timestamp DATETIME NOT NULL, " +
                "AddedTimestamp DATETIME NOT NULL, " +
                "DownloadedTimestamp DATETIME)";

        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }

    /**
     * Establishes the database connection.
     * @throws SQLException Thrown if an exception is encountered while establishing the connection.
     */
    private void createConnection() throws SQLException {
        logger.info("Attempting to connect to database in '" + file + "'...");
        connection = DriverManager.getConnection("jdbc:sqlite:" + file);
        logger.info("Database connection established.");
    }
}
