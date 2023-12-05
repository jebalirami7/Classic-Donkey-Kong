package main.java.com.game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FireBall extends Pane {
    public static Image fireImg = new Image("file:src/main/resources/assets/images/fire.png");
    public static Image fireBallImg = new Image("file:src/main/resources/assets/images/fireball.png");
    public static Image fireBall2 = new Image("file:src/main/resources/assets/images/fireball2.png");
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
    private Rectangle rect;

    public FireBall(double x, double y, Group root) {
        // Set up the image and position
        fireBall = new ImageView(fireBallImg);
        fireBall.setCache(true);
        fireBall.setFitWidth(fireBallWidth);
        fireBall.setFitHeight(fireBallHeight);
        fireBall.setX(x);
        fireBall.setY(y);
        root.getChildren().add(fireBall);
        rect = new Rectangle(fireBallWidth, fireBallHeight);
        rect.setLayoutX(x);
        rect.setLayoutY(y);
        rect.setFill(Color.TRANSPARENT);
        root.getChildren().add(rect);
    }

    public void update(ArrayList<Bridge> plats) {
        if (y_change < 3 && !climbing) 
            y_change += 0.25;

        for(int i=0; i<plats.size(); i++) {
            if (rect.getBoundsInParent().intersects(plats.get(i).getTop().getBoundsInParent())) {
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
            fireBall.setImage(fireBallImg);
            if (x_change > 0) 
                fireBall.setScaleX(1);
            else
                fireBall.setScaleX(-1);
        }
        else {
            fireBall.setImage(fireBall2);
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
        rect.setLayoutX(rect.getLayoutX() + x_change);
        rect.setLayoutY(rect.getLayoutY() + y_change);
        
        // Destroy the fireBall
        // if rect.top > screen_height or rect.top < 0
        //     kill()

    }


    public void checkFall(ArrayList<Ladder> lads, int row2_y, int row3_y, int row4_y, int row5_y, int row6_y) {
        boolean alreadyCollided = false;
        for (Ladder lad:lads) {
            if (rect.getBoundsInParent().intersects(lad.getBody().getBoundsInParent()) && !climbing && !checkLad && lad.getLength() >= 3) {
                checkLad = true;
                alreadyCollided = true;
                // System.out.println(this.rect.getLayoutX() + " " + this.rect.getLayoutY());
                if (new Random().nextInt(1) == 0) {
                    climbing = true;
                    y_change = -4;
                    fireBall.setY(fireBall.getY() + y_change);
                    rect.setLayoutY(rect.getLayoutY() + y_change);
                }
            }
        }

        if (!alreadyCollided) 
            checkLad = false;

        if (rect.getLayoutY() + fireBallHeight < row6_y)
            row = 6;
        else if (rect.getLayoutY() + fireBallHeight < row5_y)
            row = 5;
        else if (rect.getLayoutY() + fireBallHeight < row4_y)
            row = 4;
        else if (rect.getLayoutY() + fireBallHeight < row3_y)
            row = 3;
        else if (rect.getLayoutY() + fireBallHeight < row2_y)
            row = 2;
        else
            row = 1;
    }


    public Rectangle getRect() {
        return rect;
    }


    public void clear(Group root) {
        root.getChildren().removeAll(fireBall, rect);
    }


}
