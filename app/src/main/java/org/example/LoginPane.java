package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class LoginPane implements PaneInterface {

    private GridPane gridPane;

    LoginPane() {
    }

    @Override
    public Parent getPane() {
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);

        TilePane tilePane = new TilePane(Orientation.HORIZONTAL);

        tilePane.setPrefColumns(2);

        StackPane leftStackPane = new StackPane();
        StackPane rightStackPane = new StackPane();

        leftStackPane.setAlignment(Pos.CENTER);
        rightStackPane.setAlignment(Pos.CENTER);

        Image logoImage = new Image("file:logo_full_crop.png", false);
        ImageInput imginput = new ImageInput();
        imginput.setSource(logoImage);
        Rectangle rect = new Rectangle();
        rect.setFill(Color.RED);
        rect.setWidth(logoImage.getWidth());
        rect.setHeight(logoImage.getHeight());
        // imginput.setX(-10);
        rect.setEffect(imginput);

        leftStackPane.getChildren().add(rect);

        Text formTitleText = new Text("Login");
        Label userNameLabel = new Label("Username:");
        TextField usernameTextField = new TextField();
        usernameTextField.setMinWidth(44);
        usernameTextField.setMaxWidth(200);
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setMinWidth(44);
        passwordField.setMaxWidth(200);
        Button loginButton = new Button("Login");
        HBox hBox = new HBox(loginButton);
        hBox.setPadding(new Insets(10));
        hBox.setAlignment(Pos.CENTER);

        VBox fieldVBox = new VBox(formTitleText, userNameLabel, usernameTextField, passwordLabel, passwordField,
                hBox);
        fieldVBox.setAlignment(Pos.CENTER);

        rightStackPane.getChildren().add(fieldVBox);

        tilePane.getChildren().add(leftStackPane);
        tilePane.getChildren().add(rightStackPane);

        tilePane.setAlignment(Pos.CENTER);
        tilePane.setHgap(10);
        tilePane.setVgap(10);

        ScrollPane scrollPane = new ScrollPane(tilePane);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);

        stackPane.getChildren().add(scrollPane);
        // stackPane.getChildren().add(tilePane);

        return stackPane;
    }

}
