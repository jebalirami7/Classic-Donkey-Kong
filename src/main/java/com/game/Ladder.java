package main.java.com.game;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Ladder {
    private double x_pos;
    private double y_pos;
    private double length;
    private Rectangle body;


    public Ladder(double x_pos, double y_pos, double length, Group root) {
        this.x_pos = x_pos * App.section_width;
        this.y_pos = y_pos;
        this.length = length;
        this.body = draw(root);
    }


    private Rectangle draw(Group root) {
        int line_width = 3;
        Color color = Color.LIGHTBLUE;
        double lad_height = 0.6;

        for(int i=0; i<this.length; i++) {
            double top_coord = this.y_pos + lad_height * App.section_height * i;
            double bot_coord = top_coord + lad_height * App.section_height;
            double mid_coord = (lad_height / 2) * App.section_height + top_coord;
            double left_coord = this.x_pos;
            double right_coord = left_coord + App.section_width;
            
            // Create 3 lines
            Line line1 = new Line(left_coord, top_coord, left_coord, bot_coord);
            line1.setStroke(color); 
            line1.setStrokeWidth(line_width);

            Line line2 = new Line(right_coord, top_coord, right_coord, bot_coord);
            line2.setStroke(color);
            line2.setStrokeWidth(line_width);

            Line line3 = new Line(left_coord, mid_coord, right_coord, mid_coord);
            line3.setStroke(color); 
            line3.setStrokeWidth(line_width);

            // Add the new lines to the existing scene
            root.getChildren().addAll(line1, line2, line3);
        }

        // Create Top Line 
        Rectangle body = new Rectangle(this.x_pos, this.y_pos - App.section_height, App.section_width, lad_height * this.length * App.section_height + App.section_height);
        body.setFill(Color.TRANSPARENT);   // change color to ckeck its position

        root.getChildren().add(body);

        return body;
    }

    public Rectangle getBody() {
        return body;
    }

}
