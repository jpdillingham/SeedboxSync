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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

/** Loads the application Configuration from disk. */
public class ConfigurationLoader {
    /** The logger for this class. */
    private Logger logger = LoggerFactory.getLogger(Server.class);


    public Configuration Load(String file) throws IOException, ParseException {
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

        String server = this.<String>fetch("server", jsonObject);
        Integer port = this.<Long>fetch("port", jsonObject).intValue();
        String username = this.<String>fetch("username", jsonObject);

        String password = this.<String>fetch("password", jsonObject);
        Integer interval = this.<Long>fetch("interval", jsonObject).intValue();
        String remoteDirectory = this.<String>fetch("remoteDirectory", jsonObject);
        String localDirectory = this.<String>fetch("localDirectory", jsonObject);

        return new Configuration(server, port, username, password, interval, remoteDirectory, localDirectory);
    }

    @SuppressWarnings("unchecked")
    private <T> T fetch(String fieldName, JSONObject object) {
        T retVal = (T)object.get(fieldName);

        if (retVal == null) {
            badConfiguration(fieldName);
        }

        return retVal;
    }

    private void badConfiguration(String fieldName) {
        throw new RuntimeException("Bad configuration; field '" + fieldName + "' is missing or contains an invalid value.");
    }
}
