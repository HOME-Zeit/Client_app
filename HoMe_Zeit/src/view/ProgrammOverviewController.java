package view;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import application.Main;
import model.Programm;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ProgrammOverviewController {
	
	 	@FXML
	    private TableView<Programm> programmTable;
	    @FXML
	    private TableColumn<Programm, String> startTerminColumn;
	    @FXML
	    private TableColumn<Programm, String> sendungColumn;

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
	 
	    // Reference to the main application.
	    private Main main;

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
	        // Initialize the person table with the two columns.
	    	startTerminColumn.setCellValueFactory(cellData -> cellData.getValue().startTerminProperty().asString());
	    	sendungColumn.setCellValueFactory(cellData -> cellData.getValue().sendungNameProperty());
	    	
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
	    }
	    
	    /**
	     * Fills all text fields to show details about the programm.
	     * If the specified programm is null, all text fields are cleared.
	     * 
	     * @param programm the programm or null
	     */
	    private void showProgrammDetails(Programm programm) {
	        if (programm != null) {
	            // Fill the labels with info from the person object.
	        	startTerminLabel.setText(programm.getStartTermin().toString());
	        	sendungLabel.setText(programm.getSendungName());
	        	langeLabel.setText(Integer.toString(programm.getLange()));
	        	sendeVerantLabel.setText(programm.getSendeVerant());
	        	produktVerantLabel.setText(programm.getProduktVerant());

	        } else {
	            // Programm is null, remove all the text.
	        	startTerminLabel.setText("");
	        	sendungLabel.setText("");
	        	langeLabel.setText("");
	        	sendeVerantLabel.setText("");
	        	produktVerantLabel.setText("");
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
	        		programmTable.getItems().remove(selectedIndex);
	        	}
	        }
	        else {
	            // Nothing selected.
	            Dialogs.create()
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
	        }
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
	            }

	        } else {
	            // Nothing selected.
	            Dialogs.create()
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
	                .title("No Selection")
	                .masthead("No Person Selected")
	                .message("Please select a person in the table.")
	                .showWarning();
	        }
	    }
}
