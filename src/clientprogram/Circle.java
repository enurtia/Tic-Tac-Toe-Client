package clientprogram;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Circle extends Shapes
{
    private Color color;
    private int x, y, radius, thickness;
    private final double parts = 100.0;  //Amount by which the circle will be broken up (times 2pi). EX: 100 parts = 630 parts
    private final int maxTick = (int)(parts * 2 * Math.PI); //2pi * parts
    private double iTick;                                    
    private int box;
    private Area staticCircle;
    
    private final double tickIncrement = 33.75;

    public Circle(int box, int radius, int thickness, Color color)
    {
        this.box = box;
        Point p = Box.getPoint(box);
        this.x = (int) p.getX();
        this.y = (int) p.getY();

        this.radius =  radius;
        this.thickness = thickness;
        this.color = color;

        int sX = x + 5;			//Adjusted variables for static circle. (x, y, radius, and thickness)
        int sY = y + 5;
        int sR = radius + 5;
        int sT = thickness + 1;

        iTick = 0;

        //Creates O shape by subtracting smaller circle from larger circle.
        Shape outer = new Ellipse2D.Double(sX - sR, sY - sR, 2 * sR, 2 * sR);
        Shape inner = new Ellipse2D.Double(sX - sR + sT, sY - sR + sT, (2*sR) - (2*sT), (2*sR) - (2*sT));
        staticCircle = new Area(outer);
        staticCircle.subtract(new Area(inner));
    }
	
    @Override
    public void draw(Graphics2D g)
    {
        g.setColor(color);

        /*
        iTick goes up to maxTick which is 2pi * parts.
        The angles in the forloop are divided by the number of parts so that 
        the circle can be broken up properly. When iTIck = maxTick
        and divided by the number of parts, the angle will be 2pi.
        */
        for(int i = 0; i <= iTick; i++)
        {
            int x1 = (int)(Math.cos(i / parts) * radius);
            int y1 = (int)(Math.sin(i / parts) * radius);

            g.fillOval(x + x1, y + y1, thickness, thickness);
        }
    }

    @Override
    public void drawStatic(Graphics2D g)
    {
        g.setColor(color);
        g.fill(staticCircle);
    }

    @Override
    public void tick(Graphics2D g)
    {
        if(iTick <= maxTick)
        {
            draw(g);
            iTick += tickIncrement;  
        }
        else
        {
            drawStatic(g);
        }
    }
}
