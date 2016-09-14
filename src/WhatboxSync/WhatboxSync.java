package WhatboxSync;

import java.util.List;
import java.util.ArrayList;

public class WhatboxSync {
    public static void main(String[] args) {
	    System.out.println("Hello world!");

        Server server = new Server("127.0.0.1");

        if (server.connect()) {
            System.out.println("Connected!");

            List<File> files = server.list("blah");

            for (File file : files) {
                System.out.println(file.getName());
            }
        }
    }
}
