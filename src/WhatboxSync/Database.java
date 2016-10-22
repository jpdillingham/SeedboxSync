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

        createConnection();
    }

    /**
     * Closes the SQLite database connection.
     * @throws SQLException Thrown if an exception is encountered while closing the connection.
     */
    public void close() throws SQLException {
        connection.close();
    }

    /**
     * Establishes the SQLite database connection.
     * @throws SQLException Thrown if an exception is encountered while establishing the connection.
     */
    private void createConnection() throws SQLException {
        logger.info("Attempting to connect to database in '" + file + "'...");
        connection = DriverManager.getConnection("jdbc:sqlite:" + file);
        logger.info("Database connection established.");
    }
}