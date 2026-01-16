package com.example.memorycardgame;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class Main extends Application {

    public static final double WIDTH = 1600;
    public static final double HEIGHT = 900;

    private static MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) {
        startBackgroundMusic();


        primaryStage.setTitle("Memory Card Game");


        primaryStage.setResizable(false);


        Menu menu = new Menu(primaryStage);
        menu.show();
    }

    private void startBackgroundMusic() {
        try {
            URL resource = getClass().getResource("/music/background_music.mp3");

            if (resource == null) {
                System.out.println("Không tìm thấy file nhạc nền tại /music/");
                return;
            }

            Media media = new Media(resource.toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.3);
            mediaPlayer.play();

        } catch (Exception e) {
            System.err.println("Lỗi khi khởi tạo nhạc nền: " + e.getMessage());
        }
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static void main(String[] args) {
        launch(args);
    }
}