package com.example.memorycardgame;

public class Difficulty {
    private final String name;
    private final int rows;
    private final int cols;

    public Difficulty(String name, int rows, int cols) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
    }

    public String getName() { return name; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
}
