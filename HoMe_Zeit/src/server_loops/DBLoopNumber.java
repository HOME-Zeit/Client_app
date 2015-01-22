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
public class DBLoopNumber extends Thread
{
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
    private static ObjectOutputStream oOut;
    private static ObjectInputStream oIn;
    private static IntBool intBool = new IntBool(0,false);
    private static ArrayList <Programminformation> returnThis = new ArrayList<>();
    
    public void run()
    {
        while(!Thread.currentThread().isInterrupted())
        {
            try
            {
                serverSocket = new ServerSocket(55306);
                //serverSocket.setSoTimeout(10000);
                clientSocket = serverSocket.accept();
                oIn =  new ObjectInputStream(clientSocket.getInputStream());
                try{intBool = (IntBool) oIn.readObject();}catch(ClassNotFoundException e){System.out.println(e);}
                oIn.close();
                
                //TODO Minor besorgen von DB
                Datenbank DB = new Datenbank();
                returnThis = DB.ausgeben(intBool.number,intBool.truefalse);
                
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
                System.out.println(e+": 6Socket Problem");
                return;
            }
        }
    }
    
	public static void socketsClose(){
    	
    	if(serverSocket!=null ){
    		try {
    			serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	if(clientSocket!=null ){
    		try {
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    }
}