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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests the ConfigurationLoader class.
 */
public class ConfigurationLoaderTest {
    /**
     * The logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

    /**
     * Configure the logger.
     */
    @Before
    public void configureLogging() {
        ConsoleAppender console = new ConsoleAppender();
        console.setLayout(new PatternLayout("%d{yyyy-MM-dd' 'HH:mm:ss.SSS} [%-5p] [%c] - %m%n"));
        console.setThreshold(Level.INFO);
        console.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(console);
    }

    /**
     * Constructs an instance of ConfigurationLoader.
     */
    @Test
    public void testConstructor() {
        ConfigurationLoader test = new ConfigurationLoader();
    }

    /**
     * Loads a known good configuration file.
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testGoodLoad() throws IOException, ParseException {
        String configFile = System.getProperty("user.dir") + "/test/SeedboxSync/resources/goodConfig.json";
        Configuration config = ConfigurationLoader.load(configFile);

        assertEquals(config.getServer(), "server");
        assertEquals(config.getPort(), (Integer)1);
        assertEquals(config.getUsername(), "username");
        assertEquals(config.getPassword(), "password");
        assertEquals(config.getInterval(), (Integer)3600);
        assertEquals(config.getRemoteDownloadDirectory(), "remoteDownloadDirectory");
        assertEquals(config.getLocalDownloadDirectory(), "localDownloadDirectory");
        assertEquals(config.getRemoteUploadDirectory(), "remoteUploadDirectory");
        assertEquals(config.getLocalUploadDirectory(), "localUploadDirectory");
    }

    /**
     * Loads a known bad configuration file.
     * @throws IOException
     * @throws ParseException
     */
    @Test(expected=ParseException.class)
    public void testBadLoad() throws IOException, ParseException {
        String configFile = System.getProperty("user.dir") + "/test/SeedboxSync/resources/badConfig.json";
        Configuration config = ConfigurationLoader.load(configFile);
    }

    /**
     * Loads a configuration from a non-existent file.
     * @throws IOException
     * @throws ParseException
     */
    @Test(expected=FileNotFoundException.class)
    public void testMissingLoad() throws IOException, ParseException {
        Configuration config = ConfigurationLoader.load("blah");
    }

    /**
     * Loads a partially completed configuration file.
     * @throws IOException
     * @throws ParseException
     */
    @Test(expected=RuntimeException.class)
    public void testPartialLoad() throws IOException, ParseException {
        String configFile = System.getProperty("user.dir") + "/test/SeedboxSync/resources/partialConfig.json";
        Configuration config = ConfigurationLoader.load(configFile);
    }
}
