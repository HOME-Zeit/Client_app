package server_loops;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import datenbank.Datenbank;
import datenbank.Programminformation;

/**
 *
 * @author Nevanor
 */
public class DBLoopMinor extends Thread
{
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static ObjectOutputStream oOut;
    private static ObjectInputStream oIn;
    private static LongBool longBool = new LongBool(0,false);
    private static ArrayList <Programminformation> returnThis = new ArrayList<>();
    
    public void run()
    {
        while(true)
        {
            try
            {
                serverSocket = new ServerSocket(55305);
                clientSocket = serverSocket.accept();
                oIn =  new ObjectInputStream(clientSocket.getInputStream());
                try{longBool = (LongBool) oIn.readObject();}catch(ClassNotFoundException e){System.out.println(e);}
                oIn.close();
                
                //TODO Minor besorgen von DB
                returnThis = Datenbank.ausgeben(longBool.time,longBool.truefalse);
                
                oOut = new ObjectOutputStream(clientSocket.getOutputStream());
                oOut.writeObject(returnThis);
                oOut.flush();
                oOut.close();
                
                serverSocket.close();
                clientSocket.close();
                try{Thread.sleep(50);}catch(Exception e){System.out.println(e);}
            }
            catch(IOException e)
            {
                System.out.println(e+": 5Socket Problem");
                return;
            }
        }
    }
}