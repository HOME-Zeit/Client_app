package datenbank;

public class Programminformation 
{
	public Integer nummer;
	public String titel;
	public Long startzeit;
	public Long endzeit;
	public String prod_verantwortlicher;
	public String sende_verantwortlicher;
	public Long reale_startzeit;
	public Long reale_endzeit;
	
		
	public Programminformation(Integer nummer, String titel, Long startzeit, Long endzeit, String prod_verantwortlicher, String sende_verantwortlicher, Long reale_startzeit, Long reale_endzeit)
	{
		this.nummer = nummer;
		this.titel = titel;
		this.startzeit = startzeit;
		this.endzeit = endzeit;
		this.prod_verantwortlicher = prod_verantwortlicher;
		this.sende_verantwortlicher = sende_verantwortlicher;
		this.reale_startzeit = reale_startzeit;
		this.reale_endzeit = reale_endzeit;
		return;
	}	
}