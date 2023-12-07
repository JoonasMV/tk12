package application;

import entity.*;
import dao.*;
import java.util.List;

public class KassaApp {

	
	public static void initDB(Dao dao) {
		final int NUM_REGISTERS = 5;
		final int NUM_EVENTS = 10;
		// initialize registers
		for (int i = 1; i<=NUM_REGISTERS; i++) {
			Register reg = new Register(i, "Kassapiste_" + i);
			dao.addRegister(reg);
		}
		// initialize events
		for (int i = 1; i<=NUM_EVENTS; i++) {
			int regnum = (int)(Math.random()*NUM_REGISTERS)+1;
			// amount between 1.00 - 99.99
			double amount = (int)(100+Math.random()*9900)/100.0;
			dao.addEvent(i, regnum, amount);			
		}
	}
	
    public static void main(String[] args) {
    	
    	Dao dao = new Dao();
    	initDB(dao);
 
    	//  1a
    	
    	List<SalesEvent> result = dao.retrieveSmallSales(50.0);
    	if (result!=null) {
    		for (SalesEvent s : result) {
    			System.out.println(s + " at register " + s.getRegister());
    		}
    	}


//		jpqlOperations(dao);
		criteriaOperations(dao);
	}

	private static void jpqlOperations(Dao dao) {

		List<SalesEvent> beforeFee = dao.getSalesLessThan(50);

		System.out.println("Before update");
		for (SalesEvent s: beforeFee) {
			System.out.println(s);
		}

		int amountAffected =  dao.addServiceFee(10);

		List<SalesEvent> afterFee = dao.getSalesLessThan(50);

		System.out.println("After update");
		System.out.println("Updated values " + amountAffected);
		for (SalesEvent s: afterFee) {
			System.out.println(s);
		}

		int rowsDeleted = dao.deleteAllSalesEvents();
		System.out.println("Deleted rows " + rowsDeleted);
	}

	private static void criteriaOperations(Dao dao) {

		List<SalesEvent> beforeFeeCriteria = dao.getSalesLessThanCriteria(50);
		System.out.println("Before update");
		for (SalesEvent s: beforeFeeCriteria) {
			System.out.println(s);
		}

		int updatedValues = dao.addServiceFeeCriteria(10);


		List<SalesEvent> afterFeeCriteria = dao.getSalesLessThanCriteria(50);

		System.out.println("After update");
		System.out.println("Updated values " + updatedValues);
		for (SalesEvent s: afterFeeCriteria) {
			System.out.println(s);
		}

		int deletedRowsCriteria = dao.deleteAllSalesEventsCriteria();
		System.out.println("Deleted rows " + deletedRowsCriteria);
	}
}

