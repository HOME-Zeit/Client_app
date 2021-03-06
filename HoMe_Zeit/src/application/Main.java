package application;
	
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.placeholder_main;
import model.AbschnittMy;
import model.Programm;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import server_FrontDBCon.RequestDB;
import server_NTPRequest.Retrieve_Time;
import view.AbschnittEditDialogController;
import view.ProgrammEditDialogController;
import view.ProgrammOverviewController;
import view.RootLayoutController;
import view.TimerGoController;
import client_ServerRequests.RequestServer;
import datenbank.Programminformation;

/**
 * 
 * @author Viktor Osadchyi
 *
 */

public class Main extends Application {
	
	private Stage primaryStage;
    private BorderPane rootLayout;
    
    
    public static boolean isRegieMode = false; // false = Moderator. true = Regie 
    public static String choice = "";
	
    private ObservableList<Programm> programmData = FXCollections.observableArrayList();
    /*
     * TODO think about to make it not static
     */
    private static ObservableList<AbschnittMy> abschnittMyData = FXCollections.observableArrayList();
    
    /**
     * Test data in default constructor
     */
    public Main(){
    	/*programmData.add
    		(new Programm("The Simpsons","Homer","Lisa",45,LocalDate.of(2014, 11, 9),"19:30"));
    	programmData.add
		(new Programm("HIMYM","Robin","Barney",60,LocalDate.of(2014, 11, 9),"20:30"));
    	programmData.add
		(new Programm("Futurama","Bender","Zeuberg",30,LocalDate.of(2014, 11, 20),"19:00"));
    	programmData.add
    	(new Programm("Friends","Chendler","Monica",60,LocalDate.of(2014, 11, 20),"21:30"));
    	*/
    }
    
    public ObservableList<Programm> getProgrammData() {
        return programmData;
    }
    
    public static ObservableList<AbschnittMy> getAbschnittData() {
        return abschnittMyData;
    }
    
	@Override
	public void start(Stage primaryStage) {
		try {
			
			this.primaryStage = primaryStage;
	        this.primaryStage.setTitle("HoMe Zeit");
	        
	        this.primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("1415586239_clock.png")));
	        
	        initRootLayout();
	        
	        showProgramOverview();
			
