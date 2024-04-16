package org.example.database_utils;

import java.sql.SQLException;

public interface DatabaseInterface {
    /*
     * Tests the database connection.
     * 
     * This prints the JDBC Driver version and the current system date.
     * 
     * This exists to verify that the driver is correctly loaded (by checking driver
     * version) and that SQL statements can be executed (by executing `SELECT
     * SYSDATE FROM DUAL`).
     */
    abstract void testDatabaseConnection() throws SQLException;

    /*
     * Cleans up after itself.
     * 
     * This function *must* be called after the program is done interacting with the
     * database.
     */
    abstract void cleanup() throws SQLException;
}
