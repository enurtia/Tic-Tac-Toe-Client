package clientprogram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel extends JPanel implements ActionListener, MouseListener
{
    final private int tick = 25;
    final private int 	circleRadius 	= 80;
    final private int 	circleThickness = 10;
    final private Color circleColor 	= Color.CYAN;
    final private int 	xRadius 	= 100;
    final private int 	xThickness 	= 10;
    final private Color xColor 		= Color.MAGENTA;

    private ClientFrame clientFrame;
    private Timer timer;
    private Circle c;
    private X x;
    private LinkedList<Shapes> s;       //Linked list of all Shape objects -- X's and O's
    private boolean removing;       //Variable used to prevent an element being removed while being iterated

    MainPanel(ClientFrame clientFrame)
    {
        super.addMouseListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.clientFrame = clientFrame;
        timer = new Timer(tick, this);
        s = new LinkedList<>(); //LinkedList used for fast add and remove compared to ArrayList
        removing = false;       //Boolean put into place to avoid ConcurrentModificationException
                                //When clearing linkedlist while being iterated through.
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);        
        setBackground(Color.BLACK);     
        
        Graphics2D g1 = (Graphics2D)g;
        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Grid.drawGrid(Color.RED, g1);
        if(!removing && s.size() > 0)       
        {
            Shapes.tickShapes(s, g1);
        }
        else if(removing)
        {
            s.clear();
            removing = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        if(clientFrame.getIngame() && clientFrame.getTurn())
        {
            Point coords = e.getPoint();
            int box = Box.getBox(coords);
            
            
            if(box != 0)
            {
                clientFrame.getIOHandler().out("[MOVE]" + box);
            }
        }
    }

    public void addX(int box)
    {
        X x = new X(box, xRadius, xThickness, xColor);
        s.add(x);
    }
    
    public void addCircle(int box)
    {
        Circle circle = new Circle(box, circleRadius, circleThickness, circleColor);
        s.add(circle);
    }
    
    public void reset()
    {
        removing = true;
    }
    
    @Override
    public void mouseEntered(MouseEvent e) 
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e) 
    {

    }

    @Override
    public void mouseReleased(MouseEvent arg0) 
    {

    }
}
