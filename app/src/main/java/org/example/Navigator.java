package org.example;

import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/*
 * A navigator for the entire app.
 * 
 * This abstracts the app into a series of pages that can be navigated within.
 */
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
    public Scene defaultScene;

    /*
     * Setup an empty navigator for a given Stage.
     */
    Navigator(Stage stage) {
        this.stage = stage;
        this.windowWidth = 640;
        this.windowHeight = 480;
        this.defaultScene = this.createDefaultScene();
        this.scenes = new Scene[10];
        this.scenes[0] = this.defaultScene;
        this.headIndex = 0;
    }

    /*
     * Setup a default navigator with the specified width and height for a given
     * stage.
     */
    Navigator(Stage stage, int windowWidth, int windowHeight) {
        this.stage = stage;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.defaultScene = this.createDefaultScene();
        this.scenes = new Scene[10];
        this.scenes[0] = this.defaultScene;
        this.headIndex = 0;
    }

    /*
     * Setup a naviagtor with the given intialPane with the given window dimensions
     * for the given stage.
     */
    Navigator(Stage stage, int windowWidth, int windowHeight, Parent initialPane) {
        this.stage = stage;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.scenes = new Scene[10];
        this.scenes[0] = new Scene(initialPane, this.windowWidth, this.windowHeight);
        this.headIndex = 0;
        this.defaultScene = this.createDefaultScene();
    }

    // Creates a default scene for the default navigator.
    private Scene createDefaultScene() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        StackPane stackPane = new StackPane(l);
        Scene scene = new Scene(stackPane, this.windowWidth, this.windowHeight);
        return scene;
    }

    public void startNavigator() {
        this.stage.show();
        this.stage.setScene(this.scenes[headIndex]);
    }

    /*
     * Push a pane onto the stack of pages.
     */
    public void push(Parent pane) {
        headIndex++;
        this.scenes[headIndex] = new Scene(pane, this.windowWidth, this.windowHeight);
        this.stage.setScene(this.scenes[headIndex]);
    }

    /*
     * Push a scene onto the stack of pages.
     */
    public void push(Scene scene) {
        headIndex++;
        this.scenes[headIndex] = scene;
        this.stage.setScene(this.scenes[headIndex]);
    }

    /*
     * Pop the topmost pane off the navigation stack.
     */
    public void pop() {
        this.scenes[headIndex] = null;
        headIndex--;
        this.stage.setScene(this.scenes[headIndex]);
    }

    /*
     * Returns true if there is atleast one pane below the current pane in the
     * naviagtion stack.
     * 
     * Attempting to pop a page when this method returns false will cause the
     * default pane to be shown.
     */
    public boolean canPop() {
        return headIndex > 1;
    }

    /*
     * Set the window title for the current stage.
     */
    public void setTitle(String title) {
        this.stage.setTitle(title);
    }
}
