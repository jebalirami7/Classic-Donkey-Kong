package main.java.com.game.models;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public class Game {

    private Partie partie;
    private Scene gameScene;

    public Game(Group root, Player player, GraphicsContext gc, Scene gameScene, Scene menuScene) {
        this.gameScene = gameScene;
        partie = new Partie(root, player, menuScene);
    }

    public void run (GraphicsContext gc) {
        partie.createPartie(gc, gameScene);
    }
    
    
}

