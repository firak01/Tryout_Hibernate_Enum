package tryout.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import tryout.hibernate.enumeration.USState;
import tryout.hibernate.model.USCityPersisted;

public class TryOutReadTableMain005 {
	public static void main(String[] args) {
		//HibernateContextProviderTHM objContextHibernate = new HibernateContextProviderTHM(this.getKernelObject());
		
		/* Der "Lesezugriff" wird  �ber JPA und nicht �ber Hibernate gemacht. Darum die Konfiguration in den Dateien und nicht �ber das Konfigurationsobjekt */
		 /* In dieser Konfiguration wird daher CREATE verwendet */
		/*Configuration cfg = new Configuration();
		cfg.setProperty("hibernate.dialect","tryout.hibernate.enumeration.SQLiteDialect" );
		cfg.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
		cfg.setProperty("hibernate.connection.url", "jdbc:sqlite:c:\\server\\SQLite\\TestEnumeration001.sqlite");
		cfg.setProperty("hibernate.connection.username", "");
		cfg.setProperty("hibernate.connection.password", "");
		cfg.setProperty("hibernate.hbm2ddl.auto", "create"); //! Damti wird die Datenbank und sogar die Tabellen darin automatisch erstellt.
		cfg.setProperty("cache.provider_class", "org.hibernate.cache.NoCacheProvider");
		cfg.setProperty("current_session_context_class", "thread");
		
		//tryout.hibernate.enumeration.USCityPersisted
		cfg.addAnnotatedClass(USCityPersisted003.class);
		
		
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		SessionFactory sf = cfg.buildSessionFactory(sr);
		Session hibernateSession = sf.openSession();
		
		hibernateSession.beginTransaction(); //ich hab gelesen, dass die Reihenfolge beginnTransaction und definition der zu pesistierenden Objekte egal ist

		USCityPersisted003 city01 = new USCityPersisted003("Teststadt01", USState003.ALABAMA);
		hibernateSession.save(city01);

		USCityPersisted003 city02 = new USCityPersisted003("Teststadt02", USState003.ALASKA);
		hibernateSession.save(city02);
		*/
		
		//PER JPA wird �ber META-INF/persistence.xml die Konfiguration vorgenommen. Dort steht auch der Name der Persistence Unit.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TryOutEnumeration004");
		EntityManager em = emf.createEntityManager();
		//Query objQuery = em.createQuery("SELECT c FROM HexCell c");
		Query objQuery = em.createQuery("SELECT c FROM USCityPersisted004 c");
		List objResult = objQuery.getResultList();
		int max = -1;
		System.out.println("Anzahl der gefundenen Datensätze: " +  objResult.size());
		for(Object obj : objResult){
			System.out.println("obj.class: " + obj.getClass().getName());
			
			//Nein, die Objekte der Resultlist sind vom Typ AreaCell AreaType enumAreaType = (AreaType) obj;
//			AreaCell objCell = (AreaCell) obj;
//			Enum<AreaType> enumAreaType = objCell.getAreaType();
			
			USCityPersisted objCity = (USCityPersisted)obj;
			
			//Die Enumerateion wurde in der Tabelle als "BLOB" gespeichert (Version Enum.003). 
			//In Version Enum.004, als String. Trotzdem kommt man an das Enum-Objekt heran!
			Enum<USState> enumUSState = objCity.getUSStateObject(); 
			
			System.out.println("Name / Bezeichnung / Abkürzung: " + enumUSState.name() + "/" + enumUSState.toString() + "/" + ((USState) enumUSState).getAbbreviation());
		}
		
//hier muss beim "nur lesezugriff" keine Transaktion ausgeführt werden.
//		hibernateSession.getTransaction().commit();
//		hibernateSession.close();
		
	}
}
