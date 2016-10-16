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

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * Tests the ConfigurationLoader class.
 */
public class ConfigurationLoaderTest {
    @Before
    public void ConfigureLogging() {
        ConsoleAppender console = new ConsoleAppender();
        console.setLayout(new PatternLayout("%d{yyyy-MM-dd' 'HH:mm:ss.SSS} [%-5p] [%c] - %m%n"));
        console.setThreshold(Level.INFO);
        console.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(console);
    }
    /** Constructs an instance of ConfigurationLoader. */
    @Test
    public void testConstructor() {
        ConfigurationLoader test = new ConfigurationLoader();
    }

    @Test
    public void testGoodLoad() throws IOException, ParseException {
        ConfigurationLoader test = new ConfigurationLoader();

        String configFile = System.getProperty("user.dir") + "/test/WhatboxSync/resources/goodConfig.json";
        Configuration config = test.Load(configFile);

        assertEquals(config.getServer(), "server");
        assertEquals(config.getPort(), (Integer)1);
        assertEquals(config.getUsername(), "username");
        assertEquals(config.getPassword(), "password");
        assertEquals(config.getInterval(), (Integer)1);
        assertEquals(config.getRemoteDirectory(), "remoteDirectory");
        assertEquals(config.getLocalDirectory(), "localDirectory");
    }

    @Test(expected=ParseException.class)
    public void testBadLoad() throws IOException, ParseException {
        ConfigurationLoader test = new ConfigurationLoader();

        String configFile = System.getProperty("user.dir") + "/test/WhatboxSync/resources/badConfig.json";
        Configuration config = test.Load(configFile);
    }

    @Test(expected=FileNotFoundException.class)
    public void testMissingLoad() throws IOException, ParseException {
        ConfigurationLoader test = new ConfigurationLoader();
        Configuration config = test.Load("blah");
    }

    @Test(expected=RuntimeException.class)
    public void testPartialLoad() throws IOException, ParseException {
        ConfigurationLoader test = new ConfigurationLoader();

        String configFile = System.getProperty("user.dir") + "/test/WhatboxSync/resources/partialConfig.json";
        Configuration config = test.Load(configFile);
    }
}
