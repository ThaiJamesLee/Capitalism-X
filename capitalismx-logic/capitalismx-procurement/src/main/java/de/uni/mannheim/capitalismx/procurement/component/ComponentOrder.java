package de.uni.mannheim.capitalismx.procurement.component;

import java.io.Serializable;
import java.time.LocalDate;

public class ComponentOrder implements Serializable {

    private LocalDate orderDate;
    private LocalDate arrivalDate;
    private Component orderedComponent;
    private int orderedQuantity;

    private static final int DELIVERY_TIME = 3;

    public ComponentOrder(LocalDate orderDate, Component orderedComponent, int orderedQuantity) {
        this.orderDate = orderDate;
        //TODO delivery time in utils?
        this.arrivalDate = orderDate.plusDays(DELIVERY_TIME);
        this.orderedComponent = orderedComponent;
        this.orderedQuantity = orderedQuantity;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    public LocalDate getArrivalDate() {
        return this.arrivalDate;
    }

    public Component getOrderedComponent() {
        return this.orderedComponent;
    }

    public int getOrderedQuantity() {
        return this.orderedQuantity;
    }
}
