package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import util.LocalDateTimeAdapter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Programm {

	private final ObjectProperty<LocalDateTime> startTermin;
	private final StringProperty sendungName;
    private final StringProperty sendeVerant;
    private final StringProperty produktVerant;
    private final IntegerProperty lange;
    
   
    String pattern = "HH:mm";
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(pattern);

    /**
     * Default constructor.
     */
    public Programm() {
        //this(null,null,null,null,null,null);
    	this.startTermin = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0))); // +
    	this.sendungName = new SimpleStringProperty(""); // +
    	this.sendeVerant = new SimpleStringProperty(""); // +
    	this.produktVerant = new SimpleStringProperty(""); // +
    	this.lange = new SimpleIntegerProperty(); //  !?
    }

    /**
     * Constructor with some initial data.
     * 
     */
    public Programm(String sendungName, String sendeVerant , String produktVerant, Integer lange , LocalDate date ,String time ) {
        
    	this.startTermin = new SimpleObjectProperty<LocalDateTime>
    		(LocalDateTime.of(date, LocalTime.parse(time, timeFormatter))); // +
    	this.sendungName = new SimpleStringProperty(sendungName); // +
    	this.sendeVerant = new SimpleStringProperty(sendeVerant); // +
    	this.produktVerant = new SimpleStringProperty(produktVerant); // +
    	this.lange = new SimpleIntegerProperty(lange); // +
       
       
    }

    public String getSendungName() {
        return sendungName.get();
    }

    public void setSendungName(String sendungName) {
        this.sendungName.set(sendungName);
    }

    public StringProperty sendungNameProperty() {
        return sendungName;
    }

    public String getSendeVerant() {
        return sendeVerant.get();
    }

    public void setSendeVerant(String sendeVerant) {
        this.sendeVerant.set(sendeVerant);
    }

    public StringProperty sendeVerantProperty() {
        return sendeVerant;
    }

    public String getProduktVerant() {
        return produktVerant.get();
    }

    public void setProduktVerant(String produktVerant) {
        this.produktVerant.set(produktVerant);
    }

    public StringProperty produktVerantProperty() {
        return produktVerant;
    }

    public int getLange() {
        return lange.get();
    }

    public void setLange(int lange) {
        this.lange.set(lange);
    }

    public IntegerProperty langeProperty() {
        return lange;
    }
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public LocalDateTime getStartTermin() {
        return startTermin.get();
    }

    public void setStartTermin(LocalDateTime dateTime) {
        this.startTermin.set(dateTime);
    }

    public ObjectProperty<LocalDateTime> startTerminProperty() {
        return startTermin;
    }
}
