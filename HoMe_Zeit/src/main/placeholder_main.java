package main;

import server_loops.*;
/**
 *
 * @author Nevanor
 */
public class placeholder_main extends Thread
{
    public static void startClientDBLoops()
    {
        (new DBLoopMinor()).start();   
        (new DBLoopMajor()).start();
        (new DBLoopNumber()).start();
    }
    
    public static void startClientTimeLoops()
    {
        (new TimeLoopSec()).start();   
        (new TimeLoopDate()).start();
        (new TimeLoopClk()).start();
    }
    
    public static void main(String[]args)
    {
        //System.out.println("\n"+Init_Retrieve_DB.init_ret_db());    //Upcoming_Server retrieve
        
        startClientTimeLoops(); //loops für ClientTime Anfragen schalten
        startClientDBLoops();   //loops für ClientDB Anfragen schalten
    }
}