package view;

import java.text.DateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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

						// String sPlus = "";
						LocalDateTime time = LocalDateTime.now();
						currTime.setText(time.format(formatter1));
						
						java.time.Duration dur = java.time.Duration.between(time,
								 programm.getStartTermin() );
						
						java.time.Duration dur2 = java.time.Duration.between(time,
								 programm.getStartTermin().plusMinutes(programm.getLange()));

						//int hours = time.getHour()
						//		- programm.getStartTermin().getHour();
						
						int hours = (int)dur.toHours();
						int hours2 = (int)dur2.toHours();
						
						if (hours == 0 || hours2==0) {
							//int minutes = time.getMinute()
							//		- programm.getStartTermin().getMinute();
							int seconds = time.getSecond();
							
							int minutes = (int) dur.minusHours(hours).toMinutes();
							int minutes2 = (int) dur2.minusHours(hours2).toMinutes();
							
							//dur2.minusHours((int)dur2.toHours()).toMinutes();
							
							LocalTime timeToShow = LocalTime.of(0,
									Math.abs(minutes), Math.abs(seconds));

							
							int k=0;
							if(minutes==0 && seconds==0) {
								timeStartEndAfter.setText("! "
									+ LocalTime.of(0,0,0).format(formatter2));
							}else if (time.compareTo(programm.getStartTermin())<0) {
								timeStartEndAfter.setTextFill(Color.GREENYELLOW);
								if(seconds==0 || (minutes<=1 && seconds==0))
									k=1;
								timeStartEndAfter.setText("- "
										+ LocalTime.of(0,Math.abs(timeToShow.getMinute()+k),Math.floorMod((60 - Math.abs(seconds)), 60)).format(formatter2)+ "  ");
							}
							else if(time.compareTo(programm.getStartTermin().plusMinutes
									(programm.getLange()))<0){
								//if(seconds==0)
								//	minutes-=1;
								timeStartEndAfter.setTextFill(Color.WHITE);
								timeStartEndAfter.setText(LocalTime.of(0, Math.abs(programm.getLange()-Math.abs(minutes-1)), 
												Math.floorMod((60 - Math.abs(seconds)), 60)).format(formatter2));
							}
							else {
								timeStartEndAfter.setTextFill(Color.RED);
								
								timeStartEndAfter.setText("+ "
										+ LocalTime.of(0, Math.abs(minutes2), 
												time.getSecond()).format(formatter2) + "  ");
								
							}

							// else if(minutes)
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
						}
					}
				}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

	}

}
