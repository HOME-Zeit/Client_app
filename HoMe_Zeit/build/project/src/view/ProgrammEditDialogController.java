package view;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
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
public class ProgrammEditDialogController {


    @FXML
    private TextField startTerminFieldHours;
    @FXML
    private TextField startTerminFieldMinutes;
    @FXML
    private DatePicker  startTerminFieldDate;
    @FXML
    private TextField sendungField;
    @FXML
    private TextField langeField;
    @FXML
    private TextField sendeVerantField;
    @FXML
    private TextField produktVerantField;
    
    
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
    @FXML
    private Label abText;
    @FXML
    private Label abInfo;
    
    // Buttons for work with Abschnitte
    @FXML
    private Button btnAddAb;
    @FXML
    private Button btnEditAb;
    @FXML
    private Button btnDeleteAb;
    


    private Stage dialogStage;
    private Programm programm;
    private boolean okClicked = false;
    
    // Reference to the main application.
    private Main main;
    
 // reference to the UpdateDB
    UpdateDB updateDB = new UpdateDB();

    // Abschnitt to change
    //private AbschnittMy abschnitt;
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	abschnittTable.setPlaceholder(new Label("Tabelle ohne Inhalt"));
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param main
     */
    public void setMain(Main main) {
        this.main = main;

        // Add observable list data to the table
        abschnittTable.setItems(Main.getAbschnittData());
        
     //Listen for selection changes and show the person details when changed.
        abschnittTable.getSelectionModel().selectedItemProperty().addListener(
               (observable, oldValue, newValue) -> showElements(newValue));
    }
    

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private void showElements(AbschnittMy abschnitt) {
    	if(abschnitt!=null && abschnitt.getNummer()!=0){
    		btnEditAb.setDisable(false);
    		btnDeleteAb.setDisable(false);
    	}
    	else{
    		btnEditAb.setDisable(true);
    		btnDeleteAb.setDisable(true);
    	}
     }
    
    /**
     * Sets the programm to be edited in the dialog.
     * 
     * @param programm
     */
    public void setProgramm(Programm programm) {
        this.programm = programm;

        //System.out.println(programm.getNumber());
        
        startTerminFieldHours.setText(Integer.toString(programm.getStartTermin().getHour()));
        startTerminFieldMinutes.setText(Integer.toString(programm.getStartTermin().getMinute()));
        startTerminFieldDate.setValue(programm.getStartTermin().toLocalDate());
        sendungField.setText(programm.getSendungName());
    	if(programm.getLange()<=0){
    		langeField.setText("");
    	} else { 
    		langeField.setText(Integer.toString(programm.getLange()));
    		}
    	sendeVerantField.setText(programm.getSendeVerant());
    	produktVerantField.setText(programm.getProduktVerant());
    	
    	if(programm.abschnittMy.size()>0){
    		Main.getAbschnittData().clear();
        	Main.getAbschnittData().addAll(programm.abschnittMy);
        	
        	showAbschnitt(programm);
    	}else{
    		Main.getAbschnittData().clear();
    	}

    	if(programm.getNumber()<=0){
        	btnAddAb.setVisible(false);
        	abschnittTable.setVisible(false);
        	btnDeleteAb.setVisible(false);
        	btnEditAb.setVisible(false);
        	
        	abInfo.setVisible(true);
    	}
    	
    }
    
    /**
     * Fills table with abschnitte
     */
    private void showAbschnitt(Programm programm) {
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
    	
    	
    }else {
    	
    }
    	
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
        	programm.setStartTermin(LocalDateTime.of(startTerminFieldDate.getValue(),
        			LocalTime.of(Integer.parseInt(startTerminFieldHours.getText()),
        					Integer.parseInt(startTerminFieldMinutes.getText()))));
            programm.setSendungName(sendungField.getText());
            programm.setLange(Integer.parseInt(langeField.getText()));
            programm.setSendeVerant(sendeVerantField.getText());
            programm.setProduktVerant(produktVerantField.getText());
            
