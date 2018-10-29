package clientprogram;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;

public class X extends Shapes
{
    private Color color;
    private int x, y, radius, thickness;
    private final int maxTick;
    private int iTick;
    private int box;
    private Shape staticRect;

    public X(int box, int radius, int thickness, Color color)
    {
        this.box = box;
        Point p = Box.getPoint(box);
        this.x = (int) p.getX();
        this.y = (int) p.getY();

        this.radius =  radius;
        this.thickness = thickness;
        this.color = color;

        iTick = 0;
        maxTick = radius * 4;

        int thickness2 = thickness + 1;		//Adjusted variables
        int radius2 = radius + 121;
        staticRect = new RoundRectangle2D.Double(x - (radius2 / 2), y, radius2, thickness2, 10, 100);
    }

    @Override
    void draw(Graphics2D g) 
    {
        g.setColor(color);

        int side = (int)(radius / Math.sqrt(2));		//Right Triangle (45 degrees), radius is hypotenuse, therefore side is divided by sqrt 2

        if(iTick < maxTick)	//Movement
        {
            for(int i = 0; i <= iTick / 2; i++)
            {
                int tempI = (int)(i / Math.sqrt(2));	//i divided by sqrt 2 since Right Triangle (45 degrees)
                g.fillOval(x - side + tempI, y - side + tempI, thickness, thickness);
            }

                if(iTick >= maxTick / 2)
                {
                    for(int i = 0; i <= iTick - (maxTick / 2); i++)
                    {
                        int tempI = (int)(i / Math.sqrt(2));//i divided by sqrt 2 since Right Triangle (45 degrees)
                        g.fillOval(x + side - tempI, y - side + tempI, thickness, thickness);
                    }
                }
            }

            if(iTick >= maxTick)	//Static
            {			
                for(int i = 0; i <= iTick / 2; i++)
                {
                    int tempI = (int)(i / Math.sqrt(2));	//i divided by sqrt 2 since Right Triangle (45 degrees)
                    g.fillOval(x - side + tempI, y - side + tempI, thickness, thickness);
                }
                for(int i = 0; i <= iTick / 2; i++)
                {
                    int tempI = (int)(i / Math.sqrt(2));	//i divided by sqrt 2 since Right Triangle (45 degrees)
                    g.fillOval(x + side - tempI, y - side + tempI, thickness, thickness);
                }
            }
    }

    @Override
    void drawStatic(Graphics2D g) 
    {
        AffineTransform beforeRotate = g.getTransform();

        g.setColor(color);
        g.rotate(Math.toRadians(45), x, y + 19);
        g.fill(staticRect);
        g.rotate(Math.toRadians(-90), x, y + 12);
        g.fill(staticRect);

        g.setTransform(beforeRotate); 	//Reset rotations
    }

    @Override
    void tick(Graphics2D g) 
    {
        if(iTick <= maxTick)
        {
            draw(g);
            iTick += 25;
        }
        else
        {
            drawStatic(g);
        }
    }
}
