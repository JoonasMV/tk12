package dao;

import jakarta.persistence.*;

import entity.*;
import jakarta.persistence.criteria.*;

import java.math.BigDecimal;
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

		String statement = "UPDATE SalesEvent SET amount = amount + " + new BigDecimal(amount);
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

	//	a method that retrieves sales events whose amount is less than a given
//	threshold (e.g. 20 euros). For each event, print the event data accompanied with
//	the register data related to that event.

	public List<SalesEvent> getSalesLessThanCriteria(float amount){
		EntityManager em = emf.createEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<SalesEvent> cq = cb.createQuery(SalesEvent.class);
		Root<SalesEvent> salesEventRoot = cq.from(SalesEvent.class);
		cq.select(salesEventRoot);
		cq.where(cb.lt(salesEventRoot.get(SalesEvent_.amount), amount));
		TypedQuery<SalesEvent> q = em.createQuery(cq);
		return q.getResultList();
	}

	//	a method that adds a service fee into all sales events
	public int addServiceFeeCriteria(float amount) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<SalesEvent> cu = cb.createCriteriaUpdate(SalesEvent.class);
		Root<SalesEvent> salesEventRoot = cu.from(SalesEvent.class);
		cu.set("amount", cb.sum(salesEventRoot.get("amount"), amount));

		int result = em.createQuery(cu).executeUpdate();
		em.getTransaction().commit();
		return result;
	}

//	//	a method that deletes all sales events.
	public int deleteAllSalesEventsCriteria() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<SalesEvent> delete = cb.createCriteriaDelete(SalesEvent.class);

		int affected = em.createQuery(delete).executeUpdate();
		em.getTransaction().commit();
		return affected;
	}
//	DELETE FROM salesevent;

}
