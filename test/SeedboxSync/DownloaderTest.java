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

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;

import org.apache.commons.net.ftp.FTPFile;

import org.mockito.Mockito;
import static org.mockito.Mockito.mock;

/**
 * Tests the Downloader class.
 */
public class DownloaderTest extends BaseTest {
    /**
     * The temporary folder for the class.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * Tests the constructor.
     */
    @Test
    public void testConstructor() {
        try {
            begin();

            IServer server = mock(IServer.class);
            IDatabase database = mock(IDatabase.class);

            Downloader downloader = new Downloader(server, "local", "remote", database);
        }
        finally {
            end();
        }
    }

    /**
     * Tests the queue.
     */
    @Test
    public void testQueue() {
        try {
            begin();

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
        finally {
            end();
        }
    }

    /**
     * Test the process() method with one file
     * @throws Exception
     */
    @Test
    public void testProcessNoFiles() throws Exception {
        try {
            begin();

            IServer server = mock(IServer.class);
            IDatabase database = mock(IDatabase.class);

            ArrayList<FTPFile> files = new ArrayList<FTPFile>();

            Mockito.when(server.list("remote")).thenReturn(files);

            Downloader test = new Downloader(server, "local", "remote", database);

            test.process();
            test.process();
        }
        finally {
            end();
        }
    }

    /**
     * Test the process() method with one file
     * @throws Exception
     */
    @Test
    public void testProcessOneFile() throws Exception {
        try {
            begin();

            IServer server = mock(IServer.class);
            IDatabase database = mock(IDatabase.class);

            ArrayList<FTPFile> files = new ArrayList<FTPFile>();

            FTPFile one = new FTPFile();
            one.setName("one");

            files.add(one);

            Mockito.when(server.list("remote")).thenReturn(files);

            Downloader test = new Downloader(server, "local", "remote", database);

            test.process();
            test.process();
        }
        finally {
            end();
        }
    }

    /**
     * Test the process() method with one file and an assured database hit
     * @throws Exception
     */
    @Test
    public void testProcessOneFileDatabaseHit() throws Exception {
        try {
            begin();

            IServer server = mock(IServer.class);
            IDatabase database = mock(IDatabase.class);

            ArrayList<FTPFile> files = new ArrayList<FTPFile>();

            FTPFile one = new FTPFile();
            one.setName("one");
            one.setSize(0L);

            files.add(one);

            Mockito.when(server.list("remote")).thenReturn(files);

            Mockito.when(database.getFile("/one")).thenReturn(new File("one"));

            Downloader test = new Downloader(server, "local", "remote", database);

            test.process();
            test.process();
        }
        finally {
            end();
        }
    }

    /**
     * Test the process() method with one file and an assured database miss/queue hit
     * @throws Exception
     */
    @Test
    public void testProcessOneFileDatabaseMissQueueHit() throws Exception {
        try {
            begin();

            IServer server = mock(IServer.class);
            IDatabase database = mock(IDatabase.class);

            ArrayList<FTPFile> files = new ArrayList<FTPFile>();

            FTPFile one = new FTPFile();
            one.setName("one");
            one.setSize(0L);

            files.add(one);

            Mockito.when(server.list("remote")).thenReturn(files);

            Downloader test = new Downloader(server, "local", "remote", database);

            test.enqueue("/one:0");

            test.process();
        }
        finally {
            end();
        }
    }

    /**
     * Test the process() method
     * @throws Exception
     */
    @Test
    public void testProcessMultipleFiles() throws Exception {
        try {
            begin();

            IServer server = mock(IServer.class);
            IDatabase database = mock(IDatabase.class);

            ArrayList<FTPFile> files = new ArrayList<FTPFile>();

            FTPFile one = new FTPFile();
            one.setName("one");

            FTPFile two = new FTPFile();
            two.setName("two");

            FTPFile three = new FTPFile();
            three.setName("three");
            three.setType(FTPFile.DIRECTORY_TYPE);

            files.add(one);
            files.add(two);
            files.add(three);

            Mockito.when(server.list("remote")).thenReturn(files);

            Downloader test = new Downloader(server, "local", "remote", database);

            test.process();

            test.enqueue("/one:0");

            test.process();
        }
        finally {
            end();
        }
    }

    /**
     * Test the process() method with a known server exception
     * @throws Exception
     */
    @Test(expected=Exception.class)
    public void testProcessServerException() throws Exception {
        try {
            begin();

            IServer server = mock(IServer.class);
            IDatabase database = mock(IDatabase.class);

            Mockito.when(server.list("remote")).thenThrow(Exception.class);

            Downloader test = new Downloader(server, "local", "remote", database);

            test.process();
        }
        finally {
            end();
        }
    }

    /**
     * Test the process() method with a known server exception
     * @throws Exception
     */
    @Test
    public void testProcessDownloadException() throws Exception {
        try {
            begin();

            IServer server = mock(IServer.class);
            IDatabase database = mock(IDatabase.class);

            ArrayList<FTPFile> files = new ArrayList<FTPFile>();

            FTPFile one = new FTPFile();
            one.setName("one");
            one.setSize(0L);

            FTPFile two = new FTPFile();
            two.setName("two");
            two.setType(FTPFile.DIRECTORY_TYPE);

            files.add(one);
            files.add(two);

            Mockito.when(server.list("remote")).thenReturn(files);

            Mockito.when(server.download("remote/one", "local/one", 0L)).thenThrow(Exception.class);

            Downloader test = new Downloader(server, "local", "remote", database);

            test.process();
        }
        finally {
            end();
        }
    }
}