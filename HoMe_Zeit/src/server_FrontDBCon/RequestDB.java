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
    // I have added <Programminformation> - Viktor
	
	Datenbank DB = new Datenbank();
	
    public ArrayList <Programminformation> requestMinor(long time, boolean truefalse)
    {
        //TODO get certain Stuff from DB
        return DB.ausgeben(time,truefalse);
    }
    public ArrayList <Programminformation> requestMajor(boolean truefalse)
    {
        //TODO get ALL STUFF from DB
        return DB.ausgeben(truefalse);
    }
    public ArrayList <Programminformation> requestNumber(int number, boolean truefalse)
    {
        //TODO get Stuff via number
        return DB.ausgeben(number,truefalse);
    }
}