            // set startRealTime same as startTime
            programm.setStartTerminReal(LocalDateTime.of(startTerminFieldDate.getValue(),
        			LocalTime.of(Integer.parseInt(startTerminFieldHours.getText()),
        					Integer.parseInt(startTerminFieldMinutes.getText()))));
            programm.setLangeReal(Integer.parseInt(langeField.getText()));
            
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (sendungField.getText() == null || sendungField.getText().length() == 0) {
            errorMessage += "Ung\u00fcltig: Sendung Name!\n"; 
        }
        if (sendeVerantField.getText() == null || sendeVerantField.getText().length() == 0) {
            errorMessage += "Ung\u00fcltig: Sendeverantwortliche(r)!\n"; 
        }
        if (produktVerantField.getText() == null || produktVerantField.getText().length() == 0) {
            errorMessage += "Ung\u00fcltig: Produktionsverantwortliche(r)!\n"; 
        }

        int lange;
        if (langeField.getText() == null || langeField.getText().length() == 0) {
            errorMessage += "Ung\u00fcltig: L\u00e4nge der Sendung!\n"; 
        } else {
            // try to parse into an int.
            try {
                lange = Integer.parseInt(langeField.getText());
                if (lange<0)
                	errorMessage += "Ung\u00fcltig: L\u00e4nge der Sendung (must be > 0 )!\n"; 
            } catch (NumberFormatException e) {
                errorMessage += "Ung\u00fcltig: L\u00e4nge der Sendung (muss ein Zahl sein)!\n"; 
            }
        }

        if (startTerminFieldDate.getValue() == null ) {
            errorMessage += "Ung\u00fcltig: Date!\n"; 
        }
        
        int hours;
        if (startTerminFieldHours.getText() == null || startTerminFieldHours.getText().length() == 0) {
            errorMessage += "Ung\u00fcltig: Hours!\n"; 
        } else {
            // try to parse into an int.
            try {
                hours = Integer.parseInt(startTerminFieldHours.getText());
                if(hours<0 || hours>23){
               	 errorMessage += "Ung\u00fcltig: Hour (must be in interval [0,23])!\n";
               } 
            } catch (NumberFormatException e) {
                errorMessage += "Ung\u00fcltig: Hour (muss ein Zahl sein)!\n"; 
            }  
        }
       
        int minutes;
        if (startTerminFieldMinutes.getText() == null || startTerminFieldMinutes.getText().length() == 0) {
            errorMessage += "Ung\u00fcltig: Minutes!\n"; 
        } else {
            // try to parse into an int.
            try {
            	minutes = Integer.parseInt(startTerminFieldMinutes.getText());
                if(minutes<0 || minutes>59){
               	 errorMessage += "Ung\u00fcltig: Minutes (must be in interval [0,59])!\n";
               } 
            } catch (NumberFormatException e) {
                errorMessage += "Ung\u00fcltig: Minutes (muss ein Zahl sein)!\n"; 
            }  
        }
        
