package entity;

import entity.Register;
import entity.SalesEvent;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SalesEvent.class)
public class SalesEvent_ {
    public static volatile SingularAttribute<SalesEvent, Integer> eventId;
    public static volatile SingularAttribute<SalesEvent, Register> register;
    public static volatile SingularAttribute<SalesEvent, Double> amount;
}