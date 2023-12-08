package main.java.com.game.models;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public class Game {

    private Partie partie;

    public Game(Group root, Player player, GraphicsContext gc, Scene scene) {
        partie = new Partie(root, player);
    }

    public void run (GraphicsContext gc, Scene scene) {

        partie.createPartie( gc, scene );

    }
    
    
}
