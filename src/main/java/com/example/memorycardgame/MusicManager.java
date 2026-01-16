package com.example.memorycardgame;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import java.util.Random;

public class MusicManager {

    private static final int NUM_GAME_TRACKS = 5;

    private static MediaPlayer gamePlayer = null;
    private static MediaPlayer winPlayer = null; // MediaPlayer dành cho nhạc Chiến thắng

    //phát nhạc ngẫu nhiên cho game
    public static void playRandomGameMusic() {

        MediaPlayer menuPlayer = Main.getMediaPlayer();
        if (menuPlayer != null) {
            menuPlayer.stop();
        }
        if (gamePlayer != null) {
            gamePlayer.stop();
        }
        if (winPlayer != null) {
            winPlayer.stop();
            winPlayer.dispose();
            winPlayer = null;
        }

        try {
            Random random = new Random();
            int trackNumber = random.nextInt(NUM_GAME_TRACKS) + 1;
            String fileName = String.format("/music/bgm_%d.mp3", trackNumber);

            URL resource = MusicManager.class.getResource(fileName);
            if (resource == null) {
                System.out.println("LỖI: Không tìm thấy file nhạc Game: " + fileName);
                return;
            }

            Media media = new Media(resource.toExternalForm());
            gamePlayer = new MediaPlayer(media);

            gamePlayer.setCycleCount(MediaPlayer.INDEFINITE);
            gamePlayer.setVolume(0.3);
            gamePlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi khởi tạo nhạc Game.");
        }
    }

    //phát nhạc game
    public static void returnToMenuMusic() {
        if (gamePlayer != null) {
            gamePlayer.stop();
        }
        if (winPlayer != null) {
            winPlayer.stop();
            winPlayer.dispose();
            winPlayer = null;
        }

        MediaPlayer menuPlayer = Main.getMediaPlayer();
        if (menuPlayer != null) {
            menuPlayer.play();
        }
    }


    public static void stopAllMusic() {
        MediaPlayer menuPlayer = Main.getMediaPlayer();
        if (menuPlayer != null) {
            menuPlayer.stop();
        }
        if (gamePlayer != null) {
            gamePlayer.stop();
        }
        if (winPlayer != null) {
            winPlayer.stop();
            winPlayer.dispose();
            winPlayer = null;
        }
    }

    //nhạc win menu
    public static void playWinMusic() {
        if (gamePlayer != null) {
            gamePlayer.stop();
        }
        if (winPlayer != null) {
            winPlayer.stop();
            winPlayer.dispose();
            winPlayer = null;
        }

        try {
            URL resource = MusicManager.class.getResource("/music/win_music.mp3");
            if (resource == null) {
                System.out.println("LỖI: Không tìm thấy file nhạc Chiến thắng: win_music.mp3");
                return;
            }

            Media media = new Media(resource.toExternalForm());
            winPlayer = new MediaPlayer(media);

            winPlayer.setVolume(0.4);
            winPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi khởi tạo nhạc Chiến thắng.");
        }
    }


    //Xử lý sfx
    private static void playSfx(String fileName) {
        try {
            URL resource = MusicManager.class.getResource(fileName);
            if (resource == null) {
                System.out.println("LỖI: Không tìm thấy file SFX: " + fileName);
                return;
            }

            Media media = new Media(resource.toExternalForm());
            MediaPlayer sfxPlayer = new MediaPlayer(media);

            sfxPlayer.setVolume(0.5);
            sfxPlayer.setOnEndOfMedia(sfxPlayer::dispose);
            sfxPlayer.play();

        } catch (Exception e) {
            System.err.println("Lỗi khi phát SFX: " + fileName + ". " + e.getMessage());
        }
    }

    public static void playMatchSfx() {
        playSfx("/music/sfx_match.mp3");
    }

    public static void playMissSfx() {
        playSfx("/music/sfx_miss.mp3");
    }
}