package view;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import client_ServerRequests.RequestServer;
import server_NTPRequest.Retrieve_Time;
import server_loops.DBLoopMajor;
import server_loops.DBLoopMinor;
import server_loops.DBLoopNumber;
import server_loops.TimeLoopClk;
import server_loops.TimeLoopDate;
import server_loops.TimeLoopSec;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import application.Main;

/**
 * 
 * @author Viktor Osadchyi
 *
 */

/*
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 */

public class RootLayoutController {
	
	@FXML
	private MenuItem ntpChange;
	
	@FXML
	private MenuItem ipChange;

	// Reference to the main application
	private Main main;

	public RootLayoutController() {
		// TODO Auto-generated constructor stub
	}
	
	@FXML
    private void initialize() {
		
    }
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param main
	 */
	public void setMain(Main main) {
		this.main = main;
	}

	/**
	 * Creates an empty program book.
	 */
	@FXML
	private void handleNew() {
//		main.getProgrammData().clear();
//		main.setProgrammFilePath(null);
	}
	
	
	/**
	 * Opens a FileChooser to let the user select program book to load.
	 */
	@FXML
	private void handleOpen() {
//		FileChooser fileChooser = new FileChooser();
//
//		// Set extension filter
//		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
//				"XML files (*.xml)", "*.xml");
//		fileChooser.getExtensionFilters().add(extFilter);
//
//		// Show save file dialog
//		File file = fileChooser.showOpenDialog(main.getPrimaryStage());
//
//		if (file != null) {
//			main.loadProgrammDataFromFile(file);
//		}
	}
	
	/**
	 * Saves the file to the programm file that is currently open. If there is
	 * no open file, the "save as" dialog is shown.
	 */
	@FXML
	private void handleSave() {
//		File personFile = main.getProgrammFilePath();
//		if (personFile != null) {
//			main.saveProgrammDataToFile(personFile);
//		} else {
//			handleSaveAs();
//		}
	}
	
	/**
	 * Opens a FileChooser to let the user select a file to save to.
	 */
	@FXML
	private void handleSaveAs() {
//		FileChooser fileChooser = new FileChooser();
//
//		// Set extension filter
//		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
//				"XML files (*.xml)", "*.xml");
//		fileChooser.getExtensionFilters().add(extFilter);
//
//		// Show save file dialog
//		File file = fileChooser.showSaveDialog(main.getPrimaryStage());
//
//		if (file != null) {
//			// Make sure it has the correct extension
//			if (!file.getPath().endsWith(".xml")) {
//				file = new File(file.getPath() + ".xml");
//			}
//			main.saveProgrammDataToFile(file);
//		}
	}
	
	/**
	 * Opens an about dialog.
	 */
	@FXML
	private void handleAbout() {
		Dialogs.create().styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
				.title("HoMe Zeit").masthead("\u00dcber")
				.message("Autor: HoMe Group\nJahr: 2014 \nVersion: 1.0")
				.showInformation();
	}

	/**
	 * Closes the application.
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}

	/**
	 * Change mode. Works , but not sure that it's the best variant
	 */
	@FXML
	private void handleChangeMode() {

		DBLoopMajor.currentThread().interrupt();
		DBLoopMinor.currentThread().interrupt();
		DBLoopNumber.currentThread().interrupt();
		TimeLoopClk.currentThread().interrupt();
		TimeLoopDate.currentThread().interrupt();
		TimeLoopSec.currentThread().interrupt();

		DBLoopMajor.socketsClose();
		DBLoopMinor.socketsClose();
		DBLoopNumber.socketsClose();
		TimeLoopClk.socketsClose();
		TimeLoopDate.socketsClose();
		TimeLoopSec.socketsClose();

		try {

			main.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		main.start(main.getPrimaryStage());

	}
	
	@FXML
	private void handleChangeIP() {
		RequestServer requestServer = new RequestServer();
		Optional<String> iPAddress = Dialogs.create()
    			.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
    			  .actions(Dialog.ACTION_OK,Dialog.ACTION_CANCEL)
        	      .title("IP-Adresse w\u00e4hlen")
        	      .masthead("Aktuelle IP = \""+ requestServer.getIP()+"\"")
        	      .message( "Geben Sie die neue Adresse des Servers ein")
        	      .showTextInput();
		
		/**
		 * TODO chaeck or in new IP are 4 blocks - str.split(...)
		 */
		if(iPAddress.isPresent() && iPAddress.get().compareTo("")!=0){
			requestServer.setIP(iPAddress.get());
			main.saveIpNtpToPref(requestServer.getIP());
			Dialogs.create()
        	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
            .title("Sie haben die IP-Adresse ge\u00e4ndert")
            .masthead("IP-Adresse = \""+ requestServer.getIP()+"\"")
            .showWarning();
			
		}
		else{
			Dialogs.create()
        	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
            .title("Sie haben die IP-Adresse nicht ge\u00e4ndert")
            .masthead("IP-Adresse = \""+ requestServer.getIP()+"\"")
            .message("Wenn Sie die IP-Adresse \u00e4ndern wollen, versuchen Sie dies erneut")
            .showWarning();
		}
	

	}
	
	@FXML
	private void handleChangeNTP() {
		Retrieve_Time retrieve_Time = new Retrieve_Time();
		Optional<String> nTPAddress = Dialogs.create()
    			.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
    			  .actions(Dialog.ACTION_OK,Dialog.ACTION_CANCEL)
        	      .title("Ntp-Server Adresse w\u00e4hlen")
        	      .masthead("Aktuelle NTP = \""+ retrieve_Time.getUrl()+"\"")
        	      .message( "Geben Sie die neue Adresse des Ntp-Servers ein")
        	      .showTextInput();
		
		/**
		 * TODO chaeck or in new IP are 4 blocks - str.split(...)
		 */
		if(nTPAddress.isPresent() && nTPAddress.get().compareTo("")!=0){
			retrieve_Time.setUrl(nTPAddress.get());
			main.saveIpNtpToPref(retrieve_Time.getUrl());
			Dialogs.create()
        	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
            .title("Sie haben die Ntp-Server Adresse ge\u00e4ndert")
            .masthead("Ntp-Server Adresse = \""+ retrieve_Time.getUrl()+"\"")
            .showWarning();
		}
		else{
			Dialogs.create()
        	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
            .title("Sie haben die Ntp-Server Adresse nicht ge\u00e4ndert")
            .masthead("Ntp-Server Adresse = \""+ retrieve_Time.getUrl()+"\"")
            .message("Wenn Sie die Ntp-Server Adresse \u00e4ndern wollen, versuchen Sie dies erneut")
            .showWarning();
		}
	

	}
	
	public void setVisibleMode(){
    	if(Main.isRegieMode){
    		//System.out.println("I am here");
    		ipChange.setVisible(false);
    		ntpChange.setVisible(true);
 
    	}
    	else{
    		ipChange.setVisible(true);
    		ntpChange.setVisible(false);
    	}
    	
    }

}
