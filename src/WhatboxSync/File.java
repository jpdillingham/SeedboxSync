package WhatboxSync;

import java.util.Date;

/**
 * Represents files stored on a remote FTP server.
 */
public class File {
    /** The fully qualified filename of the file. */
    private String name;

    /** The size of the file in bytes. */
    private Long size;

    /** The timestamp of the last modification to the file. */
    private Date timestamp;

    /**
     * Initializes a new instance of the File class using the specified name and timestamp.
     * @param name  The fully qualified filename of the file.
     * @param timestamp The timestamp of the last modification to the file.
     */
    public File(String name, Long size, Date timestamp) {
        this.name = name;
        this.size = size;
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
