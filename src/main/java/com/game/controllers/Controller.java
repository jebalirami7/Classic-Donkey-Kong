package main.java.com.game.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.java.com.game.App;
import main.java.com.game.Game;
import main.java.com.game.Mario;
import main.java.com.game.Player;
import main.java.com.game.SaveData;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class Controller implements Initializable {

    @FXML private VBox container ;
    @FXML private Label newGameLabel;
    @FXML private Label continueLabel;
    @FXML private Label scoreBoardLabel;
    @FXML private Label exitLabel;
    
    private Label[] menuItems;

    private int selectedIndex;

    TableView<Player> tableView;

    public Controller()  {
        
    }


    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        tableView = new TableView<>();
        createTableView();
        menuItems = new Label[]{newGameLabel, continueLabel, scoreBoardLabel, exitLabel};
        selectedIndex = 0;
        updateSelection();
        for (Label menuItem : menuItems) {
            menuItem.setFocusTraversable(true);
        }
        menuItems[selectedIndex].requestFocus(); 
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
                addPlayer();
                break;
            case 1:
                selectPlayer();
                break;
            case 2:
                break;
            case 3:
                Platform.exit();
            default:
                break;
        }

    }


    public void newGame(Player player) {
        Group root = new Group();
        Canvas canvas = new Canvas(App.width, App.height);
        root.getChildren().add(canvas);
        GraphicsContext  gc = canvas.getGraphicsContext2D();
        Scene scene = new Scene(root); 

        Stage stage = (Stage) container.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = screenBounds.getMinX() + (screenBounds.getWidth() - App.width) / 2;
        double centerY = (screenBounds.getHeight() - App.height) / 2;
        
        stage.setX(centerX);
        stage.setY(centerY);
        stage.setScene(scene);


        Game game = new Game(root, player);
        game.run(gc, scene);
    }


    public void addPlayer() {
        // Load the new scene
        
        Popup popup = new Popup();

        TextField textField = new TextField();

        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.ENTER) {
                    popup.hide();
                    Player player = new Player(textField.getText(), 0);
                    SaveData.write(player);
                    tableView.getItems().add(player);
                    newGame(player);
                }
            }
        });

        VBox popupLayout = new VBox(10); 
        popupLayout.setAlignment(Pos.CENTER);
        popupLayout.getChildren().addAll(textField);

        popupLayout.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                KeyCode key = e.getCode();
                if (key == KeyCode.ESCAPE || key == KeyCode.LEFT) {
                    popup.hide();
                }
            }
        });

        popup.getContent().add(popupLayout);

        popupLayout.setMinSize(200, 50);
        popupLayout.setMaxSize(400, 148);   
        popupLayout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); -fx-background-radius: 10;");         
        popup.setAutoHide(true); 
        
        Stage stage = (Stage) container.getScene().getWindow();
        double ownerX = stage.getX();
        double ownerY = stage.getY();
        double ownerWidth = stage.getWidth();
        double ownerHeight = stage.getHeight();
        
        popup.show(stage, ownerX + ownerWidth / 2, ownerY + ownerHeight / 2);

    }

    
    public void selectPlayer() {
        // Load the new scene
        
        Popup popup = new Popup();

        VBox popupLayout = new VBox(10); 
        popupLayout.setAlignment(Pos.CENTER);
        popupLayout.getChildren().addAll(tableView);

        popupLayout.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                KeyCode key = e.getCode();
                if (key == KeyCode.ESCAPE || key == KeyCode.LEFT) {
                    popup.hide();
                }
            }
        });

        popup.getContent().add(popupLayout);

        popupLayout.setMinSize(200, 50);
        popupLayout.setMaxSize(400, 148);   
        popupLayout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); -fx-background-radius: 10;");         
        popup.setAutoHide(true); 
        
        Stage stage = (Stage) container.getScene().getWindow();
        double ownerX = stage.getX();
        double ownerY = stage.getY();
        double ownerWidth = stage.getWidth();
        double ownerHeight = stage.getHeight();
        
        popup.show(stage, ownerX + ownerWidth / 2, ownerY + ownerHeight / 2);

    }


    private void createTableView() {

        tableView = new TableView<>();
        
        TableColumn<Player, String> nameColumn = new TableColumn<>("NAME");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, Integer> scoreColumn = new TableColumn<>("SCORE");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        nameColumn.setStyle("-fx-alignment: CENTER; -fx-background-color: rgba(0, 0, 0, 0.6);");
        scoreColumn.setStyle("-fx-alignment: CENTER; -fx-background-color: rgba(0, 0, 0, 0.6);");

        tableView.getColumns().addAll(nameColumn, scoreColumn);

        Player[] playersArray = SaveData.read().entrySet().stream()
        .map(entry -> new Player(entry.getKey(), entry.getValue()))
        .toArray(Player[]::new);

        tableView.getItems().addAll(playersArray);

        tableView.getSelectionModel().selectFirst();

        tableView.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                Player selectedPlayer = tableView.getSelectionModel().getSelectedItem();
                if (selectedPlayer != null) {
                    System.out.println("Starting game with: " + selectedPlayer.getName());
                    newGame(selectedPlayer);
                }
            }
        });
    }



    
}