package datenbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Datenbank 
{
	public static String adresse = "127.0.0.1:3306";
	public static String url = "jdbc:mysql://" + adresse; // MySQL in Xampp Port 3306
	public static String db = "studiouhr";
	public static String driver = "com.mysql.jdbc.Driver";
	public static String user = "root";
	public static String pwd = "";
	public static Connection connection;
	
	public static String tabelle_1 = "programminformation";

	
	
	
		
	static public boolean dbVerbindungTesten()
	{
		try 
		{
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url + "/" + db, user, pwd);
			connection.close();  // ?
            return true;
		}
    	catch (Exception e)
    	{
    		return false;
    	}
	}
	
	
		
 
	static public ArrayList<Programminformation> ausgeben(boolean exceptionAnzeigen)
	{
		ArrayList<Programminformation> programminformation = new ArrayList<Programminformation>();		
		try 
		{
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url + "/" + db, user, pwd);
            Statement statement = connection.createStatement();            
  
            ResultSet ergebnis = statement.executeQuery("SELECT * FROM " + tabelle_1 + " ORDER by startzeit");
            while(ergebnis.next())
            {                        		           	           		           	            	       	
            	programminformation.add(new Programminformation(ergebnis.getInt("nummer"), ergebnis.getString("titel"), 
            														new Long(ergebnis.getLong("startzeit")), new Long(ergebnis.getLong("endzeit")), 
            														ergebnis.getString("prod_verantwortlicher"), ergebnis.getString("sende_verantwortlicher"), 
            														new Long(ergebnis.getLong("reale_startzeit")), new Long(ergebnis.getLong("reale_endzeit"))));
            	
            }                                
            ergebnis.close();
            statement.close();
            connection.close();                      
        }
        catch (SQLException ex) 
        {
        	if(exceptionAnzeigen == true)
        	{
        		ex.printStackTrace();
        	}      	
        }
		catch (Exception e)
		{
			if(exceptionAnzeigen == true)
        	{
        		e.printStackTrace();
        	}  
		}
		return programminformation;
	}
	
	
	
	
	static public ArrayList<Programminformation> ausgeben(Long zeit, boolean exceptionAnzeigen)
	{
		ArrayList<Programminformation> programminformation = new ArrayList<Programminformation>();
		//4 Stunden = 1000*60*60*4 = 14400000
		//20 Stunden = 1000*60*60*20 = 72000000
		Long min = zeit - 14400000;
		Long max = zeit + 72000000;
		try 
		{
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url + "/" + db, user, pwd);
            Statement statement = connection.createStatement();            
  
            ResultSet ergebnis = statement.executeQuery("SELECT * FROM " + tabelle_1 + " WHERE startzeit > " + min + 
            													" and startzeit < " + max + " ORDER by startzeit");
            while(ergebnis.next())
            {                        		           	           		           	            	       	
            	programminformation.add(new Programminformation(ergebnis.getInt("nummer"), ergebnis.getString("titel"), 
						new Long(ergebnis.getLong("startzeit")), new Long(ergebnis.getLong("endzeit")), 
						ergebnis.getString("prod_verantwortlicher"), ergebnis.getString("sende_verantwortlicher"), 
						new Long(ergebnis.getLong("reale_startzeit")), new Long(ergebnis.getLong("reale_endzeit"))));
            	
            }                                
            ergebnis.close();
            statement.close();
            connection.close();                      
        }
		catch (SQLException ex) 
        {
        	if(exceptionAnzeigen == true)
        	{
        		ex.printStackTrace();
        	}      	
        }
		catch (Exception e)
		{
			if(exceptionAnzeigen == true)
        	{
        		e.printStackTrace();
        	}  
		}
		return programminformation;
	}

	
	
	
	
	
	static public ArrayList<Programminformation> ausgeben(Integer nummer, boolean exceptionAnzeigen)
	{
		ArrayList<Programminformation> programminformation = new ArrayList<Programminformation>();
		try 
		{
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url + "/" + db, user, pwd);
            Statement statement = connection.createStatement();            
  
            ResultSet ergebnis = statement.executeQuery("SELECT * FROM " + tabelle_1 + " WHERE nummer = " + nummer + " ORDER by startzeit");
            while(ergebnis.next())
            {                        		           	           		           	            	       	
            	programminformation.add(new Programminformation(ergebnis.getInt("nummer"), ergebnis.getString("titel"), 
						new Long(ergebnis.getLong("startzeit")), new Long(ergebnis.getLong("endzeit")), 
						ergebnis.getString("prod_verantwortlicher"), ergebnis.getString("sende_verantwortlicher"), 
						new Long(ergebnis.getLong("reale_startzeit")), new Long(ergebnis.getLong("reale_endzeit"))));
            	
            }                                
            ergebnis.close();
            statement.close();
            connection.close();                      
        }
		catch (SQLException ex) 
        {
        	if(exceptionAnzeigen == true)
        	{
        		ex.printStackTrace();
        	}      	
        }
		catch (Exception e)
		{
			if(exceptionAnzeigen == true)
        	{
        		e.printStackTrace();
        	}  
		}
		return programminformation;
	}
	
	
	
	
	
	
	
		
	static public boolean eintragen(Programminformation p, boolean exceptionAnzeigen)
	{
		try 
		{
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url + "/" + db, user, pwd);
            Statement statement = connection.createStatement();   
                        
            statement.executeUpdate("INSERT INTO " + tabelle_1 + " VALUES (0,'" + p.titel + "'," + p.startzeit + "," +
            																	p.endzeit + ",'" + p.prod_verantwortlicher + "','" + 
            																	p.sende_verantwortlicher +  "'," + p.reale_startzeit + 
            																	"," + p.reale_endzeit + ")");
            statement.close();
            connection.close();  
        }
		catch (SQLException ex) 
        {
        	if(exceptionAnzeigen == true)
        	{
        		ex.printStackTrace();
        	}
        	return false;
        }
		catch (Exception e)
		{
			if(exceptionAnzeigen == true)
        	{
        		e.printStackTrace();
        	}
			return false;
		}
		return true;
	}
	
	
	
	
	
	static public boolean bearbeiten(Programminformation p, boolean exceptionAnzeigen)
	{
		try 
		{
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url + "/" + db, user, pwd);
            Statement statement = connection.createStatement();   
                        
            statement.executeUpdate("UPDATE " + tabelle_1 + " SET titel = '" + p.titel + "', startzeit = " + p.startzeit + ", endzeit  = " +
            																	p.endzeit + ",prod_verantwortlicher = '" + p.prod_verantwortlicher + "', sende_verantwortlicher = '" + 
            																	p.sende_verantwortlicher +  "', reale_startzeit = " +
            																	p.reale_startzeit + ", reale_endzeit = " + p.reale_endzeit +
            																	" WHERE nummer = " + p.nummer);

            statement.close();
            connection.close();  
        }
		catch (SQLException ex) 
        {
        	if(exceptionAnzeigen == true)
        	{
        		ex.printStackTrace();
        	}
        	return false;
        }
		catch (Exception e)
		{
			if(exceptionAnzeigen == true)
        	{
        		e.printStackTrace();
        	}
			return false;
		}
		return true;
	}
	
	
	
	
	
	static public boolean loeschen(Programminformation p, boolean exceptionAnzeigen)
	{
		try 
		{
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url + "/" + db, user, pwd);
            Statement statement = connection.createStatement();   
                        
            statement.executeUpdate("DELETE FROM " + tabelle_1 + " WHERE nummer = " + p.nummer);

            statement.close();
            connection.close();  
        }
		catch (SQLException ex) 
        {
        	if(exceptionAnzeigen == true)
        	{
        		ex.printStackTrace();
        	}
        	return false;
        }
		catch (Exception e)
		{
			if(exceptionAnzeigen == true)
        	{
        		e.printStackTrace();
        	}
			return false;
		}
		return true;
	}
}