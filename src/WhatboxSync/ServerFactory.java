/****************************************************************************
 *
 * ServerFactory.java
 *
 * Generates Server instances based on a given Configuration.
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

/**
 * Generates Server instances based on a given Configuration.
 */
public class ServerFactory {
    /**
     * The logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

    /**
     * Initializes and returns a new Server instance using information contained within the specified Configuration.
     * @param config The Configuration from which the Server settings are taken.
     * @return The newly instantiated Server instance.
     */
    public static Server createServer(IConfiguration config) {
        if (!config.isValid()) {
            throw new RuntimeException("Unable to create Server; configuration is invalid.");
        }

        return new Server(config.getServer(), config.getUsername(), config.getPassword(), config.getPort());
    }
}
