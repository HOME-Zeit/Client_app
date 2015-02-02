package client_ServerRequests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

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
        	RequestServer requestServer = new RequestServer();
            socket = new Socket(requestServer.getIP(),55303);
            /*try
            {
            	socket = new Socket(RequestServer.getIP(),55303);
                socket.setSoTimeout(2600);
            }
            catch(Exception e)
            {
                socket.close();
                socket = new Socket(RequestServer.getIP(),55303);
            }*/
            //socket.setSoTimeout(10000);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while((inputLine = bufferedReader.readLine())!=null)
                returnString = inputLine;
            bufferedReader.close();
            socket.close();
        }
        catch(Exception e)
        {
            //System.out.println(e+"\n!Socket Problem!");
        	Dialogs.create()
        	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
            .title("Kein Verbindung")
            .masthead("Das Programm konnte kein Verbindung zum Server aufbauen")
            .message("Bitte \u00dcberpr\u00fcfen Sie ihre Internetverbindung, wechseln Sie IP-Adresse oder wechseln Sie den Modus")
            .showWarning();
        }
        
        return returnString;
    }
    public static String dateCon()
    {
        try
        {
        	RequestServer requestServer = new RequestServer();
            socket = new Socket(requestServer.getIP(),55302);
            /*try
            {
            	socket = new Socket(RequestServer.getIP(),55302);
                socket.setSoTimeout(2600);
            }
            catch(Exception e)
            {
                socket.close();
                socket = new Socket(RequestServer.getIP(),55302);
            }*/
            //socket.setSoTimeout(10000);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while((inputLine = bufferedReader.readLine())!=null)
                returnString = inputLine;
            bufferedReader.close();
            socket.close();
        }
        catch(Exception e)
        {
            //System.out.println(e+"\n!Socket Problem!");
        	Dialogs.create()
        	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
            .title("Kein Verbindung")
            .masthead("Das Programm konnte kein Verbindung zum Server aufbauen")
            .message("Bitte \u00dcberpr\u00fcfen Sie ihre Internetverbindung, wechseln Sie IP-Adresse oder wechseln Sie den Modus")
            .showWarning();
        }
        
        return returnString;
    }
    public static long secCon()
    {
        try
        {
        	RequestServer requestServer = new RequestServer();
            socket = new Socket(requestServer.getIP(),55307);
            /*try
            {
            	socket = new Socket(RequestServer.getIP(),55307);
                socket.setSoTimeout(2600);
            }
            catch(Exception e)
            {
                socket.close();
                socket = new Socket(RequestServer.getIP(),55307);
            }*/
            //socket.setSoTimeout(10000);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while((inputLine = bufferedReader.readLine())!=null)
                returnString = inputLine;
            bufferedReader.close();
            socket.close();
        }
        catch(Exception e)
        {
            //System.out.println(e+"\n!Socket Problem!");
        	Dialogs.create()
        	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
            .title("Kein Verbindung")
            .masthead("Das Programm konnte kein Verbindung zum Server aufbauen")
            .message("Bitte \u00dcberpr\u00fcfen Sie ihre Internetverbindung, wechseln Sie IP-Adresse oder wechseln Sie den Modus")
            .showWarning();
        }
        
        return Long.parseLong(returnString,10);
    }
  
}
