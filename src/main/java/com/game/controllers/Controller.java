package main.java.com.game.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.java.com.game.App;
import main.java.com.game.Game;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;


public class Controller implements Initializable {

    @FXML private VBox container ;
    @FXML private Label newGameLabel;
    @FXML private Label continueLabel;
    @FXML private Label scoreBoardLabel;
    @FXML private Label exitLabel;
    
    private Label[] menuItems;

    private int selectedIndex;


    public Controller()  {

    }


    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        menuItems = new Label[]{newGameLabel, continueLabel, scoreBoardLabel, exitLabel};
        selectedIndex = 0;
        updateSelection();
        for (Label menuItem : menuItems) {
            menuItem.setFocusTraversable(true);
        }
        menuItems[selectedIndex].requestFocus();
        System.out.println("done init");
    }


    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            moveUP();
        } else if (event.getCode() == KeyCode.DOWN) {
            moveDOWN();
        } else if (event.getCode() == KeyCode.ENTER) {
            handleMenuItemAction(selectedIndex);
        }
    }


    private void moveUP() {

        selectedIndex = (selectedIndex -1) ;
        if (selectedIndex < 0) selectedIndex = menuItems.length -1 ;
        updateSelection();
        
    }


    private void moveDOWN() {
        
        selectedIndex = selectedIndex +1 ;
        if (selectedIndex >= menuItems.length) selectedIndex = 0;
        updateSelection();
        
    }


    private void updateSelection() {
        for (int i = 0; i < menuItems.length; i++) {
            if (i == selectedIndex) {
                menuItems[i].setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
            } else {
                menuItems[i].setStyle("-fx-text-fill: black;");
            }
        }
    }

    
    @FXML
    private void handleMenuItemAction(int selectedIndex) {
        switch (selectedIndex) {
            case 0:
                newGame();
                break;

            case 3:
                System.exit(0);
        
            default:
                break;
        }

    }

    
    public void newGame() {

        // Load the new scene
        Group root = new Group();
        Canvas canvas = new Canvas(App.width, App.height);
        root.getChildren().add(canvas);
        GraphicsContext  gc = canvas.getGraphicsContext2D();
        Scene scene = new Scene(root); 

        Stage stage = (Stage) container.getScene().getWindow();
        stage.show();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = screenBounds.getMinX() + (screenBounds.getWidth() - App.width) / 2;
        double centerY = screenBounds.getMinY() + (screenBounds.getHeight() - App.height) / 2;
        
        stage.setX(centerX);
        stage.setY(centerY);
        stage.setScene(scene);

        Game game = new Game();
        game.run(root, gc , scene);
            
    }






    
}