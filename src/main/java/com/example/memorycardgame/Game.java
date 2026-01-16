package com.example.memorycardgame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Game {
    private final Stage stage;
    private final Difficulty difficulty;
    private final MoveCounter moveCounter = new MoveCounter();
    private final GameTimer gameTimer = new GameTimer();
    private final AnimationManager animationManager = new AnimationManager();

    public Game(Stage stage, Difficulty difficulty) {
        this.stage = stage;
        this.difficulty = difficulty;
    }

    public void show() {
        MusicManager.playRandomGameMusic();

        AnchorPane root = new AnchorPane();

        // 1. Background
        BackgroundImage bg = new BackgroundImage(
                new Image(getClass().getResource("game_background.jpg").toExternalForm(), true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true));
        root.setBackground(new Background(bg));

        // 2. TOP BAR (Time & Moves)
        Label lblTime = new Label("Time: 00:00");
        Label lblMoves = new Label("Moves: 0");
        lblTime.getStyleClass().add("game-stats-label");
        lblMoves.getStyleClass().add("game-stats-label");

        HBox topBar = new HBox(60, lblTime, lblMoves);
        topBar.setAlignment(Pos.CENTER);

        AnchorPane.setTopAnchor(topBar, 30.0);
        AnchorPane.setLeftAnchor(topBar, 0.0);
        AnchorPane.setRightAnchor(topBar, 0.0);

        // 3. BOARD GAME
        Board board = new Board(difficulty.getRows(), difficulty.getCols(), moveCounter, gameTimer, this::onWin, animationManager);
        StackPane boardHolder = new StackPane(board.getView());
        boardHolder.setAlignment(Pos.CENTER);

        AnchorPane.setTopAnchor(boardHolder, 120.0);
        AnchorPane.setBottomAnchor(boardHolder, 150.0);
        AnchorPane.setLeftAnchor(boardHolder, 50.0);
        AnchorPane.setRightAnchor(boardHolder, 50.0);

        // 4. BOTTOM BAR (Buttons)
        Button btnRestart = new Button("RESTART");
        Button btnMenu = new Button("MENU");
        Button btnExit = new Button("EXIT");
        btnRestart.getStyleClass().add("button");
        btnMenu.getStyleClass().add("button");
        btnExit.getStyleClass().add("button");

        String btnStyle = "-fx-min-width: 200px; -fx-font-size: 22px;";
        btnRestart.setStyle(btnStyle); btnMenu.setStyle(btnStyle); btnExit.setStyle(btnStyle);

        btnRestart.setOnAction(e -> { gameTimer.stop(); new Game(stage, difficulty).show(); });
        btnMenu.setOnAction(e -> { gameTimer.stop(); MusicManager.returnToMenuMusic(); new Menu(stage).show(); });
        btnExit.setOnAction(e -> stage.close());

        HBox bottomBar = new HBox(40, btnRestart, btnMenu, btnExit);
        bottomBar.setAlignment(Pos.CENTER);

        AnchorPane.setBottomAnchor(bottomBar, 40.0);
        AnchorPane.setLeftAnchor(bottomBar, 0.0);
        AnchorPane.setRightAnchor(bottomBar, 0.0);

        root.getChildren().addAll(topBar, boardHolder, bottomBar);

        lblMoves.textProperty().bind(moveCounter.movesProperty().asString("Moves: %d"));
        lblTime.textProperty().bind(gameTimer.timeStringProperty());

        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);

        moveCounter.reset();
        gameTimer.reset();
        gameTimer.start();
    }

    private void onWin() {
        gameTimer.stop();
        MusicManager.playWinMusic();
        new WinDialog(stage, difficulty.getName(), gameTimer.getRawTimeValue(), moveCounter.getMoves(),
                () -> { MusicManager.stopAllMusic(); new Game(stage, difficulty).show(); },
                () -> { MusicManager.returnToMenuMusic(); new Menu(stage).show(); }).show();
    }
}