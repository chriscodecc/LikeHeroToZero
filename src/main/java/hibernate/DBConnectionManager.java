package hibernate;

import java.util.Collection;
import java.util.Collections;

import javax.validation.constraints.Null;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import country.Country;
import country.CountryChange;
import usermanagement.User;

import org.hibernate.Transaction;

public class DBConnectionManager {
	
	//Configuration config;
	private static final SessionFactory sessionFactory_country = buildSessionFactory_country();
	private static final SessionFactory sessionFactory_user = buildSessionFactory_user();
	private static final SessionFactory sessionFactory_changes = buildSessionFactory_changes();
	
	public DBConnectionManager(){
		/**config = new Configuration();
		config.configure("hibernate.cfg.xml");
		config.addAnnotatedClass(Country.class);**/
	}
	
	/*
	 * This function builds the SessionFactory for the CO2 Emission Data
	 */
	private static SessionFactory buildSessionFactory_country() {
		Configuration config = new Configuration();
		
		try {
			config.addAnnotatedClass(Country.class);
			config.configure("hibernate.cfg.xml");
			return config.buildSessionFactory();
			
		} catch(Throwable e) {
			System.err.println("MySQL system is currently unreachable." );
		
			return null;
		}
	}
	
	/*
	 * This function builds the SessionFactory for user Data needed for the login
	 */
	private static SessionFactory buildSessionFactory_user() {
		
		Configuration config = new Configuration();
		
		try {
			config.addAnnotatedClass(User.class);
			config.configure("hibernate_user.cfg.xml");
			return config.buildSessionFactory();
		} catch(Throwable e) {
			System.err.println("MySQL system is currently unreachable." );
			
			return null;
		}
	}
	
	private static SessionFactory buildSessionFactory_changes() {
		
			Configuration config = new Configuration();
			
		try {
			config.addAnnotatedClass(CountryChange.class);
			config.configure("hibernate.cfg.xml");
			return config.buildSessionFactory();
		} catch(Throwable e) {
			System.err.println("MySQL system is currently unreachable." );
			
			return null;
		}
	}
	

	
	
	/*
	 * This function returns all existing CO2 Data for every Country 
	 * return Collection<Country>
	 */
	public Collection<Country> getAllCountries() {
		
		try{
			Session session = sessionFactory_country.openSession();
			
			session.beginTransaction();
			
			Collection<Country> countries = session.createQuery("from Country", Country.class).list();
			session.getTransaction().commit();
			
			return countries;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Collections.emptyList();
		}
	}
	
	public Collection<CountryChange> getAllCountries_changes() {
		
		try{
			Session session = sessionFactory_changes.openSession();
			
			session.beginTransaction();
			
			Collection<CountryChange> countries = session.createQuery("from CountryChange", CountryChange.class).list();
			session.getTransaction().commit();
			
			return countries;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Collections.emptyList();
		}
	}
	
	public static User checkUsers(String userName, String password){
		
		try {
			Session session = sessionFactory_user.openSession();
			session.beginTransaction();
			//HIER IST DER ERROR
			Collection<User> users = session.createQuery("from User", User.class).list();
			session.getTransaction().commit();
			
			for(User u: users) {
				System.out.println(u.getUserName() + " " + u.getPassword());
				if(u.getUserName().equals(userName) && u.getPassword().equals(password)) {
					return u;
				}
			}
			return null;
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}		
	}
	
	/*
	 * This function commit Co2 Data Changes to the database
	 */
	public void commitChanges(Country country) {
		Session session;
		Transaction trans = null;
		try{
			session = sessionFactory_country.openSession();
			trans = session.beginTransaction();
			session.merge(country);
			trans.commit();
			session.close();
			System.out.println("data commited");
		} catch (Exception e) {
			if(trans != null) {
				trans.rollback();
			}
			e.printStackTrace();
			System.out.println("rollback");
		}
	}
	
	public void commitToChangesTable(CountryChange country) {
		Session session;
		Transaction trans = null;
		try{
			session = sessionFactory_changes.openSession();
			trans = session.beginTransaction();
			session.merge(country);
			trans.commit();
			session.close();
			System.out.println("data commited change");
		} catch (Exception e) {
			if(trans != null) {
				trans.rollback();
			}
			e.printStackTrace();
		}
	}
	
	public void removeFromChangeTable(CountryChange cc) {
		Session session;
		Transaction trans = null;
		
		try {
			session = sessionFactory_changes.openSession();
			trans = session.beginTransaction();
			session.remove(cc);
			trans.commit();
			session.close();
			
		}catch (Exception e) {
			if(trans != null) {
				trans.rollback();
			}
			e.printStackTrace();
		}
	}
	
	
}
