package main.java.com.game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music {
    private MediaPlayer mediaPlayer;

    public Music(String audioPath) {
        Media media = new Media(getClass().getResource(audioPath).toString());
        mediaPlayer = new MediaPlayer(media);
    }


    public void stop() {
        mediaPlayer.stop();
    }


    public void autoPlay(boolean isPlaying) {
        mediaPlayer.setAutoPlay(isPlaying);
    }


    public void play() {
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(mediaPlayer.getStartTime()));
    }
    
    
}
