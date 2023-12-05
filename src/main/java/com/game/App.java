package main.java.com.game ;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {

    // public static final double width = Screen.getPrimary().getBounds().getWidth() - Screen.getPrimary().getBounds().getWidth() * 0.4;
    // public static final double height = Screen.getPrimary().getBounds().getHeight() - Screen.getPrimary().getBounds().getHeight() * 0.1;
    public static final double width = Screen.getPrimary().getBounds().getWidth() - 800;
    public static final double height = Screen.getPrimary().getBounds().getHeight() - 150;
    public static final int section_width = (int)width / 32;
    public static final int section_height = (int)height / 32;
    public static final int slope = section_height / 8;

    public static final double fps = 60;
    
    private GraphicsContext gc;

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

        Game game = new Game();
        game.run(root, gc, scene);

    }

    public static void main(String[] args) {
        launch(args);
    }
}