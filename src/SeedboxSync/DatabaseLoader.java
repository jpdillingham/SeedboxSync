/****************************************************************************
 *
 * DatabaseLoader.java
 *
 * Loads the application Database from disk, or, in the absence of an existing
 * Database file, creates it and populates the schema.
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

import java.sql.SQLException;

/**
 * Loads the application Database from disk, or, in the absence of an existing
 * Database file, creates it and populates the schema.
 */
public class DatabaseLoader {
    /**
     * The logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

    /**
     * Instantiates and loads a Database from the specified file.
     * @param config The Configuration for the application.
     * @return The loaded Database instance.
     * @throws SQLException Thrown if an exception is encountered while instantiating the databse.
     */
    public static Database load(Configuration config) throws Exception {
        if (config == null) {
            throw new Exception("Unable to create Database; provided configuration is null.");
        }

        logger.debug("Loading database from '" + config.getDatabaseFilename() + "'...");

        if (config.isValid()) {
            Database retVal = new Database(config.getDatabaseFilename());

            logger.debug("Database loaded successfully.");
            return retVal;
        }
        else {
            throw new Exception("Invalid configuration; " + config.getValidationMessage());
        }
    }
}
