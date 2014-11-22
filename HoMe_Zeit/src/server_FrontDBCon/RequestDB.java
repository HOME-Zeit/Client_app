package server_FrontDBCon;
import datenbank.*;
import java.util.ArrayList;
/**
 *
 * @author Nevanor
 */
public class RequestDB 
{
    //Class<?> Programm = new server_FrontDBCon.Programminformation().getClass(); 
    
    public static ArrayList requestMinor(long time, boolean truefalse)
    {
        //TODO get certain Stuff from DB
        return Datenbank.ausgeben(time,truefalse);
    }
    public static ArrayList requestMajor(boolean truefalse)
    {
        //TODO get ALL STUFF from DB
        return Datenbank.ausgeben(truefalse);
    }
    public static ArrayList requestNumber(int number, boolean truefalse)
    {
        //TODO get Stuff via number
        return Datenbank.ausgeben(number,truefalse);
    }
}