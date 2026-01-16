package com.example.memorycardgame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WinDialog {
    private final Stage parentStage;
    private final String difficultyName;
    private final String time;
    private final int moves;
    private final Runnable onReplay;
    private final Runnable onMenu;

    public WinDialog(Stage parent, String difficultyName, String time, int moves, Runnable onReplay, Runnable onMenu) {
        this.parentStage = parent;
        this.difficultyName = difficultyName;
        this.time = time;
        this.moves = moves;
        this.onReplay = onReplay;
        this.onMenu = onMenu;
    }

    public void show() {
        Stage dialog = new Stage();
        dialog.initOwner(parentStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("You Win!");

        BackgroundImage bg = new BackgroundImage(
                new Image(getClass().getResource("win_background.jpg").toExternalForm(), 400, 500, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        Label lblTitle = new Label("VICTORY");
        lblTitle.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: white; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.9), 10, 0, 0, 0);");

        Label lblTime = new Label("Time: " + time);
        Label lblMoves = new Label("Moves: " + moves);
        lblTime.getStyleClass().add("game-stats-label");
        lblMoves.getStyleClass().add("game-stats-label");

        TextField txtName = new TextField();
        txtName.setPromptText("Enter name to save score");
        txtName.setMaxWidth(250);
        txtName.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-background-radius: 5;");

        Button btnSaveAndMenu = new Button("SAVE YOUR SCORE");
        Button btnReplay = new Button("REPLAY");

        btnSaveAndMenu.getStyleClass().add("button");
        btnReplay.getStyleClass().add("button");

        String dialogButtonStyle = "-fx-min-width: 180px; -fx-font-size: 18px; -fx-padding: 10 20;";
        btnSaveAndMenu.setStyle(btnSaveAndMenu.getStyleClass().get(0) + dialogButtonStyle);
        btnReplay.setStyle(btnReplay.getStyleClass().get(0) + dialogButtonStyle);

        btnSaveAndMenu.setOnAction(e -> {
            String playerName = txtName.getText().trim();

            if (playerName.isEmpty()) {
                playerName = "";
            }

            Leaderboard.saveScore(difficultyName, playerName, time, moves);

            dialog.close();
            onMenu.run();
        });

        btnReplay.setOnAction(e -> {
            dialog.close();
            onReplay.run();
        });

        VBox box = new VBox(25, lblTitle, lblTime, lblMoves, txtName, btnSaveAndMenu, btnReplay);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(30));
        box.setBackground(new Background(bg));

        Scene scene = new Scene(box, 400, 500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        dialog.setScene(scene);
        dialog.show();
    }
}