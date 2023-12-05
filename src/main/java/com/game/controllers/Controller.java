package main.java.com.game.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.java.com.game.App;
import main.java.com.game.Game;
import javafx.scene.input.MouseEvent;

public class Controller implements Initializable{

    private GraphicsContext gc;

    @FXML
    private Label newGameLabel;
    @FXML
    private Label continueLabel;
    @FXML
    private Label scoreboardLabel;
    @FXML
    private Label exitLabel;

    @FXML
    private void handleNewGameLabelClick(MouseEvent event) {
        // Load the new scene
        Group root = new Group();
        Canvas canvas = new Canvas(App.width, App.height);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();

        Stage stage = new Stage();
        Scene scene = new Scene(root); 
        stage.setScene(scene);
        stage.show();

        Game game = new Game();
        game.run(root, gc, scene);
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newGameLabel.setOnMouseClicked(event -> handleNewGameLabelClick(event));
        exitLabel.setOnMouseClicked(event -> System.exit(0));

        
    }


}