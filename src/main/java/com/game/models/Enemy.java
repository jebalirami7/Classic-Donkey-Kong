package main.java.com.game.models;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

sealed class Enemy permits Barrel, FireBall {
    protected ImageView image;
    protected double width;
    protected double height;
    protected double x_change = 0;
    protected double y_change = 0;
    protected double pos;
    protected int count = 0;
    protected boolean checkLad = false;
    protected Rectangle rect;

    public Enemy(double x, double y, double width, double height, double pos, Group root) {
        this.width = width;
        this.height = height;
        this.pos = pos;
        image = new ImageView();
        image.setCache(true);
        image.setFitWidth(width);
        image.setFitHeight(height);
        image.setX(x);
        image.setY(y);
        root.getChildren().add(image);
        rect = new Rectangle(width - 2, height - 2);
        rect.setLayoutX(x + 1);
        rect.setLayoutY(y + 2);
        rect.setFill(Color.TRANSPARENT);
        root.getChildren().add(rect);
    }

    
    public void clear(Group root) {
        root.getChildren().removeAll(image, rect);
    }


    public Rectangle getRect() {
        return rect;
    }

    
}
