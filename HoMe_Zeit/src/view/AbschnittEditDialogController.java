package view;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.controlsfx.dialog.Dialogs;

import model.AbschnittMy;
import model.Programm;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    		abschnitt.setStartZeit(LocalDateTime.of(startZeitFieldDate.getValue(),
        			LocalTime.of(Integer.parseInt(startZeitFieldHours.getText()),
        					Integer.parseInt(startZeitFieldMinutes.getText()))));
    		abschnitt.setTitle(titelField.getText());
    		abschnitt.setLange(Integer.parseInt(langeAbField.getText()));
            abschnitt.setMitwirkende(mitwirkendeField.getText());
            
            okClicked = true;
            dialogStage.close();
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
            errorMessage += "No valid Titel Name!\n"; 
        }
        if (mitwirkendeField.getText() == null || mitwirkendeField.getText().length() == 0) {
            errorMessage += "No valid Mitwirkende!\n"; 
        }

        int lange;
        if (langeAbField.getText() == null || langeAbField.getText().length() == 0) {
            errorMessage += "No valid Lange der Abschnitt!\n"; 
        } else {
            // try to parse into an int.
            try {
                lange = Integer.parseInt(langeAbField.getText());
                if (lange<0)
                	errorMessage += "No valid Lange der Abschnitt (must be > 0 )!\n"; 
            } catch (NumberFormatException e) {
                errorMessage += "No valid Lange der Abschnitt (must be an integer)!\n"; 
            }
        }

        if (startZeitFieldDate.getValue() == null ) {
            errorMessage += "No valid Date!\n"; 
        }
        
        int hours;
        if (startZeitFieldHours.getText() == null || startZeitFieldHours.getText().length() == 0) {
            errorMessage += "No valid Hours!\n"; 
        } else {
            // try to parse into an int.
            try {
                hours = Integer.parseInt(startZeitFieldHours.getText());
                if(hours<0 || hours>23){
               	 errorMessage += "No valid Hour (must be in interval [0,23])!\n";
               } 
            } catch (NumberFormatException e) {
                errorMessage += "No valid Hour (must be an integer)!\n"; 
            }  
        }
       
        int minutes;
        if (startZeitFieldMinutes.getText() == null || startZeitFieldMinutes.getText().length() == 0) {
            errorMessage += "No valid Minutes!\n"; 
        } else {
            // try to parse into an int.
            try {
            	minutes = Integer.parseInt(startZeitFieldMinutes.getText());
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

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
    	dialogStage.close();
    }

}