			/*BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Initializes the root layout and tries to load the last opened
	 * person file.
	 */
	public void initRootLayout() {
		RootLayoutController controller = new RootLayoutController();
        try {
            // Load root layout from fxml file.
        	//System.err.println("FXML resource: " + Main.class.getResource("../view/RootLayout.fxml"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            // Give the controller access to the main app.
            controller = loader.getController();
            controller.setMain(this);
            
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.exit(0);
                }
            });
            
            primaryStage.show();
         
            // get mode
            choice = Dialogs.create()
            		.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
            		.actions(Dialog.ACTION_OK,Dialog.ACTION_CANCEL)
            	    .title("Modus w\u00e4hlen")
            	    .masthead("Modus w\u00e4hlen")
            	    .message( "Modus wechseln unter Bearbeiten -> Modus wechseln")
      			  .showChoices("Moderator","Regie").orElse("");
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        if(choice.compareToIgnoreCase("Regie")==0){
        	isRegieMode = true;
        }
        else if(choice.compareToIgnoreCase("Moderator")==0) {
        	isRegieMode = false;
        	
        }
        else{
        	System.exit(0);
			}
        
        
        
        if(isRegieMode){
        // load data from DB - For Server
        loadAllProgrammDataFromDB();
        // threads for Server. They respond on Clients questions. 
        placeholder_main.startClientDBLoops();
        placeholder_main.startClientTimeLoops();
        
        
        }
        else{
        //placeholder_main.startClientDBLoops(); // test on the same PC
        //placeholder_main.startClientTimeLoops();
        loadAllProgrammDataFromServer();
        
        
        }
        
        controller.setVisibleMode();
        
        /*
     // Try to load last opened person file.
        File file = getProgrammFilePath();
        if (file != null) {
            loadProgrammDataFromFile(file);
        }
        */
    }
	
	public void showProgramOverview() {
        try {
            // Load program overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/ProgrammOverview.fxml"));
            AnchorPane programmOverview = (AnchorPane) loader.load();

            // Set program overview into the center of root layout.
            rootLayout.setCenter(programmOverview);
            
            // Give the controller access to the main app.
            ProgrammOverviewController controller = loader.getController();
            controller.setMain(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public Stage getPrimaryStage() {
        return primaryStage;
    }
	
	/**
	 * Opens a dialog to edit details for the specified programm. If the user
	 * clicks OK, the changes are saved into the provided programm object and true
	 * is returned.
	 * 
	 * @param programm the programm object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showProgrammEditDialog(Programm programm) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("/view/ProgrammEditDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("HoMe Zeit - Programm bearbeiten");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("1415586239_clock.png")));

	        // Set the programm into the controller.
	        ProgrammEditDialogController controller = loader.getController();
	        controller.setMain(this);
	        controller.setDialogStage(dialogStage);
	        controller.setProgramm(programm);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	/**
	 * Opens a dialog to edit details for the specified abschnitt. If the user
	 * clicks OK, the changes are saved into the provided programm object and true
	 * is returned.
	 * 
	 * @param programm the programm object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showAbschnittEditDialog(AbschnittMy abschnitt, Programm programm) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("/view/AbschnittEditDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("HoMe Zeit - Abschnitt bearbeiten");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("1415586239_clock.png")));

	        // Set the programm into the controller.
	        AbschnittEditDialogController controller = loader.getController();
	        controller.setMain(this);
	        controller.setDialogStage(dialogStage);
	        controller.setAbschnitt(abschnitt);
	        controller.setProgramm(programm);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	/**
	 * Opens a dialog with Timer and details for the specified programm. 
	 * 
	 * @param programm the programm object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showProgrammTimerGoDialog(Programm programm) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("/view/TimerGo.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("HoMe Zeit - Start");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("1415586239_clock.png")));
	        dialogStage.setMaximized(true);

	        // Set the programm into the controller.
	        TimerGoController controller = loader.getController();
	        controller.setMain(this);
	        controller.setDialogStage(dialogStage);
	        controller.setProgramm(programm);
	        

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();
	        if(controller.timeline!=null)
	        	controller.timeline.stop();
	        
	        //return true;
	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	
	/**
	 * Save IP-Url for Moderator Mode
	 *  and Ntp-Url for Regie Mode
	 *  to preferences
	 * 
	 * @param file the file or null to remove the path
	 */
	public void saveIpNtpToPref(String IPorNTP) {
	    Preferences prefs = Preferences.userNodeForPackage(Main.class);
	    
	    if(isRegieMode){
	    	//Retrieve_Time retrieve_Time = new Retrieve_Time();
	    	if (IPorNTP != null && IPorNTP.compareTo("")!=0) {
	    		
		        prefs.put("NtpAdd", IPorNTP);

		    } 
	    	
	    }else{
	    	if (IPorNTP != null && IPorNTP.compareTo("")!=0) {
	    		
		        prefs.put("IpAdd", IPorNTP);

		    } 
	    }   
	}
	
	/**
	 * Return IP-Url for Moderator Mode
	 *  and Ntp-Url for Regie Mode
	 *  to preferences
	 * 
	 * @param file the file or null to remove the path
	 */
	public static String getIpNtpFromPref() {
	    Preferences prefs = Preferences.userNodeForPackage(Main.class);
	    
	    
	    if(isRegieMode){
	    	String ntpAdd = prefs.get("NtpAdd", null);
	    	//Retrieve_Time retrieve_Time = new Retrieve_Time();
	    	if (ntpAdd != null && ntpAdd.compareTo("")!=0) {
	    		
		        return ntpAdd;
		    } 
	    	
	    }else{
	    	String ipAdd = prefs.get("IpAdd", null);
	    	if (ipAdd != null && ipAdd.compareTo("")!=0) {
	
		        return ipAdd;
		    } 
	    }
	    
	    return "";
	}
	
	/**
	 * Returns the programm file preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 * 
	 * @return
	 */
	/*public File getProgrammFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(Main.class);
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}
	*/
	/**
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 * 
	 * @param file the file or null to remove the path
	 */
	/*public void setProgrammFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(Main.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
	        primaryStage.setTitle("HoMe Zeit - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
	        primaryStage.setTitle("HoMe Zeit");
	    }
	}
	*/
	/**
	 * Loads programm data from the specified file. The current programm data will
	 * be replaced.
	 * 
	 * @param file
	 */
	/*public void loadProgrammDataFromFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(ProgrammListWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();

	        // Reading XML from the file and unmarshalling.
	        ProgrammListWrapper wrapper = (ProgrammListWrapper) um.unmarshal(file);

	        programmData.clear();
	        programmData.addAll(wrapper.getProgramm());

	        // Save the file path to the registry.
	        setProgrammFilePath(file);

	    } catch (Exception e) { // catches ANY exception
	        Dialogs.create()
	                .title("Error")
	                .masthead("Could not load data from file:\n" + file.getPath())
	                .showException(e);
	    }
	}
	*/
	/**
	 * Saves the current programm data to the specified file.
	 * 
	 * @param file
	 */
	/*public void saveProgrammDataToFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(ProgrammListWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping our programm data.
	        ProgrammListWrapper wrapper = new ProgrammListWrapper();
	        wrapper.setProgramm(programmData);

	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, file);

	        // Save the file path to the registry.
	        setProgrammFilePath(file);
	    } catch (Exception e) { // catches ANY exception
	        Dialogs.create().title("Error")
	                .masthead("Could not save data to file:\n" + file.getPath())
	                .showException(e);
	    }
	}
	*/
	public static void main(String[] args) {
		launch(args);
	}
	
	/*
	 * Function just for Server. Load all data from DB
	 * TODO maybe transport to controller
	 */
	public void loadAllProgrammDataFromDB(){
		try {
		RequestDB requestDB = new RequestDB();
		ArrayList<Programminformation> progrDB = requestDB.requestMajor(true);
		ArrayList< Programm> progrClient = new ArrayList<Programm>();
		
		for(Programminformation p : progrDB ){
			progrClient.add(new Programm(p));
			
		}
		
		//System.out.println(progrClient.get(0).getLangeReal());
		
		programmData.clear();
        programmData.addAll(progrClient);
        
		} catch (Exception e) { // catches ANY exception
	        Dialogs.create()
	                .title("Error")
	                .masthead("Could not load data from DB:\n")
	                .showException(e);
	    }
	}
	
	/*
	 * Function for Server/Regie . Load today's data from DB.
	 */
	public void loadTodayProgrammDataFromDB(){
    	try {
    		//System.out.println(Retrieve_Time.getTime()); // Test time
    		RequestDB requestDB = new RequestDB();
    		long epoch = 0;
    		Retrieve_Time retrieve_Time = new Retrieve_Time();
    		try
            {
                epoch = retrieve_Time.getTime();
            }
            catch(Exception e)
            {
                epoch = retrieve_Time.getTime_failed();
            }
    		ArrayList<Programminformation> progrDB = requestDB.requestMinor(epoch,true);
    		ArrayList< Programm> progrClient = new ArrayList<Programm>();
    		
    		for(Programminformation p : progrDB ){
    			progrClient.add(new Programm(p));
    		}
    		
    		
    		programmData.clear();
            programmData.addAll(progrClient);
            
    		} catch (Exception e) { // catches ANY exception
    	        Dialogs.create()
    	                .title("Error")
    	                .masthead("Could not load today's data from DB:\n")
    	                .showException(e);
    	    }
    	
    	}
	
	/*
	 * Function just for Client. Load all data from Server
	 * TODO maybe transport to controller
	 */
	public void loadAllProgrammDataFromServer(){
		try {
		RequestServer requestServer = new RequestServer();
		ArrayList<Programminformation> progrServer = requestServer.requestMajor(true);
		ArrayList< Programm> progrClient = new ArrayList<Programm>();
		
		for(Programminformation p : progrServer ){
			progrClient.add(new Programm(p));
			
		}
		
		//System.out.println(progrClient.get(0).getLangeReal());
		
		programmData.clear();
        programmData.addAll(progrClient);
        
		} catch (Exception e) { // catches ANY exception
	        Dialogs.create()
	                .title("Error")
	                .masthead("Could not load data from Server:\n")
	                .showException(e);
	    }
	}
	
	/*
	 * Function for Server/Regie . Load today's data from DB.
	 */
	public void loadTodayProgrammDataFromServer(){
    	try {
    		//System.out.println(Retrieve_Time.getTime()); // Test time
    		RequestServer requestServer = new RequestServer();
    		long time =requestServer.requestSec();
    		ArrayList<Programminformation> progrServer = requestServer.requestMinor(time,true);
    		ArrayList< Programm> progrClient = new ArrayList<Programm>();
    		
    		for(Programminformation p : progrServer ){
    			progrClient.add(new Programm(p));
    		}
    		
    		
    		programmData.clear();
            programmData.addAll(progrClient);
            
    		} catch (Exception e) { // catches ANY exception
//    	        Dialogs.create()
//    	                .title("Error")
//    	                .masthead("Could not load today's data from DB:\n")
//    	                .showException(e);
    	    }
    	}
	
}
