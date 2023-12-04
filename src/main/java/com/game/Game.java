package main.java.com.game;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public class Game {

    private Partie partie ;


    public Game() {
        partie = new Partie();
    }

    public void run (Group root, GraphicsContext gc, Scene scene) {

        
        partie.createPartie(root, gc, scene);


    }
    
    
}

