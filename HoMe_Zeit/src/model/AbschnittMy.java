package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import util.LocalDateTimeAdapter;
import datenbank.Abschnitt;
import datenbank.Programminformation;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AbschnittMy implements Serializable {
	
	private IntegerProperty nummer; // +
	private IntegerProperty programm; // +
	private StringProperty titel; // +
	private ObjectProperty<LocalDateTime> startZeit; //  +
	private IntegerProperty lange; // +
	private StringProperty mitwirkende; // +
	private ObjectProperty<LocalDateTime> real_startZeit; // +
	private IntegerProperty reale_lange; // +

	
	/**
     * Default constructor.
     */
    public AbschnittMy() {
        //this(null,null,null,null,null,null);
    	this.startZeit = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0))); 
    	this.titel = new SimpleStringProperty(""); 
    	this.mitwirkende = new SimpleStringProperty(""); 
    	this.lange = new SimpleIntegerProperty(); 
    	this.nummer = new SimpleIntegerProperty();
    	this.programm = new SimpleIntegerProperty();
    	
    	this.reale_lange = new SimpleIntegerProperty();
    	this.real_startZeit = new SimpleObjectProperty<LocalDateTime>();
    }
		
    /**
     * Constructor with some initial data.
     * 
     */
	public AbschnittMy(Integer nummer, Integer programm, String titel, Long startzeit, Integer laenge, String mitwirkende, Long reale_startzeit, Integer reale_laenge)
	{
		this.startZeit = new SimpleObjectProperty<LocalDateTime>
		(LocalDateTime.ofEpochSecond(startzeit, 0,ZoneOffset.of("+01:00")));
		this.titel = new SimpleStringProperty(titel); // +
    	this.mitwirkende = new SimpleStringProperty(mitwirkende); // +
    	this.lange = new SimpleIntegerProperty(laenge); //  !?
    	this.nummer = new SimpleIntegerProperty(nummer);
    	this.programm = new SimpleIntegerProperty(programm);
    	
    	this.reale_lange = new SimpleIntegerProperty(reale_laenge);
    	this.real_startZeit = new SimpleObjectProperty<LocalDateTime>
		(LocalDateTime.ofEpochSecond(reale_startzeit, 0,ZoneOffset.of("+01:00")));
	}
	
	/**
     * Constructor from datenbank.Abschnitt object
     */
    public AbschnittMy(Abschnitt abschnitt){
		this.startZeit = new SimpleObjectProperty<LocalDateTime>
		(LocalDateTime.ofEpochSecond(abschnitt.startzeit, 0,ZoneOffset.of("+01:00")));
		this.titel = new SimpleStringProperty(abschnitt.titel); // +
    	this.mitwirkende = new SimpleStringProperty(abschnitt.mitwirkende); // +
    	this.lange = new SimpleIntegerProperty(abschnitt.laenge.intValue()); //  !?
    	this.nummer = new SimpleIntegerProperty(abschnitt.nummer);
    	this.programm = new SimpleIntegerProperty(abschnitt.programm);
    	
    	this.reale_lange = new SimpleIntegerProperty(abschnitt.reale_laenge.intValue());
    	this.real_startZeit = new SimpleObjectProperty<LocalDateTime>
		(LocalDateTime.ofEpochSecond(abschnitt.reale_startzeit, 0,ZoneOffset.of("+01:00")));
    }
    
    /*
     * Return datenbank.Abschnitt object from AbschnittMy object
     */
    public Abschnitt getAbschnittObject(){
    	Integer nummer = this.getNummer();
    	Integer programm = this.getProgramm();
    	String titel = this.getTitel();
    	Long startzeit = this.getStartZeit().toEpochSecond(ZoneOffset.of("+01:00"));
    	Long laenge = (long)this.getLange(); 
    	String mitwirkende = this.getMitwirkende();
    	
    	Long reale_startzeit = this.getStartZeit().toEpochSecond(ZoneOffset.of("+01:00"));
    	if(this.getStartZeitReal()!=null){
    		reale_startzeit = this.getStartZeitReal().toEpochSecond(ZoneOffset.of("+01:00")); 
    	}
    	
    	Long reale_laenge = (long)this.getLange(); // (long) this.getRealLange();
    	if(this.getRealLange()!=0){
    		reale_laenge = (long) this.getRealLange();
    	}

    	return new Abschnitt(nummer, programm, titel, startzeit, laenge, mitwirkende, reale_startzeit, reale_laenge);
    }
    
 // titel setters and getters  
    public String getTitel() {
        return titel.get();
    }

    public void setTitle(String titel) {
        this.titel.set(titel);
    }

    public StringProperty titleProperty() {
        return titel;
    }

    
 // mitwirkende setters and getters
    public String getMitwirkende() {
        return mitwirkende.get();
    }

    public void setMitwirkende(String mitwirkende) {
        this.mitwirkende.set(mitwirkende);
    }

    public StringProperty mitwirkendeProperty() {
        return mitwirkende;
    }
    
 // nummer setters and getters
    public int getNummer() {
        return nummer.get();
    }

    public void setNummer(int nummer) {
        this.nummer.set(nummer);
    }

    public IntegerProperty nummerProperty() {
        return nummer;
    }
    
    
    // programm setters and getters
    public int getProgramm() {
        return programm.get();
    }

    public void setProgramm(int programm) {
        this.programm.set(programm);
    }

    public IntegerProperty programmProperty() {
        return programm;
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
    
 // reale_lange setters and getters
    public int getRealLange() {
        return reale_lange.get();
    }

    public void setRealLange(int lange) {
        this.reale_lange.set(lange);
    }

    public IntegerProperty realLangeProperty() {
        return reale_lange;
    }
    
 // startZeit setters and getters
    public LocalDateTime getStartZeit() {
        return startZeit.get();
    }

    public void setStartZeit(LocalDateTime dateTime) {
        this.startZeit.set(dateTime);
    }

    public ObjectProperty<LocalDateTime> startZeitProperty() {
        return startZeit;
    }
    
 // real_startZeit setters and getters
    public LocalDateTime getStartZeitReal() {
        return real_startZeit.get();
    }

    public void setStartZeitReal(LocalDateTime dateTime) {
        this.real_startZeit.set(dateTime);
    }

    public ObjectProperty<LocalDateTime> startZeitRealProperty() {
        return real_startZeit;
    }
}
