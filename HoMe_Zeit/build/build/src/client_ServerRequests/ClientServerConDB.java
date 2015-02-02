package client_ServerRequests;

import java.util.ArrayList;
import java.net.Socket;
import java.io.*;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import datenbank.Programminformation;
/**
 *
 * @author Nevanor
 */
public class ClientServerConDB extends RequestServer
{
    private static Socket socket;
    //private static FileOutputStream fileOut;
    //private static FileInputStream fileIn;9
    private static ObjectOutputStream oOut;
    private static ObjectInputStream oIn;
    private static ArrayList <Programminformation> answer = new  ArrayList<>();
    
    public static ArrayList <Programminformation> minorCon (long time, boolean truefalse)
    {
        LongBool longBool = new LongBool(time,truefalse);
        try
        {
        	RequestServer requestServer = new RequestServer();
            socket = new Socket(requestServer.getIP(),55305);
            /*try
            {
            	socket = new Socket(RequestServer.getIP(),55305);
                socket.setSoTimeout(2600);
            }
            catch(Exception e)
            {
                socket.close();
                socket = new Socket(RequestServer.getIP(),55305);
            }*/
            //socket.setSoTimeout(10000); 
            //fileOut = new FileOutputStream("/tmp/longBool.ser");
            oOut = new ObjectOutputStream(socket.getOutputStream());
            oOut.writeObject(longBool);
            
            
            oIn =  new ObjectInputStream(socket.getInputStream());
            try{answer = (ArrayList<Programminformation>) oIn.readObject();}catch(ClassNotFoundException e){System.out.println(e);}
            oIn.close();

            oOut.flush();
            oOut.close();
            socket.close();
        }
        catch(IOException e)
        {
            //System.out.println(e);
        	Dialogs.create()
        	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
            .title("Kein Verbindung")
            .masthead("Das Programm konnte kein Verbindung zum Server aufbauen")
            .message("Bitte \u00dcberpr\u00fcfen Sie ihre Internetverbindung, wechseln Sie IP-Adresse oder wechseln Sie den Modus")
            .showWarning();
        }
        return answer;
    }
    public static ArrayList<Programminformation> majorCon (boolean truefalse)
    {
        try
        {
        	RequestServer requestServer = new RequestServer();
            socket = new Socket(requestServer.getIP(),55304); 
            /*try
            {
            	socket = new Socket(RequestServer.getIP(),55304);
                socket.setSoTimeout(2600);
            }
            catch(Exception e)
            {
                socket.close();
                socket = new Socket(RequestServer.getIP(),55304);
            }*/
            //socket.setSoTimeout(10000);
            //fileOut = new FileOutputStream("/tmp/longBool.ser");
            oOut = new ObjectOutputStream(socket.getOutputStream());
            oOut.writeObject(truefalse);
            
            
            oIn =  new ObjectInputStream(socket.getInputStream());
            try{
            	answer = (ArrayList<Programminformation>) oIn.readObject();
            	//System.out.println(answer.get(0).sende_verantwortlicher + " check 3");
            	}catch(ClassNotFoundException e){
            		System.out.println(e + "object error");
            		}
            oIn.close();
            oOut.flush();
            oOut.close();
            socket.close();
        }
        catch(IOException e)
        {
            //System.out.println(e + " socket error");
        	Dialogs.create()
        	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
            .title("Kein Verbindung")
            .masthead("Das Programm konnte kein Verbindung zum Server aufbauen")
            .message("Bitte \u00dcberpr\u00fcfen Sie ihre Internetverbindung, wechseln Sie IP-Adresse oder wechseln Sie den Modus")
            .showWarning();
        }
        return answer;
        
    }
    public static ArrayList <Programminformation> numberCon (int number, boolean truefalse)
    {
        IntBool intBool = new IntBool(number,truefalse);
        try
        {
        	RequestServer requestServer = new RequestServer();
            socket = new Socket(requestServer.getIP(),55306);
            /*try
            {
            	socket = new Socket(RequestServer.getIP(),55306);
                socket.setSoTimeout(2600);
            }
            catch(Exception e)
            {
                socket.close();
                socket = new Socket(RequestServer.getIP(),55306);
            }*/
            //socket.setSoTimeout(10000);
            //fileOut = new FileOutputStream("/tmp/longBool.ser");
            oOut = new ObjectOutputStream(socket.getOutputStream());
            oOut.writeObject(intBool);
            oOut.flush();
            oOut.close();
            
            oIn =  new ObjectInputStream(socket.getInputStream());
            try{answer = (ArrayList<Programminformation>) oIn.readObject();}catch(ClassNotFoundException e){System.out.println(e);}
            oIn.close();

            socket.close();
        }
        catch(IOException e)
        {
            //System.out.println(e);
        	Dialogs.create()
        	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
            .title("Kein Verbindung")
            .masthead("Das Programm konnte kein Verbindung zum Server aufbauen")
            .message("Bitte \u00dcberpr\u00fcfen Sie ihre Internetverbindung, wechseln Sie IP-Adresse oder wechseln Sie den Modus")
            .showWarning();
        }
        return answer;
    }
}
