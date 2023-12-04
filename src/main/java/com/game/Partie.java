package main.java.com.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

public class Partie {
    // ImageViews
    ImageView fireImg = new ImageView(App.fireImg);
    ImageView barrelImg = new ImageView(App.barrelImg);
    ImageView dk = new ImageView();
    ImageView peach = new ImageView(App.peach2);

    // Initialize bridges rows
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

    private boolean fireBallTrigger = true;

    private int fireCounter = 0;
    private int counter = 0;

    private Level lvl1;

    private int score;
    private int highScore = 0;
    private int bonus = 6000;
    private int attempts;
    private int activeLevel = 0;
    private boolean reset = false;
    private boolean gameOver = false;
    private boolean victory = false;
    private Rectangle targetRect = new Rectangle();
    private Timeline gameTimeLine;

    List<Level> levels;

    private final int barrelSpawnTime = 360;   //3600, 1000
    private int barrelCount = barrelSpawnTime / 2;
    private int barrelTime = 180;   //2000, 500
    private ArrayList<Bridge> bridge_objs = new ArrayList<Bridge>();
    private ArrayList<Ladder> ladder_objs = new ArrayList<Ladder>();
    private ArrayList<Hammer> hammer_objs = new ArrayList<Hammer>();
    private List<Barrel> barrels = new ArrayList<>();
    private List<FireBall> fireBalls = new ArrayList<>();
    private Rectangle oilDrum;

    Pair<Boolean, Boolean> climbingStatus = new Pair<>(false, false);

    Text scoreText = new Text();
    Text highScoreText = new Text();
    Text symbolText = new Text();
    Text columnsText = new Text();
    Text infoText = new Text();
    Text gameOverText = new Text();
    Text victoryText = new Text();

    public Partie() {
        this.score = 0;
        this.attempts = 3;
        this.levels = new ArrayList<Level>();
    }


