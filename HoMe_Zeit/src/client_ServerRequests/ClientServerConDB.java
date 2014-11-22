package client_ServerRequests;

import java.util.ArrayList;
import java.net.Socket;
import java.io.*;
/**
 *
 * @author Nevanor
 */
public class ClientServerConDB extends RequestServer
{
    private static Socket socket;
    //private static FileOutputStream fileOut;
    //private static FileInputStream fileIn;
    private static ObjectOutputStream oOut;
    private static ObjectInputStream oIn;
    private static ArrayList answer = new ArrayList();
    
    public static ArrayList minorCon (long time, boolean truefalse)
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
            try{answer = (ArrayList) oIn.readObject();}catch(ClassNotFoundException e){System.out.println(e);}
            oIn.close();

            socket.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        return answer;
    }
    public static ArrayList majorCon (boolean truefalse)
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
            try{answer = (ArrayList) oIn.readObject();}catch(ClassNotFoundException e){System.out.println(e);}
            oIn.close();

            socket.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        return answer;
    }
    public static ArrayList numberCon (int number, boolean truefalse)
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
            try{answer = (ArrayList) oIn.readObject();}catch(ClassNotFoundException e){System.out.println(e);}
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
