package server_FrontDBCon;

import datenbank.*;
import java.util.ArrayList;

/**
 *
 * @author Nevanor
 */
public class UpdateDB 
{
    Datenbank DB = new Datenbank();
    
    //Anfrage des Servers an die Datenbank über eine Änderung zum Thema "neue Programminformation anhängen"
    //ÜbergabeParameter 1.die notwendige Programminformation 2.true or false für rückgabe mit oder ohne Exception von der Datenbank
    public void updateAppend(Programminformation p, boolean truefalse)
    {
        //TODO append given file
        DB.eintragen(p,truefalse);
    }
    
    //Anfrage des Servers an die Datenbank über eine Änderung zum Thema "bestehende Programminformation löschen"
    //ÜbergabeParameter 1.die notwendige Programminformation 2.true or false für rückgabe mit oder ohne Exception von der Datenbank
    public void updateDelete(Programminformation p, boolean truefalse)
    {
        //TODO delete selected file
        DB.loeschen(p,truefalse);
    }
    
    //Anfrage des Servers an die Datenbank über eine Änderung zum Thema "bestehende Programminformation ändern"
    //ÜbergabeParameter 1.die notwendige Programminformation 2.true or false für rückgabe mit oder ohne Exception von der Datenbank
    public void updateRefactor(Programminformation p, boolean truefalse)
    {
        //TODO refactor selected file
        DB.bearbeiten(p,truefalse);
    }
    
    //-----
    
    public void segmentsRegister(Programminformation p, boolean truefalse)
    {
        //TODO Register a new Segment with the whole Programminformation
        DB.abschnitte_eintragen(p,truefalse);
    }
    public void segmentsRegister(ArrayList<Abschnitt> segment, boolean truefalse)
    {
        //TODO Register a new Segment with an ArrayList of Segments
        DB.abschnitte_eintragen(segment,truefalse);
    }
    public void segmentsRegister(Abschnitt segment, boolean truefalse)
    {
        //TODO Register a new Segment with this single Segment
        DB.abschnitte_eintragen(segment,truefalse);
    }
    
    //-----
    
    public void segmentsDelete(int number, boolean truefalse)
    {
        //TODO Delete a Segment with its number
        DB.abschnitt_loeschen(number,truefalse);
    }
    public void segmentsRefactor(Abschnitt segment, boolean truefalse)
    {
        //TODO Refactor a Segment by overwriting it directly
        DB.abschnitt_bearbeiten(segment,truefalse);
    }
}
