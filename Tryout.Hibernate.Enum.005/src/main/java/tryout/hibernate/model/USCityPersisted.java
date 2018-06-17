package tryout.hibernate.model;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Parameter;

import tryout.hibernate.enumeration.USState;


@Entity
@Table(name="USCity")
public class USCityPersisted implements Serializable{
	private static final long serialVersionUID = 1L;
	

	//s. Buch "Java Persistence API 2", Seite 41ff.
	//@Type verwendet, weil es eine Fehlermeldung gab, das der Typ nicht gefunden wird. 
	//Ergänzend zu der Buchlösung die Enumeration anders aufgebaut. So kommt man auch per JPQL an Objekte der Enumeration im Resultset.
	//20170213: ABER - nun Transient machen, damit der ENUM nicht als BLOB gespeichert wird. Statt dessen neue String-Spalten hinzugefügt.  
	@Enumerated(EnumType.STRING)	
	@Column(name="USState")
	@Type(type = "tryout.hibernate.enumeration.USState", parameters = @Parameter(name = "type", value = "tryout.hibernate.enumeration.USState"))
	@Transient
	private Enum<USState> enumUSState = null; //weil der EnumType nun String ist. @Column wird verwendet, da sonst der technische Name enumAreaType als Tabellenspalte herhalten muss.
	
	@Id
	@GeneratedValue
	private String sId;
	
     
	private String sName; 
	
	
	//Der Default Contruktor wird für JPA - Abfragen wohl benötigt
	public USCityPersisted(){
	}
	public USCityPersisted(String sName, Enum<USState> enumUSState){
		this.setUSStateObject(enumUSState);
		this.sName = sName;
	}

	
	public String getId(){
		return this.sId;
	}
	public String getName(){
		return this.sName;
	}
	
	@Transient
	public void setUSStateObject(Enum<USState> enumUSState){
		this.enumUSState = enumUSState;
	}
	@Transient
	public Enum<USState> getUSStateObject(){
		return this.enumUSState;
	}
	
	//Hier der Trick, aus dem ENUM den String holen
	//20170201: Versuche den Textwert in der Tabelle zu speichern, damit es kein BLOB ist. Das scheint nicht auszureichen 
	@Column(name="USSTATE")
	@Access(AccessType.PROPERTY)
	//@Enumerated(EnumType.STRING)
	public String getUSState(){
		//das ist die Langbeschreibung return this.getUSStateObject().name();
		String sName = this.getUSStateObject().name();	
		USState at =  this.getUSStateObject().valueOf(USState.class, sName);
		return at.getAbbreviation();
		}	

	public void setUSState(String sAreaType){
		USState objType = USState.fromAbbreviation(sAreaType);
		this.setUSStateObject(objType);
		}
	
}//end class
