package Main;

import java.awt.*;

public class Board {

    final int MAX_COLUMN = 8;
    final int MAX_ROW = 8;
    public static final int SQUARE_SIZE = 100;
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE/2;

    public void draw(Graphics2D g2)
    {
        String color = "LIGHT_SQUARE";

        for (int row = 0; row < MAX_ROW; row++){
            if (color == "LIGHT_SQUARE"){
                color = "DARK_SQUARE";
            }
            else {
                color = "LIGHT_SQUARE";
            }
            for (int col = 0; col < MAX_COLUMN; col++){
                if (color == "LIGHT_SQUARE"){
                    g2.setColor(new Color(210,165,125));
                    color = "DARK_SQUARE";
                }
                else {
                    g2.setColor(new Color(175,115,70));
                    color = "LIGHT_SQUARE";
                }
                g2.fillRect(col * SQUARE_SIZE, row * SQUARE_SIZE , SQUARE_SIZE, SQUARE_SIZE);

            }
        }
    }



}
