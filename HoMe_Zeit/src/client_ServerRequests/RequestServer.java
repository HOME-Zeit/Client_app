package client_ServerRequests;

import java.util.ArrayList;

import datenbank.Programminformation;
/**
 *
 * @author Nevanor
 */
public class RequestServer 
{
    private static String IPAddress = "localhost";

	
    public static ArrayList <Programminformation> requestMinor(long time, boolean truefalse)
    {
        return ClientServerConDB.minorCon(time,truefalse);
    }
    public static ArrayList <Programminformation> requestMajor(boolean truefalse)
    {
        return ClientServerConDB.majorCon(truefalse);
    }
    public static ArrayList <Programminformation> requestNumber(int number, boolean truefalse)
    {
        return ClientServerConDB.numberCon(number,truefalse);
    }
    
    public static String requestClk ()
    {
        return ClientServerConTime.clkCon();
    }
    public static String requestDate ()
    {
        return ClientServerConTime.dateCon();
    }
    public static long requestSec ()
    {
        return ClientServerConTime.secCon();
    }
    
    public static String getIP(){
    	return RequestServer.IPAddress;
    }
    
    public static void setIP(String ipTmp){
    	RequestServer.IPAddress = ipTmp;
    	
    }
    
}
