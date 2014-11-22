package client_ServerRequests;

import java.util.ArrayList;
/**
 *
 * @author Nevanor
 */
public class RequestServer 
{
    public static ArrayList requestMinor(long time, boolean truefalse)
    {
        return ClientServerConDB.minorCon(time,truefalse);
    }
    public static ArrayList requestMajor(boolean truefalse)
    {
        return ClientServerConDB.majorCon(truefalse);
    }
    public static ArrayList requestNumber(int number, boolean truefalse)
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
    
}
