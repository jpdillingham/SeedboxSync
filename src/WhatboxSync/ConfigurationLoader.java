/****************************************************************************
 *
 * ConfigurationLoader.java
 *
 * Loads the application Configuration from disk.
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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Loads the application Configuration from disk.
 */
public class ConfigurationLoader implements IConfigurationLoader {
    /**
     * The logger for this class.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * Instantiates and loads a Configuration from the specified file.
     * @param file The file from which the configuration is to be loaded.
     * @return The loaded Configuration instance.
     * @throws IOException Thrown if there is an issue locating or reading the file.
     * @throws ParseException Thrown if the file can't be deserialized to JSON.
     */
    public Configuration load(String file) throws IOException, ParseException {
        if (!(new File(file)).exists()) {
            throw new FileNotFoundException("File not found: '" + file + "'");
        }

        JSONParser parser = new JSONParser();
        Object obj;

        try {
            obj = parser.parse(new FileReader(file));
        }
        catch (ParseException ex) {
            logger.error("File parsing error: " + ex.getMessage());
            throw ex;
        }

        JSONObject jsonObject = (JSONObject)obj;

        String server = this.fetch("server", jsonObject);
        Integer port = this.<Long>fetch("port", jsonObject).intValue();
        String username = this.fetch("username", jsonObject);

        String password = this.fetch("password", jsonObject);
        Integer interval = this.<Long>fetch("interval", jsonObject).intValue();
        String remoteDirectory = this.fetch("remoteDirectory", jsonObject);
        String localDirectory = this.fetch("localDirectory", jsonObject);

        return new Configuration(server, port, username, password, interval, remoteDirectory, localDirectory);
    }

    /**
     * Fetches the specified field from the specified JSONObject, or throws a RuntimeException if the fetch fails.
     * @param fieldName The name of the field to fetch.
     * @param object The object from which the field is to be fetched.
     * @param <T> The expected type of the field's value.
     * @return The fetched value.
     * @throws RuntimeException Thrown if the field can't be fetched, or if the value is null.
     */
    @SuppressWarnings("unchecked")
    private <T> T fetch(String fieldName, JSONObject object) throws RuntimeException {
        T retVal = (T)object.get(fieldName);

        if (retVal == null) {
            throw new RuntimeException("Bad configuration; field '" + fieldName + "' is missing or contains an invalid value.");
        }

        return retVal;
    }
}
