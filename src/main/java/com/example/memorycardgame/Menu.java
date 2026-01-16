package com.example.memorycardgame;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Menu {
    private final Stage stage;

    public Menu(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        AnchorPane root = new AnchorPane();

        // 1. Background
        BackgroundImage bg = new BackgroundImage(
                new Image(getClass().getResource("background.jpg").toExternalForm(),
                        Main.WIDTH, Main.HEIGHT, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(bg));

        // 2. Tiêu đề
        Label lblSmall = new Label("Project I");
        lblSmall.getStyleClass().add("title-small"); // Nhận font 32px từ CSS

        Label lblLarge = new Label("Memory Card Game");
        lblLarge.getStyleClass().add("title-large"); // Nhận font 85px từ CSS

        VBox titleBox = new VBox(5, lblSmall, lblLarge);
        titleBox.setAlignment(Pos.CENTER);

        AnchorPane.setTopAnchor(titleBox, 120.0);
        AnchorPane.setLeftAnchor(titleBox, 0.0);
        AnchorPane.setRightAnchor(titleBox, 0.0);

        // 3. Buttons
        Button btnNew = new Button("New Game");
        Button btnHigh = new Button("Highscore");
        Button btnExit = new Button("Exit");

        btnNew.getStyleClass().add("button");
        btnHigh.getStyleClass().add("button");
        btnExit.getStyleClass().add("button");

        // Logic chuyển cảnh
        btnNew.setOnAction(e -> new DifficultySelection(stage).show());
        btnHigh.setOnAction(e -> new Leaderboard(stage).show());
        btnExit.setOnAction(e -> stage.close());

        VBox buttonBox = new VBox(25, btnNew, btnHigh, btnExit);
        buttonBox.setAlignment(Pos.CENTER);

        AnchorPane.setTopAnchor(buttonBox, 420.0);
        AnchorPane.setLeftAnchor(buttonBox, 0.0);
        AnchorPane.setRightAnchor(buttonBox, 0.0);

        root.getChildren().addAll(titleBox, buttonBox);

        // 4. Thiết lập Scene
        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("Memory Card Game");
        stage.setScene(scene);

        if (!stage.isShowing()) {
            stage.show();
            stage.centerOnScreen();
        }
    }
}