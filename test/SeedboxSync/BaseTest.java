/****************************************************************************
 *
 * BaseTest.java
 *
 * Base class for all tests.
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

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Base class for all tests.
 */
public class BaseTest {
    /**
     * The timestamp corresponding to the start of the test.
     */
    private Long startTime;

    /**
     * Initializes a new instance of the BaseTest class.
     */
    protected BaseTest() {
        org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
    }

    /**
     * Logs the specified message to the console.
     * @param message The message to log.
     */
    protected void log(String message) {
        String formattedDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new Date());

        System.out.println(formattedDate + " [TEST] " + message);
    }

    /**
     * Logs a message indicating the start of a test to the console.
     */
    protected void begin() {
        startTime = System.currentTimeMillis();

        log("--> Starting test '" + Thread.currentThread().getStackTrace()[2].getMethodName() + "'...");
    }

    /**
     * Logs a message indicating the end of a test to the console.
     */
    protected void end() {
        Long duration = System.currentTimeMillis() - startTime;
        Double divisor = 1000.0;

        log("<-- Completed test '" + Thread.currentThread().getStackTrace()[2].getMethodName() + "' in " + duration / divisor + " sec");
    }
}
