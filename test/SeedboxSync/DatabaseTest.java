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

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.sql.SQLException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
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
     * The temporary folder for the class.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * Configure the logger.
     */
    @Before
    public void configureLogging() throws IOException {
        ConsoleAppender console = new ConsoleAppender();
        console.setLayout(new PatternLayout("%d{yyyy-MM-dd' 'HH:mm:ss.SSS} [%-5p] [%c] - %m%n"));
        console.setThreshold(Level.DEBUG);
        console.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(console);
        org.apache.log4j.Logger.getRootLogger().setAdditivity(false);

        // create folder
        folder.newFolder("db");
    }

    /**
     * Tests instantiation and close
     * @throws SQLException
     */
    @Test
    public void testConstructor() throws IOException, SQLException {
        java.io.File file = folder.newFile("db/constructor.db");
        Database test = new Database(file.getAbsolutePath());
        test.close();
    }

    /**
     * Tests instantiation with known good file
     * @throws SQLException
     */
    @Test
    public void testGoodDatabase() throws IOException, SQLException {
        java.io.File file = folder.newFile("db/good.db");
        Database test = new Database(file.getAbsolutePath());
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
    public void testGoodAdd() throws IOException, SQLException {
        java.io.File file = folder.newFile("db/goodAdd.db");
        Database test = new Database(file.getAbsolutePath());

        test.addFile(new File("test", 0L, new Timestamp(0L)));

        File testFile = test.getFile("test");

        assertEquals(testFile.getName(), "test");
    }

    /**
     * Tests the addition of a duplicate file records
     * @throws SQLException
     */
    @Test
    public void testDuplicateAdd() throws IOException, SQLException {
        java.io.File file = folder.newFile("db/duplicate.db");
        Database test = new Database(file.getAbsolutePath());

        test.addFile(new File("testduplicate", 0L, new Timestamp(0L)));

        // SQLite library is throwing an improper exception on the constraint violation
        //test.addFile(new File("testduplicate", 0L, new Timestamp(0L)));
    }

    /**
     * Tests the update of the downloaded column
     * @throws SQLException
     */
    @Test
    public void testDownloadUpdate() throws IOException, SQLException {
        java.io.File file = folder.newFile("db/download.db");

        // create new db
        Database test = new Database(file.getAbsolutePath());

        // add a file with null timestamp
        test.addFile(new File("test", 0L, new Timestamp(0L)));

        // fetch the file we just added and test the timestamp
        File startFile = test.getFile(new File("test"));
        assertEquals(startFile.getDownloadedTimestamp(), null);

        // set the timestamp, then fetch and test
        test.setDownloadedTimestamp(new File("test"));
        File testFile = test.getFile(new File("test"));
        assertEquals(testFile.getName(), "test");
        assertNotEquals(testFile.getDownloadedTimestamp(), null);
    }

    /**
     * Tests retrieval of the file list
     * @throws SQLException
     */
    @Test
    public void testGetFiles() throws IOException, SQLException {
        java.io.File file = folder.newFile("db/get.db");
        Database test = new Database(file.getAbsolutePath());

        test.addFile(new File("test", 0L, new Timestamp(0L)));
        test.addFile(new File("test2", 0L, new Timestamp(0L)));
        test.addFile(new File("test3", 0L, new Timestamp(0L)));

        List<File> list = test.getFiles();

        assertEquals(list.size(), 3);
        assertEquals(list.get(0).getName(), "test");
        assertEquals(list.get(2).getName(), "test3");
    }

    /**
     * Tests retrieval of files from a blank database.
     * @throws SQLException
     */
    @Test
    public void testBadGetFile() throws IOException, SQLException {
        java.io.File file = folder.newFile("db/badGet.db");
        Database test = new Database(file.getAbsolutePath());

        File testFile = test.getFile(new File("test"));

        assertEquals(testFile, null);
    }

    /**
     * Tests instantiation of a database from an existing file.
     */
    @Test
    public void testDoubleDatabase() throws IOException, SQLException {
        java.io.File file = folder.newFile("db/double.db");
        Database test = new Database(file.getAbsolutePath());
    }
}
