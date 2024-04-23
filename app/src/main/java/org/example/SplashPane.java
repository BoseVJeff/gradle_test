package org.example;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;;

public class SplashPane implements PaneInterface {

    private Image image;

    SplashPane() {
        // System.out.println("System Dir: " + System.getProperty("user.dir"));
        System.out.println("Loading image...");
        this.image = new Image("file:logo_solo.png", false);
        System.out.println("Loaded image!");
    }

    @Override
    public Parent getPane() {
        if (this.image.isError()) {
            // Image loading error
            System.err.println("Error loading " + this.image.getUrl());
            System.err.println(this.image.getException().getMessage());
        }

        // Image img = new Image("file:logo.jpg");
        ImageInput imginput = new ImageInput();
        Rectangle rect = new Rectangle();
        imginput.setSource(this.image);
        // imginput.setX(20);
        // imginput.setY(100);
        GridPane gridPane = new GridPane();
        Group root = new Group();
        rect.setEffect(imginput);
        root.getChildren().add(rect);
        gridPane.add(root, 0, 0);
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }

}
