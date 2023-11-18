package main.java.com.game;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Bridge {
    
    private double x_pos;
    private double y_pos;
    private double length;
    private Rectangle top;


    public Bridge(double x_pos, double y_pos, double length, Group root) {
        this.x_pos = x_pos * App.section_width;
        this.y_pos = y_pos;
        this.length = length;
        this.top = draw(root);
    }


    private Rectangle draw(Group root) {
        int line_width = 6;
        Color color = Color.rgb(225, 51, 129);

        for(int i=0; i<this.length; i++) {
            double bot_coord = this.y_pos + App.section_width;
            double left_coord = this.x_pos + (App.section_width * i);
            double mid_coord = left_coord + (App.section_width / 2);
            double right_coord = left_coord + App.section_width;
            double top_coord = this.y_pos;
            
            // Create 4 lines
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

            // Add the new lines to the existing scene
            root.getChildren().addAll(line1, line2, line3, line4);
        }

        // Create Top Line 
        Rectangle topLine = new Rectangle(x_pos, y_pos, this.length * App.section_width, 2);
        topLine.setFill(Color.TRANSPARENT);   // change color to ckeck its position

        root.getChildren().add(topLine);

        return topLine;
    }


    public Rectangle getTop() {
        return top;
    }


}
