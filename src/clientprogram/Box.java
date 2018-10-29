package clientprogram;

import java.awt.Point;

public class Box 
{
    public static Point getPoint(int box)   //Returns Point object for center of box.
    {
	int x = 0;
	int y = 0;
	if(box < 1 || box > 9)
	{
            try
            {
                throw new Exception("Box integer argument must be 1 to 9.");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
	}
	else
	{
            if(box <= 3)
            {
		x = (210 * box) - 85;
		y = 120;
            }
            else if(box <= 6)
            {
		x = (210 * (box - 3)) - 85;
		y = 335;
            }
            else if(box <= 9)
            {
		x = (210 * (box-6)) - 85;
		y = 545;
            }
	}
	return new Point(x, y);
    }
	
	public static int getBox(Point p)   //Returns box number based upon Point coordinates.
	{
            int x = (int) p.getX();
            int y = (int) p.getY();
            int box = 0;
            
            int row = rowCol(y);
            int col = rowCol(x);
            
            if(row != 0 && col != 0)
            {
            	box = col + 3*(row - 1);
            }
		
            return box;
	}
	
	private static int rowCol(int n)
	{
            if(n >= 20 && n < 230)              //1
            {
            	return 1;
            }
            else if(n >= 230 && n < 440)	//2
            {
            	return 2;
            }
            else if(n >= 440 && n < 650)	//3
            {
            	return 3;
            }
	
            return 0;
	}
}
