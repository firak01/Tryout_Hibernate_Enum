package tryout.hibernate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import tryout.hibernate.enumeration.Anrede;
import tryout.hibernate.enumeration.USState;
import tryout.hibernate.model.Kunde;
import tryout.hibernate.model.USCityPersisted;

public class TryOutGenerateTableMain005 {
	private static final DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
	
	/**Zusätzlich aus dem Buchbeispiel "Java Persistence API 2.0: Chapter02_secondary-table" den "Kunden" übernommen und hier angepasst
	 * Einfache Enummeration wird hier dann auch zusätzlich verwendet 
	 * @param args
	 */
	public static void main(String[] args) {
		//HibernateContextProviderTHM objContextHibernate = new HibernateContextProviderTHM(this.getKernelObject());
		
		Configuration cfg = new Configuration();
		cfg.setProperty("hibernate.dialect","tryout.hibernate.dialect.SQLiteDialect" );
		cfg.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
		cfg.setProperty("hibernate.connection.url", "jdbc:sqlite:c:\\server\\SQLite\\TryOutEnumeration005.sqlite");
		cfg.setProperty("hibernate.connection.username", "");
		cfg.setProperty("hibernate.connection.password", "");
		cfg.setProperty("hibernate.hbm2ddl.auto", "create"); //! Damti wird die Datenbank und sogar die Tabellen darin automatisch NEU erstellt.
		cfg.setProperty("cache.provider_class", "org.hibernate.cache.NoCacheProvider");
		cfg.setProperty("current_session_context_class", "thread");
		
		//tryout.hibernate.enumeration.USCityPersisted
		cfg.addAnnotatedClass(USCityPersisted.class);
		
		
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		SessionFactory sf = cfg.buildSessionFactory(sr);
		Session hibernateSession = sf.openSession();
		
		hibernateSession.beginTransaction(); //ich hab gelesen, dass die Reihenfolge beginnTransaction und definition der zu pesistierenden Objekte egal ist

		USCityPersisted city01 = new USCityPersisted("Teststadt01", USState.ALABAMA);
		hibernateSession.save(city01);

		USCityPersisted city02 = new USCityPersisted("Teststadt02", USState.ALASKA);
		hibernateSession.save(city02);
				
		hibernateSession.getTransaction().commit();
		hibernateSession.close();
		
		
		//################## Aus dem Buch übernommene Vorgehensweise #################
		//Nun alternativ per EntityManager auf die gleiche Datenbank zugreifen.
		//ABER: Fehlermeldung: Exception in thread "main" javax.persistence.PersistenceException: No Persistence provider for EntityManager named TryOutEnumeration005.sqlite
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TryOutEnumeration005");//Das ist in persistence.xml der Eintrag unter dem Tag: persistence-unit
		
		//ABER: Fehlermeldung: Exception in thread "main" javax.persistence.PersistenceException: [PersistenceUnit: TryOutEnumeration005] Unable to configure EntityManagerFactory
		//Das lag daran, dass in hibernate.cfg.xml falsche klassen angegeben waren / Packagenamen falsch waren
		
		EntityManager em=null;	
		em = emf.createEntityManager();
		em.getTransaction().begin();
		Kunde kunde=null;
		try {
			kunde = new Kunde("Max", "Mustermann", format.parse("11.11.1911"), Anrede.HERR, "Am Wasserwerk 7", "12345", "Musterhausen");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		em.persist(kunde);
		em.getTransaction().commit();
		em.clear();
		emf.close();
		
	}

}
