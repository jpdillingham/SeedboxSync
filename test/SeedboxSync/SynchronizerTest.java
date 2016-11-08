/****************************************************************************
 *
 * SynchronizerTest.java
 *
 * Tests the Synchronizer class.
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
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Tests the Synchronizer class.
 */
public class SynchronizerTest {
    /**
     * The logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * Configure the logger.
     */
    @Before
    public void setup() {
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getRootLogger();

        if (logger.getAppender("console") != null) {
            ConsoleAppender console = new ConsoleAppender();
            console.setName("console");
            console.setLayout(new PatternLayout("%d{yyyy-MM-dd' 'HH:mm:ss.SSS} [%-5p] [%c] - %m%n"));
            console.setThreshold(Level.INFO);
            console.activateOptions();
            org.apache.log4j.Logger.getRootLogger().addAppender(console);
            org.apache.log4j.Logger.getRootLogger().setAdditivity(false);
        }
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void testConstructor() throws Exception {
        Configuration configuration = new Configuration("server", 1, "user", "pass", 1, "remoteDownload", "localDownload", "remoteUpload", "localUpload", "db");
        IServer server = mock(IServer.class);
        IDatabase database = mock(IDatabase.class);

        Synchronizer test = new Synchronizer(configuration, server, database);

        test.synchronize();
    }

    /**
     * Perform teardown.
     */
    @After
    public void teardown() {
    }
}