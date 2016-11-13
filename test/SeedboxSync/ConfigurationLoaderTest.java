/****************************************************************************
 *
 * ConfigurationLoaderTest.java
 *
 * Tests the ConfigurationLoader class.
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
import java.io.FileNotFoundException;

import org.json.simple.parser.ParseException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;

/**
 * Tests the ConfigurationLoader class.
 */
public class ConfigurationLoaderTest extends BaseTest {
    /**
     * The temporary folder for the class.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * Constructs an instance of ConfigurationLoader.
     */
    @Test
    public void testConstructor() {
        try {
            begin();

            ConfigurationLoader test = new ConfigurationLoader();
        }
        finally {
            end();
        }
    }

    /**
     * Loads a known good configuration file.
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testGoodLoad() throws IOException, ParseException {
        try {
            begin();

            java.io.File downloadDir = folder.newFolder("download");
            java.io.File uploadDir = folder.newFolder("upload");


            String configFile = System.getProperty("user.dir") + "/test/SeedboxSync/resources/goodConfig.json";
            Configuration config = ConfigurationLoader.load(configFile);

            config.setLocalDownloadDirectory(downloadDir.getAbsolutePath());
            config.setLocalUploadDirectory(uploadDir.getAbsolutePath());

            assertEquals(config.getServer(), "server");
            assertEquals(config.getPort(), (Integer) 1);
            assertEquals(config.getUsername(), "username");
            assertEquals(config.getPassword(), "password");
            assertEquals(config.getInterval(), (Integer) 3600);
            assertEquals(config.getRemoteDownloadDirectory(), "remoteDownloadDirectory");
            assertEquals(config.getLocalDownloadDirectory(), downloadDir.getAbsolutePath());
            assertEquals(config.getRemoteUploadDirectory(), "remoteUploadDirectory");
            assertEquals(config.getLocalUploadDirectory(), uploadDir.getAbsolutePath());
            assertEquals(config.isValid(), true);
        }
        finally {
            end();
        }
    }

    /**
     * Loads a known bad configuration file.
     * @throws IOException
     * @throws ParseException
     */
    @Test(expected=ParseException.class)
    public void testBadLoad() throws IOException, ParseException {
        try {
            begin();

            String configFile = System.getProperty("user.dir") + "/test/SeedboxSync/resources/badConfig.json";
            Configuration config = ConfigurationLoader.load(configFile);
        }
        finally {
            end();
        }
    }

    /**
     * Loads a configuration from a non-existent file.
     * @throws IOException
     * @throws ParseException
     */
    @Test(expected=FileNotFoundException.class)
    public void testMissingLoad() throws IOException, ParseException {
        try {
            begin();

            Configuration config = ConfigurationLoader.load("blah");
        }
        finally {
            end();
        }
    }

    /**
     * Loads a partially completed configuration file.
     * @throws IOException
     * @throws ParseException
     */
    @Test(expected=RuntimeException.class)
    public void testPartialLoad() throws IOException, ParseException {
        try {
            begin();

            String configFile = System.getProperty("user.dir") + "/test/SeedboxSync/resources/partialConfig.json";
            Configuration config = ConfigurationLoader.load(configFile);
        }
        finally {
            end();
        }
    }
}
