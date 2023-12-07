package main.java.com.game;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music {
    private MediaPlayer mediaPlayer;

    public Music(String audioPath) {
        try {
            // mediaPlayer = new MediaPlayer(new Media(audioPath));
            File file = new File(audioPath);
            Media media = new Media(file.toURI().toString());
            System.out.println(file.toURI().toString());
        } catch (Exception e) {
            System.out.println("Exception Message: " + e.getMessage());
        }
    }


    public void play() {
        mediaPlayer.play();
    }


    public void stop() {
        mediaPlayer.stop();
    }
    
    
}
