package view;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import client_ServerRequests.RequestServer;
import server_loops.DBLoopMajor;
import server_loops.DBLoopMinor;
import server_loops.DBLoopNumber;
import server_loops.TimeLoopClk;
import server_loops.TimeLoopDate;
import server_loops.TimeLoopSec;
import javafx.fxml.FXML;
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

	// Reference to the main application
	private Main main;

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
				.title("HoMe Zeit").masthead("About")
				.message("Author: HoMe Group\nYear: 2014 \nVersion: 1.0")
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
		Optional<String> iPAddress = Dialogs.create()
    			.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
    			  .actions(Dialog.ACTION_OK,Dialog.ACTION_CANCEL)
        	      .title("IP - Address choice")
        	      .masthead("Now IP = \""+ RequestServer.getIP()+"\"")
        	      .message( "Enter new IP-Address of the server or Cancel")
        	      .showTextInput();
		
		/**
		 * TODO chaeck or in new IP are 4 blocks - str.split(...)
		 */
		if(iPAddress.isPresent() && iPAddress.get().compareTo("")!=0){
			RequestServer.setIP(iPAddress.get());
			
			Dialogs.create()
        	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
            .title("You have changed IP-Address")
            .masthead("IP-Address = \""+ RequestServer.getIP()+"\"")
            .showWarning();
		}
		else{
			Dialogs.create()
        	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
            .title("You have not changed IP-Address")
            .masthead("IP-Address = \""+ RequestServer.getIP()+"\"")
            .message("If you want to change IP-Address -  try again")
            .showWarning();
		}
	

	}

}
