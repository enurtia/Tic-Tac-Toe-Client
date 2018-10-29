package clientprogram;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class Shapes 
{
    int x, y, radius, thickness, iTick, maxTick;
    Color color;

    abstract void draw(Graphics2D g);

    abstract void drawStatic(Graphics2D g);

    abstract void tick(Graphics2D g);

    public static void tickShapes(LinkedList<Shapes> s, Graphics2D g)
    {
        Iterator<Shapes> i = s.iterator();	//Tick all shapes
        while(i.hasNext())
        {
            Shapes shape = (Shapes)i.next();
            shape.tick(g);
        }
    }
}
