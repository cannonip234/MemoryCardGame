package com.example.memorycardgame;

import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimationManager {
    public void playFlipAnimation(Node node) {
        RotateTransition rt = new RotateTransition(Duration.millis(300), node);
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.play();
    }
}
