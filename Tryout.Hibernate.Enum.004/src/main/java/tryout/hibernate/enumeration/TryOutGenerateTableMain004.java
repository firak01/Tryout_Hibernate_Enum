package tryout.hibernate.enumeration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class TryOutGenerateTableMain004 {

	/**Im Unterschied zum Enum.003 Beispiel, wird hier der Wert für 
	 * @param args
	 */
	public static void main(String[] args) {
		//HibernateContextProviderTHM objContextHibernate = new HibernateContextProviderTHM(this.getKernelObject());
		
		Configuration cfg = new Configuration();
		cfg.setProperty("hibernate.dialect","tryout.hibernate.enumeration.SQLiteDialect" );
		cfg.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
		cfg.setProperty("hibernate.connection.url", "jdbc:sqlite:c:\\server\\SQLite\\TryOutEnumeration004.sqlite");
		cfg.setProperty("hibernate.connection.username", "");
		cfg.setProperty("hibernate.connection.password", "");
		cfg.setProperty("hibernate.hbm2ddl.auto", "create"); //! Damti wird die Datenbank und sogar die Tabellen darin automatisch NEU erstellt.
		cfg.setProperty("cache.provider_class", "org.hibernate.cache.NoCacheProvider");
		cfg.setProperty("current_session_context_class", "thread");
		
		//tryout.hibernate.enumeration.USCityPersisted
		cfg.addAnnotatedClass(USCityPersisted004.class);
		
		
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		SessionFactory sf = cfg.buildSessionFactory(sr);
		Session hibernateSession = sf.openSession();
		
		hibernateSession.beginTransaction(); //ich hab gelesen, dass die Reihenfolge beginnTransaction und definition der zu pesistierenden Objekte egal ist

		USCityPersisted004 city01 = new USCityPersisted004("Teststadt01", USState004.ALABAMA);
		hibernateSession.save(city01);

		USCityPersisted004 city02 = new USCityPersisted004("Teststadt02", USState004.ALASKA);
		hibernateSession.save(city02);
				
		hibernateSession.getTransaction().commit();
		hibernateSession.close();
		
	}

}
