package main.java.com.game;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

public class Player {
    private Image standing = new Image("file:src/main/resources/assets/images/mario/standing.png");
    private Image running = new Image("file:src/main/resources/assets/images/mario/running.png");
    private Image jumping = new Image("file:src/main/resources/assets/images/mario/jumping.png");
    private Image climbing1 = new Image("file:src/main/resources/assets/images/mario/climbing1.png");
    private Image climbing2 = new Image("file:src/main/resources/assets/images/mario/climbing2.png");
    private Image hammerJump = new Image("file:src/main/resources/assets/images/mario/hammer_jump.png");
    private Image hammerOverhead = new Image("file:src/main/resources/assets/images/mario/hammer_overhead.png");

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
    private Rectangle bottom;

    public Player(double x, double y, Group root) {
        // Set up the image and position
        image = new ImageView(standing);
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
        hitbox = new Rectangle();
        hitbox.setFill(Color.TRANSPARENT);
        root.getChildren().add(hitbox);
        hammerBox = new Rectangle(0,0);
        hammerBox.setFill(Color.TRANSPARENT);
        root.getChildren().add(hammerBox);
    }


    public void reset(double x, double y) {
        // Reset the player
        image.setX(x);
        image.setY(y);
        rect.setLayoutX(x);
        rect.setLayoutY(y);
        bottom.setLayoutX(x);
        bottom.setLayoutY(y);
        landed = false;
        climbing = false;
        hammer = false;
        dir = 1;
        pos = 0;
        count = 0;
        hammerPos = 1;
        maxHammer = 450;
        image.setImage(standing);
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
        imageHeight = 2.5 * App.section_height;
        imageWidth = 2 * App.section_width;
        if (!hammer) {
            if (!climbing && landed)
                if (pos == 0)
                    image.setImage(standing);
                else
                    image.setImage(running);

            if (!landed && !climbing)
                image.setImage(jumping);

            if (climbing)
                if (pos == 0)
                    image.setImage(climbing1);
                else
                    image.setImage(climbing2);
        } else {
            if (hammerPos == 0) {
                imageWidth = 2.5 * App.section_width;
                image.setImage(hammerJump);
            }
            else {
                imageHeight = 4 * App.section_height;
                image.setImage(hammerOverhead);
            }
        }

        image.setFitWidth(imageWidth);
        image.setFitHeight(imageHeight);

        image.setScaleX(1);
        if (dir == -1)
            image.setScaleX(-1);

        calcHitbox();

        // if (hammerPos == 1 && hammer) {
        //     image.setY(image.getY() - App.section_height);
        //     rect.setLayoutY(rect.getLayoutY() - App.section_height);
        // } else {
        //     image.setY(image.getY());
        //     rect.setLayoutY(rect.getLayoutY());
        // }

    }


    private void calcHitbox() {
        hitbox.setY(rect.getLayoutY() + 5);
        hitbox.setWidth(rect.getWidth() - 30);
        hitbox.setHeight(rect.getHeight() - 10);

        hammerBox.setX(0);
        hammerBox.setY(0);
        hammerBox.setWidth(hitbox.getWidth());
        hammerBox.setHeight(rect.getHeight() - 10);
        
        if (!hammer) {
            hitbox.setX(rect.getLayoutX() + 15);
        }
        else if (hammerPos == 0) {
            hammerBox.setY(rect.getLayoutY() + 5);
            if (dir == 1) {
                hitbox.setX(rect.getLayoutX());
                hammerBox.setX(hitbox.getX() + hitbox.getWidth() + 10);
            } else {
                hitbox.setX(rect.getLayoutX() + 40);
                hammerBox.setX(hitbox.getX() - hitbox.getWidth() - 5);
            }
        } else {
            hitbox.setX(rect.getLayoutX() + 15);
            hammerBox.setY(rect.getLayoutY() + 5);
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


    public boolean getHammer() {
        return hammer;
    }


    public Rectangle getHammerBox() {
        return hammerBox;
    }

  
    public void clear(Group root) {
        root.getChildren().removeAll(image, hitbox, bottom, hammerBox, rect);
    }


    public Rectangle getRect() {
        return rect;
    }


}
