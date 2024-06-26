/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import java.sql.*;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    // private int counter = 0;

    private int windowWidth = 1280;
    private int windowHeight = 480;

    private Navigator navigator;

    private DatabaseInterface databaseInterface;

    public String getGreeting() {
        return "Hello World!";
    }

    @Override
    public void start(Stage stage) throws SQLException, InterruptedException {
        SplashPane splashPane = new SplashPane();
        // HelloWorldPane helloWorldPane = new HelloWorldPane();
        this.navigator = new Navigator(stage, this.windowWidth, this.windowHeight,
                splashPane.getPane());
        // helloWorldPane.getPane());
        this.navigator.setTitle("Indian Flights");
        this.navigator.startNavigator();

        // Initialising database

        this.databaseInterface = new Database();

        this.databaseInterface.testDatabaseConnection();

        // Thread.sleep(1000);

        System.out.println(new App().getGreeting());

        // Initialising all the app pages

        // Login Page

        LoginPane loginPane = new LoginPane();

        // Navigate to the first page

        this.navigator.push(loginPane.getPane());
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Cleaning up database connection");
        this.databaseInterface.cleanup();
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}
