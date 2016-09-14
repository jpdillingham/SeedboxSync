package WhatboxSync;

import org.junit.Test;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Tests the File class.
 */
public class FileTest {
    @Test
    public void testConstructor() {
        File test = new File("filename", 0L, new Date(1999, 1, 1));

        assertEquals("filename", test.getName());
        assertEquals(new Date(1999, 1,1), test.getTimestamp());
    }
}
