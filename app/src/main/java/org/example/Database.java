package org.example;

import java.math.BigDecimal;
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

    public void insertIntoFlights(String flight_no, String flight_name, String airline_name, String source,
    String destination, Timestamp departure_time, Timestamp arrival_time,
    BigDecimal price, int available_seats, String status, int max_capacity) throws SQLException {
String query = "INSERT INTO Flights VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
PreparedStatement preparedStatement = connection.prepareStatement(query);
preparedStatement.setString(1, flight_no);
preparedStatement.setString(2, flight_name);
preparedStatement.setString(3, airline_name);
preparedStatement.setString(4, source);
preparedStatement.setString(5, destination);
preparedStatement.setTimestamp(6, departure_time);
preparedStatement.setTimestamp(7, arrival_time);
preparedStatement.setBigDecimal(8, price);
preparedStatement.setInt(9, available_seats);
preparedStatement.setString(10, status);
preparedStatement.setInt(11, max_capacity);
preparedStatement.executeUpdate();
preparedStatement.close();
}

// Update data in Flights table
public void updateFlights(String flight_no, BigDecimal newPrice) throws SQLException {
String query = "UPDATE Flights SET price = ? WHERE flight_no = ?";
PreparedStatement preparedStatement = connection.prepareStatement(query);
preparedStatement.setBigDecimal(1, newPrice);
preparedStatement.setString(2, flight_no);
preparedStatement.executeUpdate();
preparedStatement.close();
}

// Delete data from Flights table
public void deleteFromFlights(String flight_no) throws SQLException {
String query = "DELETE FROM Flights WHERE flight_no = ?";
PreparedStatement preparedStatement = connection.prepareStatement(query);
preparedStatement.setString(1, flight_no);
preparedStatement.executeUpdate();
preparedStatement.close();
}

// Select data from Flights table
public ResultSet selectFromFlights() throws SQLException {
String query = "SELECT * FROM Flights";
Statement statement = connection.createStatement();
ResultSet resultSet = statement.executeQuery(query);
return resultSet;
}

// Insert data into Users table
public void insertIntoUsers(int userId, String username, String password, String email,
  String phoneNumber, int isAdmin) throws SQLException {
String query = "INSERT INTO Users VALUES (?, ?, ?, ?, ?, ?)";
PreparedStatement preparedStatement = connection.prepareStatement(query);
preparedStatement.setInt(1, userId);
preparedStatement.setString(2, username);
preparedStatement.setString(3, password);
preparedStatement.setString(4, email);
preparedStatement.setString(5, phoneNumber);
preparedStatement.setInt(6, isAdmin);
preparedStatement.executeUpdate();
preparedStatement.close();
}

// Update data in Users table
public void updateUsers(int userId, String columnName, Object newValue) throws SQLException {
String query = "UPDATE Users SET " + columnName + " = ? WHERE user_id = ?";
PreparedStatement preparedStatement = connection.prepareStatement(query);
if (newValue instanceof String) {
preparedStatement.setString(1, (String) newValue);
} else if (newValue instanceof Integer) {
preparedStatement.setInt(1, (Integer) newValue);
} // Add more cases for other data types if needed
preparedStatement.setInt(2, userId);
preparedStatement.executeUpdate();
preparedStatement.close();
}

// Delete data from Users table
public void deleteFromUsers(int userId) throws SQLException {
String query = "DELETE FROM Users WHERE user_id = ?";
PreparedStatement preparedStatement = connection.prepareStatement(query);
preparedStatement.setInt(1, userId);
preparedStatement.executeUpdate();
preparedStatement.close();
}

// Select data from Users table
public ResultSet selectFromUsers() throws SQLException {
String query = "SELECT * FROM Users";
Statement statement = connection.createStatement();
ResultSet resultSet = statement.executeQuery(query);
return resultSet;
}

// Insert data into Passengers table
public void insertIntoPassengers(int passengerId, String name, String identificationType,
       String identificationId, String flightNo, int seatNo,
       int userId, String notes, String paymentId, int age,
       String gender) throws SQLException {
String query = "INSERT INTO Passengers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
PreparedStatement preparedStatement = connection.prepareStatement(query);
preparedStatement.setInt(1, passengerId);
preparedStatement.setString(2, name);
preparedStatement.setString(3, identificationType);
preparedStatement.setString(4, identificationId);
preparedStatement.setString(5, flightNo);
preparedStatement.setInt(6, seatNo);
preparedStatement.setInt(7, userId);
preparedStatement.setString(8, notes);
preparedStatement.setString(9, paymentId);
preparedStatement.setInt(10, age);
preparedStatement.setString(11, gender);
preparedStatement.executeUpdate();
preparedStatement.close();
}

// Update data in Passengers table
public void updatePassengers(int passengerId, String columnName, Object newValue) throws SQLException {
String query = "UPDATE Passengers SET " + columnName + " = ? WHERE passenger_id = ?";
PreparedStatement preparedStatement = connection.prepareStatement(query);
if (newValue instanceof String) {
preparedStatement.setString(1, (String) newValue);
} else if (newValue instanceof Integer) {
preparedStatement.setInt(1, (Integer) newValue);
} // Add more cases for other data types if needed
preparedStatement.setInt(2, passengerId);
preparedStatement.executeUpdate();
preparedStatement.close();
}

// Delete data from Passengers table
public void deleteFromPassengers(int passengerId) throws SQLException {
String query = "DELETE FROM Passengers WHERE passenger_id = ?";
PreparedStatement preparedStatement = connection.prepareStatement(query);
preparedStatement.setInt(1, passengerId);
preparedStatement.executeUpdate();
preparedStatement.close();
}

// Select data from Passengers table
public ResultSet selectFromPassengers() throws SQLException {
String query = "SELECT * FROM Passengers";
Statement statement = connection.createStatement();
ResultSet resultSet = statement.executeQuery(query);
return resultSet;
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
