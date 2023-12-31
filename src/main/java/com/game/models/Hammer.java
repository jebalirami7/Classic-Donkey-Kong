package main.java.com.game.models;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.java.com.game.App;

public class Hammer {
    
    private Image hammerImg = new Image("file:src/main/resources/assets/images/hammer.png");

    private ImageView hammer;
    private Rectangle rect;
    private boolean used = false;
    private double hammerWidth = 2 * App.section_width;
    private double hammerHeight = 2 * App.section_height;

    Group root;

    public Hammer(double x, double y, Group root) {
        this.root = root;
        hammer = new ImageView(hammerImg);
        hammer.setCache(true);
        hammer.setFitWidth(hammerWidth);
        hammer.setFitHeight(hammerHeight);
        hammer.setX(x);
        hammer.setY(y);
        root.getChildren().add(hammer);
        rect = new Rectangle(hammerWidth, hammerHeight);
        rect.setLayoutX(x);
        rect.setLayoutY(y);
        rect.setFill(Color.TRANSPARENT);
        root.getChildren().add(rect);
    }
    

    public void draw(Mario player) {
        if (!used) {
            if (rect.getBoundsInParent().intersects(player.getHitbox().getBoundsInParent())) {
                hammer.setImage(null);
                player.setHammer(true);
                player.setHammerLen(player.getMaxHammer());
                used = true;
            }
        }
    }

    public void clear() {
        root.getChildren().removeAll(hammer, rect);
    }

}
