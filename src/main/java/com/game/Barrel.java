package main.java.com.game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Barrel extends Pane {
    private ImageView barrel;
    private double barrelWidth = 50;
    private double barrelHeight = 50;
    private double x_change;
    private double y_change;
    private double pos = 0;
    private int count = 0;
    private boolean oilCollision = false;
    private boolean falling = false;
    private boolean checkLad = false;
    private Rectangle bottom;

    public Barrel(double x, double y, Group root) {
        // Set up the image and position
        barrel = new ImageView(App.barrelImg);
        barrel.setCache(true);
        barrel.setFitWidth(barrelWidth);
        barrel.setFitHeight(barrelHeight);
        barrel.setX(x);
        barrel.setY(y);
        root.getChildren().add(barrel);
        this.bottom = new Rectangle(barrelWidth, barrelHeight);
        this.bottom.setLayoutX(x);
        this.bottom.setLayoutY(y);
        this.bottom.setFill(Color.TRANSPARENT);
        root.getChildren().add(this.bottom);
    }

    public boolean update(ArrayList<Bridge> plats, double row1_top, double row2_top, double row3_top, double row4_top, double row5_top, Rectangle oilDrum, boolean fireTrigger) {
        if (y_change<8 && !falling) 
            y_change += 0.02;

        for(int i=0; i<plats.size(); i++) {
            if (this.bottom.getBoundsInParent().intersects(plats.get(i).getTop().getBoundsInParent())) {
                y_change = 0;
                this.falling = false;
            }
        }

        if (this.bottom.getBoundsInParent().intersects(oilDrum.getBoundsInParent())) {
            if (!oilCollision) {
                oilCollision = true;
                if (new Random().nextInt(4) == 4) {
                    fireTrigger = true;
                }
            }
        }

        if (!falling) {
            double rectBottom = this.bottom.getLayoutY() + barrelWidth;
            if (row5_top >= rectBottom || (row3_top >= rectBottom && rectBottom >= row4_top) || (row1_top >= rectBottom && rectBottom >= row2_top)) {
                x_change = 0.5;
            } else {
                x_change = -0.5;
            }
        } else {
            x_change = 0;
        }
        barrel.setX(barrel.getX() + x_change);
        barrel.setY(barrel.getY() + y_change);
        this.bottom.setLayoutX(this.bottom.getLayoutX() + x_change);
        this.bottom.setLayoutY(this.bottom.getLayoutY() + y_change);
        
        if (count < 15)
            count += 1;
        else {
            count = 0;
            if (x_change > 0)
                if (pos < 4)
                    pos += 0.1;
                else
                    pos = 0;
            else
                if (pos > 0)
                    pos -= 0.1;
                else
                    pos = 4;
        }

        return fireTrigger;
    }

    public void checkFall(ArrayList<Ladder> lads) {
        boolean alreadyCollided = false;
        Rectangle below = new Rectangle(this.bottom.getLayoutX(), this.bottom.getLayoutY() );
        for (Ladder lad:lads) {
            if (below.getBoundsInParent().intersects(lad.getBody().getBoundsInParent()) && !falling && !checkLad) {
                checkLad = true;
                alreadyCollided = true;
                // System.out.println(this.bottom.getLayoutX() + " " + this.bottom.getLayoutY());
                if (new Random().nextInt(1) == 1) {
                    falling = true;
                    y_change = 4;
                }
            }
        }

        if (!alreadyCollided) 
            checkLad = false;
    }

    public void rotate() {
        // Set the rotation based on the 'pos' variable
        barrel.setRotate(90 * pos);       
    }

    // Add getters and setters for other properties as needed
}
