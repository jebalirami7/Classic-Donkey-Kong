package main.java.com.game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public final class FireBall extends Enemy {
    private Image image1 = new Image( "file:src/main/resources/assets/images/fireball.png");
    private Image image2 = new Image( "file:src/main/resources/assets/images/fireball2.png");
    private double width = 1.5 * App.section_width;
    private double height = 2 * App.section_height;
    private double x_count = 0;
    private double x_max = 4;
    private double row = 2;
    private boolean climbing = false;

    public FireBall(double x, double y, Group root) {
        super(x, y, 2 * App.section_height, 1.5 * App.section_width, 1, root);
        image.setImage(image1);
        x_change = 2;
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

        // Animate image movements
        if (pos == 1) {
            image.setImage(image1);
            if (x_change > 0) 
                image.setScaleX(1);
            else
                image.setScaleX(-1);
        }
        else {
            image.setImage(image2);
            if (x_change > 0)
                image.setScaleX(1);
            else
                image.setScaleX(-1);
        }

        // Make the image turn when it touchs the screen
        if (image.getX() + width + x_change >= App.width || image.getX() + x_change <= 0)
            x_change *= -1;

        image.setX(image.getX() + x_change);
        image.setY(image.getY() + y_change);
        rect.setLayoutX(rect.getLayoutX() + x_change);
        rect.setLayoutY(rect.getLayoutY() + y_change);
        
        // Destroy the image
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
                    image.setY(image.getY() + y_change);
                    rect.setLayoutY(rect.getLayoutY() + y_change);
                }
            }
        }

        if (!alreadyCollided) 
            checkLad = false;

        if (rect.getLayoutY() + height < row6_y)
            row = 6;
        else if (rect.getLayoutY() + height < row5_y)
            row = 5;
        else if (rect.getLayoutY() + height < row4_y)
            row = 4;
        else if (rect.getLayoutY() + height < row3_y)
            row = 3;
        else if (rect.getLayoutY() + height < row2_y)
            row = 2;
        else
            row = 1;
    }

}
