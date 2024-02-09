package main.java.com.game.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import main.java.com.game.App;

public class Partie {

    private Map map ;

    private Group root;

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

    private Player p;
    private Mario mario;
    private int score;
    private int highScore;
    private int bonus = 6000;
    private int attempts;
    private int activeLevel = 0;
    private boolean reset = false;
    private boolean gameOver = false;
    private boolean victory = false;
    private boolean paused = false;
    
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
    Text menuText = new Text();

    private int selectedIndex = 0;
    private Text[] menuItems;

    Pair<Integer, Integer> playerPosition = new Pair<>(7 * App.section_width, (int) App.height - 6 * App.section_height);

    Music winAudio;
    Music loseAudio;
    Music gameOverAudio;
    Music gameAudio;

    Scene menuScene;

    Font font = Font.font("Arial", App.section_width * 50 / 35);
    Font font1 = Font.font("Arial", App.section_width * 80 / 35);
    Font font2 = Font.font("Arial", App.section_width * 28 / 35);
    
    public Partie(Group root, Player p, Scene menuScene) {
        this.menuScene = menuScene;
        this.root = root;
        this.p = p;
        score = 0;
        attempts = 3;
        highScore = p.getScore();
        levels = new ArrayList<Level>();
        map = new Map(root);
        winAudio = new Music("/main/resources/media/win.wav");
        loseAudio = new Music("/main/resources/media/reset.wav");
        gameOverAudio = new Music("/main/resources/media/gameOver.wav");
        gameAudio = new Music("/main/resources/media/gameSound.wav");
    }


