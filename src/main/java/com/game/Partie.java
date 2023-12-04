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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

public class Partie {

    private Map map ;

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

    private int counter = 0;


    private int score;
    private int highScore = 0;
    private int bonus = 6000;
    private int attempts;
    private int activeLevel = 0;
    private boolean reset = false;
    private boolean gameOver = false;
    private boolean victory = false;
    
    private Timeline gameTimeLine;

    List<Level> levels;

    private final int barrelSpawnTime = 360;   //3600, 1000
    private int barrelCount = barrelSpawnTime / 2;
    private int barrelTime = 180;   //2000, 500
    private ArrayList<Bridge> bridge_objs;
    private ArrayList<Ladder> ladder_objs;
    private ArrayList<Hammer> hammer_objs;
    private List<Barrel> barrels = new ArrayList<>();
    private List<FireBall> fireBalls = new ArrayList<>();

    Pair<Boolean, Boolean> climbingStatus = new Pair<>(false, false);

    Text scoreText = new Text();
    Text highScoreText = new Text();
    Text symbolText = new Text();
    Text columnsText = new Text();
    Text infoText = new Text();
    Text gameOverText = new Text();
    Text victoryText = new Text();

    public Partie() {
        score = 0;
        attempts = 3;
        levels = new ArrayList<Level>();
        map = new Map();
    }


    public void createPartie(Group root, GraphicsContext gc, Scene scene) {
    
        map.draw(root);
        bridge_objs = map.getBridges();
        ladder_objs = map.getLadders();
        hammer_objs = map.getHammers();
        

        // Add all elements to the root (to be seen)
        root.getChildren().addAll(scoreText, highScoreText, symbolText, columnsText, infoText, gameOverText, victoryText);
        
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
        victory = map.getTargetRect().getBoundsInParent().intersects(player.getRect().getBoundsInParent());
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
            fireBallTrigger = barrel.update(bridge_objs, row1_top, row2_top, row3_top, row4_top, row5_top, map.getOilDrum());
            
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
            if (fireball.getRect().getBoundsInParent().intersects(player.getHitbox().getBoundsInParent())) {
                resetGame(player, root);
                return;
            }
        }

        for (Hammer hammer : hammer_objs) {
            hammer.draw(player);
        }

        map.animateKong(root, barrelTime, barrelSpawnTime, barrelCount);
        drawText();

        player.update(bridge_objs);
        player.draw();
        climbingStatus = player.checkClimb(ladder_objs);

        // check if the player is out of the window
        if (player.getRect().getLayoutX() + player.getRect().getWidth() < 0 || player.getRect().getLayoutX() > App.width || player.getRect().getLayoutY() < 0 || player.getRect().getLayoutY() > App.height) {
            resetGame(player, root);
            return;
        }
        
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
        
        keyboardManager(scene, player, climbingStatus.getKey(), climbingStatus.getValue());
    
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
            map.drawHammers(root);
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

                if (key == KeyCode.RIGHT || key == KeyCode.D || key == KeyCode.LEFT || key == KeyCode.Q) {
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
