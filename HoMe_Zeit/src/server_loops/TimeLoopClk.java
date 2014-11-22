package server_loops;

import server_NTPRequest.Retrieve_Time;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
/**
 *
 * @author Nevanor
 */
public class TimeLoopClk extends Thread 
{
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static PrintWriter printWriter;
    long epoch = 0;
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    Date date = new Date();
    
    public void run()
    {
        System.out.println("Clk READY! Waiting for Clients...");
        while(true)
        {
            try
            {
                serverSocket = new ServerSocket(55303);
                clientSocket = serverSocket.accept();
                printWriter = new PrintWriter(clientSocket.getOutputStream(),true);
                try
                {
                    epoch = Retrieve_Time.getTime();
                    date = new Date(epoch);
                }
                catch(Exception e)
                {
                    epoch = Retrieve_Time.getTime_failed();
                    date = new Date(epoch);
                }
                printWriter.println(dateFormat.format(date));
                printWriter.flush();
                printWriter.close();
                serverSocket.close();
                clientSocket.close();
                try{Thread.sleep(50);}catch(Exception e){}
            }
            catch(IOException e)
            {
                System.out.println(e+": Socket 3Problem");
                return;
            }
        }
    }
}