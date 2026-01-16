package com.example.memorycardgame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Leaderboard {
    private static final String FILE_NAME = "scores.txt";
    private final Stage stage;

    public Leaderboard(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        // --- TIÊU ĐỀ ---
        Label lblTitle = new Label("HIGHSCORES");
        lblTitle.getStyleClass().add("title-large");
        lblTitle.setStyle("-fx-font-size: 50px; -fx-text-fill: white; -fx-font-weight: bold;");

        // --- TABS CHỌN ĐỘ KHÓ ---
        HBox tabs = new HBox(20);
        tabs.setAlignment(Pos.CENTER);

        Button btnEasy = new Button("EASY");
        Button btnMedium = new Button("MEDIUM");
        Button btnHard = new Button("HARD");

        String tabStyle = "-fx-min-width: 180px; -fx-font-size: 20px; -fx-padding: 10 20;";
        btnEasy.getStyleClass().add("button");
        btnMedium.getStyleClass().add("button");
        btnHard.getStyleClass().add("button");

        btnEasy.setStyle(tabStyle);
        btnMedium.setStyle(tabStyle);
        btnHard.setStyle(tabStyle);

        // --- BẢNG ĐIỂM ---
        VBox scoreContainer = new VBox();
        scoreContainer.setAlignment(Pos.CENTER);
        scoreContainer.setMaxHeight(480); // Tăng nhẹ không gian cho 10 hàng

        List<ScoreEntry> allEntries = loadAndParseScores();

        // Mặc định hiển thị bảng Easy
        updateTable(scoreContainer, allEntries, "Easy");

        btnEasy.setOnAction(e -> updateTable(scoreContainer, allEntries, "Easy"));
        btnMedium.setOnAction(e -> updateTable(scoreContainer, allEntries, "Medium"));
        btnHard.setOnAction(e -> updateTable(scoreContainer, allEntries, "Hard"));

        tabs.getChildren().addAll(btnEasy, btnMedium, btnHard);

        // --- NÚT BACK ---
        Button btnBack = new Button("RETURN");
        btnBack.getStyleClass().add("button");
        btnBack.setStyle("-fx-min-width: 250px; -fx-font-size: 24px;");
        btnBack.setOnAction(e -> new Menu(stage).show());

        // --- BỐ CỤC TỔNG THỂ ---
        VBox root = new VBox(25, lblTitle, tabs, scoreContainer, btnBack);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Background
        BackgroundImage bg = new BackgroundImage(
                new Image(getClass().getResource("background.jpg").toExternalForm(),
                        Main.WIDTH, Main.HEIGHT, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        root.setBackground(new Background(bg));

        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
    }

    private void updateTable(VBox container, List<ScoreEntry> allEntries, String difficulty) {
        container.getChildren().clear();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(50);
        grid.setVgap(10);
        grid.getStyleClass().add("score-list-container");

        // Header Style
        String hStyle = "-fx-font-weight: bold; -fx-text-fill: #ffcc00; -fx-font-size: 26px;";
        grid.add(new Label("#"), 0, 0);
        grid.add(new Label("NAME"), 1, 0);
        grid.add(new Label("MOVES"), 2, 0);
        grid.add(new Label("TIME"), 3, 0);
        grid.getChildren().forEach(node -> node.setStyle(hStyle));

        // Lấy giữ liệu
        List<ScoreEntry> filtered = allEntries.stream()
                .filter(e -> e.difficulty.equalsIgnoreCase(difficulty))
                .sorted(Comparator.comparingInt((ScoreEntry e) -> e.moves)
                        .thenComparing(e -> e.time))
                .limit(10)
                .collect(Collectors.toList());

        // Data Style
        String dStyle = "-fx-text-fill: white; -fx-font-size: 22px; -fx-font-family: 'Consolas', monospace;";

        // setup tralala
        for (int i = 0; i < 10; i++) {
            Label r = new Label(String.valueOf(i + 1));
            Label n, m, t;

            if (i < filtered.size()) {
                // Hàng có dữ liệu
                ScoreEntry e = filtered.get(i);
                n = new Label(e.name.isEmpty() ? "---" : e.name);
                m = new Label(String.valueOf(e.moves));
                t = new Label(e.time);
            } else {
                // Hàng trống
                n = new Label("---");
                m = new Label("---");
                t = new Label("--:--");
            }

            r.setStyle(dStyle); n.setStyle(dStyle); m.setStyle(dStyle); t.setStyle(dStyle);
            grid.add(r, 0, i + 1);
            grid.add(n, 1, i + 1);
            grid.add(m, 2, i + 1);
            grid.add(t, 3, i + 1);
        }

        container.getChildren().add(grid);
    }

    private List<ScoreEntry> loadAndParseScores() {
        List<ScoreEntry> entries = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return entries;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split("\\|");
                    if (parts.length < 4) continue;
                    String diff = parts[0].split(":")[1].trim();
                    String name = parts[1].split(":")[1].trim();
                    String timeVal = parts[2].substring(parts[2].indexOf(":") + 1).trim();
                    int moves = Integer.parseInt(parts[3].split(":")[1].trim());
                    entries.add(new ScoreEntry(diff, name, timeVal, moves));
                } catch (Exception e) { }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return entries;
    }

    public static void saveScore(String difficulty, String name, String time, int moves) {
        if (difficulty.toLowerCase().contains("custom")) return;
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(String.format("Diff: %s | Name: %s | Time: %s | Moves: %d%n",
                    difficulty, name, time, moves));
        } catch (IOException e) { e.printStackTrace(); }
    }

    private static class ScoreEntry {
        final String difficulty, name, time;
        final int moves;
        public ScoreEntry(String d, String n, String t, int m) {
            this.difficulty = d; this.name = n; this.time = t; this.moves = m;
        }
    }
}