/****************************************************************************
 *
 * IConfigurationLoader.java
 *
 * Defines the interface for ConfigurationLoader objects.
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

import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 * Defines the interface for ConfigurationLoader objects.
 */
interface IConfigurationLoader {
    /**
     * Instantiates and loads a Configuration from the specified file.
     * @param file The file from which the configuration is to be loaded.
     * @return The loaded Configuration instance.
     * @throws IOException Thrown if there is an issue locating or reading the file.
     * @throws ParseException Thrown if the file can't be deserialized to JSON.
     */
    Configuration Load(String file) throws IOException, ParseException;
}
