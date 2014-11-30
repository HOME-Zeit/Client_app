package view;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import server_NTPRequest.Retrieve_Time;
import application.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Programm;
/**
 * 
 * @author Viktor Osadchyi
 * 
 */

public class TimerGoController {

	/**
	 * Default constructor
	 */
	public TimerGoController() {
	}

	@FXML
	private Label programInfo;

	@FXML
	public Label currTime;

	@FXML
	private Label timeStartEndAfter;

	@FXML
	private ProgressBar progresProgram;

	private Stage dialogStage;
	private Programm programm;
	private boolean okClicked = false;
	
	private long secondsDiff = 0;
	
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
	 * To show different times
	 */
	private DateTimeFormatter formatter1 = DateTimeFormatter
			.ofPattern("HH:mm:ss");
	private DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("mm:ss");
	private DateTimeFormatter formatter3 = DateTimeFormatter
			.ofPattern("HH:mm  dd.MM.yyyy");
	
	public Timeline timeline;

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

	public void setProgramm(Programm programm) {
		this.programm = programm;

		programInfo.setText(programm.getSendungName() + " | "
				+ programm.getStartTermin().format(formatter3).toString()
				+ " | " + Integer.toString(programm.getLange()) + " Minuten");

		getDiffTime();
		//System.out.println(secondsDiff);
		timeChanger();
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
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleMainWindow() {
		if(timeline!=null)
			timeline.stop();
		dialogStage.close();
	}

	/**
	 * Current time and time before | till the end | after program .
	 * code of shame ^^
	 */
	public void timeChanger() {

		timeline = new Timeline(new KeyFrame(
				Duration.seconds(1), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
		
						LocalDateTime time = LocalDateTime.now().plusSeconds(secondsDiff);
						currTime.setText(time.format(formatter1)); // show current time
						
						// time between current time and start of the programm.
						java.time.Duration dur1 = java.time.Duration.between(time,
								 programm.getStartTermin() ); 
						dur1 = dur1.plusSeconds(1);
						
						// time between current time and end of the programm.
						java.time.Duration dur2 = java.time.Duration.between(time,
								 programm.getStartTermin().plusMinutes(programm.getLange()));
						dur2 = dur2.plusSeconds(1);
						
						long dur1SecondsAll = dur1.get(ChronoUnit.SECONDS);
						long dur2secondsAll = dur2.get(ChronoUnit.SECONDS);
						
						int dur1Hours = (int) dur1.toHours();
						int dur1Minutes = (int) dur1.minusHours(dur1Hours).toMinutes();
						int dur1Seconds = (int) dur1.minusHours(dur1Hours).minusMinutes(dur1Minutes).getSeconds();
						
						int dur2Hours = (int) dur2.toHours();
						int dur2Minutes = (int) dur2.minusHours(dur2Hours).toMinutes();
						int dur2Seconds = (int) dur2.minusHours(dur2Hours).minusMinutes(dur2Minutes).getSeconds();
						
						//System.out.println(dur1Hours + " 1");
						//System.out.println(dur2Hours + " 2");
						if(dur1Hours>=24){
							timeStartEndAfter.setStyle("-fx-font-size:40;");
							timeStartEndAfter.setTextFill(Color.GREENYELLOW);
							timeStartEndAfter.setText("More then 24 hours to START");
						}
						else if(dur2Hours<=-24){
							timeStartEndAfter.setStyle("-fx-font-size:40;");
							timeStartEndAfter.setTextFill(Color.RED);
							timeStartEndAfter.setText("More then 24 hours after END");
							progresProgram.setProgress(1);
						}
						// before start of the program
						else if(dur1SecondsAll>0){
							timeStartEndAfter.setTextFill(Color.GREENYELLOW);
							timeStartEndAfter.setText("- "
									+LocalTime.of(dur1Hours, dur1Minutes, dur1Seconds ).format(formatter1) +" ") ;
						}
						// start of program
						else if(dur1SecondsAll==0 ){
							timeStartEndAfter.setTextFill(Color.WHITE);
							timeStartEndAfter.setText("  "
								+ LocalTime.of(0,0,0).format(formatter1)+" ");
						}
						//  during program
						else if(dur2secondsAll>0){
							timeStartEndAfter.setTextFill(Color.WHITE);
							timeStartEndAfter.setText("  "
									+LocalTime.of(dur2Hours, dur2Minutes, dur2Seconds ).format(formatter1)+" " ) ;
							//progresProgram.setProgress(((programm.getLange()*60.0-dur2.getSeconds())+1)/(programm.getLange()*60.0));
							//System.out.println(dur2.getSeconds()+" ");
							progresProgram.setProgress((programm.getLange()*60.0-dur2.getSeconds()+0.5)/(programm.getLange()*60.0));
							
							System.out.println(progresProgram.getProgress()+ " " + time.format(formatter1));
						}
						// end of program
						else if(dur2secondsAll==0){
							timeStartEndAfter.setTextFill(Color.WHITE);
							timeStartEndAfter.setText("  "
								+ LocalTime.of(0,0,0).format(formatter1)+" ");
							progresProgram.setProgress(1);
						}
						// after end of the program
						else if(dur2secondsAll<0){
							timeStartEndAfter.setTextFill(Color.RED);
							
							timeStartEndAfter.setText("+ "
									+LocalTime.of(Math.abs(dur2Hours), Math.abs(dur2Minutes), Math.abs(dur2Seconds) ).format(formatter1) +" ") ;
							progresProgram.setProgress(1);
						}
						// should never happen
						else {
							
						}
						
					}
				}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

	}
	
	private void getDiffTime(){
		if(Main.isRegieMode){
			try {
				this.secondsDiff = Retrieve_Time.getTime() - LocalDateTime.now().toEpochSecond(ZoneOffset.of("+01:00"))  ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			
		}
	}
}
