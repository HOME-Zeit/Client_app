package server_loops;

import datenbank.Datenbank;
import datenbank.Programminformation;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Nevanor
 */
public class DBLoopMajor extends Thread
{
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static ObjectOutputStream oOut;
    private static ObjectInputStream oIn;
    private static boolean truefalse;
    private static ArrayList <Programminformation> returnThis = new ArrayList<>();
    
    public void run()
    {
        while(true)
        {
            try
            {
                serverSocket = new ServerSocket(55304);
                clientSocket = serverSocket.accept();
                oIn =  new ObjectInputStream(clientSocket.getInputStream());
                try{truefalse = (boolean) oIn.readObject();}catch(ClassNotFoundException e){System.out.println(e);}
                oIn.close();
                
                //TODO Minor besorgen von DB
                returnThis = Datenbank.ausgeben(truefalse);
                
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
            	
                System.out.println(e+": 4Socket Problem");
                return;
            }
        
        }
    }
}

/*if (serverSocket != null && !serverSocket.isClosed()) {
try {
	serverSocket.close();
} catch (IOException e1)
{
    e1.printStackTrace(System.err);
}
}
*/