package main.java.com.game;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class Level {
    
    private String name;

    private List<List<Integer>> bridges;
    private ArrayList<Bridge> bridge_objs = new ArrayList<Bridge>();

    private List<List<Integer>> ladders;
    private ArrayList<Ladder> ladder_objs = new ArrayList<Ladder>();

    private List<List<Integer>> hammers;
    private ArrayList<Hammer> hammer_objs = new ArrayList<Hammer>();

    private List<Integer> target;
    Rectangle targetRect = new Rectangle();

    public Level(String name, List<List<Integer>> bridges, List<List<Integer>> ladders, List<List<Integer>> hammers, List<Integer> target) {
        this.name = name;
        this.bridges = bridges;
        this.ladders = ladders;
        this.hammers = hammers;
        this.target = target;
    }
    
    public ArrayList<Bridge> createBridges(Group root) {
        root.getChildren().add(targetRect);
        for(List<Integer> bridge : bridges) {
            bridge_objs.add(new Bridge(bridge.get(0), bridge.get(1), bridge.get(2), root));
        }
        return bridge_objs;
    }

    public ArrayList<Ladder> createLadders(Group root) {
        for(List<Integer> ladder : ladders) {
            ladder_objs.add(new Ladder(ladder.get(0), ladder.get(1), ladder.get(2), root));
        }
        return ladder_objs;
    }

    public ArrayList<Hammer> createHammers(Group root) {
        for(List<Integer> hammer : hammers) {
            hammer_objs.add(new Hammer(hammer.get(0), hammer.get(1), root));
        }
        return hammer_objs;
    }

    public List<Integer> getTarget() {    
        return target;
    }

}
