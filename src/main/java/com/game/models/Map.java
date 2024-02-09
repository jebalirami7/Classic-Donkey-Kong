package main.java.com.game.models;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.java.com.game.App;

public class Map {
    private Image dk1 = new Image("file:src/main/resources/assets/images/dk/dk1.png");
    private Image dk2 = new Image("file:src/main/resources/assets/images/dk/dk2.png");
    private Image dk3 = new Image("file:src/main/resources/assets/images/dk/dk3.png");
    private Image barrelImage = new Image( "file:src/main/resources/assets/images/barrels/barrel.png");

    private final ImageView fireImg = new ImageView(new Image("file:src/main/resources/assets/images/fire.png"));
    private final ImageView barrelImgView = new ImageView();
    private final ImageView dk = new ImageView();
    private final ImageView peach = new ImageView(new Image("file:src/main/resources/assets/images/peach/peach2.png"));

    private ArrayList<Level> levels = new ArrayList<Level>();

    private int start_y = (int)App.height - 2 * App.section_height;
    private int row2_y = start_y - 4 * App.section_height;
    private int row3_y = row2_y - 7 * App.slope - 3 * App.section_height;
    private int row4_y = row3_y - 4 * App.section_height;
    private int row5_y = row4_y - 7 * App.slope - 3 * App.section_height;
    private int row6_y = row5_y - 4 * App.section_height;
    private int row6_top = row6_y - 4 * App.slope;
    private int row5_top = row5_y - 8 * App.slope;
    private int row4_top = row4_y - 8 * App.slope;
    private int row3_top = row3_y - 8 * App.slope;
    private int row2_top = row2_y - 8 * App.slope;
    private int row1_top = start_y - 5 * App.slope;


    private ArrayList<Bridge> bridge_objs = new ArrayList<Bridge>();
    private ArrayList<Ladder> ladder_objs = new ArrayList<Ladder>();
    private ArrayList<Hammer> hammer_objs = new ArrayList<Hammer>();
    
    private int fireCounter = 0;

    private Rectangle oilDrum;
    private Rectangle targetRect = new Rectangle();

    Group root;
    
    public Map(Group root) {
        this.root = root;
    }


