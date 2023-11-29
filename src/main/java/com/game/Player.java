package main.java.com.game;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

public class Player {
    private ImageView image;
    private double imageWidth = 2 * App.section_width;
    private double imageHeight = 2.5 * App.section_height;
    private double y_change = 0;
    private double x_speed = 3;
    private double x_change = 0;
    private boolean landed = false;
    private double pos = 0;
    private double dir = 1;
    private int count = 0;
    private boolean climbing = false;
    private boolean hammer = false;
    private int maxHammer = 450;
    private int hammerLen = maxHammer;
    private double hammerPos = 1;
    private Rectangle rect;
    private Rectangle hitbox;
    private Rectangle hammerBox;
    private boolean overBarrel = false;
    private Rectangle bottom;

    public Player(double x, double y, Group root) {
        // Set up the image and position
        image = new ImageView(App.standing);
        image.setCache(true);
        image.setFitWidth(imageWidth);
        image.setFitHeight(imageHeight);
        image.setX(x);
        image.setY(y);
        root.getChildren().add(image);
        rect = new Rectangle(imageWidth, imageHeight);
        rect.setLayoutX(x);
        rect.setLayoutY(y);
        rect.setFill(Color.TRANSPARENT);
        root.getChildren().add(rect);
        bottom = new Rectangle(rect.getX() + imageWidth/2, rect.getY() + imageHeight - 20, 1, 20);
        bottom.setLayoutX(x);
        bottom.setLayoutY(y);
        bottom.setFill(Color.TRANSPARENT);
        root.getChildren().add(bottom);
        hitbox = new Rectangle(rect.getX() + 15, rect.getY() + 5, rect.getX() + rect.getWidth() - 30, rect.getY() + rect.getHeight() - 10);
        hitbox.setFill(Color.TRANSPARENT);
        root.getChildren().add(hitbox);
        hammerBox = new Rectangle(hitbox.getX() + hitbox.getX() + hitbox.getWidth(), rect.getY() + 5, hitbox.getX() + hitbox.getWidth(), rect.getY() + rect.getHeight() - 10);
        hammerBox.setFill(Color.GREEN);
        root.getChildren().add(hammerBox);
    }


    public void update(ArrayList<Bridge> plats) {
        landed = false;

        for(Bridge plat : plats) {
            if (bottom.getBoundsInParent().intersects(plat.getTop().getBoundsInParent())) {
                landed = true;
                if (!climbing) {
                    image.setY(plat.getTop().getY() - imageHeight);
                    rect.setLayoutY(plat.getTop().getY() - rect.getHeight());
                }
            }
        }

        if (!landed && !climbing) {
            y_change +=0.25;
        }

        image.setX(image.getX() + x_change * x_speed);
        image.setY(image.getY() + y_change);
        rect.setLayoutX(rect.getLayoutX() + x_change * x_speed);
        rect.setLayoutY(rect.getLayoutY() + y_change);
        bottom.setLayoutX(rect.getLayoutX());
        bottom.setLayoutY(rect.getLayoutY());

        if (x_change != 0 || (climbing && y_change != 0))
            if (count < 3)
                count += 1;
            else {
                count = 0;
                if (pos == 0)
                    pos += 1;
                else
                    pos = 0;
            }
        else
            pos = 0;

        if (hammer) {
            hammerPos = (hammerLen / 30) % 2;
            hammerLen -= 1;
            if (hammerLen == 0) {
                hammer = false;
                hammerLen = maxHammer;
            }
        }
    }


    public void draw() {
        if (!hammer) {
            if (!climbing && landed)
                if (pos == 0)
                    image.setImage(App.standing);
                else
                    image.setImage(App.running);

            if (!landed && !climbing)
                image.setImage(App.jumping);

            if (climbing)
                if (pos == 0)
                    image.setImage(App.climbing1);
                else
                    image.setImage(App.climbing2);
        } else
            if (hammerPos == 0)
                image.setImage(App.hammerJump);
            else
                image.setImage(App.hammerOverhead);

        image.setScaleX(1);
        if (dir == -1)
            image.setScaleX(-1);

        calcHitbox();

        if (hammerPos == 1 && hammer) {
            image.setY(image.getY() - App.section_height);
            rect.setLayoutY(rect.getLayoutY() - App.section_height);
        } else {
            image.setY(image.getY());
            rect.setLayoutY(rect.getLayoutY());
        }

    }


    private void calcHitbox() {
        hitbox.setY(rect.getLayoutY() + 5);
        hitbox.setWidth(rect.getWidth() - 30);
        hitbox.setHeight(rect.getHeight() - 10);

        hammerBox.setY(rect.getY() + 5);
        hammerBox.setWidth(hitbox.getWidth());
        hammerBox.setHeight(rect.getHeight() - 10);

        if (!hammer) {
            hitbox.setX(rect.getLayoutX() + 15);
        }
        else if (hammerPos == 0)
            if (dir == 1) {
                hitbox.setX(rect.getLayoutX());
                hammerBox.setX(2 * hitbox.getX() + hitbox.getWidth());
            } else {
                hitbox.setX(rect.getLayoutX() + 40);
                hammerBox.setX(hitbox.getWidth());
            }
        else {
            hitbox.setX(rect.getLayoutX() + 15);
            hammerBox.setX(hitbox.getX());
            hammerBox.setY(hitbox.getY() - App.section_height);
            hammerBox.setHeight(App.section_height);
        }

    }


    public Pair<Boolean, Boolean> checkClimb(ArrayList<Ladder> lads) {
        boolean canClimb = false;
        boolean climbDown = false; 

        for (Ladder lad : lads) {
            if (lad.getLength() >= 3) {
                if (bottom.getBoundsInParent().intersects(lad.getBody().getBoundsInParent()) && !canClimb)
                canClimb = true;
                if (bottom.getBoundsInParent().intersects(lad.getBody().getBoundsInParent()))
                climbDown = true;
            }
        }

        if ((!canClimb && (!climbDown || y_change < 0)) || (landed && canClimb && y_change > 0 && !climbDown))
            climbing = false;  

        return new Pair<Boolean,Boolean>(canClimb, climbDown);
    }


    public boolean isLanded() {
        return landed;
    }


    public boolean isClimbing() {
        return climbing;
    }


    public void setY_change(double y_change) {
        this.y_change = y_change;
    }


    public void setX_change(double x_change) {
        this.x_change = x_change;
    }


    public void setLanded(boolean landed) {
        this.landed = landed;
    }


    public void setClimbing(boolean climbing) {
        this.climbing = climbing;
    }


    public void setDir(double dir) {
        this.dir = dir;
    }


    public int getMaxHammer() {
        return maxHammer;
    }


    public Rectangle getHitbox() {
        return hitbox;
    }


    public void setHammer(boolean hammer) {
        this.hammer = hammer;
    }


    public void setHammerLen(int hammerLen) {
        this.hammerLen = hammerLen;
    }

  
    
    


}
