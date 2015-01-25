package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.AbschnittMy;
import model.Programm;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import server_FrontDBCon.UpdateDB;
import application.Main;
/**
 * 
 * @author Viktor Osadchyi
 *
 */
public class ProgrammOverviewController {
	
	 	@FXML
	    private TableView<Programm> programmTable;
	    @FXML
	    private TableColumn<Programm, String> startTerminColumn;
	    @FXML
	    private TableColumn<Programm, String> sendungColumn;
	    @FXML
	    private TableColumn<Programm, String> numberColumn;

	    @FXML
	    private Label startTerminLabel;
	    @FXML
	    private Label sendungLabel;
	    @FXML
	    private Label langeLabel;
	    @FXML
	    private Label sendeVerantLabel;
	    @FXML
	    private Label produktVerantLabel;
	    
	    @FXML
	    private Button refreshButton;
	    @FXML
	    private RadioButton allProgramsRB;
	    @FXML
	    private RadioButton todayProgramsRB;
	    
	    @FXML
	    private Button newButton;
	    @FXML
	    private Button deleteButton;
	    @FXML
	    private Button editButton;
	 
	    // Reference to the main application.
	    private Main main;
	    
	    // reference to the UpdateDB
	    UpdateDB updateDB = new UpdateDB();
	    
	    // Abschnitte table and column
	    @FXML
	    private TableView<AbschnittMy> abschnittTable;
	    @FXML
	    private TableColumn<AbschnittMy, String> numberAbColumn;
	    @FXML
	    private TableColumn<AbschnittMy, String> titelAbColumn;
	    @FXML
	    private TableColumn<AbschnittMy, String> startAbColumn;
	    @FXML
	    private TableColumn<AbschnittMy, String> langeAbColumn;
	    @FXML
	    private TableColumn<AbschnittMy, String> mitwirkendeAbColumn;
	    @FXML
	    private TableColumn<AbschnittMy, String> startRealAbColumn;
	    @FXML
	    private TableColumn<AbschnittMy, String> langeRealColumn;
	    

	    /**
	     * The constructor.
	     * The constructor is called before the initialize() method.
	     */
	    public ProgrammOverviewController() {
	    }

