package org.example;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class HelloWorldPane implements PaneInterface {
    @Override
    public Parent getPane() {
        Label l = new Label("Hello, JavaFX");
        StackPane stackPane = new StackPane(l);
        return stackPane;
    }
}
