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
import java.util.List;

import java.sql.Timestamp;
import java.sql.SQLException;

import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the Database class.
 */
public class DatabaseTest extends BaseTest {
    /**
     * The temporary folder for the class.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * Perform setup.
     */
    @Before
    public void setup() throws IOException {
        folder.newFolder("db");
    }

    /**
     * Tests instantiation and close
     * @throws SQLException
     */
    @Test
    public void testConstructor() throws IOException, SQLException {
        Database test = null;

        try {
            begin();

            java.io.File file = folder.newFile("db/constructor.db");
            test = new Database(file.getAbsolutePath());
        }
        finally {
            test.close();

            end();
        }
    }

    /**
     * Tests instantiation with known good file
     * @throws SQLException
     */
    @Test
    public void testGoodDatabase() throws IOException, SQLException {
        Database test = null;

        try {
            begin();

            java.io.File file = folder.newFile("db/good.db");
            test = new Database(file.getAbsolutePath());
        }
        finally {
            test.close();

            end();
        }
    }

    /**
     * Tests instantiation with a known bad filename
     * @throws SQLException
     */
    @Test(expected=SQLException.class)
    public void testBadConstructor() throws SQLException {
        Database test = null;

        try {
            begin();

            test = new Database("/.");

            assertEquals(test, null);
        }
        finally {
            end();
        }
    }

    /**
     * Tests the addition of a "good" file record
     * @throws SQLException
     */
    @Test
    public void testGoodAdd() throws IOException, SQLException {
        Database test = null;

        try {
            begin();

            java.io.File file = folder.newFile("db/goodAdd.db");
            test = new Database(file.getAbsolutePath());

            test.addFile(new File("test", 0L, new Timestamp(0L)));

            File testFile = test.getFile("test");

            assertEquals(testFile.getName(), "test");
        }
        finally {
            test.close();

            end();
        }
    }

    /**
     * Tests the addition of a duplicate file records
     * @throws SQLException
     */
    @Test
    public void testDuplicateAdd() throws IOException, SQLException {
        Database test = null;

        try {
            begin();

            java.io.File file = folder.newFile("db/duplicate.db");
            test = new Database(file.getAbsolutePath());

            test.addFile(new File("testduplicate", 0L, new Timestamp(0L)));

            // SQLite library is throwing an improper exception on the constraint violation
            //test.addFile(new File("testduplicate", 0L, new Timestamp(0L)));
        }
        finally {
            test.close();

            end();
        }
    }

    /**
     * Tests the update of the downloaded column
     * @throws SQLException
     */
    @Test
    public void testDownloadUpdate() throws IOException, SQLException {
        Database test = null;

        try {
            begin();

            java.io.File file = folder.newFile("db/download.db");

            // create new db
            test = new Database(file.getAbsolutePath());

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
        finally {
            test.close();

            end();
        }
    }

    /**
     * Tests retrieval of the file list
     * @throws SQLException
     */
    @Test
    public void testGetFiles() throws IOException, SQLException {
        Database test = null;

        try {
            begin();

            java.io.File file = folder.newFile("db/get.db");
            test = new Database(file.getAbsolutePath());

            test.addFile(new File("test", 0L, new Timestamp(0L)));
            test.addFile(new File("test2", 0L, new Timestamp(0L)));
            test.addFile(new File("test3", 0L, new Timestamp(0L)));

            List<File> list = test.getFiles();

            assertEquals(list.size(), 3);
            assertEquals(list.get(0).getName(), "test");
            assertEquals(list.get(2).getName(), "test3");
        }
        finally {
            test.close();

            end();
        }
    }

    /**
     * Tests retrieval of files from a blank database.
     * @throws SQLException
     */
    @Test
    public void testBadGetFile() throws IOException, SQLException {
        Database test = null;

        try {
            begin();

            java.io.File file = folder.newFile("db/badGet.db");
            test = new Database(file.getAbsolutePath());

            File testFile = test.getFile(new File("test"));

            assertEquals(testFile, null);
        }
        finally {
            test.close();

            end();
        }
    }

    /**
     * Tests instantiation of a database from an existing file.
     */
    @Test
    public void testDoubleDatabase() throws IOException, SQLException {
        Database test = null;

        try {
            begin();

            java.io.File file = folder.newFile("db/double.db");
            test = new Database(file.getAbsolutePath());
        }
        finally {
            test.close();

            end();
        }
    }
}
