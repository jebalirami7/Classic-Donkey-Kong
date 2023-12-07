package main.java.com.game;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public class Game {

    private Partie partie;

    public Game(Group root, Player player) {
        partie = new Partie(root, player);
    }

    public void run (GraphicsContext gc, Scene scene) {

        partie.createPartie( gc, scene);

    }
    
    
}

