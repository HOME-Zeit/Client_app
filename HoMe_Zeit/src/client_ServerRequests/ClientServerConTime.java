package client_ServerRequests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Nevanor
 */
public class ClientServerConTime 
{
    private static BufferedReader bufferedReader;
    private static Socket socket;
    private static String inputLine;
    private static String returnString; 
    
    public static String clkCon()
    {
        try
        {
            socket = new Socket("149.205.109.19",55303);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while((inputLine = bufferedReader.readLine())!=null)
                returnString = inputLine;
            bufferedReader.close();
            socket.close();
        }
        catch(Exception e)
        {
            System.out.println(e+"\n!Socket Problem!");
        }
        
        return returnString;
    }
    public static String dateCon()
    {
        try
        {
            socket = new Socket("149.205.109.19",55302);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while((inputLine = bufferedReader.readLine())!=null)
                returnString = inputLine;
            bufferedReader.close();
            socket.close();
        }
        catch(Exception e)
        {
            System.out.println(e+"\n!Socket Problem!");
        }
        
        return returnString;
    }
    public static long secCon()
    {
        try
        {
            socket = new Socket("149.205.109.19",55307);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while((inputLine = bufferedReader.readLine())!=null)
                returnString = inputLine;
            bufferedReader.close();
            socket.close();
        }
        catch(Exception e)
        {
            System.out.println(e+"\n!Socket Problem!");
        }
        
        return Long.parseLong(returnString,10);
    }
}
