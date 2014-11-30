package client_ServerRequests;

import java.util.ArrayList;
import java.net.Socket;
import java.io.*;

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
            socket = new Socket("localhost",1);
            //fileOut = new FileOutputStream("/tmp/longBool.ser");
            oOut = new ObjectOutputStream(socket.getOutputStream());
            oOut.writeObject(longBool);
            oOut.flush();
            oOut.close();
            
            oIn =  new ObjectInputStream(socket.getInputStream());
            try{answer = (ArrayList<Programminformation>) oIn.readObject();}catch(ClassNotFoundException e){System.out.println(e);}
            oIn.close();

            socket.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        return answer;
    }
    public static ArrayList<Programminformation> majorCon (boolean truefalse)
    {
        try
        {
            socket = new Socket("localhost",1);
            //fileOut = new FileOutputStream("/tmp/longBool.ser");
            oOut = new ObjectOutputStream(socket.getOutputStream());
            oOut.writeObject(truefalse);
            oOut.flush();
            oOut.close();
            
            oIn =  new ObjectInputStream(socket.getInputStream());
            try{answer = (ArrayList<Programminformation>) oIn.readObject();}catch(ClassNotFoundException e){System.out.println(e);}
            oIn.close();

            socket.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        return answer;
    }
    public static ArrayList <Programminformation> numberCon (int number, boolean truefalse)
    {
        IntBool intBool = new IntBool(number,truefalse);
        try
        {
            socket = new Socket("localhost",1);
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
            System.out.println(e);
        }
        return answer;
    }
}
