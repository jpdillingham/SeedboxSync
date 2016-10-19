/****************************************************************************
 *
 * IServerFactory.java
 *
 * Defines the interface for ServerFactory objects.
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

/**
 * Defines the interface for ServerFactory objects.
 */
public interface IServerFactory {
    /**
     * Initializes and returns a new Server instance using information contained within the specified Configuration.
     * @param config The Configuration from which the Server settings are taken.
     * @return The newly instantiated Server instance.
     */
    Server createServer(IConfiguration config);
}
