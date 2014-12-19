package server_FrontDBCon;

import datenbank.*;
import java.util.ArrayList;

/**
 *
 * @author Nevanor
 */
public class UpdateDB 
{
    public static void updateAppend(Programminformation p, boolean truefalse)
    {
        //TODO append given file
        Datenbank.eintragen(p,truefalse);
    }
    public static void updateDelete(Programminformation p, boolean truefalse)
    {
        //TODO delete selected file
        Datenbank.loeschen(p,truefalse);
    }
    public static void updateRefactor(Programminformation p, boolean truefalse)
    {
        //TODO refactor selected file
        Datenbank.bearbeiten(p,truefalse);
    }
    
    //-----
    
    public static void segmentsRegister(Programminformation p, boolean truefalse)
    {
        //TODO Register a new Segment with the whole Programminformation
        Datenbank.abschnitte_eintragen(p,truefalse);
    }
    public static void segmentsRegister(ArrayList<Abschnitt> segment, boolean truefalse)
    {
        //TODO Register a new Segment with an ArrayList of Segments
        Datenbank.abschnitte_eintragen(segment,truefalse);
    }
    public static void segmentsRegister(Abschnitt segment, boolean truefalse)
    {
        //TODO Register a new Segment with this single Segment
        Datenbank.abschnitte_eintragen(segment,truefalse);
    }
    
    //-----
    
    public static void segmentsDelete(int number, boolean truefalse)
    {
        //TODO Delete a Segment with its number
        Datenbank.abschnitt_loeschen(number,truefalse);
    }
    public static void segmentsRefactor(Abschnitt segment, boolean truefalse)
    {
        //TODO Refactor a Segment by overwriting it directly
        Datenbank.abschnitt_bearbeiten(segment,truefalse);
    }
}
