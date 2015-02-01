package view;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.AmbientLight;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AbschnittMy;
import model.Programm;

import org.controlsfx.dialog.Dialogs;

import application.Main;

/**
 * 
 * @author Viktor Osadchyi
 *
 */
public class AbschnittEditDialogController {
	
	@FXML
    private TextField titelField;
	@FXML
    private TextField startZeitFieldHours;
    @FXML
    private TextField startZeitFieldMinutes;
    @FXML
    private DatePicker  startZeitFieldDate;
    @FXML
    private TextField langeAbField;
    @FXML
    private TextField mitwirkendeField;
    
    // Reference to the main application.
    private Main main;
    private Stage dialogStage;
    
    private AbschnittMy abschnitt;
    private boolean okClicked = false;
    
    // curennt programm of this Abschnitt
    private Programm programm;
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param main
     */
    public void setMain(Main main) {
        this.main = main;
    }
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setAbschnitt(AbschnittMy abschnitt) {
        this.abschnitt = abschnitt;

        titelField.setText(abschnitt.getTitel());
        startZeitFieldHours.setText(Integer.toString(abschnitt.getStartZeit().getHour()));
        startZeitFieldMinutes.setText(Integer.toString(abschnitt.getStartZeit().getMinute()));
        startZeitFieldDate.setValue(abschnitt.getStartZeit().toLocalDate());
        langeAbField.setText(Integer.toString(abschnitt.getLange()));
        mitwirkendeField.setText(abschnitt.getMitwirkende());

    }
    
    /**
     * Sets the programm of this Abschnitt.
     * 
     * @param programm
     */
    public void setProgramm(Programm programm) {
        this.programm = programm;
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
                 
            String errMessage = checkTimeOfAb();
            if(errMessage.length()==0){
            	abschnitt.setStartZeit(LocalDateTime.of(startZeitFieldDate.getValue(),
            			LocalTime.of(Integer.parseInt(startZeitFieldHours.getText()),
            					Integer.parseInt(startZeitFieldMinutes.getText()))));
        		abschnitt.setTitle(titelField.getText());
        		abschnitt.setLange(Integer.parseInt(langeAbField.getText()));
                abschnitt.setMitwirkende(mitwirkendeField.getText());
                abschnitt.setStartZeitReal(LocalDateTime.of(startZeitFieldDate.getValue(),
            			LocalTime.of(Integer.parseInt(startZeitFieldHours.getText()),
            					Integer.parseInt(startZeitFieldMinutes.getText()))));
                abschnitt.setRealLange(Integer.parseInt(langeAbField.getText()));;
            	
            	okClicked = true;
            	dialogStage.close();
            }
            else{
            	// Show the error message.
                Dialogs.create()
                    .title("Ung\u00fcltige Eingabe")
                    .masthead("Bitte korriegiren Sie die ung\u00fcltige(n) Eingabe(n)")
                    .message(errMessage)
                    .showError();
            }
        }
    }
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (titelField.getText() == null || titelField.getText().length() == 0) {
            errorMessage += "Ung\u00fcltig: Titel Name!\n"; 
        }
        if (mitwirkendeField.getText() == null || mitwirkendeField.getText().length() == 0) {
            errorMessage += "Ung\u00fcltig: Mitwirkende!\n"; 
        }

        int lange;
        if (langeAbField.getText() == null || langeAbField.getText().length() == 0) {
            errorMessage += "Ung\u00fcltig: L\u00e4nge der Abschnitt!\n"; 
        } else {
            // try to parse into an int.
            try {
                lange = Integer.parseInt(langeAbField.getText());
                if (lange<0)
                	errorMessage += "Ung\u00fcltig: L\u00e4nge der Abschnitt (must be > 0 )!\n"; 
            } catch (NumberFormatException e) {
                errorMessage += "Ung\u00fcltig: L\u00e4nge der Abschnitt (muss ein Zahl sein)!\n"; 
            }
        }

        if (startZeitFieldDate.getValue() == null ) {
            errorMessage += "Ung\u00fcltig: Date!\n"; 
        }
        
        int hours;
        if (startZeitFieldHours.getText() == null || startZeitFieldHours.getText().length() == 0) {
            errorMessage += "Ung\u00fcltig: Hours!\n"; 
        } else {
            // try to parse into an int.
            try {
                hours = Integer.parseInt(startZeitFieldHours.getText());
                if(hours<0 || hours>23){
               	 errorMessage += "Ung\u00fcltig: Hour (must be in interval [0,23])!\n";
               } 
            } catch (NumberFormatException e) {
                errorMessage += "Ung\u00fcltig: Hour (muss ein Zahl sein)!\n"; 
            }  
        }
       
        int minutes;
        if (startZeitFieldMinutes.getText() == null || startZeitFieldMinutes.getText().length() == 0) {
            errorMessage += "Ung\u00fcltig: Minutes!\n"; 
        } else {
            // try to parse into an int.
            try {
            	minutes = Integer.parseInt(startZeitFieldMinutes.getText());
                if(minutes<0 || minutes>59){
               	 errorMessage += "Ung\u00fcltig: Minutes (must be in interval [0,59])!\n";
               } 
            } catch (NumberFormatException e) {
                errorMessage += "Ung\u00fcltig: Minutes (muss ein Zahl sein)!\n"; 
            }  
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
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
    	dialogStage.close();
    }
    
    /*
     * Check overlap in Abschnitte
     */
    private String checkTimeOfAb(){
    	String str = "";
    	
    	
    	// set data on abschnittTest
    	AbschnittMy abschnittTest = new AbschnittMy();
    	abschnittTest.setStartZeit(LocalDateTime.of(startZeitFieldDate.getValue(),
    			LocalTime.of(Integer.parseInt(startZeitFieldHours.getText()),
    					Integer.parseInt(startZeitFieldMinutes.getText()))));
    	abschnittTest.setTitle(titelField.getText());
    	abschnittTest.setLange(Integer.parseInt(langeAbField.getText()));
    	abschnittTest.setMitwirkende(mitwirkendeField.getText());
    	abschnittTest.setStartZeitReal(LocalDateTime.of(startZeitFieldDate.getValue(),
    			LocalTime.of(Integer.parseInt(startZeitFieldHours.getText()),
    					Integer.parseInt(startZeitFieldMinutes.getText()))));
    	abschnittTest.setRealLange(Integer.parseInt(langeAbField.getText()));;
    	abschnittTest.setNummer(abschnitt.getNummer());
    	
    	// test time with start and end of the programm
    	if(abschnittTest.getStartZeit().isBefore(programm.getStartTermin()) ||
    			abschnittTest.getStartZeit().isAfter(programm.getStartTermin().plusMinutes(programm.getLange()))
    			|| abschnittTest.getStartZeit().plusMinutes(abschnittTest.getLange()).isAfter(
    					programm.getStartTermin().plusMinutes(programm.getLange()))){
    		str+="Ung\u00fcltig: Zeit des Abschnitt  (must be in interval of the Program)!\n";
    	}
    	
    	// test time with others abschnitte
    	for(AbschnittMy abMy : programm.abschnittMy ){
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

    	return str;
    }

}
