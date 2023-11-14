package main.java.com.game ;

import java.util.Arrays;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    public static final int width = 1024;
    public static final int height = 768;
    public static final int section_width = width / 32;
    public static final int section_height = height / 32;
    public static final int slope = section_height / 8;

    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Parent root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/stageOne.fxml"));

        primaryStage.setTitle("Donkey Kong");
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        
        drawScreen(root);

        Scene scene = new Scene(root); 
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawScreen(Group root) {
        // Draw Background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        // Initialize bridges rows
        int start_y = height - 2 * section_height;
        int row2_y = start_y - 4 * section_height;
        int row3_y = row2_y - 7 * slope - 3 * section_height;
        int row4_y = row3_y - 4 * section_height;
        int row5_y = row4_y - 7 * slope - 3 * section_height;
        int row6_y = row5_y - 4 * section_height;
        // int row6_top = row6_y - 4 * slope;
        // int row5_top = row5_y - 8 * slope;
        // int row4_top = row4_y - 8 * slope;
        // int row3_top = row3_y - 8 * slope;
        // int row2_top = row2_y - 8 * slope;
        // int row1_top = start_y - 5 * slope;

        // Create level 1 (just a test, we are gonna move it to class Partie)
        Level lvl1 = new Level(Arrays.asList(
            Arrays.asList(1, start_y, 15), Arrays.asList(16, start_y - slope, 3),
            Arrays.asList(19, start_y - 2 * slope, 3), Arrays.asList(22, start_y - 3 * slope, 3),
            Arrays.asList(25, start_y - 4 * slope, 3), Arrays.asList(28, start_y - 5 * slope, 3),
            Arrays.asList(25, row2_y, 3), Arrays.asList(22, row2_y - slope, 3),
            Arrays.asList(19, row2_y - 2 * slope, 3), Arrays.asList(16, row2_y - 3 * slope, 3),
            Arrays.asList(13, row2_y - 4 * slope, 3), Arrays.asList(10, row2_y - 5 * slope, 3),
            Arrays.asList(7, row2_y - 6 * slope, 3), Arrays.asList(4, row2_y - 7 * slope, 3),
            Arrays.asList(2, row2_y - 8 * slope, 2), Arrays.asList(4, row3_y, 3),
            Arrays.asList(7, row3_y - slope, 3), Arrays.asList(10, row3_y - 2 * slope, 3),
            Arrays.asList(13, row3_y - 3 * slope, 3), Arrays.asList(16, row3_y - 4 * slope, 3),
            Arrays.asList(19, row3_y - 5 * slope, 3), Arrays.asList(22, row3_y - 6 * slope, 3),
            Arrays.asList(25, row3_y - 7 * slope, 3), Arrays.asList(28, row3_y - 8 * slope, 2),
            Arrays.asList(25, row4_y, 3), Arrays.asList(22, row4_y - slope, 3),
            Arrays.asList(19, row4_y - 2 * slope, 3), Arrays.asList(16, row4_y - 3 * slope, 3),
            Arrays.asList(13, row4_y - 4 * slope, 3), Arrays.asList(10, row4_y - 5 * slope, 3),
            Arrays.asList(7, row4_y - 6 * slope, 3), Arrays.asList(4, row4_y - 7 * slope, 3),
            Arrays.asList(2, row4_y - 8 * slope, 2), Arrays.asList(4, row5_y, 3),
            Arrays.asList(7, row5_y - slope, 3), Arrays.asList(10, row5_y - 2 * slope, 3),
            Arrays.asList(13, row5_y - 3 * slope, 3), Arrays.asList(16, row5_y - 4 * slope, 3),
            Arrays.asList(19, row5_y - 5 * slope, 3), Arrays.asList(22, row5_y - 6 * slope, 3),
            Arrays.asList(25, row5_y - 7 * slope, 3), Arrays.asList(28, row5_y - 8 * slope, 2),
            Arrays.asList(25, row6_y, 3), Arrays.asList(22, row6_y - slope, 3),
            Arrays.asList(19, row6_y - 2 * slope, 3), Arrays.asList(16, row6_y - 3 * slope, 3),
            Arrays.asList(2, row6_y - 4 * slope, 14), Arrays.asList(13, row6_y - 4 * section_height, 6),
            Arrays.asList(10, row6_y - 3 * section_height, 3)
            ));
        lvl1.create(root);
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}