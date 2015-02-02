package server_NTPRequest;

import java.net.InetAddress;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeInfo;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import application.Main;
/**
 *
 * @author Nevanor
 */
public class Retrieve_Time 
{
    private  String TIME_SERVER;

    public Retrieve_Time (){
    	String tmpNtp = Main.getIpNtpFromPref();
		if(tmpNtp.compareTo("")!=0){
			setUrl(tmpNtp);
		}else{
			setUrl("time-a.nist.gov");
		}
		
    }
    public String getUrl()
    {
        return TIME_SERVER;
    }
    public void setUrl(String url)
    {
        TIME_SERVER = url;
    }
    
    public long getTime() throws Exception 
    {
    	TIME_SERVER = getUrl();
        NTPUDPClient timeClient = new NTPUDPClient();
        InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
        TimeInfo timeInfo = timeClient.getTime(inetAddress);    //org.apache.commons.net.ntp.TimeInfo@2503dbd3
        //long returnTime = timeInfo.getReturnTime();             //1415862544656
        //Date time = new Date(returnTime);                     //Thu Nov 13 08:09:04 CET 2014
        NtpV3Packet timePacket = timeInfo.getMessage();			// osa
        long timeOsa = timePacket.getReceiveTimeStamp().getTime()/1000; // give back time in seconds

        
        //return returnTime; // It's return just time of my PC
        return timeOsa; 
    }
    public long getTime_failed()
    {
        long time = System.currentTimeMillis();
        return time;
    }
}