    public void createPartie(Group root, GraphicsContext gc, Scene scene) {
        
        // Draw Extras
        oilDrum = drawOil(root);
        drawBarrels(root);

        // Create level 1 
        lvl1 = new Level("LEVEL 1", 120, 
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
            List.of(10, row6_y - 5* App.section_height, 5)
        );

        ladder_objs = lvl1.createLadders(root);
        bridge_objs = lvl1.createBridges(root);
        hammer_objs = lvl1.createHammers(root);

        // Draw Kong
        dk.setCache(true);
        dk.setFitWidth(5 * App.section_height);
        dk.setFitHeight(5 * App.section_height);
        dk.setX(3.5 * App.section_width);
        dk.setY(row6_y - 5.5 * App.section_height);
        barrelImg.setCache(true);
        barrelImg.setFitWidth(50);
        barrelImg.setFitHeight(50);
        barrelImg.setX(230);
        barrelImg.setY(250);

        // Draw Peach
        peach.setCache(true);
        peach.setFitWidth(2 * App.section_height);
        peach.setFitHeight(3 * App.section_height);
        peach.setX(10 * App.section_width);
        peach.setY(row6_y - 6 * App.section_height - App.slope);

        // Set Victory Position
        targetRect.setX(lvl1.getTarget().get(0) * App.section_width);
        targetRect.setY(lvl1.getTarget().get(1));
        targetRect.setWidth(lvl1.getTarget().get(2) * App.section_width);
        targetRect.setHeight(50);
        
        // Add all elements to the root (to be seen)
        root.getChildren().addAll(barrelImg, dk, peach, scoreText, highScoreText, symbolText, columnsText, infoText, gameOverText, victoryText);
        
        // AnimationTimer gameLoop = new AnimationTimer() {
            //     @Override
            //     public void handle(long now) {
                //         renderGame(gc, root);
                //     }
                // };
                // gameLoop.start();
                
        Player player = new Player(7 * App.section_width, App.height - 6 * App.section_height, root);

        gameTimeLine = new Timeline(new KeyFrame(Duration.millis(15), event -> {
            renderGame(gc, root, scene, player);
            if (gameOver || reset) {
                gameTimeLine.stop();
                new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                    gameTimeLine.playFromStart();
                    return;
                })).play();
                return;
            }
        }));
        gameTimeLine.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        gameTimeLine.play();
        
    }
    
    
    private void renderGame(GraphicsContext gc, Group root, Scene scene, Player player) {
        // Draw Background
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // Check Victory
        victory = targetRect.getBoundsInParent().intersects(player.getRect().getBoundsInParent());
        if (victory) {
            resetGame(player, root);
            return;
        }

        if (barrelCount < barrelSpawnTime) {
            barrelCount++;
        } else {
            barrelCount = new Random().nextInt(180);
            barrelTime = barrelSpawnTime - barrelCount;
            Barrel barrel = new Barrel(270, 250, root);
            barrels.add(barrel);
        }

        for (Barrel barrel : barrels) {
            // barrel.checkFall(ladder_objs, root);   // feha mochkla sghira
            fireBallTrigger = barrel.update(bridge_objs, row1_top, row2_top, row3_top, row4_top, row5_top, oilDrum);
            
            if (fireBallTrigger) {
                FireBall fireBall = new FireBall(5 * App.section_width, App.height - 4 * App.section_height, root);
                fireBalls.add(fireBall);
                fireBallTrigger = false;
                barrel.clear(root);
                barrels.remove(barrel);
            }

            if (barrel.getRect().getBoundsInParent().intersects(player.getHammerBox().getBoundsInParent())) {
                barrel.clear(root);
                barrels.remove(barrel);
                score += 500;
            }

            if (barrel.getRect().getBoundsInParent().intersects(player.getHitbox().getBoundsInParent())) {
                resetGame(player, root);
                return;
            }
        }

        for (FireBall fireball : fireBalls) {
            fireball.checkFall(ladder_objs, row2_y, row3_y, row4_y, row5_y, row6_y);
            fireball.update(bridge_objs);
            if (fireball.getRect().getBoundsInParent().intersects(player.getHitbox().getBoundsInParent())) 
                resetGame(player, root);
                return;
            }

        for (Hammer hammer : hammer_objs) {
            hammer.draw(player);
        }

        animateKong(root);
        drawText();

        player.update(bridge_objs);
        player.draw();
        climbingStatus = player.checkClimb(ladder_objs);

        // check if the player is out of the window
        if (player.getRect().getLayoutX() + player.getRect().getWidth() < 0 || player.getRect().getLayoutX() > App.width || player.getRect().getLayoutY() < 0 || player.getRect().getLayoutY() > App.height) {
            resetGame(player, root);
            return;
        }

        keyboardManager(scene, player, climbingStatus.getKey(), climbingStatus.getValue());

        if (counter < 60)
            counter ++;
        else {
            counter = 0;
            if (bonus > 0)
                bonus -= 100;
            else {
                resetGame(player, root);
                return;
            }
        }

    }


    private void resetGame(Player player, Group root) {
        highScore = Math.max(highScore, score + bonus);
        
        if (victory) {
            drawText();
            victoryText.setText("You Win");
            victoryText.setFont(Font.font("Arial", 80)); 
            victoryText.setFill(Color.WHITE);
            victoryText.setX((App.width - victoryText.getLayoutBounds().getWidth()) / 2);
            victoryText.setY(App.height / 2);
            return;
        }

        if (attempts > 1 && !reset) { 
            reset = true;
            return;
        }

        for(Barrel barrel : barrels) {
            barrel.clear(root);
        }
        barrels.clear();
        for(FireBall fireBall : fireBalls) {
            fireBall.clear(root);
        }
        fireBalls.clear();
        for (Hammer hammer : hammer_objs) {
            hammer.clear(root);
        }
        hammer_objs.clear();

        bonus = 6000;
        score = 0;
        
        if (attempts > 1) {
            attempts --;           
            hammer_objs = lvl1.createHammers(root);
            player.setPosition(7 * App.section_width, App.height - 6 * App.section_height);
            fireBallTrigger = false;
            barrelCount = barrelSpawnTime / 2;
            reset = false;
            victory = false;
        } else if (attempts == 1) {
            gameOver = true;
            attempts --;           
            drawText();
            gameOverText.setText("Game Over");
            gameOverText.setFont(Font.font("Arial", 80)); 
            gameOverText.setFill(Color.WHITE);
            gameOverText.setX((App.width - gameOverText.getLayoutBounds().getWidth()) / 2);
            gameOverText.setY(App.height / 2);
            player.clear(root);
        }
    }


    public void keyboardManager(Scene scene, Player player, boolean climb, boolean down) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                KeyCode key = e.getCode();

                if ((key == KeyCode.RIGHT || key == KeyCode.D) && !player.isClimbing() ) {
                    // System.out.println("moving right");
                    player.setX_change(1);
                    player.setDir(1);
                }
                if ((key == KeyCode.LEFT || key == KeyCode.Q) && !player.isClimbing() ) {
                    // System.out.println("moving left");
                    player.setX_change(-1);
                    player.setDir(-1);
                }
                if (key == KeyCode.SPACE && player.isLanded() && !player.getHammer()) {
                    // System.out.println("jumping");
                    player.setLanded(false);
                    player.setY_change(-5.5);
                }
                if ((key == KeyCode.UP || key == KeyCode.Z) && !player.getHammer()) {
                    if (climb) {
                        // System.out.println("climbing up");
                        player.setY_change(-2);
                        player.setX_change(0);
                        player.setClimbing(true);
                    }
                }
                if ((key == KeyCode.DOWN || key == KeyCode.S) && !player.getHammer()) {
                    if (down) {
                        // System.out.println("climbing down");
                        player.setY_change(2);
                        player.setX_change(0);
                        player.setClimbing(true);
                    }
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                KeyCode key = e.getCode();

                if (key == KeyCode.RIGHT || key == KeyCode.D)  {
                    player.setX_change(0);
                }
                if (key == KeyCode.LEFT || key == KeyCode.Q) {
                    player.setX_change(0);
                }
                if (key == KeyCode.UP || key == KeyCode.Z || key == KeyCode.DOWN || key == KeyCode.S) {
                    if (climb) {
                        player.setY_change(0);
                    }
                    if (player.isClimbing() && player.isLanded()) {
                        player.setClimbing(false);
                    }
                }             
            }
        });
    }


    public void drawBarrels(Group root) {
        // Draw 4 Barrels On The Top-Left Corner
        List<Double> dx = List.of(1.2, 1.2, 2.5, 2.5);
        List<Double> dy = List.of(5.4, 7.7, 7.7, 5.4);
        for(int i=0; i<4 ;i++) {
            ImageView barrel = new ImageView(App.barrel2);
            barrel.setCache(true);
            barrel.setFitWidth(2 * App.section_width);
            barrel.setFitHeight(2.5 * App.section_height);
            barrel.setX(dx.get(i) * App.section_width);
            barrel.setY(dy.get(i) * App.section_height);
            barrel.setRotate(90);
            root.getChildren().add(barrel);
        }
    }


    public void animateKong(Group root) {
        int phaseTime = barrelTime / 4;
        dk.setScaleX(1);
        barrelImg.setImage(null);
        if (barrelSpawnTime - barrelCount > 3 * phaseTime) {
            dk.setImage(App.dk2);
        }
        else if (barrelSpawnTime - barrelCount > 2 * phaseTime) {
            dk.setImage(App.dk1);
        }
        else if (barrelSpawnTime - barrelCount > phaseTime) {
            dk.setImage(App.dk3);
        }
        else {
            dk.setScaleX(-1);
            dk.setImage(App.dk1);
            barrelImg.setImage(App.barrelImg);
        }       
    }


    public Rectangle drawOil(Group root) {
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

        // Add Text In The Oil Drum
        Text oilText = new Text("OIL");
        oilText.setFont(Font.font("Arial", 30)); 
        oilText.setFill(Color.LIGHTBLUE);

        // Set the position of the text
        oilText.setLayoutX(x_coord + 0.4 * App.section_width);
        oilText.setLayoutY(y_coord + 1.55 * App.section_height);

        // Add The Element To The Scene
        root.getChildren().addAll(oil, oilTop, oilBottom, x, y, z, oilText);

        // Draw 4 Red Circles
        for (int i=0; i<4; i++) {
            Circle circle = new Circle(x_coord + 0.5 * App.section_width + i * 0.4 * App.section_width, y_coord + 2.1 * App.section_height, 3);
            circle.setFill(Color.RED);
            root.getChildren().add(circle);
        }

        // Draw Flames On Top Of The Oil Drum
        fireImg.setCache(true);
        fireImg.setFitWidth(2 * App.section_width);
        fireImg.setFitHeight(App.section_height);
        root.getChildren().add(fireImg);
        fireImg.setX(x_coord);
        fireImg.setY(y_coord - App.section_height);

        // Set A fireCounter To Increment Every Second        
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
        fireTimeLine.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        fireTimeLine.play();
        
        return oil;
    }


    public void drawText() {
        Font font = Font.font("Arial", 50);
        Font font2 = Font.font("Arial", 30);

        scoreText.setText("I•" + score);
        scoreText.setFont(font); 
        scoreText.setFill(Color.WHITE);
        scoreText.setX(3 * App.section_width);
        scoreText.setY(3 * App.section_height);

        highScoreText.setText("TOP•" + highScore);
        highScoreText.setFont(font); 
        highScoreText.setFill(Color.WHITE);
        highScoreText.setX(14 * App.section_width);
        highScoreText.setY(3 * App.section_height);

        symbolText.setText("[  ][        ][  ]");
        symbolText.setFont(font); 
        symbolText.setFill(Color.WHITE);
        symbolText.setX(20 * App.section_width);
        symbolText.setY(5 * App.section_height);

        columnsText.setText("  ♥     BONUS      L ");
        columnsText.setFont(font2); 
        columnsText.setFill(Color.WHITE);
        columnsText.setX(20 * App.section_width + 5);
        columnsText.setY(4 * App.section_height);

        if (bonus >= 1000)
            infoText.setText("  " + attempts + "       " + bonus + "        " + (activeLevel + 1));
        else 
            infoText.setText("  " + attempts + "         " + bonus + "        " + (activeLevel + 1));
        infoText.setFont(font2); 
        infoText.setFill(Color.WHITE);
        infoText.setX(20 * App.section_width + 5);
        infoText.setY(5.5 * App.section_height);

    }
    
}
