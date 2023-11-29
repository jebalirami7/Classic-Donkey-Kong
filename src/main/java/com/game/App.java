package main.java.com.game ;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {

    //Images
    public static Image barrelImg = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/barrels/barrel.png");
    public static Image barrel2 = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/barrels/barrel2.png");
    public static Image fireImg = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/fire.png");
    public static Image dk1 = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/dk/dk1.png");
    public static Image dk2 = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/dk/dk2.png");
    public static Image dk3 = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/dk/dk3.png");
    public static Image peach1 = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/peach/peach1.png");
    public static Image peach2 = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/peach/peach2.png");
    public static Image fireBallImg = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/fireball.png");
    public static Image fireBall2 = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/fireball2.png");
    public static Image hammerImg = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/hammer.png");
    public static Image standing = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/mario/standing.png");
    public static Image running = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/mario/running.png");
    public static Image jumping = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/mario/jumping.png");
    public static Image climbing1 = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/mario/climbing1.png");
    public static Image climbing2 = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/mario/climbing2.png");
    public static Image hammerJump = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/mario/hammer_jump.png");
    public static Image hammerStand = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/mario/hammer_stand.png");
    public static Image hammerOverhead = new Image("file:/home/rami/Desktop/donkey-kong/src/main/resources/assets/images/mario/hammer_overhead.png");

    // public static final double width = Screen.getPrimary().getBounds().getWidth() - Screen.getPrimary().getBounds().getWidth() * 0.4;
    // public static final double height = Screen.getPrimary().getBounds().getHeight() - Screen.getPrimary().getBounds().getHeight() * 0.1;
    public static final double width = Screen.getPrimary().getBounds().getWidth() - 800;
    public static final double height = Screen.getPrimary().getBounds().getHeight() - 150;
    public static final int section_width = (int)width / 32;
    public static final int section_height = (int)height / 32;
    public static final int slope = section_height / 8;

    public static final double fps = 60;
    
    private GraphicsContext gc;

    private boolean isRunning = true;

    @Override
    public void start(Stage stage) throws Exception{

        stage.setTitle("Classic Donkey Kong");
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(root); 
        stage.setScene(scene);
        stage.show();

        run(root, scene);

    }

    private void run(Group root, Scene scene) {
        while (isRunning) {

            Partie partie = new Partie();
            partie.createPartie(root, gc, scene);

            break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}