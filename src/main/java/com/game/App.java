package main.java.com.game ;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
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

    public static final int width = (int) Screen.getPrimary().getBounds().getWidth() - 800;
    public static final int height = (int) Screen.getPrimary().getBounds().getHeight() - 150;
    public static final int section_width = width / 32;
    public static final int section_height = height / 32;
    public static final int slope = section_height / 8;

    public static final double fps = 60;
    
    private GraphicsContext gc;

    private boolean isRunning = true;

    @Override
    public void start(Stage stage) throws Exception{

        stage.setTitle("Donkey Kong");
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();

        
        Scene scene = new Scene(root); 
        stage.setScene(scene);
        stage.show();

        run(root);

    }

    private void run(Group root) {
        while (isRunning) {

            Partie partie = new Partie();
            partie.createPartie(root, gc);

            break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}