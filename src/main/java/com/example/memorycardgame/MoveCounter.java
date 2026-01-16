package com.example.memorycardgame;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class MoveCounter {
    private final IntegerProperty moves = new SimpleIntegerProperty(0);

    public void increment() {
        moves.set(moves.get() + 1);
    }

    public void reset() {
        moves.set(0);
    }

    public int getMoves() {
        return moves.get();
    }

    public IntegerProperty movesProperty() {
        return moves;
    }
}
