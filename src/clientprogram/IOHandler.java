package clientprogram;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class IOHandler
{
    private ClientFrame clientFrame;
    private InputStream inputStream;
    private PrintWriter output;
    
    public IOHandler(ClientFrame clientFrame)
    {
        this.clientFrame = clientFrame;
    }   
    
    public int connect(String ip, int port)     //Attempts socket connection.
    {                                           //Returns integer depending upon valid connection, or exceptions.
        try
        {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 2500);
            inputStream = socket.getInputStream();
            
            output = new PrintWriter(socket.getOutputStream());
            
            InputSwingWorker inputSwingWorker = new InputSwingWorker(clientFrame, this);
            inputSwingWorker.execute();
            
            return 0;                   //Valid connection
        }
        catch(UnknownHostException e)   
        {
            return 1;                   //IP doesn't exist
        } 
        catch (IOException e)           
        {
            return 2;                   //Error connecting to ip. Could be an issue with port
        }
    }
    
    public void out(String s)   //Outputs through outputstream 
    {
        output.println(s);
        output.flush();
    }
    
    public ClientFrame getClientFrame()
    {
        return clientFrame;
    }
    
    public InputStream getInputStream()
    {
        return inputStream;
    }
}