        if(programm.abschnittMy.size()!=0 ){
        	//System.out.println("Yes, I'm here " + programm.abschnittMy.size());
        	errorMessage+=checkTimeOfAb();
        }
        	

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Dialogs.create()
                .title("Ung\u00fcltige Eingabe")
                .masthead("Bitte korriegiren Sie die ung\u00fcltige(n) Eingabe(n)")
                .message(errorMessage)
                .showError();
            return false;
        }
    }
    
    /**
     * Set current Time
     */
    @FXML
    private void handleJetzt() {
    	startTerminFieldHours.setText(Integer.toString(LocalDateTime.now().getHour()));
        startTerminFieldMinutes.setText(Integer.toString(LocalDateTime.now().getMinute()));
        startTerminFieldDate.setValue(LocalDateTime.now().toLocalDate());
    }
    
    /**
     * Copy sendev.
     */
    @FXML
    private void handleCopySende() {
    	produktVerantField.setText(sendeVerantField.getText());
    }
    
    /**
     * Called when the user clicks add Abschnitt.
     */
    @FXML
    private void handleAddAb() {
    	Programm programmT = new Programm(this.programm.getProgInfoObject());
    	
    	programmT.setStartTermin(LocalDateTime.of(startTerminFieldDate.getValue(),
    			LocalTime.of(Integer.parseInt(startTerminFieldHours.getText()),
    					Integer.parseInt(startTerminFieldMinutes.getText()))));
    	programmT.setSendungName(sendungField.getText());
    	programmT.setLange(Integer.parseInt(langeField.getText()));
    	programmT.setSendeVerant(sendeVerantField.getText());
    	programmT.setProduktVerant(produktVerantField.getText());
        
        // set startRealTime same as startTime
    	programmT.setStartTerminReal(LocalDateTime.of(startTerminFieldDate.getValue(),
    			LocalTime.of(Integer.parseInt(startTerminFieldHours.getText()),
    					Integer.parseInt(startTerminFieldMinutes.getText()))));
    	programmT.setLangeReal(Integer.parseInt(langeField.getText()));
    	
        AbschnittMy tempAbschnitt = new AbschnittMy();
        boolean okClicked = main.showAbschnittEditDialog(tempAbschnitt,programmT);
        if (okClicked) {
        	if(programm.getNumber()==0){
        		main.getProgrammData().add(programm);
	            updateDB.updateAppend(programm.getProgInfoObject(), true);
        	}
        	tempAbschnitt.setProgramm(programm.getNumber());
            programm.abschnittMy.add(tempAbschnitt);
            updateDB.segmentsRegister(tempAbschnitt.getAbschnittObject(), true);
        }
        main.loadAllProgrammDataFromDB();
        
        for(Programm pr : main.getProgrammData()){
        	if(programm.getNumber()==pr.getNumber()){
        		programm.abschnittMy.clear();
        		programm.abschnittMy.addAll(pr.abschnittMy);
        	}
        }
        Main.getAbschnittData().clear();
    	Main.getAbschnittData().addAll(programm.abschnittMy);

    	showAbschnitt(programm);
    }
    
    /**
     * Called when the user clicks edit Abschnitt.
     */
    @FXML
    private void handleEditAb() {
    	Programm programmT = new Programm(this.programm.getProgInfoObject());
    	programmT.setStartTermin(LocalDateTime.of(startTerminFieldDate.getValue(),
    			LocalTime.of(Integer.parseInt(startTerminFieldHours.getText()),
    					Integer.parseInt(startTerminFieldMinutes.getText()))));
    	programmT.setSendungName(sendungField.getText());
    	programmT.setLange(Integer.parseInt(langeField.getText()));
    	programmT.setSendeVerant(sendeVerantField.getText());
    	programmT.setProduktVerant(produktVerantField.getText());
        
        // set startRealTime same as startTime
    	programmT.setStartTerminReal(LocalDateTime.of(startTerminFieldDate.getValue(),
    			LocalTime.of(Integer.parseInt(startTerminFieldHours.getText()),
    					Integer.parseInt(startTerminFieldMinutes.getText()))));
    	programmT.setLangeReal(Integer.parseInt(langeField.getText()));
    	
    	AbschnittMy selectedAbschnitt = abschnittTable.getSelectionModel().getSelectedItem();
    	int index = programm.abschnittMy.indexOf(selectedAbschnitt);
    	if(selectedAbschnitt.getNummer()!=0){
            boolean okClicked = main.showAbschnittEditDialog(selectedAbschnitt,programmT);
            if (okClicked) {
                programm.abschnittMy.set(index, selectedAbschnitt);
                updateDB.segmentsRefactor(selectedAbschnitt.getAbschnittObject(), true);
            }
            
            main.loadAllProgrammDataFromDB();
            
            for(Programm pr : main.getProgrammData()){
            	if(programm.getNumber()==pr.getNumber()){
            		programm.abschnittMy.clear();
            		programm.abschnittMy.addAll(pr.abschnittMy);
            	}
            }
            Main.getAbschnittData().clear();
        	Main.getAbschnittData().addAll(programm.abschnittMy);
            
            showAbschnitt(programm);
        } else {
            // Nothing selected.
        	Dialogs.create()
        	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
            .title("Can't edit it now")
            .masthead("This Abschnitt can't be edited now")
            .message("Please at first close and reopen this window.")
            .showWarning();
        }
    }
    
    /**
     * Called when the user clicks delete Abschnitt.
     */
    @FXML
    private void handleDeleteAb() {
    	int selectedIndex = abschnittTable.getSelectionModel().getSelectedIndex();
      	
    	AbschnittMy abschnittToDelete = abschnittTable.getItems().get(selectedIndex);
    	if(abschnittToDelete.getNummer()!=0){
        	Action response = Dialogs.create()
        			.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
        			  .actions(Dialog.ACTION_OK,Dialog.ACTION_CANCEL)
	        	      .title("Abschnitt l\u00f6schen?")
	        	      .masthead("Warnung !")
	        	      .message( "Alle Daten dieser Abschnitt werden gel\u00f6scht ")
	        	      .showConfirm();
        	if(response==Dialog.ACTION_OK){
        		abschnittToDelete = abschnittTable.getItems().remove(selectedIndex);
        		programm.abschnittMy.remove(abschnittToDelete);
        		updateDB.segmentsDelete(abschnittToDelete.getNummer(), true);
        		
        	}
        	showAbschnitt(programm);
        }
        else {
            // Nothing selected.
        	//abschnittToDelete = abschnittTable.getItems().remove(selectedIndex);
    		//programm.abschnittMy.remove(abschnittToDelete);
    		//showAbschnitt(programm);
            Dialogs.create()
            	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
                .title("Can't delete it now")
                .masthead("This Abschnitt can't be deleted now")
                .message("Please at first close and reopen this window.")
                .showWarning();
        }
    }
    
    /*
     * Check overlap in Abschnitte
     */
    private String checkTimeOfAb(){
    	String str = "";
    	
    	Programm programmTest = new Programm();
    	
    	programmTest.setStartTermin(LocalDateTime.of(startTerminFieldDate.getValue(),
    			LocalTime.of(Integer.parseInt(startTerminFieldHours.getText()),
    					Integer.parseInt(startTerminFieldMinutes.getText()))));
    	programmTest.setSendungName(sendungField.getText());
    	programmTest.setLange(Integer.parseInt(langeField.getText()));
    	programmTest.setSendeVerant(sendeVerantField.getText());
        programmTest.setProduktVerant(produktVerantField.getText());
        
        // set startRealTime same as startTime
        programmTest.setStartTerminReal(LocalDateTime.of(startTerminFieldDate.getValue(),
    			LocalTime.of(Integer.parseInt(startTerminFieldHours.getText()),
    					Integer.parseInt(startTerminFieldMinutes.getText()))));
        programmTest.setLangeReal(Integer.parseInt(langeField.getText()));
        programmTest.abschnittMy.addAll(programm.abschnittMy);
    	
    	// set data on abschnittTest
    	for(AbschnittMy abschnittTest : programmTest.abschnittMy ){
    	
    	// test time with start and end of the programm
    	if(abschnittTest.getStartZeit().isBefore(programmTest.getStartTermin()) ||
    			abschnittTest.getStartZeit().isAfter(programmTest.getStartTermin().plusMinutes(programmTest.getLange()))
    			|| abschnittTest.getStartZeit().plusMinutes(abschnittTest.getLange()).isAfter(
    					programmTest.getStartTermin().plusMinutes(programmTest.getLange()))){
    		str+="Ung\u00fcltig: Zeit des Abschnitt  (must be in interval of the Program)!\n";
    	}
    	
    	// test time with others abschnitte
    	for(AbschnittMy abMy : programmTest.abschnittMy ){
    		if(abschnittTest.getNummer() != abMy.getNummer()){
    		if( (abschnittTest.getStartZeit().isAfter(abMy.getStartZeit()) && abschnittTest.getStartZeit().isBefore(
    				abMy.getStartZeit().plusMinutes(abMy.getLange()))) || 
    				(abschnittTest.getStartZeit().plusMinutes(abschnittTest.getLange()).isAfter(abMy.getStartZeit()) &&
    						abschnittTest.getStartZeit().plusMinutes(abschnittTest.getLange()).isBefore(
    								abMy.getStartZeit().plusMinutes(abMy.getLange()))) ||
    								abschnittTest.getStartZeit().isEqual(abMy.getStartZeit()) || 
    							 	 ( abschnittTest.getStartZeit().isBefore(abMy.getStartZeit()) &&
    							 		abschnittTest.getStartZeit().plusMinutes(abschnittTest.getLange()).isAfter(
    							 				abMy.getStartZeit().plusMinutes(abMy.getLange()))) ){
    				str+="Ung\u00fcltig: Zeit des Abschnitt  (Must not overlap other Abschnitte.)!\n";
    				return str;
    		}
    		
    		}
    	}
    	
    	}

    	return str;
    }
    
    /*
	// / try add | delete | edit functions again

	
	 * Called when the user clicks add Abschnitt.
	 
	@FXML
	private void handleAddAb() {
		AbschnittMy tempAbschnitt = new AbschnittMy();
		boolean okClicked = main.showAbschnittEditDialog(tempAbschnitt);
		if (okClicked) {
			Main.getAbschnittData().add(tempAbschnitt);
		}
		
		programm.abschnittMy.clear();
		for(AbschnittMy ab : Main.getAbschnittData())
			programm.abschnittMy.add(ab);

		showAbschnitt(programm);
	}
	
	
    * Called when the user clicks edit Abschnitt.
    
   @FXML
   private void handleEditAb() {
   	AbschnittMy selectedAbschnitt = abschnittTable.getSelectionModel().getSelectedItem();
   	int index = Main.getAbschnittData().indexOf(selectedAbschnitt);
   	if (selectedAbschnitt != null) {
           boolean okClicked = main.showAbschnittEditDialog(selectedAbschnitt);
           if (okClicked) {
        	   Main.getAbschnittData().set(index, selectedAbschnitt);
        	   programm.abschnittMy.clear();
       			for(AbschnittMy ab : Main.getAbschnittData())
       				programm.abschnittMy.add(ab);
           }
           
           showAbschnitt(programm);
       } else {
           // Nothing selected.
           Dialogs.create()
           	.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
               .title("No Selection")
               .masthead("No Abschnitt Selected")
               .message("Please select a abschnitt in the table.")
               .showWarning();
       }
   }

	*//**
	 * Called when the user clicks delete Abschnitt.
	 *//*
	@FXML
	private void handleDeleteAb() {
		int selectedIndex = abschnittTable.getSelectionModel()
				.getSelectedIndex();

		if (selectedIndex >= 0) {
			Action response = Dialogs.create()
					.styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
					.actions(Dialog.ACTION_OK, Dialog.ACTION_CANCEL)
					.title("You sure want to delete this abschnitt?")
					.masthead("Warning !")
					.message("All datas about this abschnitt will be delete")
					.showConfirm();
			if (response == Dialog.ACTION_OK) {
				AbschnittMy abschnittToDelete = abschnittTable.getItems()
						.remove(selectedIndex);
				Main.getAbschnittData().remove(abschnittToDelete);
				programm.abschnittMy.clear();
       			for(AbschnittMy ab : Main.getAbschnittData())
       				programm.abschnittMy.add(ab);

			}
			showAbschnitt(programm);
		} else {
			// Nothing selected.
			Dialogs.create().styleClass(Dialog.STYLE_CLASS_CROSS_PLATFORM)
					.title("No Selection").masthead("No Abschnitt Selected")
					.message("Please select a abschnitt in the table.")
					.showWarning();
		}
	}*/
}
