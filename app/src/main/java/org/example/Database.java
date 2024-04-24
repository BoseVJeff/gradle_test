package org.example;

import java.sql.*;

import oracle.jdbc.pool.OracleDataSource;

public class Database implements DatabaseInterface {

    Connection connection;

    public Database() throws SQLException {
        OracleDataSource ods = new OracleDataSource();

        Credentials credentials = new Credentials();

        ods.setURL("jdbc:oracle:thin:@//localhost:1521/XE");
        ods.setUser(credentials.getDatabaseUsernameString());
        ods.setPassword(credentials.getDatabsePasswordString());

        this.connection = ods.getConnection();
        
        String flightsQuery = "BEGIN " +
        "  EXECUTE IMMEDIATE 'CREATE TABLE Flights ( " +
        "    flight_no VARCHAR2(7) PRIMARY KEY, " +
        "    flight_name VARCHAR2(250), " +
        "    airline_name VARCHAR2(250), " +
        "    source VARCHAR2(250), " +
        "    destination VARCHAR2(250), " +
        "    departure_time TIMESTAMP NOT NULL, " +
        "    arrival_time TIMESTAMP NOT NULL, " +
        "    price DECIMAL(10, 2) NOT NULL, " +
        "    available_seats NUMBER(5) NOT NULL, " +
        "    status VARCHAR2(10) NOT NULL CONSTRAINT check_status CHECK (status = ''SCHEDULED'' OR status = ''COMPLETED'' OR status = ''CANCELLED''), " +
        "    max_capacity NUMBER(5), " +
        "    CONSTRAINT check_source CHECK (source != destination), " +
        "    CONSTRAINT check_departure CHECK (departure_time < arrival_time), " +
        "    CONSTRAINT check_seats_capacity CHECK (available_seats <= max_capacity) " +
        "  )'; " +
        "EXCEPTION " +
        "  WHEN OTHERS THEN " +
        "    IF SQLCODE != -955 THEN " +
        "      RAISE; " +
        "    END IF; " +
        "END;";

        Statement statement1 = connection.createStatement();
        statement1.executeUpdate(flightsQuery);
        statement1.close();

        String usersQuery = "BEGIN " +
        "  EXECUTE IMMEDIATE 'CREATE TABLE Users (" +
        "    user_id NUMBER PRIMARY KEY," +
        "    username VARCHAR2(255) NOT NULL," +
        "    password VARCHAR2(255) NOT NULL," +
        "    email VARCHAR2(255) NOT NULL," +
        "    phone_number VARCHAR(20)," +
        "    isadmin NUMBER(1) NOT NULL CHECK (isadmin IN (0, 1))," +
        "    CONSTRAINT unique_username UNIQUE (username)," +
        "    CONSTRAINT unique_email UNIQUE (email)," +
        "    CONSTRAINT unique_phone_number UNIQUE (phone_number)," +
        "    CONSTRAINT check_phone_number_pattern CHECK (REGEXP_LIKE(phone_number, ''^\\+\\d{1,3}\\d{6,14}$''))" +
        "  )'; " +
        "EXCEPTION " +
        "  WHEN OTHERS THEN " +
        "    IF SQLCODE != -955 THEN " +
        "      RAISE; " +
        "    END IF; " +
        "END;";
        
        Statement statement2 = connection.createStatement();
        statement2.executeUpdate(usersQuery);
        statement2.close();

        String passengersQuery = "BEGIN " +
        "  EXECUTE IMMEDIATE 'CREATE TABLE Passengers (" +
        "    passenger_id NUMBER PRIMARY KEY," +
        "    name VARCHAR2(250) NOT NULL," +
        "    identification_type VARCHAR2(250) NOT NULL," +
        "    identification_id VARCHAR2(250) NOT NULL," +
        "    flight_no VARCHAR2(7)," +
        "    seat_no NUMBER," +
        "    user_id NUMBER," +
        "    notes VARCHAR2(250)," +
        "    payment_id VARCHAR2(250)," +
        "    age NUMBER," +
        "    gender VARCHAR2(8) CHECK (gender IN (''Male'' , ''Female'' , ''Other''))," +
        "    FOREIGN KEY (flight_no) REFERENCES Flights(flight_no)," +
        "    FOREIGN KEY (user_id) REFERENCES Users(user_id)" +
        "  )'; " +
        "EXCEPTION " +
        "  WHEN OTHERS THEN " +
        "    IF SQLCODE != -955 THEN " +
        "      RAISE; " +
        "    END IF; " +
        "END;";

        Statement statement3 = connection.createStatement();
        statement3.executeUpdate(passengersQuery);
        statement3.close();
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
