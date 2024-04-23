/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import java.sql.*;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    // private int counter = 0;

    private int windowWidth = 640;
    private int windowHeight = 480;

    private Navigator navigator;

    public String getGreeting() {
        return "Hello World!";
    }

    @Override
    public void start(Stage stage) throws SQLException, InterruptedException {
        SplashPane splashPane = new SplashPane();
        HelloWorldPane helloWorldPane = new HelloWorldPane();
        this.navigator = new Navigator(stage, this.windowWidth, this.windowHeight,
                splashPane.getPane());
        // helloWorldPane.getPane());
        this.navigator.setTitle("Hello World!");
        this.navigator.startNavigator();

        DatabaseInterface databaseInterface = new Database();

        databaseInterface.testDatabaseConnection();

        databaseInterface.cleanup();

        // Thread.sleep(1000);

        System.out.println(new App().getGreeting());

        this.navigator.push(this.navigator.defaultScene);

        // // Image img = new
        // // Image("https://static.javatpoint.com/images/404-javatpoint.gif");
        // Image img = new Image("file:logo.jpg");
        // ImageInput imginput = new ImageInput();
        // Rectangle rect = new Rectangle();
        // imginput.setSource(img);
        // imginput.setX(20);
        // imginput.setY(100);
        // Group root = new Group();
        // rect.setEffect(imginput);
        // root.getChildren().add(rect);
        // Scene scene = new Scene(root, 530, 500, Color.BLACK);
        // stage.setScene(scene);
        // stage.setTitle("ImageInput Example");
        // stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
