package main.java.com.game;

import javafx.scene.image.ImageView;

public class ImageControl {
    double x = 0;
    double y = 0;
    double width;
    double height;
    
    public ImageControl(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        ImageView img = new ImageView();
        img.setCache(true);
        img.setFitWidth(width);
        img.setFitHeight(height);
        img.setX(x);
        img.setY(y);
    }

    

    

}
