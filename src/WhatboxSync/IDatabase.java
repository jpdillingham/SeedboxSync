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

import java.sql.SQLException;

/**
 * Defines the itnerface for Database objects.
 */
public interface IDatabase {
    /**
     * Closes the SQLite database connection.
     * @throws SQLException Thrown if an exception is encountered while closing the connection.
     */
    void close() throws SQLException;
}
