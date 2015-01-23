package view;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
    
    
    
    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
    }

}
