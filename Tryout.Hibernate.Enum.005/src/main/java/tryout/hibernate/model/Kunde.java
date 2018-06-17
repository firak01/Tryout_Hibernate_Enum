/*
 * (c) Bernd M�ller & Harald Wehr, www.jpainfo.de
 * 
 * Erg�nzt von Fritz Lindhauer 2013-12-12
 */ 
package tryout.hibernate.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tryout.hibernate.enumeration.Anrede;

@Entity
@SecondaryTable(name = "KUNDE_ADRESSE",	pkJoinColumns={@PrimaryKeyJoinColumn(name = "KUNDE_FK")})
public class Kunde  {

	//!!! FGL: @GeneratedValue bei der Arbeit mit MySQL erweitern um "(strategy = GenerationType.TABLE)", ansonsten kann er die Tabelle nicht vern�nftig erstellen und es gibt entsprechende Fehlermeldungen 'Column 'KUNDE_PK' cannot be null'	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "KUNDE_PK")
	private Integer id;
	@Column(length = 30)
	private String vorname;
	@Column(length = 30)
	private String nachname;
	@Temporal(TemporalType.DATE)
	@Column(name = "GEBU")
	private Date geburtsdatum;
	
	//Auch Ohne das wird die Spalte ausgewiesen, aber eben als default mit Integer-Wert
	@Enumerated(EnumType.STRING)
	private Anrede anrede;

	@Column(table = "KUNDE_ADRESSE", length = 30)
	private String strasse;
	@Column(table = "KUNDE_ADRESSE", length = 5)
	private String plz;
	@Column(table = "KUNDE_ADRESSE", length = 30)
	private String ort;
	
	
	public Kunde() {
	}

	public Kunde(String vorname, String nachname, Date geburtsdatum,
			Anrede anrede, String strasse, String plz, String ort) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.geburtsdatum = geburtsdatum;
		this.anrede = anrede;
		this.strasse = strasse;
		this.plz = plz;
		this.ort = ort;
		//this.id = 12;   //TEST
	}



	public Integer getId() {
		return id;
	}
	
	public String getVorname() {
		return this.vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	public String getNachname() {
		return this.nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	
	public Date getGeburtsdatum() {
		return this.geburtsdatum;
	}
	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public Anrede getAnrede() {
		return anrede;
	}
	public void setAnrede(Anrede anrede) {
		this.anrede = anrede;
	}

	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getPlz() {
		return plz;
	}
	public void setPlz(String plz) {
		this.plz = plz;
	}
	
	public String getStrasse() {
		return strasse;
	}
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	
}
