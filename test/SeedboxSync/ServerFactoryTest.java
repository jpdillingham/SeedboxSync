/****************************************************************************
 *
 * ServerFactoryTest.java
 *
 * Tests the ServerFactory class.
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerFactoryTest {
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
     * Constructs an instance of ServerFactory.
     */
    @Test
    public void testConstructor() {
        ServerFactory test = new ServerFactory();
    }

    /**
     * Constructs a Server using ServerFactory, providing a known good configuration.
     */
    @Test
    public void testGoodConfig() throws Exception {
        Configuration config = new Configuration("server", 1, "user", "password", 1, "remote", "local", "remoteUp", "localUp", "db");
        Server test = ServerFactory.createServer(config);

        assertEquals(test.getAddress(), "server");
        assertEquals(test.getPort(), (Integer)1);
        assertEquals(test.getUsername(), "user");
        assertEquals(test.getPassword(), "password");
    }

    /**
     * Constructs a Server using ServerFactory, providing a known bad configuration.
     */
    @Test(expected=Exception.class)
    public void testBadConfig() throws Exception {
        Configuration config = new Configuration("", 1, "user", "password", 1, "remote", "local", "remoteUp", "localUp", "db");
        Server test = ServerFactory.createServer(config);
    }

    /**
     * Constructs a Server using ServerFactory, providing a null configuration.
     * @throws Exception
     */
    @Test(expected=Exception.class)
    public void testNullConfig() throws Exception {
        Server test = ServerFactory.createServer(null);
    }
}
