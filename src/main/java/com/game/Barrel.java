package main.java.com.game;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public final class Barrel extends Enemy {
    private boolean oilCollision = false;
    private boolean falling = false;


    public Barrel(double x, double y, Group root) {
        super(x, y, 50, 50, 0, root);
        image.setImage(new Image( "file:src/main/resources/assets/images/barrels/barrel.png"));
    }


    public boolean update(ArrayList<Bridge> plats, double row1_top, double row2_top, double row3_top, double row4_top, double row5_top, Rectangle oilDrum) {
        boolean fireTrigger = false;

        if (y_change < 8 && !falling) 
            y_change += 0.5; //0.02, 1

        for(int i=0; i<plats.size(); i++) {
            if (rect.getBoundsInParent().intersects(plats.get(i).getTop().getBoundsInParent())) {
                y_change = 0;
                falling = false;
            }
        }

        if (rect.getBoundsInParent().intersects(oilDrum.getBoundsInParent())) {
            if (!oilCollision) {
                oilCollision = true;
                if (new Random().nextInt(1) == 0) {
                    fireTrigger = true;
                }
            }
        }

        if (!falling) {
            double rectBottom = rect.getLayoutY() + width;
            if (row5_top > rectBottom || (row3_top > rectBottom && rectBottom > row4_top) || (row1_top > rectBottom && rectBottom > row2_top)) {
                x_change = 3; //0.5, 3
            } else {
                x_change = -3;
            }
        } else {
            x_change = 0;
        }

        image.setX(image.getX() + x_change);
        image.setY(image.getY() + y_change);
        rect.setLayoutX(rect.getLayoutX() + x_change);
        rect.setLayoutY(rect.getLayoutY() + y_change);
        
        if (count < 15)
            count += 1;
        else {
            count = 0;
            if (x_change > 0)
                if (pos < 3) //4
                    pos += 0.5; //0.1, 1
                else
                    pos = 0;
            else
                if (pos > 0)
                    pos -= 0.5; 
                else
                    pos = 3;
        }

        // Rotate the barrel
        image.setRotate(90 * pos);  

        return fireTrigger;
    }

    public void checkFall(ArrayList<Ladder> lads, Group root) {
        boolean alreadyCollided = false;
        Line below = new Line(rect.getLayoutX() + width, rect.getLayoutY() + height, rect.getLayoutX() + width, rect.getLayoutY() + height);
        below.setStroke(Color.GREEN); 
        below.setStrokeWidth(3);
        root.getChildren().add(below);
        for (Ladder lad:lads) {
            if (lad.getLength() >= 3) {
                Rectangle ladBody = lad.getBody();
                Line ladTopLine = new Line(ladBody.getX(), ladBody.getY(), ladBody.getX() + App.section_width, ladBody.getY());
                Line ladrectLine = new Line(ladBody.getX(), ladBody.getY() + ladBody.getHeight(), ladBody.getX() + App.section_width, ladBody.getY() + ladBody.getHeight());
                ladrectLine.setStroke(Color.BLUE); 
                ladrectLine.setStrokeWidth(3);
                root.getChildren().addAll(ladTopLine, ladrectLine);

                // ladBody.setFill(Color.RED);   // change color to ckeck its position
                
                if (below.getBoundsInParent().intersects(ladTopLine.getBoundsInParent()) && !falling && !checkLad) {
                    checkLad = true;
                    alreadyCollided = true;
                    System.out.println(below.getBoundsInParent().intersects(ladBody.getBoundsInParent()));
                    if (new Random().nextInt(1) == 0) {
                        falling = true;
                        Timeline barrelFaTimeline = new Timeline(new KeyFrame(Duration.millis(5), event -> {
                            y_change += 100;
                        }));
                        // barrelFaTimeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
                        barrelFaTimeline.play();
                        image.setY(image.getY() + y_change);
                        rect.setLayoutY(rect.getLayoutY() + y_change);
                    }
                }
            }
        }
            if (!alreadyCollided) 
               checkLad = false;
    }
    

}
