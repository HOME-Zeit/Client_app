package view;

import java.text.DateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import model.Programm;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	private Label timePlusMinus;

	@FXML
	private ProgressBar progresProgram;

	private Stage dialogStage;
	private Programm programm;
	private boolean okClicked = false;

	/**
	 * To show different times
	 */
	private DateTimeFormatter formatter1 = DateTimeFormatter
			.ofPattern("HH:mm:ss");
	private DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("mm:ss");
	private DateTimeFormatter formatter3 = DateTimeFormatter
			.ofPattern("HH:mm  dd.MM.yyyy");

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
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Current time and time before | till the end | after program .
	 * code of shame ^^
	 */
	public void timeChanger() {
			
		
		
		final Timeline timeline = new Timeline(new KeyFrame(
				Duration.seconds(1), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
		
						LocalDateTime time = LocalDateTime.now();
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
						
						// before start of the program
						if(dur1SecondsAll>0){
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
							
							//System.out.println(progresProgram.getProgress()+ " " + time.format(formatter1));
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
}


/* previos timer - with bugs
int hours = (int)dur.toHours(); // hours "to start"/"after start" of the program
int hours2 = (int)dur2.toHours(); // hours "to end"/"after end" of the program

// this will work if there is one hour before and after the end of program
if (hours == 0 || hours2==0) {
	
	// seconds of the current time
	int seconds = time.getSecond();
	
	// minutes to start/after start of the program
	int minutes = (int) dur.minusHours(hours).toMinutes(); 
	// minutes to end/after end of the program
	int minutes2 = (int) dur2.minusHours(hours2).toMinutes();
	
	//System.out.println(" " + minutes + " " + minutes2);
	//dur2.minusHours((int)dur2.toHours()).toMinutes();
	
	//LocalTime timeToShow = LocalTime.of(0,
	//		Math.abs(minutes), Math.abs(seconds));

	
	int k=0;
	
	
	// it's time before the start of the programm 
	// time.compareTo(programm.getStartTermin())<0
	if (minutes>=0 && time.compareTo(programm.getStartTermin())<0) {
		timeStartEndAfter.setTextFill(Color.GREENYELLOW);
		if(seconds==0 )
			k=1;
		else k=0;
		timeStartEndAfter.setText("- "
				+ LocalTime.of(0,Math.abs(minutes)+k,Math.floorMod((60 - Math.abs(seconds)), 60)).format(formatter2));
	}
	// it's start of the program 00:00
	// time.isEqual(programm.getStartTermin())
	else if(minutes==0 && seconds==0) {
		timeStartEndAfter.setTextFill(Color.WHITE);
		timeStartEndAfter.setText("  "
			+ LocalTime.of(0,0,0).format(formatter2));
	
	}
	//time.compareTo(programm.getStartTermin().plusMinutes(programm.getLange()))<0	
	else if(minutes2>=0 && time.compareTo(programm.getStartTermin().plusMinutes(programm.getLange()))<0){
		if(Math.floorMod((60 - Math.abs(seconds)), 60)==0)
			k=1;
		timeStartEndAfter.setTextFill(Color.WHITE);
		timeStartEndAfter.setText("  "+ LocalTime.of(0, Math.abs(programm.getLange()-Math.abs(minutes-1))+k, 
						Math.floorMod((60 - Math.abs(seconds)), 60)).format(formatter2));
	}
	else {
		timeStartEndAfter.setTextFill(Color.RED);
		
		timeStartEndAfter.setText("+ "
				+ LocalTime.of(0, Math.abs(minutes2), 
						time.getSecond()).format(formatter2) + "  ");
		
	}
}

*/

//String sPlus = "";


//int hours = time.getHour()
//		- programm.getStartTermin().getHour();


//int minutes = time.getMinute()
//		- programm.getStartTermin().getMinute();

//else if(minutes)
// timeStartEndAfter.setText("+ " +
// timeToShow.format(formatter2));

/*
 * java.time.Duration dur =
 * java.time.Duration.between(
 * programm.getStartTermin(), time);
 * 
 * 
 * long minutes = dur.toMinutes();
 * 
 * if (minutes > 0 + programm.getLange()) {
 * 
 * sPlus = "+ "; } else if (minutes < 0) { sPlus =
 * ""; }
 * 
 * 
 * timeStartEndAfter.setText(sPlus +
 * Long.toString(minutes) + ":" +
 * Long.toString(dur.abs().getSeconds() % 60));
 */