	    /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	    	setVisibleMode();
	        // Initialize the person table with the two columns.
	    	startTerminColumn.setCellValueFactory(cellData -> cellData.getValue().startTerminProperty().asString());
	    	sendungColumn.setCellValueFactory(cellData -> cellData.getValue().sendungNameProperty());
	    	numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asString());
	    	
	    	// Clear person details.
	        showProgrammDetails(null);

	        // Listen for selection changes and show the person details when changed.
	        programmTable.getSelectionModel().selectedItemProperty().addListener(
	                (observable, oldValue, newValue) -> showProgrammDetails(newValue));
	        
	    }

	    /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMain(Main main) {
	        this.main = main;

	        // Add observable list data to the table
	        programmTable.setItems(main.getProgrammData());
	        abschnittTable.setItems(Main.getAbschnittData());
	    }
	    
	    /**
	     * Fills all text fields to show details about the programm.
	     * If the specified programm is null, all text fields are cleared.
	     * 
	     * @param programm the programm or null
	     */
	    private void showProgrammDetails(Programm programm) {
	        if (programm != null) {
	        	
	        	deleteButton.setDisable(false);
	    		editButton.setDisable(false);
	        	
	            // Fill the labels with info from the person object.
	        	startTerminLabel.setText(programm.getStartTermin().toString());
	        	sendungLabel.setText(programm.getSendungName());
	        	langeLabel.setText(Integer.toString(programm.getLange()));
	        	sendeVerantLabel.setText(programm.getSendeVerant());
	        	produktVerantLabel.setText(programm.getProduktVerant());
	        	
	        	Main.getAbschnittData().clear();
	        	
	        	Main.getAbschnittData().addAll(programm.abschnittMy);
	        	
	        	
	        	showAbschnittDetails(programm);

	        } else {
	            // Programm is null, remove all the text.
	        	startTerminLabel.setText("");
	        	sendungLabel.setText("");
	        	langeLabel.setText("");
	        	sendeVerantLabel.setText("");
	        	produktVerantLabel.setText("");
	        	
	        	deleteButton.setDisable(true);
	    		editButton.setDisable(true);
	        
	        	Main.getAbschnittData().clear();}
	       
	    }
	    
	    /**
	     * Fills table with abschnitte
	     */
	    private void showAbschnittDetails(Programm programm) {
	    if(programm.abschnittMy.size()>0){
	    	numberAbColumn.setCellValueFactory(cellData -> cellData.getValue().nummerProperty().asString());
	    	titelAbColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
	    	startAbColumn.setCellValueFactory(cellData -> cellData.getValue().startZeitProperty().asString());
	    	langeAbColumn.setCellValueFactory(cellData -> cellData.getValue().langeProperty().asString());
	    	mitwirkendeAbColumn.setCellValueFactory(cellData -> cellData.getValue().mitwirkendeProperty());
	    	startRealAbColumn.setCellValueFactory(cellData -> cellData.getValue().startZeitRealProperty().asString());
	    	langeRealColumn.setCellValueFactory(cellData -> cellData.getValue().realLangeProperty().asString());
	    	//System.out.println(" in showAbschnittDetails");
	    	//System.out.println(programm.abschnittMy.get(0).getTitel());
	    	//System.out.println(numberAbColumn.getCellData(0));
	    }else{
	    	
	    }
	    	
	    }
	    
	    /**
	     * Called when the user clicks on the delete button.
	     */
	    @FXML
	    private void handleDeleteProgramm() {
	        int selectedIndex = programmTable.getSelectionModel().getSelectedIndex();
	 
	        if(selectedIndex>=0 ){
	        	Action response = Dialogs.create()
	        			.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
	        			  .actions(Dialog.ACTION_OK,Dialog.ACTION_CANCEL)
		        	      .title("You sure want to delete this program?")
		        	      .masthead("Warning !")
		        	      .message( "All datas about this program will be delete")
		        	      .showConfirm();
	        	if(response==Dialog.ACTION_OK){
	        		
	        		Programm deleteProgramm = programmTable.getItems().remove(selectedIndex);
	        		for(AbschnittMy ab : deleteProgramm.abschnittMy ){
	        			updateDB.segmentsDelete(ab.getNummer(), true);
	        		}
	        		updateDB.updateDelete(deleteProgramm.getProgInfoObject(), true);
	        	}
	        	handleRefreshButton(); // refresh table
	        }
	        else {
	            // Nothing selected.
	            Dialogs.create()
	            	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
	                .title("No Selection")
	                .masthead("No Person Selected")
	                .message("Please select a person in the table.")
	                .showWarning();
	        }
	    }
	    
	    /**
	     * Called when the user clicks the new button. Opens a dialog to edit
	     * details for a new programm.
	     */
	    @FXML
	    private void handleNewProgramm() {
	        Programm tempProgramm = new Programm();
	        boolean okClicked = main.showProgrammEditDialog(tempProgramm);
	        if (okClicked) {
	            main.getProgrammData().add(tempProgramm);
	            updateDB.updateAppend(tempProgramm.getProgInfoObject(), true);
	        }
	        handleRefreshButton(); // refresh table
	        
	        /*int i = main.getProgrammData().size();
	        System.out.println(i);
	        int n = main.getProgrammData().get(0).getNumber();
	        for(AbschnittMy ab : Main.getAbschnittData()){
	        	ab.setProgramm(n);
	        	updateDB.segmentsRegister(ab.getAbschnittObject(),true);
	        }*/
	        
	        //handleRefreshButton(); // refresh table
	    }

	    /**
	     * Called when the user clicks the edit button. Opens a dialog to edit
	     * details for the selected programm.
	     */
	    @FXML
	    private void handleEditProgramm() {
	    	Programm selectedProgramm = programmTable.getSelectionModel().getSelectedItem();
	        if (selectedProgramm != null) {
	            boolean okClicked = main.showProgrammEditDialog(selectedProgramm);
	            if (okClicked) {
	                showProgrammDetails(selectedProgramm);
	                updateDB.updateRefactor(selectedProgramm.getProgInfoObject(), true);
	            }
	            
	            handleRefreshButton(); // refresh table
	        } else {
	            // Nothing selected.
	            Dialogs.create()
	            	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
	                .title("No Selection")
	                .masthead("No Person Selected")
	                .message("Please select a person in the table.")
	                .showWarning();
	        }
	    }
	    
	    /**
	     * Called when the user clicks the GO button. Opens a dialog with Timer and
	     * details for the selected programm.
	     */
	    @FXML
	    private void handleGoProgramm() {
	    	Programm selectedProgramm = programmTable.getSelectionModel().getSelectedItem();
	        if (selectedProgramm != null) {
	            boolean okClicked = main.showProgrammTimerGoDialog(selectedProgramm);
	            if (okClicked) {
	                showProgrammDetails(selectedProgramm);
	            }

	        } else {
	            // Nothing selected.
	            Dialogs.create()
	            	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
	                .title("No Selection")
	                .masthead("No Person Selected")
	                .message("Please select a person in the table.")
	                .showWarning();
	        }
	    }
	    
	    /**
	     * Called when Refresh button pressed.
	     * Works in two mode: Regie and Moderator (check bool state in main)
	     * Also check radioButtons for load-mode.
	     */
	    @FXML
	    private void handleRefreshButton(){
	    	if(Main.isRegieMode){
	    		if(allProgramsRB.isSelected()){
	    			main.loadAllProgrammDataFromDB();
	    		}else{
	    			main.loadTodayProgrammDataFromDB();
	    		}
	    	}
	    	else{
	    		if(allProgramsRB.isSelected()){
	    			main.loadAllProgrammDataFromServer();
	    		}else{
	    			main.loadTodayProgrammDataFromServer();
	    		}
	    		
	    	}
	    }
	    
	    private void setVisibleMode(){
	    	if(Main.isRegieMode){
	    		newButton.setVisible(true);
	    		deleteButton.setVisible(true);
	    		editButton.setVisible(true);
	    		
	    		numberAbColumn.setVisible(true);
	    		startRealAbColumn.setVisible(true);
	    		langeRealColumn.setVisible(true);
	    		
	    	}
	    	else{
	    		newButton.setVisible(false);
	    		deleteButton.setVisible(false);
	    		editButton.setVisible(false);
	    		
	    		numberAbColumn.setVisible(false);
	    		startRealAbColumn.setVisible(false);
	    		langeRealColumn.setVisible(false);
	    	}
	    	
	    }
	    
}
