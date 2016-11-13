/****************************************************************************
 *
 * ConfigurationTest.java
 *
 * Tests the Configuration class.
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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

/**
 * Tests the Configuration class.
 */
public class ConfigurationTest extends BaseTest {
    /**
     * The temporary folder for the class.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * Constructs an instance of Configuration and tests all accessors.
     */
    @Test
    public void testConfiguration() throws IOException {
        try {
            begin();

            java.io.File downloadDir = folder.newFolder("download");
            java.io.File uploadDir = folder.newFolder("upload");

            Configuration test = new Configuration("server", 1, "user", "password", 1, "remote",
                    downloadDir.getAbsolutePath(), "remoteUp", uploadDir.getAbsolutePath());

            assertEquals(test.getServer(), "server");
            assertEquals(test.getPort(), (Integer) 1);
            assertEquals(test.getUsername(), "user");
            assertEquals(test.getPassword(), "password");
            assertEquals(test.getInterval(), (Integer) 1);
            assertEquals(test.getRemoteDownloadDirectory(), "remote");
            assertEquals(test.getLocalDownloadDirectory(), downloadDir.getAbsolutePath());
            assertEquals(test.getRemoteUploadDirectory(), "remoteUp");
            assertEquals(test.getLocalUploadDirectory(), uploadDir.getAbsolutePath());
            assertEquals(test.isValid(), true);

            // swap folders
            test.setLocalDownloadDirectory(uploadDir.getAbsolutePath());
            test.setLocalUploadDirectory(downloadDir.getAbsolutePath());

            assertEquals(test.getLocalDownloadDirectory(), uploadDir.getAbsolutePath());
            assertEquals(test.getLocalUploadDirectory(), downloadDir.getAbsolutePath());
            assertEquals(test.isValid(), true);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with a blank server value.
     */
    @Test
    public void testBlankServer() {
        try {
            begin();

            Configuration test = new Configuration("", 1, "user", "password", 1, "remote",
                "local", "remoteUp", "localUp");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with a missing server value.
     */
    @Test
    public void testMissingServer() {
        try {
            begin();

            Configuration test = new Configuration(null, 1, "user", "password", 1, "remote",
                    "local", "remoteUp", "localUp");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with an invalid server port.
     */
    @Test
    public void testInvalidPort() {
        try {
            begin();

            Configuration test = new Configuration("server", 0, "user", "password", 1, "remote",
                    "local", "remoteUp", "localUp");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with a missing server port.
     */
    @Test
    public void testMissingPort() {
        try {
            begin();

            Configuration test = new Configuration("server", null, "user", "password", 1, "remote",
                    "local", "remoteUp", "localUp");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with an invalid username.
     */
    @Test
    public void testInvalidUsername() {
        try {
            begin();

            Configuration test = new Configuration("server", 1, "", "password", 1, "remote",
                    "local", "remoteUp", "localUp");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with a missing username.
     */
    @Test
    public void testMissingUsername() {
        try {
            begin();

            Configuration test = new Configuration("server", 1, null, "password", 1, "remote",
                    "local", "remoteUp", "localUp");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with a missing password.
     */
    @Test
    public void testMissingPassword() {
        try {
            begin();

            Configuration test = new Configuration("server", 1, "user", null, 1, "remote",
                    "local", "remoteUp", "localUp");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with an invalid interval.
     */
    @Test
    public void testInvalidInterval() {
        try {
            begin();

            Configuration test = new Configuration("server", 1, "user", "password", -1, "remote",
                    "local", "remoteUp", "localUp");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with a missing interval.
     */
    @Test
    public void testMissingInterval() {
        try {
            begin();

            Configuration test = new Configuration("server", 1, "user", "password", null, "remote",
                    "local", "remoteUp", "localUp");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with an invalid remote directory.
     */
    @Test
    public void testInvalidRemoteDirectory() {
        try {
            begin();

            Configuration test = new Configuration("server", 1, "user", "password", 1, "",
                    "local", "remoteUp", "localUp");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with a missing remote directory.
     */
    @Test
    public void testMissingRemoteDirectory() {
        try {
            begin();

            Configuration test = new Configuration("server", 1, "user", "password", 1, null,
                    "local", "remoteUp", "localUp");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with an invalid local directory.
     */
    @Test
    public void testInvalidLocalDirectory() {
        try {
            begin();

            Configuration test = new Configuration("server", 1, "user", "password", 1, "remote",
                    "", "remoteUp", "localUp");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with a missing local directory.
     */
    @Test
    public void testMissingLocalDirectory() {
        try {
            begin();

            Configuration test = new Configuration("server", 1, "user", "password", 1, "remote",
                    null, "remoteUp", "localUp");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with an invalid remote upload directory.
     */
    @Test
    public void testInvalidRemoteUploadDirectory() {
        try {
            begin();

            Configuration test = new Configuration("server", 1, "user", "password", 1, "remote",
                    "local", "", "localUp");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with a missing remote upload directory.
     */
    @Test
    public void testMissingRemoteUploadDirectory() {
        try {
            begin();

            Configuration test = new Configuration("server", 1, "user", "password", 1, "remote",
                    "local", null, "localUp");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with an invalid local upload directory.
     */
    @Test
    public void testInvalidLocalUploadDirectory() {
        try {
            begin();

            Configuration test = new Configuration("server", 1, "user", "password", 1, "remote",
                    "local", "remoteUp", "");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with a missing local upload directory.
     */
    @Test
    public void testMissingLocalUploadDirectory() {
        try {
            begin();

            Configuration test = new Configuration("server", 1, "user", "password", 1, "remote",
                    "local", "remoteUp", null);

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with a local download directory that does not exist on the disk.
     * @throws IOException
     */
    @Test
    public void testNotFoundLocalDownloadDirectory() throws IOException {
        try {
            begin();

            java.io.File uploadDir = folder.newFolder("up0");

            Configuration test = new Configuration("server", 1, "user", "password", 1, "remote",
                    "down0", "remoteUp", uploadDir.getAbsolutePath());

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }

    /**
     * Constructs an instance of Configuration with a local upload directory that does not exist on the disk.
     * @throws IOException
     */
    @Test
    public void testNotFoundLocalUploadDirectory() throws IOException {
        try {
            begin();

            java.io.File downloadDir = folder.newFolder("down1");

            Configuration test = new Configuration("server", 1, "user", "password", 1, "remote",
                    downloadDir.getAbsolutePath(), "remoteUp", "up1");

            assertEquals(test.isValid(), false);
        }
        finally {
            end();
        }
    }
}
