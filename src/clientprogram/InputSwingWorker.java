package clientprogram;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;


public class InputSwingWorker extends SwingWorker<Integer, String>
{
    private IOHandler ioHandler;
    private ClientFrame clientFrame;
    private BufferedReader input;
    private String inputMessage;
    private MainPanel gridPanel;
    
    private boolean isX;
    private boolean isO;
    
    public InputSwingWorker(ClientFrame clientFrame, IOHandler ioHandler)
    {
        this.clientFrame = clientFrame;
        this.ioHandler = ioHandler;
        input = new BufferedReader(new InputStreamReader(ioHandler.getInputStream()));
        
        isX = false;
        isO = false;
    }
    
    @Override
    public Integer doInBackground()
    {
        try
        {
            while(true)     
            {
                inputMessage = input.readLine();
                analyzeInput();
            }
        }
        catch(Exception e)
        {
            //Connection terminated
            clientFrame.setLabel("Connection to server terminated.");
            clientFrame.setConnected(false);
            e.printStackTrace();
        }
        
        return 1;
    }
    
    private void analyzeInput()
    {
        gridPanel = clientFrame.getGrid();
        switch(tag(inputMessage))
        {
            case "USERNAMECHECK":
            {
                if(info(inputMessage).equals("true"))   //Username is valid
                {
                    clientFrame.getConnectFrame().usernameValidity(true);
                }
                else                                    //Username is invalid
                {
                    clientFrame.getConnectFrame().usernameValidity(false);
                }
                
                break;
            }
            case "GLOBALCHAT":
            {
                if(clientFrame.globalChat())
                {
                    clientFrame.appendChat(info(inputMessage));
                }
                
                break;
            }
            case "GAMECHAT":
            {
                if(clientFrame.gameChat())
                {
                    clientFrame.appendChat(info(inputMessage));
                }
                
                break;
            }
            case "USERLIST":
            {
                String[] users = info(inputMessage).split(", ");
                clientFrame.getUserList().removeAllElements();
                for (String user : users)
                {
                    clientFrame.getUserList().addElement(user);
                }
                
                break;
            }
            case "REQUEST":
            {
                String requesterName = info(inputMessage);
                boolean ans = GameRequest.show(requesterName);
                if(ans)
                {
                    ioHandler.out("[REQUESTACCEPTED]" + requesterName);
                }
                else
                {
                    ioHandler.out("[REQUESTDENIED]" + requesterName);
                }
                
                break;
            }
            case "REQUESTFAILED":
            {
                JOptionPane.showMessageDialog(null, "User can't be requested right now.");
                
                break;
            }
            case "REQUESTDENIED":
            {
                JOptionPane.showMessageDialog(null, "User denied your request.");
                
                break;
            }
            case "BEGINGAME":   //info split by #. 0 is X. 1 is O. 2 is Turn
            {
                String[] gameInfo = info(inputMessage).split("#");
                clientFrame.isX(isX = Boolean.parseBoolean(gameInfo[0]));
                clientFrame.isO(isO = Boolean.parseBoolean(gameInfo[1]));
                clientFrame.setTurn(Boolean.parseBoolean(gameInfo[2]));
                clientFrame.setIngame(true);
                
                break;
            }
            case "MOVE":
            {
                clientFrame.setTurn(false);  //Opponent's turn
                int box = Integer.parseInt(info(inputMessage));
                
                if(isX)
                {
                    gridPanel.addX(box);
                }
                else
                {
                    gridPanel.addCircle(box);
                }
                
                break;
            }
            case "OPPONENTMOVE":
            {
                clientFrame.setTurn(true);   //Player's turn
                int box = Integer.parseInt(info(inputMessage));
                if(isO)
                {
                    gridPanel.addX(box);
                }
                else
                {
                    gridPanel.addCircle(box);
                }
                
                break;
            }
            case "WINMOVE":
            {
                int box = Integer.parseInt(info(inputMessage));
                if(isX)
                {
                    gridPanel.addX(box);
                }
                else
                {
                    gridPanel.addCircle(box);
                }
                
                rematch(1);
                
                break;
            }
            case "LOSSOPPONENTMOVE":
            {
                int box = Integer.parseInt(info(inputMessage));
                if(isO)
                {
                    gridPanel.addX(box);
                }
                else
                {
                    gridPanel.addCircle(box);
                }
                
                rematch(2);
                
                break;
            }    
            case "CATSGAME":
            {
                int box = Integer.parseInt(info(inputMessage));
                if(isX)
                {
                    gridPanel.addX(box);
                }
                else
                {
                    gridPanel.addCircle(box);
                }
                
                rematch(3);
                
                break;
            }
            case "OPPONENTCATSGAME":
            {
                int box = Integer.parseInt(info(inputMessage));
                if(isO)
                {
                    gridPanel.addX(box);
                }
                else
                {
                    gridPanel.addCircle(box);
                }
                
                rematch(3);
                
                break;
            }
            case "REMATCH":
            {
                boolean isTurn = Boolean.parseBoolean(info(inputMessage));
                clientFrame.setTurn(isTurn);
                clientFrame.setIngame(true);
                
                break;
            }
            case "NOREMATCH":
            {
                clientFrame.resetGameVariables();
                
                break;
            }
            case "OPPONENTDISCONNECT":
            {
                clientFrame.setIngame(false);
                gridPanel.reset();
                clientFrame.resetGameVariables();
                
                JOptionPane.showMessageDialog(null, "Opponent disconnected.");
                
                break;
            }
        }//End switch
    }//End method analyzeInput
    
    private String tag(String s)
    {
        int startIndex = s.indexOf("[");
        int endIndex = s.indexOf("]");
        
        return s.substring(startIndex + 1, endIndex);
    }
    
    private String info(String s)
    {
        int startIndex = s.indexOf("]");
        int endIndex = s.length();
        
        return s.substring(startIndex + 1, endIndex);
    }
    
    private void rematch(int gameInfo)   //1 = win, 2 = loss, 3 = cat's game
    {
        clientFrame.setIngame(false);       //Ingame variable will be false until new game has started.
        String s = "";      //Message displayed on dialog
        
        switch(gameInfo)
        {
            case 1:
                s = "You won!";
                break;
            case 2:
                s = "You lost!";
                break;
            case 3:
                s = "Cat's game!";
                break;
        }
        
        int in = JOptionPane.showConfirmDialog(null, s + " Rematch?", "Rematch", JOptionPane.YES_NO_OPTION);
        
        gridPanel.reset();
        boolean rematch = (in == JOptionPane.YES_OPTION);
        if(rematch)
        {
            ioHandler.out("[REMATCH]");
        }
        else
        {
            ioHandler.out("[NOREMATCH]");
        }
    }
}
