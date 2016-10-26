/****************************************************************************
 *
 * DatabaseTest.java
 *
 * Tests the Database class.
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

import java.sql.Timestamp;
import java.util.List;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Tests the Database class.
 */
public class DatabaseTest {
    /**
     * The logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

    /**
     * The name of the test database file.
     */
    private final String testDB = "test.db";

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
     * Tests instantiation and close
     * @throws SQLException
     */
    @Test
    public void testConstructor() throws SQLException {
        Database test = new Database(testDB);
        test.close();

        (new java.io.File(testDB)).deleteOnExit();
    }

    /**
     * Tests instantiation with known good file
     * @throws SQLException
     */
    @Test
    public void testGoodDatabase() throws SQLException {
        String file = System.getProperty("user.dir") + "/test/WhatboxSync/resources/goodDatabase.db";
        Database test = new Database(file);
        (new java.io.File(file)).deleteOnExit();
    }

    /**
     * Tests instantiation with a known bad filename
     * @throws SQLException
     */
    @Test(expected=SQLException.class)
    public void testBadConstructor() throws SQLException {
        Database test = new Database("/.");
    }

    /**
     * Tests the addition of a "good" file record
     * @throws SQLException
     */
    @Test
    public void testGoodAdd() throws SQLException {
        String file = System.getProperty("user.dir") + "/test/WhatboxSync/resources/goodAddDatabase.db";
        Database test = new Database(file);

        test.addFile(new File("test", 0L, new Timestamp(0L)));

        File testFile = test.getFile("test");

        assertEquals(testFile.getName(), "test");

        (new java.io.File(file)).deleteOnExit();
    }

    /**
     * Tests the addition of a duplicate file records
     * @throws SQLException
     */
    @Test
    public void testDuplicateAdd() throws SQLException {
        String file = System.getProperty("user.dir") + "/test/WhatboxSync/resources/duplicateAddDatabase.db";
        Database test = new Database(file);

        test.addFile(new File("test", 0L, new Timestamp(0L)));
        test.addFile(new File("test"));

        (new java.io.File(file)).deleteOnExit();
    }

    /**
     * Tests the update of the downloaded column
     * @throws SQLException
     */
    @Test
    public void testDownloadUpdate() throws SQLException {
        String file = System.getProperty("user.dir") + "/test/WhatboxSync/resources/updateDatabase.db";
        Database test = new Database(file);

        test.addFile(new File("test", 0L, new Timestamp(0L)));

        test.setDownloadedTimestamp(new File("test"));

        File testFile = test.getFile(new File("test"));

        assertEquals(testFile.getName(), "test");

        (new java.io.File(file)).deleteOnExit();
    }

    /**
     * Tests retrieval of the file list
     * @throws SQLException
     */
    @Test
    public void testGetFiles() throws SQLException {
        String file = System.getProperty("user.dir") + "/test/WhatboxSync/resources/listDatabase.db";
        Database test = new Database(file);

        test.addFile(new File("test", 0L, new Timestamp(0L)));
        test.addFile(new File("test2", 0L, new Timestamp(0L)));
        test.addFile(new File("test3", 0L, new Timestamp(0L)));

        List<File> list = test.getFiles();

        assertEquals(list.size(), 3);
        assertEquals(list.get(0).getName(), "test");
        assertEquals(list.get(2).getName(), "test3");

        (new java.io.File(file)).deleteOnExit();
    }
}
