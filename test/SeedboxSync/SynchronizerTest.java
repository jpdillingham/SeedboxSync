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

import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Tests the Synchronizer class.
 */
public class SynchronizerTest extends BaseTest {
    /**
     * Tests the constructor.
     */
    @Test
    public void testConstructor() throws Exception {
        try {
            begin();

            Configuration configuration = new Configuration("server", 1, "user", "pass", 1, "remoteDownload", "localDownload", "remoteUpload", "localUpload", "db");
            IServer server = mock(IServer.class);
            IDatabase database = mock(IDatabase.class);

            Synchronizer test = new Synchronizer(configuration, server, database);

            test.synchronize();
        }
        finally {
            end();
        }
    }
}