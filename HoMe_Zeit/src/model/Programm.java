package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import util.LocalDateTimeAdapter;
import datenbank.Programminformation;
/**
 * 
 * @author Viktor Osadchyi
 *
 */
public class Programm {

	// number of the program . We get it from database.
	private final IntegerProperty number;  
	private final ObjectProperty<LocalDateTime> startTermin;
	private final StringProperty sendungName;
    private final StringProperty sendeVerant;
    private final StringProperty produktVerant;
    private final IntegerProperty lange;
    
    private final ObjectProperty<LocalDateTime> startTerminReal;
    private final IntegerProperty langeReal;
    
   
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
    	this.number = new SimpleIntegerProperty();
    	
    	this.langeReal = new SimpleIntegerProperty();
    	this.startTerminReal = new SimpleObjectProperty<LocalDateTime>();
    }

    /**
     * Constructor with some initial data.
     * 
     */
    public Programm(String sendungName, String sendeVerant , String produktVerant, Integer lange , LocalDate date ,String time ) {
        
    	this.number=new SimpleIntegerProperty(0); // +
    	this.startTermin = new SimpleObjectProperty<LocalDateTime>
    		(LocalDateTime.of(date, LocalTime.parse(time, timeFormatter))); // +
    	this.sendungName = new SimpleStringProperty(sendungName); // +
    	this.sendeVerant = new SimpleStringProperty(sendeVerant); // +
    	this.produktVerant = new SimpleStringProperty(produktVerant); // +
    	this.lange = new SimpleIntegerProperty(lange); // +
       
    	this.langeReal = new SimpleIntegerProperty();
    	this.startTerminReal = new SimpleObjectProperty<LocalDateTime>();
       
    }
    
    /**
     * Constructor from Programminformation object
     */
    public Programm(Programminformation p){
    	this.number=new SimpleIntegerProperty(p.nummer);
    	this.produktVerant = new SimpleStringProperty(p.prod_verantwortlicher);
    	this.sendeVerant = new SimpleStringProperty(p.sende_verantwortlicher);
    	this.sendungName = new SimpleStringProperty(p.titel);
    	this.startTermin = new SimpleObjectProperty<LocalDateTime>
    		(LocalDateTime.ofEpochSecond(p.startzeit, 0,ZoneOffset.of("+01:00")));
    	this.lange = new SimpleIntegerProperty((int)((p.endzeit - p.startzeit)/60));
    	
    	this.startTerminReal = new SimpleObjectProperty<LocalDateTime>
		(LocalDateTime.ofEpochSecond(p.reale_startzeit, 0,ZoneOffset.of("+01:00")));
    	this.langeReal = new SimpleIntegerProperty((int)((p.reale_endzeit - p.reale_startzeit)/60));
    	
    }
    
    /*
     * Return Programminformation object from Programm object
     */
    public Programminformation getProgInfoObject(){
    	Integer nummer = this.getNumber();
    	String titel = this.getSendungName();
    	Long startzeit = this.getStartTermin().toEpochSecond(ZoneOffset.of("+01:00"));
    	Long endzeit = this.getStartTermin().plusMinutes(getLange()).toEpochSecond(ZoneOffset.of("+01:00"));
    	String prod_verantwortlicher = this.getProduktVerant();
    	String sende_verantwortlicher = this.getSendeVerant();
    	
    	Long reale_startzeit = this.getStartTerminReal().toEpochSecond(ZoneOffset.of("+01:00")); 
    	Long reale_endzeit = this.getStartTerminReal().plusMinutes(getLangeReal()).toEpochSecond(ZoneOffset.of("+01:00"));
    	
    	return new Programminformation(nummer, titel, startzeit, endzeit, prod_verantwortlicher, sende_verantwortlicher, reale_startzeit, reale_endzeit);
    }
    

    // sendungName setters and getters  
    public String getSendungName() {
        return sendungName.get();
    }

    public void setSendungName(String sendungName) {
        this.sendungName.set(sendungName);
    }

    public StringProperty sendungNameProperty() {
        return sendungName;
    }

    // sendeVerant setters and getters
    public String getSendeVerant() {
        return sendeVerant.get();
    }

    public void setSendeVerant(String sendeVerant) {
        this.sendeVerant.set(sendeVerant);
    }

    public StringProperty sendeVerantProperty() {
        return sendeVerant;
    }

    // produktVerant setters and getters
    public String getProduktVerant() {
        return produktVerant.get();
    }

    public void setProduktVerant(String produktVerant) {
        this.produktVerant.set(produktVerant);
    }

    public StringProperty produktVerantProperty() {
        return produktVerant;
    }

    // lange setters and getters
    public int getLange() {
        return lange.get();
    }

    public void setLange(int lange) {
        this.lange.set(lange);
    }

    public IntegerProperty langeProperty() {
        return lange;
    }
    
    // startTermin setters and getters
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
    
    // number setters and getters
    public int getNumber() {
        return number.get();
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public IntegerProperty numberProperty() {
        return number;
    }
    
    // langeReal setters and getters
    public int getLangeReal() {
        return langeReal.get();
    }

    public void setLangeReal(int langeReal) {
        this.langeReal.set(langeReal);
    }

    public IntegerProperty langeRealProperty() {
        return langeReal;
    }
    
    // startTerminReal setters and getters
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public LocalDateTime getStartTerminReal() {
        return startTerminReal.get();
    }

    public void setStartTerminReal(LocalDateTime dateTime) {
        this.startTerminReal.set(dateTime);
    }

    public ObjectProperty<LocalDateTime> startTerminRealProperty() {
        return startTerminReal;
    }
    
//    public void setAllTime(Programminformation p){
//    	this.startTermin = new SimpleObjectProperty<LocalDateTime>
//    		(LocalDateTime.ofEpochSecond(p.startzeit, 0,ZoneOffset.of("+1:00")));
//    	
//    }
    
}
