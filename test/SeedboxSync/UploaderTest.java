/****************************************************************************
 *
 * UploaderTest.java
 *
 * Tests the Uploader class.
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
import java.io.File;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.*;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

/**
 * Tests the Uploader class.
 */
public class UploaderTest extends BaseTest {
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

            Uploader uploader = new Uploader(server, "local", "remote");
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

            Uploader test = new Uploader(server, "local", "remote");

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
     * Tests a typical process() call
     * @throws IOException
     */
    @Test
    public void testProcess() throws Exception {
        try {
            begin();

            File uploadFolder = folder.newFolder("upload");
            File uploadFile = folder.newFile("upload/file.txt");

            IServer server = mock(IServer.class);
            Uploader uploader = new Uploader(server, uploadFolder.getAbsolutePath(), "");

            uploader.enqueue(uploadFile.getAbsolutePath());

            uploader.process();

            // assert that the file was removed from the queue
            assertEquals(uploader.getQueue().size(), 0);

            // assert that the original file is now missing, and that a file with
            // [Uploaded] prepended to the name exists.
            assertEquals(uploadFile.exists(), false);

            assertEquals(new File(uploadFile.getParent() + "/[Uploaded] file.txt").exists(), true);
        }
        finally {
            end();
        }
    }

    /**
     * Tests process() with a known bad upload folder.
     * @throws IOException
     */
    @Test
    public void testMissingDirProcess() throws Exception {
        try {
            begin();

            IServer server = mock(IServer.class);
            Uploader uploader = new Uploader(server, "bad folder", "");

            uploader.enqueue("test");

            uploader.process();
        }
        finally {
            end();
        }
    }

    /**
     * Tests process() under all possible combinations of file/folders.
     * @throws IOException
     */
    @Test
    public void testAllFolderPossibilitiesProcess() throws Exception {
        try {
            begin();

            File uploadFolder = folder.newFolder("upload");
            File childFolder = folder.newFolder("upload", "test");
            File file = folder.newFile("upload/file.txt");
            File uploadedFile = folder.newFile("upload/[Uploaded] file2.txt");

            IServer server = mock(IServer.class);
            Uploader uploader = new Uploader(server, uploadFolder.getAbsolutePath(), "");

            uploader.process();
        }
        finally {
            end();
        }
    }

    /**
     * Tests process() with an empty queue.
     * @throws IOException
     */
    @Test
    public void testEmptyQueueProcess() throws Exception {
        try {
            begin();

            File uploadFolder = folder.newFolder("upload");

            IServer server = mock(IServer.class);
            Uploader uploader = new Uploader(server, uploadFolder.getAbsolutePath(), "");

            // assert that the queue is empty
            assertEquals(uploader.getQueue().size(), 0);
            uploader.process();
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
    public void testProcessUploadException() throws Exception {
        try {
            begin();

            IServer server = mock(IServer.class);

            Mockito.doThrow(new Exception()).when(server).upload(new File("local\\one"), "remote/one");

            Uploader test = new Uploader(server, "local", "remote");

            test.enqueue("local/one");

            test.process();
        }
        finally {
            end();
        }
    }
}