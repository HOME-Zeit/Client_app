package client_ServerRequests;

import java.util.ArrayList;

import application.Main;
import datenbank.Programminformation;
/**
 *
 * @author Nevanor
 */
public class RequestServer 
{
    private  String IPAddress;

    public RequestServer(){
    	String tmpNtp = Main.getIpNtpFromPref();
		if(tmpNtp.compareTo("")!=0){
			setIP(tmpNtp);
		}else{
			setIP("localhost");
		}
    }
	
    public  ArrayList <Programminformation> requestMinor(long time, boolean truefalse)
    {
        return ClientServerConDB.minorCon(time,truefalse);
    }
    public  ArrayList <Programminformation> requestMajor(boolean truefalse)
    {
        return ClientServerConDB.majorCon(truefalse);
    }
    public  ArrayList <Programminformation> requestNumber(int number, boolean truefalse)
    {
        return ClientServerConDB.numberCon(number,truefalse);
    }
    
    public  String requestClk ()
    {
        return ClientServerConTime.clkCon();
    }
    public  String requestDate ()
    {
        return ClientServerConTime.dateCon();
    }
    public  long requestSec ()
    {
        return ClientServerConTime.secCon();
    }
    
    public  String getIP(){
    	return IPAddress;
    }
    
    public  void setIP(String ipTmp){
    	IPAddress = ipTmp;
    	
    }
    
}