    private void createLevels() {
        // Create level 1 
        levels.add(new Level(root, Color.rgb(225, 51, 129),
            List.of(
                List.of(1, start_y, 15), List.of(16, start_y - App.slope, 3),
                List.of(19, start_y - 2 * App.slope, 3), List.of(22, start_y - 3 * App.slope, 3),
                List.of(25, start_y - 4 * App.slope, 3), List.of(28, start_y - 5 * App.slope, 3),
                List.of(25, row2_y, 3), List.of(22, row2_y - App.slope, 3),
                List.of(19, row2_y - 2 * App.slope, 3), List.of(16, row2_y - 3 * App.slope, 3),
                List.of(13, row2_y - 4 * App.slope, 3), List.of(10, row2_y - 5 * App.slope, 3),
                List.of(7, row2_y - 6 * App.slope, 3), List.of(4, row2_y - 7 * App.slope, 3),
                List.of(2, row2_y - 8 * App.slope, 2), List.of(4, row3_y, 3),
                List.of(7, row3_y - App.slope, 3), List.of(10, row3_y - 2 * App.slope, 3),
                List.of(13, row3_y - 3 * App.slope, 3), List.of(16, row3_y - 4 * App.slope, 3),
                List.of(19, row3_y - 5 * App.slope, 3), List.of(22, row3_y - 6 * App.slope, 3),
                List.of(25, row3_y - 7 * App.slope, 3), List.of(28, row3_y - 8 * App.slope, 2),
                List.of(25, row4_y, 3), List.of(22, row4_y - App.slope, 3),
                List.of(19, row4_y - 2 * App.slope, 3), List.of(16, row4_y - 3 * App.slope, 3),
                List.of(13, row4_y - 4 * App.slope, 3), List.of(10, row4_y - 5 * App.slope, 3),
                List.of(7, row4_y - 6 * App.slope, 3), List.of(4, row4_y - 7 * App.slope, 3),
                List.of(2, row4_y - 8 * App.slope, 2), List.of(4, row5_y, 3),
                List.of(7, row5_y - App.slope, 3), List.of(10, row5_y - 2 * App.slope, 3),
                List.of(13, row5_y - 3 * App.slope, 3), List.of(16, row5_y - 4 * App.slope, 3),
                List.of(19, row5_y - 5 * App.slope, 3), List.of(22, row5_y - 6 * App.slope, 3),
                List.of(25, row5_y - 7 * App.slope, 3), List.of(28, row5_y - 8 * App.slope, 2),
                List.of(25, row6_y, 3), List.of(22, row6_y - App.slope, 3),
                List.of(19, row6_y - 2 * App.slope, 3), List.of(16, row6_y - 3 * App.slope, 3),
                List.of(2, row6_y - 4 * App.slope, 14), List.of(13, row6_y - 4 * App.section_height, 6),
                List.of(10, row6_y - 3 * App.section_height, 3)
            ),
            List.of(
                List.of(12, row2_y + 6 * App.slope, 2), List.of(12, row2_y + 26 * App.slope, 2),
                List.of(25, row2_y + 11 * App.slope, 4), List.of(6, row3_y + 11 * App.slope, 3),
                List.of(14, row3_y + 8 * App.slope, 4), List.of(10, row4_y + 6 * App.slope, 1),
                List.of(10, row4_y + 24 * App.slope, 2), List.of(16, row4_y + 6 * App.slope, 5),
                List.of(25, row4_y + 9 * App.slope, 4), List.of(6, row5_y + 11 * App.slope, 3),
                List.of(11, row5_y + 8 * App.slope, 4), List.of(23, row5_y + 4 * App.slope, 1),
                List.of(23, row5_y + 24 * App.slope, 2), List.of(25, row6_y + 9 * App.slope, 4),
                List.of(13, row6_y + 5 * App.slope, 2), List.of(13, row6_y + 25 * App.slope, 2),
                List.of(18, row6_y - 27 * App.slope, 4), List.of(12, row6_y - 17 * App.slope, 2),
                List.of(10, row6_y - 17 * App.slope, 2), List.of(12, -5, 13), List.of(10, -5, 13)
            ),
            List.of(        

                List.of(3 * App.section_width, row6_top + App.section_height), 
                List.of(3 * App.section_width, row4_top + App.section_height)
            ),
            List.of(10, row6_y - 7 * App.section_height, 3)
        ));

        // Create level 2 
        levels.add(new Level(root, Color.BLUE,
            List.of(
                List.of(1, start_y, 15), List.of(16, start_y - App.slope, 3),
                List.of(19, start_y - 2 * App.slope, 3), List.of(22, start_y - 3 * App.slope, 3),
                List.of(25, start_y - 4 * App.slope, 3), List.of(28, start_y - 5 * App.slope, 3),
                List.of(25, row2_y, 3), List.of(22, row2_y - App.slope, 3),
                List.of(19, row2_y - 2 * App.slope, 3), List.of(16, row2_y - 3 * App.slope, 3),
                List.of(13, row2_y - 4 * App.slope, 3), List.of(10, row2_y - 5 * App.slope, 3),
                List.of(7, row2_y - 6 * App.slope, 3), List.of(4, row2_y - 7 * App.slope, 3),
                List.of(2, row2_y - 8 * App.slope, 2), List.of(4, row3_y, 3),
                List.of(7, row3_y - App.slope, 3), List.of(10, row3_y - 2 * App.slope, 3),
                List.of(13, row3_y - 3 * App.slope, 3), List.of(16, row3_y - 4 * App.slope, 3),
                List.of(19, row3_y - 5 * App.slope, 3), List.of(22, row3_y - 6 * App.slope, 3),
                List.of(25, row3_y - 7 * App.slope, 3), List.of(28, row3_y - 8 * App.slope, 2),
                List.of(25, row4_y, 3), List.of(22, row4_y - App.slope, 3),
                List.of(19, row4_y - 2 * App.slope, 3), List.of(16, row4_y - 3 * App.slope, 3),
                List.of(13, row4_y - 4 * App.slope, 3), List.of(10, row4_y - 5 * App.slope, 3),
                List.of(7, row4_y - 6 * App.slope, 3), List.of(4, row4_y - 7 * App.slope, 3),
                List.of(2, row4_y - 8 * App.slope, 2), List.of(4, row5_y, 3),
                List.of(7, row5_y - App.slope, 3), List.of(10, row5_y - 2 * App.slope, 3),
                List.of(13, row5_y - 3 * App.slope, 3), List.of(16, row5_y - 4 * App.slope, 3),
                List.of(19, row5_y - 5 * App.slope, 3), List.of(22, row5_y - 6 * App.slope, 3),
                List.of(25, row5_y - 7 * App.slope, 3), List.of(28, row5_y - 8 * App.slope, 2),
                List.of(25, row6_y, 3), List.of(22, row6_y - App.slope, 3),
                List.of(19, row6_y - 2 * App.slope, 3), List.of(16, row6_y - 3 * App.slope, 3),
                List.of(2, row6_y - 4 * App.slope, 14), List.of(13, row6_y - 4 * App.section_height, 6),
                List.of(10, row6_y - 3 * App.section_height, 3)
            ),
            List.of(
                List.of(12, row2_y + 6 * App.slope, 2), List.of(12, row2_y + 26 * App.slope, 2),
                List.of(25, row2_y + 11 * App.slope, 4), List.of(6, row3_y + 11 * App.slope, 3),
                List.of(14, row3_y + 8 * App.slope, 4), List.of(10, row4_y + 6 * App.slope, 1),
                List.of(10, row4_y + 24 * App.slope, 2), List.of(16, row4_y + 6 * App.slope, 5),
                List.of(25, row4_y + 9 * App.slope, 4), List.of(6, row5_y + 11 * App.slope, 3),
                List.of(11, row5_y + 8 * App.slope, 4), List.of(23, row5_y + 4 * App.slope, 1),
                List.of(23, row5_y + 24 * App.slope, 2), List.of(25, row6_y + 9 * App.slope, 4),
                List.of(13, row6_y + 5 * App.slope, 2), List.of(13, row6_y + 25 * App.slope, 2),
                List.of(18, row6_y - 27 * App.slope, 4), List.of(12, row6_y - 17 * App.slope, 2),
                List.of(10, row6_y - 17 * App.slope, 2), List.of(12, -5, 13), List.of(10, -5, 13)
            ),
            List.of(        

                List.of(3 * App.section_width, row6_top + App.section_height), 
                List.of(3 * App.section_width, row4_top + App.section_height)
            ),
            List.of(10, row6_y - 7 * App.section_height, 3)
        ));
    }


