package org.example;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Navigator {
    // List of pages
    private Scene[] scenes;
    // Default window dimensions
    private int windowWidth;
    private int windowHeight;
    // Index of stack head
    private int headIndex;
    // The stage that the navigator works on
    private Stage stage;
    // The default scene
    private Scene defaultScene;

    Navigator(Stage stage) {
        this.stage = stage;
        this.windowWidth = 640;
        this.windowHeight = 480;
        this.scenes = new Scene[] {};
        this.headIndex = -1;
        this.defaultScene = this.createDefaultScene();
    }

    Navigator(Stage stage, int windowWidth, int windowHeight) {
        this.stage = stage;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.scenes = new Scene[] {};
        this.headIndex = -1;
        this.defaultScene = this.createDefaultScene();
    }

    Navigator(Stage stage, int windowWidth, int windowHeight, StackPane initialPane) {
        this.stage = stage;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.scenes = new Scene[] { new Scene(initialPane, this.windowWidth, this.windowHeight) };
        this.headIndex = 0;
        this.defaultScene = this.createDefaultScene();
    }

    private Scene createDefaultScene() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        StackPane stackPane = new StackPane(l);
        Scene scene = new Scene(stackPane, this.windowWidth, this.windowHeight);
        return scene;
    }

    void push(StackPane pane) {
        headIndex++;
        this.scenes[headIndex] = new Scene(pane, this.windowWidth, this.windowHeight);
        this.stage.setScene(this.scenes[headIndex]);
    }

    void pop() {
        this.scenes[headIndex] = null;
        headIndex--;
        this.stage.setScene(this.scenes[headIndex]);
    }

    boolean canPop() {
        return headIndex > 1;
    }
}
