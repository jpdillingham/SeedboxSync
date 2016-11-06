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
import java.io.IOException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the DatabaseLoader class.
 */
public class DatabaseLoaderTest {
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
        console.setThreshold(Level.INFO);
        console.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(console);

        folder.newFolder("dbLoader");
    }

    /**
     * Constructs an instance of DatabaseLoader.
     */
    @Test
    public void testConstructor() {
        DatabaseLoader test = new DatabaseLoader();
    }

    /**
     * Loads a database and ensures that it was created.
     * @throws SQLException
     */
    @Test
    public void testLoad() throws Exception {
        File file = folder.newFile("dbLoader/dbLoader.db");
        Configuration test = new Configuration("server", 1, "user", "password", 1, "remote",
                "local", "remoteUp", "localUp", file.getAbsolutePath());

        Database db = DatabaseLoader.load(test);

        assertNotEquals(db, null);
        assertEquals((file.exists()), true);

        db.close();
    }

    /**
     * Loads a database with a known bad configuraiton.
     * @throws Exception
     */
    @Test(expected=Exception.class)
    public void testBadLoad() throws Exception {
        Configuration config = new Configuration("", 1, "user", "password", 1, "remote", "local", "remoteUp", "localUp", "db");
        Database db = DatabaseLoader.load(config);
    }

    /**
     * Loads a database with a null configuration.
     * @throws Exception
     */
    @Test(expected=Exception.class)
    public void testNullLoad() throws Exception {
        Database db = DatabaseLoader.load(null);
    }
}
