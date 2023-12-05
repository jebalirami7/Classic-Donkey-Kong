package main.java.com.game.controllers;

import App;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.java.com.game.App;
import main.java.com.game.Game;
import javafx.scene.input.MouseEvent;

public class Controller {

    private GraphicsContext gc;

    @FXML
    private Label newGameLabel;

    private void handleButtonClick(MouseEvent event) {
        // Load the new scene
        try {
            Group root = new Group();
            Canvas canvas = new Canvas(App.width, App.height);
            root.getChildren().add(canvas);

            gc = canvas.getGraphicsContext2D();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Another Scene");
            stage.show();

            Scene scene = new Scene(root); 

            Game game = new Game();
            game.run(root, gc, scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}