/****************************************************************************
 *
 * DownloaderTest.java
 *
 * Tests the Downloader class.
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
 * Tests the Downloader class.
 */
public class DownloaderTest {
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
    public void configureLogging() {
        ConsoleAppender console = new ConsoleAppender();
        console.setLayout(new PatternLayout("%d{yyyy-MM-dd' 'HH:mm:ss.SSS} [%-5p] [%c] - %m%n"));
        console.setThreshold(Level.INFO);
        console.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(console);
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void testConstructor() {
        IServer server = mock(IServer.class);
        IDatabase database = mock(IDatabase.class);

        Downloader downloader = new Downloader(server, "local", "remote", database);
    }

    /**
     * Tests the queue.
     */
    @Test
    public void testQueue() {
        IServer server = mock(IServer.class);
        IDatabase database = mock(IDatabase.class);

        Downloader test = new Downloader(server, "local", "remote", database);

        // enqueue a string and confirm the size
        test.enqueue("hello world!");
        assertEquals(test.getQueue().size(), 1);

        // try to enqueue the same string.  it should be discarded.
        test.enqueue("hello world!");
        assertEquals(test.getQueue().size(), 1);

        // remove the string we enqueued
        test.dequeue("hello world!");
        assertEquals(test.getQueue().size(), 0);

        // try to remove the same string.  it shouldn't raise an error.
        test.dequeue("hello world!");
    }
}