package WhatboxSync;

import java.util.List;

/**
 * Defines the interface for Server objects.
 */
interface IServer {
    /** Opens the Server connection. */
    Boolean connect();

    /** Closes the Server connection. */
    Boolean disconnect();

    /** Returns a value indicating whether the Server connection is open. */
    Boolean isConnected();

    /** Lists the files in the specified directory. */
    List<File> list(String directory);

    /** Downloads the specified file. */
    Boolean download(String file);

}
