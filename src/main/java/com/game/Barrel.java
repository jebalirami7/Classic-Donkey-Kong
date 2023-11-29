package main.java.com.game;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Barrel extends Pane {
    private ImageView barrel;
    private double barrelWidth = 50;
    private double barrelHeight = 50;
    private double x_change = 0;
    private double y_change = 0;
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
        bottom = new Rectangle(barrelWidth, barrelHeight);
        bottom.setLayoutX(x);
        bottom.setLayoutY(y);
        bottom.setFill(Color.TRANSPARENT);
        root.getChildren().add(bottom);
    }

    public boolean update(ArrayList<Bridge> plats, double row1_top, double row2_top, double row3_top, double row4_top, double row5_top, Rectangle oilDrum) {
        boolean fireTrigger = false;

        if (y_change < 8 && !falling) 
            y_change += 0.5; //0.02, 1

        for(int i=0; i<plats.size(); i++) {
            if (bottom.getBoundsInParent().intersects(plats.get(i).getTop().getBoundsInParent())) {
                y_change = 0;
                falling = false;
            }
        }

        if (bottom.getBoundsInParent().intersects(oilDrum.getBoundsInParent())) {
            if (!oilCollision) {
                oilCollision = true;
                if (new Random().nextInt(2) == 0) {
                    fireTrigger = true;
                }
            }
        }

        if (!falling) {
            double rectBottom = bottom.getLayoutY() + barrelWidth;
            if (row5_top > rectBottom || (row3_top > rectBottom && rectBottom > row4_top) || (row1_top > rectBottom && rectBottom > row2_top)) {
                x_change = 3; //0.5, 3
            } else {
                x_change = -3;
            }
        } else {
            x_change = 0;
        }

        barrel.setX(barrel.getX() + x_change);
        barrel.setY(barrel.getY() + y_change);
        bottom.setLayoutX(bottom.getLayoutX() + x_change);
        bottom.setLayoutY(bottom.getLayoutY() + y_change);
        
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
        barrel.setRotate(90 * pos);  

        // Destroy the barrel
        // if (bottom.getLayoutY() > App.height) {
        //     System.out.println("barrel out");
        // }

        return fireTrigger;
    }

    public void checkFall(ArrayList<Ladder> lads, Group root) {
        boolean alreadyCollided = false;
        Line below = new Line(bottom.getLayoutX() + barrelWidth, bottom.getLayoutY() + barrelHeight, bottom.getLayoutX() + barrelWidth, bottom.getLayoutY() + barrelHeight);
        below.setStroke(Color.GREEN); 
        below.setStrokeWidth(3);
        root.getChildren().add(below);
        for (Ladder lad:lads) {
            if (lad.getLength() >= 3) {
                Rectangle ladBody = lad.getBody();
                Line ladTopLine = new Line(ladBody.getX(), ladBody.getY(), ladBody.getX() + App.section_width, ladBody.getY());
                Line ladBottomLine = new Line(ladBody.getX(), ladBody.getY() + ladBody.getHeight(), ladBody.getX() + App.section_width, ladBody.getY() + ladBody.getHeight());
                ladBottomLine.setStroke(Color.BLUE); 
                ladBottomLine.setStrokeWidth(3);
                root.getChildren().addAll(ladTopLine, ladBottomLine);

                // ladBody.setFill(Color.RED);   // change color to ckeck its position
                
                if (below.getBoundsInParent().intersects(ladTopLine.getBoundsInParent()) && !falling && !checkLad) {
                    checkLad = true;
                    alreadyCollided = true;
                    int rand = new Random().nextInt(5);
                    System.out.println(below.getBoundsInParent().intersects(ladBody.getBoundsInParent()));
                    if (true) {
                        falling = true;
                        Timeline barrelFaTimeline = new Timeline(new KeyFrame(Duration.millis(5), event -> {
                            y_change += 100;
                        }));
                        // barrelFaTimeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
                        barrelFaTimeline.play();
                        barrel.setY(barrel.getY() + y_change);
                        bottom.setLayoutY(bottom.getLayoutY() + y_change);
                    }
                }
            }
        }
            if (!alreadyCollided) 
               checkLad = false;
    }


    public void clear() {
        barrel.setImage(null);
    }

}
