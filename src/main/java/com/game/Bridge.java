package main.java.com.game;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Bridge {
    
    private int x_pos;
    private int y_pos;
    private int length;
    private Rectangle top;


    public Bridge(int x_pos, int y_pos, int length, Group root) {
        this.x_pos = x_pos * App.section_width;
        this.y_pos = y_pos;
        this.length = length;
        this.top = draw(root);
    }


    private Rectangle draw(Group root) {
        int line_width = 7;
        Color color = Color.rgb(225, 51, 129);

        for(int i=0; i<this.length; i++) {
            int bot_coor = this.y_pos + App.section_width;
            int left_coor = this.x_pos + (App.section_width * i);
            int mid_coor = left_coor + (App.section_width / 2);
            int rigth_coor = left_coor + App.section_width;
            int top_coor = this.y_pos;
            
            // Create lines
            Line line1 = new Line(left_coor, top_coor, rigth_coor, top_coor);
            line1.setStroke(color); 
            line1.setStrokeWidth(line_width);

            Line line2 = new Line(left_coor, bot_coor, rigth_coor, bot_coor);
            line2.setStroke(color); 
            line2.setStrokeWidth(line_width);

            Line line3 = new Line(left_coor, bot_coor, mid_coor, top_coor);
            line3.setStroke(color); 
            line3.setStrokeWidth(line_width);

            Line line4 = new Line(mid_coor, top_coor, rigth_coor, bot_coor);
            line4.setStroke(color); 
            line4.setStrokeWidth(line_width);

            // Add the new Line to the existing scene
            root.getChildren().addAll(line1, line2, line3, line4);
        }
        Rectangle topLine = new Rectangle(x_pos, y_pos, this.length * App.section_width, 2);
        topLine.setFill(Color.BLUE);

        root.getChildren().add(topLine);

        return topLine;
    }


    public Rectangle getTop() {
        return top;
    }


}
