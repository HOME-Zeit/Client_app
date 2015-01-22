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
        while(!Thread.currentThread().isInterrupted())
        {
            try
            {
                serverSocket = new ServerSocket(55304);
                //serverSocket.setSoTimeout(10000);
                clientSocket = serverSocket.accept();
                oIn =  new ObjectInputStream(clientSocket.getInputStream());
                try{
                	truefalse = (boolean) oIn.readObject();
                	//System.out.println("check "+truefalse);
                	}catch(ClassNotFoundException e){
                		System.out.println(e);
                		}
                
                
                //TODO Minor besorgen von DB
                Datenbank DB = new Datenbank();
                returnThis = DB.ausgeben(truefalse);
                //System.out.println(returnThis.get(0).prod_verantwortlicher + " check2");
                oOut = new ObjectOutputStream(clientSocket.getOutputStream());
                oOut.writeObject(returnThis);
                
                //System.out.println(" check4");
                oIn.close();
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

/*if (serverSocket != null && !serverSocket.isClosed()) {
try {
	serverSocket.close();
} catch (IOException e1)
{
    e1.printStackTrace(System.err);
}
}
*/