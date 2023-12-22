package main.java.com.game.models;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import main.java.com.game.App;

public class Bridge {
    
    private double x_pos;
    private double y_pos;
    private double length;
    private Rectangle top;
    private Color color;

    private ArrayList<Line> lines;

    private Group root;


    public Bridge(double x_pos, double y_pos, double length, Color color, Group root) {
        this.color = color;
        this.root = root;
        this.x_pos = x_pos * App.section_width;
        this.y_pos = y_pos;
        this.length = length;
        lines = new ArrayList<Line>();
        this.top = draw();
    }


    private Rectangle draw() {
        double line_width = App.section_width * 6 / 35;

        for(int i=0; i<this.length; i++) {
            double bot_coord = this.y_pos + App.section_height;
            double left_coord = this.x_pos + (App.section_width * i);
            double mid_coord = left_coord + (App.section_width / 2);
            double right_coord = left_coord + App.section_width;
            double top_coord = this.y_pos;
            
            Line line1 = new Line(left_coord, top_coord, right_coord, top_coord);
            line1.setStroke(color); 
            line1.setStrokeWidth(line_width);

            Line line2 = new Line(left_coord, bot_coord, right_coord, bot_coord);
            line2.setStroke(color); 
            line2.setStrokeWidth(line_width);

            Line line3 = new Line(left_coord, bot_coord, mid_coord, top_coord);
            line3.setStroke(color); 
            line3.setStrokeWidth(line_width);

            Line line4 = new Line(mid_coord, top_coord, right_coord, bot_coord);
            line4.setStroke(color); 
            line4.setStrokeWidth(line_width);

            root.getChildren().addAll(line1, line2, line3, line4);

            lines.add(line1);
            lines.add(line2);
            lines.add(line3);
            lines.add(line4);
        }

        Rectangle topLine = new Rectangle(x_pos, y_pos, this.length * App.section_width, 2);
        topLine.setFill(Color.TRANSPARENT);

        root.getChildren().add(topLine);

        return topLine;
    }


    public Rectangle getTop() {
        return top;
    }

    public void clear() {
        for(Line line:lines) {
            root.getChildren().remove(line);
        }
        root.getChildren().remove(top);
    }



}
