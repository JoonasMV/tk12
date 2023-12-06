package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import entity.*;
import java.util.*;

public class Dao {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Harj1PU");

	
	public void addRegister(Register reg) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
        em.persist(reg);
        
        em.getTransaction().commit();
        em.close();
	}
	
	public void addEvent(int eventNumber, int regNumber, double amount) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
        Register reg = em.find(Register.class, regNumber);
        SalesEvent evt = new SalesEvent(eventNumber, reg, amount);
        
        em.persist(evt);
        
        em.getTransaction().commit();
        em.close();	
	}
	
	public List<SalesEvent> retrieveSmallSales(double limit) {
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<SalesEvent> result = null;
		// logic goes here
		
		
		em.getTransaction().commit();
		em.close();
		return result;
	}

//	a method that retrieves sales events whose amount is less than a given
//	threshold (e.g. 20 euros). For each event, print the event data accompanied with
//	the register data related to that event.

	public List<SalesEvent> getSalesLessThan(float amount){
		EntityManager em = emf.createEntityManager();

		String statement = "SELECT s FROM SalesEvent s WHERE s.amount < " + amount;
		Query query = em.createQuery(statement);
		List<SalesEvent> result = query.getResultList();

		em.close();
		return result;
	}

//	a method that adds a service fee into all sales events
	public int addServiceFee(float amount) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		String statement = "UPDATE SalesEvent SET amount = amount + " + amount;
		Query query = em.createQuery(statement);
		int affected = query.executeUpdate();

		em.getTransaction().commit();
		em.close();
		return affected;
	}

//	a method that deletes all sales events.
	public int deleteAllSalesEvents() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		String statement = "DELETE FROM SalesEvent";
		Query query = em.createQuery(statement);
		int affected = query.executeUpdate();

		em.getTransaction().commit();
		em.close();
		return affected;

	}
//	DELETE FROM salesevent;

}
