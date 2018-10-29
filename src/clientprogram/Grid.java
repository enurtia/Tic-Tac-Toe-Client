package clientprogram;

import java.awt.Color;
import java.awt.Graphics2D;

public class Grid 
{
    public static void drawGrid(Color color, Graphics2D g)
    {
        g.setColor(color);

        //Outer Lines	
        g.fillRect(20, 20, 630, 2);
        g.fillRect(20, 650, 630, 2);
        g.fillRect(20, 20, 2, 630);
        g.fillRect(650, 20, 2, 632);

        //Inner Grid
        g.fillRect(20, 230, 630, 2);	//Horizontal
        g.fillRect(20, 440, 630, 2);

        g.fillRect(230, 20, 2, 630);	//Vertical
        g.fillRect(440, 20, 2, 630);
    }
}
