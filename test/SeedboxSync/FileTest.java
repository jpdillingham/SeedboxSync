/****************************************************************************
 *
 * FileTest.java
 *
 * Tests the File class.
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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the File class.
 */
public class FileTest extends BaseTest {
    /**
     * Tests the constructor and all accessors.
     */
    @Test
    public void testConstructor() {
        try {
            begin();

            // initialize timestamps for timestamp, addedTimestamp and downloadedTimestamp fields
            Timestamp ts = new Timestamp(0L);
            Timestamp ats = new Timestamp(1L);
            Timestamp dts = new Timestamp(2L);

            // test complete constructor
            File test = new File("test", new Long(1), ts, ats, dts);

            // test accessors
            assertEquals(test.getName(), "test");
            assertEquals(test.getSize(), new Long(1));
            assertEquals(test.getTimestamp(), ts);
            assertEquals(test.getAddedTimestamp(), ats);
            assertEquals(test.getDownloadedTimestamp(), dts);
            assertEquals(test.isDownloaded(), true);
        }
        finally {
            end();
        }
    }

    /**
     * Tests the truncated constructor.
     */
    @Test
    public void testTruncatedConstructor() {
        try {
            begin();

            File test2 = new File("test");

            // ensure that the downloadedTimestamp field is initialized to false.
            assertEquals(test2.isDownloaded(), false);
        }
        finally {
            end();
        }
    }
}
