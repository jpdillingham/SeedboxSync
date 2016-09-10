package WhatboxSync;

import java.util.Calendar;

/**
 * Represents files stored on a remote FTP server.
 */
public class File {
    /** the fully qualified filename of the file. */
    private String name;

    /** the timestamp of the last modification to the file. */
    private Date timestamp;

    /**
     * Initializes a new instance of the File class using the specified name and timestamp.
     * @param name  the fully qualified filename of the file.
     * @param timestamp the timestamp of the last modification to the file.
     */
    public File(String name, Date timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }

    /**
     * Gets the fully qualified filename of the file.
     * @return  The fully qualified filename of the file.
     */
    String getName() {
        return name;
    }

    /**
     * Gets the timestamp of the last modification to the file.
     * @return  The timestamp of the last modification to the file.
     */
    Date getTimestamp() {
        return timestamp;
    }
}
