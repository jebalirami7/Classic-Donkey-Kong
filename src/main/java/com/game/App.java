package main.java.com.game ;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {

    public static final double width = Screen.getPrimary().getBounds().getWidth() - ( Screen.getPrimary().getBounds().getWidth() * 800/1920);
    public static final double height = Screen.getPrimary().getBounds().getHeight() - ( Screen.getPrimary().getBounds().getHeight() * 150/1080);
    public static final int section_width = (int)width / 32;
    public static final int section_height = (int)height / 32;
    public static final int slope = section_height / 8;

    public static final double fps = 60;
   
    @Override
    public void start(Stage stage) throws Exception{

        try {
            stage.setTitle("Classic Donkey Kong");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/homeScreen.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root); 
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}