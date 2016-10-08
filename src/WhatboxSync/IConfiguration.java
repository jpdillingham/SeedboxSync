/**
 * IConfiguration.java
 *
 * Defines the interface for Configuration objects.
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
 */
 package WhatboxSync;

/**
 * Defines the interface for Configuration objects.
 */
public interface IConfiguration {
    /** Gets the configured Server address. */
    String getServer();
    /** Gets the configured Server Port. */
    Integer getPort();
    /** Gets the configured Server Username. */
    String getUsername();
    /** Gets the configured Password for the configured Username. */
    String getPassword();
    /** Gets the configured Remote Directory on the Server which is to be synchronized. */
    String getRemoteDirectory();
    /** Gets the configured Local Directory to which files are to be downloaded. */
    String getLocalDirectory();
}
