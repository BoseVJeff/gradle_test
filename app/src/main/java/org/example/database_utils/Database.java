package org.example.database_utils;

import java.sql.*;

import org.example.Credentials;

import oracle.jdbc.pool.OracleDataSource;

public class Database implements DatabaseInterface {

    Connection connection;

    public Database() throws SQLException {
        OracleDataSource ods = new OracleDataSource();

        Credentials credentials = new Credentials();

        ods.setURL("jdbc:oracle:thin:@//localhost:1521");
        ods.setUser(credentials.getDatabaseUsernameString());
        ods.setPassword(credentials.getDatabsePasswordString());

        this.connection = ods.getConnection();
    }

    @Override
    public void testDatabaseConnection() throws SQLException {
        // Create Oracle DatabaseMetaData object
        DatabaseMetaData meta = this.connection.getMetaData();

        // gets driver info:
        System.out.println("JDBC driver version is " + meta.getDriverVersion());

        String query = "SELECT SYSDATE FROM DUAL";
        Statement statement = connection.createStatement();

        ResultSet rSet = statement.executeQuery(query);

        while (rSet.next()) {
            System.out.println("Current date is " + rSet.getString("SYSDATE"));
        }

        rSet.close();

        statement.close();
    }

    @Override
    public void cleanup() throws SQLException {
        this.connection.close();
    }
}
