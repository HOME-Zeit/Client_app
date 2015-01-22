package server_loops;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import datenbank.Datenbank;
import datenbank.Programminformation;

import client_ServerRequests.LongBool;

;

/**
 *
 * @author Nevanor
 */
public class DBLoopMinor extends Thread {
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static ObjectOutputStream oOut;
	private static ObjectInputStream oIn;
	private static LongBool longBool = new LongBool(0, false);
	private static ArrayList<Programminformation> returnThis = new ArrayList<>();

	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				serverSocket = new ServerSocket(55305);
				//serverSocket.setSoTimeout(10000);
				clientSocket = serverSocket.accept();
				oIn = new ObjectInputStream(clientSocket.getInputStream());
				try {
					// have changed package of LongBool
					longBool = (client_ServerRequests.LongBool) oIn
							.readObject();
				} catch (ClassNotFoundException e) {
					System.out.println(e);
				}

				// TODO Minor besorgen von DB
				Datenbank DB = new Datenbank();
				returnThis = DB.ausgeben(longBool.time,
						longBool.truefalse);

				oOut = new ObjectOutputStream(clientSocket.getOutputStream());
				oOut.writeObject(returnThis);

				oIn.close();
				oOut.flush();
				oOut.close();

				serverSocket.close();
				clientSocket.close();
				try {
					Thread.sleep(50);
				} catch (Exception e) {
					System.out.println(e);
				}
			} catch (IOException e) {
				System.out.println(e + ": 5Socket Problem");
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