/****************************************************************************
 *
 * DatabaseLoaderTest.java
 *
 * Tests the DatabaseLoader class.
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

import java.io.File;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the DatabaseLoader class.
 */
public class DatabaseLoaderTest extends BaseTest {
    /**
     * The temporary folder for the class.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * Constructs an instance of DatabaseLoader.
     */
    @Test
    public void testConstructor() {
        try {
            begin();

            DatabaseLoader test = new DatabaseLoader();
        }
        finally {
            end();
        }
    }

    /**
     * Loads a database and ensures that it was created.
     * @throws SQLException
     */
    @Test
    public void testLoad() throws Exception {
        Database db = null;

        try {
            begin();

            File file = folder.newFile();
            Configuration test = new Configuration("server", 1, "user", "password", 1, "remote",
                    "local", "remoteUp", "localUp", file.getAbsolutePath());

            db = DatabaseLoader.load(test);

            assertNotEquals(db, null);
            assertEquals((file.exists()), true);
        }
        finally {
            db.close();

            end();
        }
    }

    /**
     * Loads a database from an existing file.
     * @throws Exception
     */
    @Test
    public void testExistingLoad() throws Exception {
        Database db = null;

        try {
            begin();

            String file = System.getProperty("user.dir") + "/test/SeedboxSync/resources/testDatabase.db";
            Configuration test = new Configuration("server", 1, "user", "password", 1, "remote",
                    "local", "remoteUp", "localUp", file);

            db = DatabaseLoader.load(test);

            assertNotEquals(db, null);
        }
        finally {
            db.close();

            end();
        }
    }

    /**
     * Loads a database with a known bad configuraiton.
     * @throws Exception
     */
    @Test(expected=Exception.class)
    public void testBadLoad() throws Exception {
        Database db = null;

        try {
            begin();

            Configuration config = new Configuration("", 1, "user", "password", 1, "remote", "local", "remoteUp", "localUp", "db");
            db = DatabaseLoader.load(config);
        }
        finally {
            db.close();

            end();
        }
    }

    /**
     * Loads a database with a null configuration.
     * @throws Exception
     */
    @Test(expected=Exception.class)
    public void testNullLoad() throws Exception {
        Database db = null;

        try {
            begin();

            db = DatabaseLoader.load(null);
        }
        finally {
            db.close();

            end();
        }
    }
}
