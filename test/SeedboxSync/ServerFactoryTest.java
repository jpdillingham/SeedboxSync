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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;

public class ServerFactoryTest extends BaseTest {
    /**
     * The temporary folder for the class.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * Constructs an instance of ServerFactory.
     */
    @Test
    public void testConstructor() {
        try {
            begin();

            ServerFactory test = new ServerFactory();
        }
        finally {
            end();
        }
    }

    /**
     * Constructs a Server using ServerFactory, providing a known good configuration.
     */
    @Test
    public void testGoodConfig() throws Exception {
        try {
            begin();

            java.io.File downloadDir = folder.newFolder("download");
            java.io.File uploadDir = folder.newFolder("upload");

            Configuration config = new Configuration("server", 1, "user", "password", 1, "remote",
                    downloadDir.getAbsolutePath(), "remoteUp", uploadDir.getAbsolutePath());

            Server test = ServerFactory.createServer(config);

            assertEquals(test.getAddress(), "server");
            assertEquals(test.getPort(), (Integer) 1);
            assertEquals(test.getUsername(), "user");
            assertEquals(test.getPassword(), "password");
        }
        finally {
            end();
        }
    }

    /**
     * Constructs a Server using ServerFactory, providing a known bad configuration.
     */
    @Test(expected=Exception.class)
    public void testBadConfig() throws Exception {
        try {
            begin();

            Configuration config = new Configuration("", 1, "user", "password", 1, "remote", "local", "remoteUp", "localUp");
            Server test = ServerFactory.createServer(config);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs a Server using ServerFactory, providing a null configuration.
     * @throws Exception
     */
    @Test(expected=Exception.class)
    public void testNullConfig() throws Exception {
        try {
            begin();

            Server test = ServerFactory.createServer(null);
        }
        finally {
            end();
        }
    }
}
