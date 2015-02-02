package server_loops;

import server_NTPRequest.Retrieve_Time;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;

/**
 *
 * @author Nevanor
 */
public class TimeLoopSec extends Thread 
{
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static PrintWriter printWriter;
    long epoch = 0;
    
    public void run()
    {
        System.out.println("Sec READY! Waiting for Clients...");
        while(!Thread.currentThread().isInterrupted())
        {
            try
            {
                serverSocket = new ServerSocket(55307);
                /*try
                {
                    serverSocket = new ServerSocket(55307);
                    serverSocket.setSoTimeout(2600);
                }
                catch(Exception e)
                {
                    serverSocket.close();
                    serverSocket = new ServerSocket(55307);
                }*/
                //serverSocket.setSoTimeout(10000);
                clientSocket = serverSocket.accept();
                printWriter = new PrintWriter(clientSocket.getOutputStream(),true);
                try
                {
                	Retrieve_Time retrieve_Time = new Retrieve_Time();
                    epoch = retrieve_Time.getTime();
                }
                catch(Exception e)
                {
                	Retrieve_Time retrieve_Time = new Retrieve_Time();
                    epoch = retrieve_Time.getTime_failed();
                }
                printWriter.println(epoch);
                printWriter.flush();
                printWriter.close();
                serverSocket.close();
                clientSocket.close();
                try{Thread.sleep(50);}catch(Exception e){}
            }
            catch(IOException e)
            {
                System.out.println(e+": 1Socket Problem");
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