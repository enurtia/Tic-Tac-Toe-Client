package clientprogram;

import javax.swing.JOptionPane;


public class GameRequest
{
    public static boolean show(String username)
    {
        int input;
        JOptionPane optionPane = new JOptionPane();
        input = optionPane.showConfirmDialog(null, "Game request from " + username + ". Accept?", "Game Request", JOptionPane.YES_NO_OPTION);
        
        return input == JOptionPane.YES_OPTION;
    }
}
