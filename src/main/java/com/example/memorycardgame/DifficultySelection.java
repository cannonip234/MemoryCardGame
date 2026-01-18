package com.example.memorycardgame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DifficultySelection {
    private final Stage stage;

    public DifficultySelection(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        // 1. Tiêu đề chính
        Label lblTitle = new Label("SELECT DIFFICULTY");
        lblTitle.getStyleClass().add("title-large");

        // 2. nút chọn diff
        Button btnEasy = new Button("EASY (2x4)");
        Button btnMedium = new Button("MEDIUM (4x4)");
        Button btnHard = new Button("HARD (6x6)");
        Button btnCustom = new Button("CUSTOM SIZE");
        Button btnBack = new Button("RETURN");

        btnEasy.getStyleClass().add("button");
        btnMedium.getStyleClass().add("button");
        btnHard.getStyleClass().add("button");
        btnCustom.getStyleClass().add("button");
        btnBack.getStyleClass().add("button");

        // 3. Custom size
        Label lblCustomTitle = new Label("ENTER SIZE (Rows x Columns)");
        lblCustomTitle.getStyleClass().add("title-small");
        lblCustomTitle.setStyle("-fx-font-size: 30px; -fx-text-fill: white;");

        TextField txtRows = new TextField();
        txtRows.setPromptText("Rows (2-6)");
        txtRows.setPrefSize(120, 50);
        txtRows.setStyle("-fx-font-size: 20px;");

        TextField txtCols = new TextField();
        txtCols.setPromptText("Cols (2-13)");
        txtCols.setPrefSize(120, 50);
        txtCols.setStyle("-fx-font-size: 20px;");

        Button btnStartCustom = new Button("START");
        btnStartCustom.getStyleClass().add("button");
        btnStartCustom.setStyle("-fx-min-width: 150px; -fx-font-size: 22px; -fx-padding: 10 20;");

        Label lblError = new Label("");
        lblError.setStyle("-fx-text-fill: #ff4d4d; -fx-font-weight: bold; -fx-font-size: 22px;");

        HBox inputBox = new HBox(20, txtRows, new Label("x"), txtCols, btnStartCustom);
        inputBox.setAlignment(Pos.CENTER);
        ((Label)inputBox.getChildren().get(1)).setStyle("-fx-font-size: 30px; -fx-text-fill: white;");

        VBox customInputBox = new VBox(20, lblCustomTitle, inputBox, lblError);
        customInputBox.setAlignment(Pos.CENTER);
        customInputBox.setPadding(new Insets(20, 0, 20, 0));

        VBox.setMargin(lblError, new Insets(40, 0, 0, 0));

        customInputBox.setVisible(false);
        customInputBox.setManaged(false);

        // 4. Xử lý Logic event
        btnEasy.setOnAction(e -> new Game(stage, new Difficulty("Easy", 2, 4)).show());
        btnMedium.setOnAction(e -> new Game(stage, new Difficulty("Medium", 4, 4)).show());
        btnHard.setOnAction(e -> new Game(stage, new Difficulty("Hard", 6, 6)).show());

        btnCustom.setOnAction(e -> {
            boolean isVisible = customInputBox.isVisible();
            customInputBox.setVisible(!isVisible);
            customInputBox.setManaged(!isVisible);
            btnCustom.setText(isVisible ? "CUSTOM SIZE" : "CLOSE CUSTOM");
            lblError.setText("");
            if (!isVisible) {
                txtRows.clear();
                txtCols.clear();
            }
        });

        btnStartCustom.setOnAction(e -> {
            lblError.setText("");
            try {
                int rows = Integer.parseInt(txtRows.getText().trim());
                int cols = Integer.parseInt(txtCols.getText().trim());

                if (rows < 2 || rows > 6 || cols < 2 || cols > 13) {
                    lblError.setText("Row (2-6) and Column (2-13) only.");
                } else if ((rows * cols) % 2 != 0) {
                    lblError.setText("The total number of cards must be even!");
                } else {
                    new Game(stage, new Difficulty(rows + "x" + cols, rows, cols)).show();
                }
            } catch (NumberFormatException ex) {
                lblError.setText("Please enter positive integers.");
            }
        });

        btnBack.setOnAction(e -> new Menu(stage).show());

        // 5. Layout
        VBox difficultyButtons = new VBox(25, btnEasy, btnMedium, btnHard, btnCustom);
        difficultyButtons.setAlignment(Pos.CENTER);

        VBox root = new VBox(40);
        root.setAlignment(Pos.CENTER);

        Region spacer1 = new Region();
        VBox.setVgrow(spacer1, Priority.ALWAYS);
        Region spacer2 = new Region();
        VBox.setVgrow(spacer2, Priority.ALWAYS);

        root.getChildren().addAll(lblTitle, spacer1, difficultyButtons, customInputBox, spacer2, btnBack);
        root.setPadding(new Insets(50, 0, 50, 0));

        // Background
        BackgroundImage bg = new BackgroundImage(
                new Image(getClass().getResource("background.jpg").toExternalForm(),
                        Main.WIDTH, Main.HEIGHT, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(bg));

        // 6. Scene Setup
        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setScene(scene);
    }
}