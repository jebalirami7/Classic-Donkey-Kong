package main.java.com.game;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;

public class Level {
    
    private String name;
    private int timeLimit;

    private List<List<Integer>> bridges;
    private ArrayList<Bridge> bridge_objs = new ArrayList<Bridge>();

    private List<List<Integer>> ladders;
    private ArrayList<Ladder> ladder_objs = new ArrayList<Ladder>();

    public Level(String name, int timeLimit, List<List<Integer>> bridges, List<List<Integer>> ladders) {
        this.name = name;
        this.timeLimit = timeLimit;
        this.bridges = bridges;
        this.ladders = ladders;
    }

    ArrayList<Bridge> createBridges(Group root) {
        for(int i=0; i<bridges.size(); i++) {
            bridge_objs.add(new Bridge(bridges.get(i).get(0), bridges.get(i).get(1), bridges.get(i).get(2), root));
        }
        return bridge_objs;
    }

    ArrayList<Ladder> createLadders(Group root) {
        for(int i=0; i<ladders.size(); i++) {
            ladder_objs.add(new Ladder(ladders.get(i).get(0), ladders.get(i).get(1), ladders.get(i).get(2), root));
        }
        return ladder_objs;
    }

}