    public void draw (int activeLevel) {
        if (activeLevel == 0) {
            // Draw Extras
            oilDrum = drawOil();
            drawBarrels();
        }

        createLevels();

        ladder_objs = levels.get(activeLevel).createLadders();
        bridge_objs = levels.get(activeLevel).createBridges();
        hammer_objs = levels.get(activeLevel).createHammers();

        // Draw Kong
        dk.setCache(true);
        dk.setFitWidth(5 * App.section_height);
        dk.setFitHeight(5 * App.section_height);
        dk.setX(3.5 * App.section_width);
        dk.setY(row6_y - 5.5 * App.section_height);
        barrelImgView.setCache(true);
        barrelImgView.setFitWidth((double)App.section_width * 50 / 35);
        barrelImgView.setFitHeight((double)App.section_height * 50 / 29);
        barrelImgView.setX(App.width * 230 / 1120);
        barrelImgView.setY(row6_top - (double)App.section_height * 50 / 29);

        // Draw Peach
        peach.setCache(true);
        peach.setFitWidth(2 * App.section_height);
        peach.setFitHeight(3 * App.section_height);
        peach.setX(10 * App.section_width);
        peach.setY(row6_y - 6 * App.section_height - App.slope);


        // Set Victory Position
        targetRect.setX(levels.get(activeLevel).getTarget().get(0) * App.section_width);
        targetRect.setY(levels.get(activeLevel).getTarget().get(1));
        targetRect.setWidth(levels.get(activeLevel).getTarget().get(2) * App.section_width);
        targetRect.setHeight(50);

        // Add all elements to the root (to be seen)
        root.getChildren().addAll(barrelImgView, dk, peach);
        
    }


    public void destroy(int lvl) {
        root.getChildren().removeAll(barrelImgView, dk, peach);
        levels.get(lvl).destroy();
    }


