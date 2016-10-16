/****************************************************************************
 *
 * ConfigurationTest.java
 *
 * Tests the Configuration class.
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
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests the Configuration class.
 */
public class ConfigurationTest {
    @Before
    public void ConfigureLogging() {
        ConsoleAppender console = new ConsoleAppender();
        console.setLayout(new PatternLayout("%d{yyyy-MM-dd' 'HH:mm:ss.SSS} [%-5p] [%c] - %m%n"));
        console.setThreshold(Level.INFO);
        console.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(console);
    }
    /** Constructs an instance of Configuration and tests all accessors. */
    @Test
    public void testConfiguration() {
        Configuration test = new Configuration("server", 1, "user", "password", 1, "remote", "local");

        assertEquals(test.getServer(), "server");
        assertEquals(test.getPort(), (Integer)1);
        assertEquals(test.getUsername(), "user");
        assertEquals(test.getPassword(), "password");
        assertEquals(test.getInterval(), (Integer)1);
        assertEquals(test.getRemoteDirectory(), "remote");
        assertEquals(test.getLocalDirectory(), "local");
    }
}
