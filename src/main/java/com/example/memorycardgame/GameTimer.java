package com.example.memorycardgame;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameTimer {
    private long startTime;
    // cái để hiển thị
    private final StringProperty timeDisplay = new SimpleStringProperty("Time: 00:00");
    // value and shit
    private final StringProperty timeValue = new SimpleStringProperty("00:00");

    private AnimationTimer timer;

    public void start() {
        startTime = System.currentTimeMillis();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateTime();
            }
        };
        timer.start();
    }

    public void stop() {
        if (timer != null) timer.stop();
    }

    public void reset() {
        stop();
        timeDisplay.set("Time: 00:00");
        timeValue.set("00:00");
    }

    private void updateTime() {
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        long minutes = elapsed / 60;
        long seconds = elapsed % 60;

        String formattedTime = String.format("%02d:%02d", minutes, seconds);


        timeDisplay.set("Time: " + formattedTime);


        timeValue.set(formattedTime);
    }


    public String getRawTimeValue() {
        return timeValue.get();
    }


    public StringProperty timeStringProperty() {
        return timeDisplay;
    }
}