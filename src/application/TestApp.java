package application;

import dao.Dao;
import entity.SalesEvent;

import java.util.List;

public class TestApp {
    public static void main(String[] args) {
        Dao dao = new Dao();

        List<SalesEvent> test = dao.getSalesLessThan(20);

        for (SalesEvent s: test) {
            System.out.println(s.getAmount());
        }
        System.out.println(test.size());
    }
}
