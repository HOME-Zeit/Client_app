package datenbank;

import java.io.Serializable;

public class Abschnitt implements Serializable {
	public Integer nummer;
	public Integer programm;
	public String titel;
	public Long startzeit;
	public Long laenge;
	public String mitwirkende;
	public Long reale_startzeit;
	public Long reale_laenge;

		
	public Abschnitt(Integer nummer, Integer programm, String titel, Long startzeit, Long laenge, String mitwirkende, Long reale_startzeit, Long reale_laenge)
	{
		this.nummer = nummer;
		this.programm = programm;
		this.titel = titel;
		this.startzeit = startzeit;
		this.laenge = laenge;
		this.mitwirkende = mitwirkende;
		this.reale_startzeit = reale_startzeit;
		this.reale_laenge = reale_laenge;
		return;
	}

}
