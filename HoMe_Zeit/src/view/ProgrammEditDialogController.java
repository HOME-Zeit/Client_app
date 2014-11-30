package view;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Programm;

import org.controlsfx.dialog.Dialogs;
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


    private Stage dialogStage;
    private Programm programm;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the programm to be edited in the dialog.
     * 
     * @param programm
     */
    public void setProgramm(Programm programm) {
        this.programm = programm;

        startTerminFieldHours.setText(Integer.toString(programm.getStartTermin().getHour()));
        startTerminFieldMinutes.setText(Integer.toString(programm.getStartTermin().getMinute()));
        startTerminFieldDate.setValue(programm.getStartTermin().toLocalDate());
        sendungField.setText(programm.getSendungName());
    	if(programm.getLange()==0){
    		langeField.setText("");
    	} else { 
    		langeField.setText(Integer.toString(programm.getLange()));
    		}
    	sendeVerantField.setText(programm.getSendeVerant());
    	produktVerantField.setText(programm.getProduktVerant());
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
            errorMessage += "No valid Sendung Name!\n"; 
        }
        if (sendeVerantField.getText() == null || sendeVerantField.getText().length() == 0) {
            errorMessage += "No valid Sendeverantwortlichen!\n"; 
        }
        if (produktVerantField.getText() == null || produktVerantField.getText().length() == 0) {
            errorMessage += "No valid Produktionsverantwortlichen!\n"; 
        }

        int lange;
        if (langeField.getText() == null || langeField.getText().length() == 0) {
            errorMessage += "No valid Lange der Sendung!\n"; 
        } else {
            // try to parse into an int.
            try {
                lange = Integer.parseInt(langeField.getText());
                if (lange<0)
                	errorMessage += "No valid Lange der Sendung (must be > 0 )!\n"; 
            } catch (NumberFormatException e) {
                errorMessage += "No valid Lange der Sendung (must be an integer)!\n"; 
            }
        }

        if (startTerminFieldDate.getValue() == null ) {
            errorMessage += "No valid Date!\n"; 
        }
        
        int hours;
        if (startTerminFieldHours.getText() == null || startTerminFieldHours.getText().length() == 0) {
            errorMessage += "No valid Hours!\n"; 
        } else {
            // try to parse into an int.
            try {
                hours = Integer.parseInt(startTerminFieldHours.getText());
                if(hours<0 || hours>23){
               	 errorMessage += "No valid Hour (must be in interval [0,23])!\n";
               } 
            } catch (NumberFormatException e) {
                errorMessage += "No valid Hour (must be an integer)!\n"; 
            }  
        }
       
        int minutes;
        if (startTerminFieldMinutes.getText() == null || startTerminFieldMinutes.getText().length() == 0) {
            errorMessage += "No valid Minutes!\n"; 
        } else {
            // try to parse into an int.
            try {
            	minutes = Integer.parseInt(startTerminFieldMinutes.getText());
                if(minutes<0 || minutes>59){
               	 errorMessage += "No valid Minutes (must be in interval [0,59])!\n";
               } 
            } catch (NumberFormatException e) {
                errorMessage += "No valid Minutes (must be an integer)!\n"; 
            }  
        }
        	

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Dialogs.create()
                .title("Invalid Fields")
                .masthead("Please correct invalid fields")
                .message(errorMessage)
                .showError();
            return false;
        }
    }
}
