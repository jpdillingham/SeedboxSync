/****************************************************************************
 *
 * IConfiguration.java
 *
 * Defines the interface for Configuration objects.
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

 package WhatboxSync;

/**
 * Defines the interface for Configuration objects.
 */
public interface IConfiguration {
    /** Gets the configured server address.
     * @return The configured server address. */
    String getServer();

    /** Gets the configured server port.
     * @return The configured server port. */
    Integer getPort();

    /** Gets the configured username for the server.
     * @return The configured username for the server. */
    String getUsername();

    /** Gets the configured password for the server.
     * @return The configured password for the server. */
    String getPassword();

    /** Gets the configured interval on which the synchronization should take place.
     * @return The configured interval on which the synchronization should take place. */
    Integer getInterval();

    /** Gets the remote directory which is to be synchronized.
     * @return The remote directory which is to be synchronized. */
    String getRemoteDirectory();

    /** Gets the local directory to which files are to be downloaded.
     * @return The local directory to which files are to be downloaded. */
    String getLocalDirectory();
}