    public void createPartie(GraphicsContext gc, Scene scene) {
    
        map.draw(activeLevel);
        bridge_objs = map.getBridges();
        ladder_objs = map.getLadders();
        hammer_objs = map.getHammers();

        root.getChildren().addAll(scoreText, highScoreText, symbolText, columnsText, infoText);

        mario = new Mario(playerPosition.getKey(), playerPosition.getValue(), root);

        gameTimeLine = new Timeline(new KeyFrame(Duration.millis(15), event -> {
            renderGame(gc, scene);
            if (victory || gameOver || reset) {
                gameTimeLine.stop();
                if (gameOver) {
                    gameAudio.stop();
                    gameOverAudio.play();
                    new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                        gameOverAudio.stop();
                        return;
                    })).play();
                }
                if (victory) {
                    gameAudio.stop();
                    winAudio.play();   
                    new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                        winAudio.stop();
                        return;
                    })).play();                 
                }
                if (reset) {
                    new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                        gameTimeLine.playFromStart();
                        return;
                    })).play();
                }
                return;
            }
        }));
        gameTimeLine.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        gameTimeLine.play();
        
    }

    
    private void restartGame() {
        score = 0;
        attempts = 3;
        bonus = 6000;
        
        for(Barrel barrel : barrels) {
            barrel.clear(root);
        }
        barrels.clear();
        for(FireBall fireBall : fireBalls) {
            fireBall.clear(root);
        }
        fireBalls.clear();
        for (Hammer hammer : hammer_objs) {
            hammer.clear();
        }
        hammer_objs.clear();

        map.drawHammers(activeLevel);
        mario.reset(playerPosition.getKey(), playerPosition.getValue());
        fireBallTrigger = false;
        barrelCount = barrelSpawnTime / 2;
        reset = false;
        victory = false;
        gameOver = false;
    }


    private void nextLevel() {
        attempts++;
        bonus += 6000;
        
        for(Barrel barrel : barrels) {
            barrel.clear(root);
        }
        barrels.clear();
        for(FireBall fireBall : fireBalls) {
            fireBall.clear(root);
        }
        fireBalls.clear();
        for (Hammer hammer : hammer_objs) {
            hammer.clear();
        }
        hammer_objs.clear();

        map.destroy(activeLevel - 1);
        map.draw(activeLevel);
        bridge_objs = map.getBridges();
        ladder_objs = map.getLadders();
        hammer_objs = map.getHammers();
        map.drawHammers(activeLevel);
        mario.reset(playerPosition.getKey(), playerPosition.getValue());
        fireBallTrigger = false;
        barrelCount = barrelSpawnTime / 2;
        reset = false;
        victory = false;
        gameOver = false;
    }


    private void renderGame(GraphicsContext gc, Scene scene) {
        // Draw Background
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        
        loseAudio.stop();
        gameAudio.play();

        // Check Victory
        victory = map.getTargetRect().getBoundsInParent().intersects(mario.getRect().getBoundsInParent());
        if (victory) {
            resetGame();
            return;
        }

        if (barrelCount < barrelSpawnTime) {
            barrelCount++;
        } else {
            barrelCount = new Random().nextInt(180);
            barrelTime = barrelSpawnTime - barrelCount;
            Barrel barrel = new Barrel(App.width * 270 / 1120, row6_top - (double)App.section_height * 50 / 29, root);
            barrels.add(barrel);
            if (activeLevel >= 1) {
                FireBall fireBall = new FireBall(5 * App.section_width, App.height - 4 * App.section_height, root);
                fireBalls.add(fireBall);
            }
        }

        Iterator<Barrel> barrelIterator = barrels.iterator();
        while (barrelIterator.hasNext()) {
            Barrel barrel = barrelIterator.next();

            // barrel.checkFall(ladder_objs, root);   // feha mochkla sghira
            fireBallTrigger = barrel.update(bridge_objs, row1_top, row2_top, row3_top, row4_top, row5_top, map.getOilDrum());
            
            if (fireBallTrigger) {
                barrelIterator.remove();
                barrel.clear(root);
                FireBall fireBall = new FireBall(5 * App.section_width, App.height - 4 * App.section_height, root);
                fireBalls.add(fireBall);
                fireBallTrigger = false;
            }

            if (barrel.getRect().getBoundsInParent().intersects(mario.getHammerBox().getBoundsInParent())) {
                barrelIterator.remove();
                barrel.clear(root);
                score += 500;
            }

            if (barrel.getRect().getBoundsInParent().intersects(mario.getHitbox().getBoundsInParent())) {
                resetGame();
                return;
            }
        }

        for (FireBall fireball : fireBalls) {
            fireball.checkFall(ladder_objs, row2_y, row3_y, row4_y, row5_y, row6_y);
            fireball.update(bridge_objs);
            if (fireball.getRect().getBoundsInParent().intersects(mario.getHitbox().getBoundsInParent())) {
                resetGame();
                return;
            }
        }

        for (Hammer hammer : hammer_objs) {
            hammer.draw(mario);
        }

        map.animateKong(barrelTime, barrelSpawnTime, barrelCount);
        drawText();

        mario.update(bridge_objs);
        mario.draw();
        climbingStatus = mario.checkClimb(ladder_objs);

        // check if the player is out of the window
        if (mario.getRect().getLayoutX() + mario.getRect().getWidth() < 0 || mario.getRect().getLayoutX() > App.width || mario.getRect().getLayoutY() < 0 || mario.getRect().getLayoutY() > App.height) {
            resetGame();
            return;
        }
        
        if (counter < 60)
        counter ++;
        else {
            counter = 0;
            if (bonus > 0)
            bonus -= 100;
            else {
                resetGame();
                return;
            }
        }
        
        keyboardManager(scene, climbingStatus.getKey(), climbingStatus.getValue());
    
    }


    private void resetGame() {
        if (victory) {
            highScore = Math.max(highScore, score + bonus);
            System.out.println("New High Score: " + highScore + " by " + p.getName());
            if (p.getScore() < highScore){
                p.setScore(highScore);
                SaveData.update(p.getName(), p.getScore());
            }
            drawText();
            root.getChildren().add(menu("You Win"));
            return;
        }

        if (attempts > 1 && !reset) { 
            loseAudio.play();
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
            hammer.clear();
        }
        hammer_objs.clear();

        bonus = 6000;
        score = 0;
        
        if (attempts > 1) {
            attempts --;           
            map.drawHammers(activeLevel);
            mario.reset(playerPosition.getKey(), playerPosition.getValue());
            fireBallTrigger = false;
            barrelCount = barrelSpawnTime / 2;
            reset = false;
            victory = false;
        } else if (attempts == 1) {
            gameOver = true;
            attempts --;           
            drawText();
            root.getChildren().add(menu("Game Over"));
        }
    }


    public void keyboardManager(Scene scene, boolean climb, boolean down) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                KeyCode key = e.getCode();

                if ((key == KeyCode.RIGHT || key == KeyCode.D) && !mario.isClimbing() ) {
                    // System.out.println("moving right");
                    mario.setX_change(1);
                    mario.setDir(1);
                }
                if ((key == KeyCode.LEFT || key == KeyCode.Q) && !mario.isClimbing() ) {
                    // System.out.println("moving left");
                    mario.setX_change(-1);
                    mario.setDir(-1);
                }
                if (key == KeyCode.SPACE && mario.isLanded() && !mario.getHammer()) {
                    // System.out.println("jumping");
                    mario.setLanded(false);
                    mario.setY_change(-App.section_height * 5.5 / 29);
                }
                if ((key == KeyCode.UP || key == KeyCode.Z) && !mario.getHammer()) {
                    if (climb) {
                        // System.out.println("climbing up");
                        mario.setY_change(-App.section_height * 2 / 29);
                        mario.setX_change(0);
                        mario.setClimbing(true);
                    }
                }
                if ((key == KeyCode.DOWN || key == KeyCode.S) && !mario.getHammer()) {
                    if (down) {
                        // System.out.println("climbing down");
                        mario.setY_change(App.section_height * 2 / 29);
                        mario.setX_change(0);
                        mario.setClimbing(true);
                    }
                }
                if ((key == KeyCode.P || key == KeyCode.ESCAPE) && !victory && !gameOver) {
                    if (paused) {
                        gameTimeLine.play();
                        paused = false;
                        menuText.setText("");
                        root.getChildren().remove(root.getChildren().size() - 1);
                    } else {
                        gameAudio.pause();
                        gameTimeLine.pause();
                        paused = true;
                        root.getChildren().add(menu("Game Paused"));
                    }
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                KeyCode key = e.getCode();

                if (key == KeyCode.RIGHT || key == KeyCode.D || key == KeyCode.LEFT || key == KeyCode.Q) {
                    mario.setX_change(0);
                }
                if (key == KeyCode.UP || key == KeyCode.Z || key == KeyCode.DOWN || key == KeyCode.S) {
                    if (climb) {
                        mario.setY_change(0);
                    }
                    if (mario.isClimbing() && mario.isLanded()) {
                        mario.setClimbing(false);
                    }
                }             
            }
        });
    }


    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            moveUP();
        } else if (event.getCode() == KeyCode.DOWN) {
            moveDOWN();
        } else if (event.getCode() == KeyCode.ENTER) {
            handleMenuItemAction(selectedIndex);
        }
    }
    private void moveUP() {
        selectedIndex--;
        if (selectedIndex < 0) selectedIndex = menuItems.length -1 ;
        updateSelection();
        
    }
    private void moveDOWN() {
        selectedIndex++;
        if (selectedIndex >= menuItems.length) selectedIndex = 0;
        updateSelection();
        
    }
    private void updateSelection() {
        for (int i = 0; i < menuItems.length; i++) {
            if (i == selectedIndex) {
                menuItems[i].setFill(Color.BLUE);
                menuItems[i].setStyle("-fx-font-weight: bold;");
            } else {
                menuItems[i].setFill(Color.WHITE);
                menuItems[i].setStyle("-fx-font-weight: normal;");
            }
        }
    }
    private void handleMenuItemAction(int selectedIndex) {
        if (selectedIndex == 1 && menuItems.length == 3 || selectedIndex == 2 && menuItems.length == 4) {
            gameTimeLine.playFromStart();
            paused = false;
            menuText.setText("");
            root.getChildren().remove(root.getChildren().size() - 1);
            restartGame();
        } else if (selectedIndex == 0 && menuItems.length == 4) {
            root.getChildren().remove(root.getChildren().size() - 1);
            if (victory) {
                activeLevel++;
                System.out.println("Congrats You Passed To Level " + (activeLevel + 2));
                nextLevel();
            }
            gameTimeLine.play();
            paused = false;
            menuText.setText("");
        } else if (selectedIndex == menuItems.length - 3) {
            System.out.println("back to main menu");
            backToMenu();
        } else if (selectedIndex == menuItems.length - 1) {
            Platform.exit();
        }
    }


    private void backToMenu() {
        Stage stage = (Stage) root.getScene().getWindow();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = screenBounds.getMinX() + (screenBounds.getWidth() - App.width) / 2;
        double centerY = screenBounds.getMinY() + (screenBounds.getHeight() - App.height) / 2;
        stage.setX(centerX);
        stage.setY(centerY);
        stage.setScene(menuScene);
        stage.show();
    }


    public VBox menu(String s) {
        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10; -fx-padding: 30;");
        vbox.setAlignment(Pos.CENTER);
        vbox.setOnKeyPressed(e -> {handleKeyPressed(e);});
        
        Text text = new Text();
        text.setText(s);
        text.setFont(font1); 
        text.setFill(Color.WHITE);
        vbox.getChildren().add(text);

        Text mainMenuLabel = new Text("MAIN MENU");
        mainMenuLabel.setFont(font);
        Text resumeLabel = new Text("RESUME");
        resumeLabel.setFont(font);
        Text nextLevelLabel = new Text("NEXT LEVEL");
        nextLevelLabel.setFont(font);
        Text replayLabel = new Text("REPLAY");
        replayLabel.setFont(font);
        Text exitLabel = new Text("EXIT");
        exitLabel.setFont(font);

        if (s == "Game Paused") {
            menuItems = new Text[]{resumeLabel, mainMenuLabel, replayLabel, exitLabel};
            vbox.getChildren().add(resumeLabel);
        } else if (s == "You Win") {
            menuItems = new Text[]{nextLevelLabel, mainMenuLabel, replayLabel, exitLabel};
            vbox.getChildren().add(nextLevelLabel);
        } else {
            menuItems = new Text[]{mainMenuLabel, replayLabel, exitLabel};
        }

        selectedIndex = 0;
        updateSelection();
        for (Text menuItem : menuItems) {
            menuItem.setFocusTraversable(true);
        }
        menuItems[selectedIndex].requestFocus(); 

        vbox.setAlignment(Pos.CENTER);

        vbox.setLayoutX((App.width - text.getLayoutBounds().getWidth()) / 2 - 30);
        vbox.setLayoutY((App.height - (text.getLayoutBounds().getHeight() + nextLevelLabel.getLayoutBounds().getHeight() + mainMenuLabel.getLayoutBounds().getHeight() + resumeLabel.getLayoutBounds().getHeight() + replayLabel.getLayoutBounds().getHeight() + exitLabel.getLayoutBounds().getHeight())) / 2 - 30);
                
        vbox.getChildren().addAll(mainMenuLabel, replayLabel, exitLabel);
        return vbox;
    }


    public void drawText() {
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

        columnsText.setText("  ♥      BONUS       L ");
        columnsText.setFont(font2); 
        columnsText.setFill(Color.WHITE);
        columnsText.setX(20 * App.section_width + 5);
        columnsText.setY(4 * App.section_height);

        if (bonus >= 10000)
            infoText.setText("  " + attempts + "       " + bonus + "        " + (activeLevel + 1));
        else if (bonus >= 1000)
            infoText.setText("  " + attempts + "        " + bonus + "         " + (activeLevel + 1));
        else 
            infoText.setText("  " + attempts + "          " + bonus + "         " + (activeLevel + 1));
        infoText.setFont(font2); 
        infoText.setFill(Color.WHITE);
        infoText.setX(20 * App.section_width + 5);
        infoText.setY(5.5 * App.section_height);

    }
    
}
