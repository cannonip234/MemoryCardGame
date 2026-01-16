package com.example.memorycardgame;

import javafx.animation.PauseTransition;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import java.util.*;

public class Board {
    private final int rows, cols;
    private final GridPane view = new GridPane();
    private final MoveCounter moveCounter;
    private final GameTimer timer;
    private final Runnable onWin;
    private final AnimationManager animationManager;
    private final List<Card> allCards = new ArrayList<>();
    private final Deque<Card> opened = new ArrayDeque<>();
    private boolean isProcessing = false;

    public Board(int rows, int cols, MoveCounter moveCounter, GameTimer timer, Runnable onWin, AnimationManager animationManager) {
        this.rows = rows; this.cols = cols; this.moveCounter = moveCounter;
        this.timer = timer; this.onWin = onWin; this.animationManager = animationManager;
        init();
    }

    private void init() {
        view.getChildren().clear();
        view.setHgap(10);
        view.setVgap(10);
        view.setAlignment(Pos.CENTER);

        view.setStyle("-fx-padding: 10;");

        List<Integer> ids = new ArrayList<>();
        for (int i = 1; i <= (rows * cols) / 2; i++) { ids.add(i); ids.add(i); }
        Collections.shuffle(ids);

        allCards.clear();
        Iterator<Integer> it = ids.iterator();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Card card = new Card(it.next(), animationManager);
                card.setOnMouseClicked(e -> onCardClicked(card));
                allCards.add(card);
                view.add(card, c, r);
            }
        }
    }

    private void onCardClicked(Card card) {
        if (isProcessing || card.isMatched() || card.isFlipped()) return;
        card.flip();
        opened.addLast(card);
        if (opened.size() == 2) {
            moveCounter.increment();
            isProcessing = true;
            Card a = opened.pollFirst(); Card b = opened.pollFirst();
            if (a.getCardId() == b.getCardId()) {
                MusicManager.playMatchSfx();
                a.setMatched(true); b.setMatched(true);
                isProcessing = false; checkWinLater();
            } else {
                MusicManager.playMissSfx();
                PauseTransition delay = new PauseTransition(Duration.seconds(1.0));
                delay.setOnFinished(ev -> { a.flip(); b.flip(); isProcessing = false; });
                delay.play();
            }
        }
    }

    private void checkWinLater() {
        if (allCards.stream().allMatch(Card::isMatched)) {
            PauseTransition p = new PauseTransition(Duration.seconds(0.8));
            p.setOnFinished(e -> onWin.run());
            p.play();
        }
    }

    public GridPane getView() { return view; }
}