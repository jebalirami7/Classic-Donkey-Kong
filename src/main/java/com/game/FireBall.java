package main.java.com.game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FireBall extends Pane {
    private ImageView fireBall;
    private double fireBallWidth = 1.5 * App.section_width;
    private double fireBallHeight = 2 * App.section_height;
    private double x_change = 2;
    private double y_change = 0;    
    private double x_count = 0;
    private double x_max = 4;
    private double row = 2;
    private double pos = 1;
    private int count = 0;
    private boolean climbing = false;
    private boolean checkLad = false;
    private Rectangle bottom;

    public FireBall(double x, double y, Group root) {
        // Set up the image and position
        fireBall = new ImageView(App.fireBallImg);
        fireBall.setCache(true);
        fireBall.setFitWidth(fireBallWidth);
        fireBall.setFitHeight(fireBallHeight);
        fireBall.setX(x);
        fireBall.setY(y);
        root.getChildren().add(fireBall);
        bottom = new Rectangle(fireBallWidth, fireBallHeight);
        bottom.setLayoutX(x);
        bottom.setLayoutY(y);
        bottom.setFill(Color.TRANSPARENT);
        root.getChildren().add(bottom);
    }

    public void update(ArrayList<Bridge> plats) {
        if (y_change < 3 && !climbing) 
            y_change += 0.25;

        for(int i=0; i<plats.size(); i++) {
            if (bottom.getBoundsInParent().intersects(plats.get(i).getTop().getBoundsInParent())) {
                y_change = -4;
                climbing = false;
            }
        }

        if (count < 15)
            count += 1;
        else {
            count = 0;
            pos *= -1;
            if (x_count < x_max)
                x_count += 1;
            else {  // row 1,3 and 5 - go further right than left overall, otherwise flip it
                x_count = 0;
                if (x_change > 0)
                    if (row == 1 || row == 3 || row == 5)
                        x_max = new Random().nextInt(7);
                    else
                        x_max = new Random().nextInt(7, 15);
                else
                    if (row == 1 || row == 3 || row == 5)
                        x_max = new Random().nextInt(7, 15);
                    else
                        x_max = new Random().nextInt(7);
                x_change *= -1;
            }
        }

        // Animate fireBall movements
        if (pos == 1) {
            fireBall.setImage(App.fireBallImg);
            if (x_change > 0) 
                fireBall.setScaleX(1);
            else
                fireBall.setScaleX(-1);
        }
        else {
            fireBall.setImage(App.fireBall2);
            if (x_change > 0)
                fireBall.setScaleX(1);
            else
                fireBall.setScaleX(-1);
        }

        // Make the fireBall turn when it touchs the screen
        if (fireBall.getX() + fireBallWidth + x_change >= App.width || fireBall.getX() + x_change <= 0)
            x_change *= -1;

        fireBall.setX(fireBall.getX() + x_change);
        fireBall.setY(fireBall.getY() + y_change);
        bottom.setLayoutX(bottom.getLayoutX() + x_change);
        bottom.setLayoutY(bottom.getLayoutY() + y_change);
        
        // Destroy the fireBall
        // if rect.top > screen_height or rect.top < 0
        //     kill()

    }

    public void checkFall(ArrayList<Ladder> lads, int row2_y, int row3_y, int row4_y, int row5_y, int row6_y) {
        boolean alreadyCollided = false;
        for (Ladder lad:lads) {
            if (bottom.getBoundsInParent().intersects(lad.getBody().getBoundsInParent()) && !climbing && !checkLad && lad.getLength() >= 3) {
                checkLad = true;
                alreadyCollided = true;
                // System.out.println(this.bottom.getLayoutX() + " " + this.bottom.getLayoutY());
                if (new Random().nextInt(60) == 0) {
                    climbing = true;
                    y_change = -4;
                    fireBall.setY(fireBall.getY() + y_change);
                    bottom.setLayoutY(bottom.getLayoutY() + y_change);
                }
            }
        }

        if (!alreadyCollided) 
            checkLad = false;

        if (bottom.getLayoutY() + fireBallHeight < row6_y)
            row = 6;
        else if (bottom.getLayoutY() + fireBallHeight < row5_y)
            row = 5;
        else if (bottom.getLayoutY() + fireBallHeight < row4_y)
            row = 4;
        else if (bottom.getLayoutY() + fireBallHeight < row3_y)
            row = 3;
        else if (bottom.getLayoutY() + fireBallHeight < row2_y)
            row = 2;
        else
            row = 1;
    }

}
