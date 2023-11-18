package main.java.com.game ;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    public static final int width = 1280;
    public static final int height = 960;
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
            // Draw Background
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, width, height);

            Partie partie = new Partie();
            partie.createPartie(root, gc);

            break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}