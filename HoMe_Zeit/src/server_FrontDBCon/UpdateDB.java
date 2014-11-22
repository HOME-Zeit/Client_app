package server_FrontDBCon;

import datenbank.*;

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
}