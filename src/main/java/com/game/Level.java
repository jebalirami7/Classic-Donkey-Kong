package main.java.com.game;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;

public class Level {
    
    private List<List<Integer>> bridges;
    private ArrayList<Bridge> bridge_objs;

    public Level(List<List<Integer>> bridges) {
        this.bridges = bridges;
        this.bridge_objs = new ArrayList<Bridge>();
    }

    void create(Group root) {
        for(int i=0; i<bridges.size(); i++) {
            bridge_objs.add(new Bridge(bridges.get(i).get(0), bridges.get(i).get(1), bridges.get(i).get(2), root));
        }
    }

}