    public Rectangle drawOil() {
        double x_coord = 4 * App.section_width;
        double y_coord = App.height - 4.5 * App.section_height;

        // Draw Oil Drum 
        Rectangle oil = new Rectangle(x_coord, y_coord, 2 * App.section_width, 2.5 * App.section_height);
        oil.setFill(Color.BLUE);  

        // Draw Oil Draw Reflexions
        Rectangle oilTop = new Rectangle(x_coord - 0.1 * App.section_width, y_coord, 2.2 * App.section_width, .2 * App.section_height);
        oilTop.setFill(Color.BLUE);
        Rectangle oilBottom = new Rectangle(x_coord - 0.1 * App.section_width, y_coord + 2.3 * App.section_height, 2.2 * App.section_width, .2 * App.section_height);
        oilBottom.setFill(Color.BLUE);
        Rectangle x = new Rectangle(x_coord + 0.1 * App.section_width, y_coord + .2 * App.section_height, .2 * App.section_width, 2 * App.section_height);
        x.setFill(Color.LIGHTBLUE);
        Rectangle y = new Rectangle(x_coord, y_coord + 0.5 * App.section_height, 2 * App.section_width, .2 * App.section_height);
        y.setFill(Color.LIGHTBLUE);
        Rectangle z = new Rectangle(x_coord, y_coord + 1.7 * App.section_height, 2 * App.section_width, .2 * App.section_height);
        z.setFill(Color.LIGHTBLUE);

        Text oilText = new Text("OIL");
        oilText.setFont(Font.font("Arial", App.section_width * 30 / 35)); 
        oilText.setFill(Color.LIGHTBLUE);

        oilText.setLayoutX(x_coord + 0.4 * App.section_width);
        oilText.setLayoutY(y_coord + 1.55 * App.section_height);

        root.getChildren().addAll(oil, oilTop, oilBottom, x, y, z, oilText);

        // Draw 4 Red Circles
        for (int i=0; i<4; i++) {
            Circle circle = new Circle(x_coord + 0.5 * App.section_width + i * 0.4 * App.section_width, y_coord + 2.1 * App.section_height, 3);
            circle.setFill(Color.RED);
            root.getChildren().add(circle);
        }

        // Draw Flame On Top Of The Oil Drum
        fireImg.setCache(true);
        fireImg.setFitWidth(2 * App.section_width);
        fireImg.setFitHeight(App.section_height);
        root.getChildren().add(fireImg);
        fireImg.setX(x_coord);
        fireImg.setY(y_coord - App.section_height);

        Timeline fireTimeLine = new Timeline(new KeyFrame(Duration.millis(15), event -> {
            if (fireCounter < 15 || (fireCounter > 30 && fireCounter < 45)) {
                fireImg.setScaleX(1);
            } else {
                fireImg.setScaleX(-1);
            }
            if (fireCounter < 60)
                fireCounter++;
            else fireCounter =0;
        }));
        fireTimeLine.setCycleCount(Timeline.INDEFINITE);
        fireTimeLine.play();
        
        return oil;
    }



    public void drawBarrels() {
        // Draw 4 Barrels On The Top-Left Corner
        List<Double> dx = List.of(1.2, 1.2, 2.5, 2.5);
        List<Double> dy = List.of(1.0, 1.9, 1.9, 1.0);
        Image barrelNextToKong = new Image("file:src/main/resources/assets/images/barrels/barrel2.png");
        for(int i=0; i<4 ;i++) {
            ImageView barrel = new ImageView(barrelNextToKong);
            barrel.setCache(true);
            barrel.setFitWidth(2 * App.section_width);
            barrel.setFitHeight(2.5 * App.section_height);
            barrel.setX(dx.get(i) * App.section_width);
            barrel.setY(row6_top - 2.5 * App.section_height * dy.get(i));
            barrel.setRotate(90);
            root.getChildren().add(barrel);
        }
    }

    public Rectangle getTargetRect() {
        return targetRect;
    }


    public void animateKong(int barrelTime, int barrelSpawnTime, int barrelCount) {
        int phaseTime = barrelTime / 4;
        dk.setScaleX(1);
        barrelImgView.setImage(null);
        if (barrelSpawnTime - barrelCount > 3 * phaseTime) {
            dk.setImage(dk2);
        }
        else if (barrelSpawnTime - barrelCount > 2 * phaseTime) {
            dk.setImage(dk1);
        }
        else if (barrelSpawnTime - barrelCount > phaseTime) {
            dk.setImage(dk3);
        }
        else {
            dk.setScaleX(-1);
            dk.setImage(dk1);
            barrelImgView.setImage(barrelImage);
        }       
    }

    public ArrayList<Bridge> getBridges() {
        return bridge_objs;
    }

    public ArrayList<Ladder> getLadders() {
        return ladder_objs;
    }

    public ArrayList<Hammer> getHammers() {
        return hammer_objs;
    }

    public Rectangle getOilDrum() {
        return oilDrum;
    }


    public void drawHammers(int activeLevel) {
        levels.get(activeLevel).createHammers();
    }


    
}
