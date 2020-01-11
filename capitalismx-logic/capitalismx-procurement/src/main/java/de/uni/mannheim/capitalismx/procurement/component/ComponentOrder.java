package de.uni.mannheim.capitalismx.procurement.component;

import java.time.LocalDate;

public class ComponentOrder {

    private LocalDate orderDate;
    private Component orderedComponent;
    private int orderedQuantity;

    public ComponentOrder(LocalDate orderDate, Component orderedComponent, int orderedQuantity) {
        this.orderDate = orderDate;
        this.orderedComponent = orderedComponent;
        this.orderedQuantity = orderedQuantity;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    public Component getOrderedComponent() {
        return this.orderedComponent;
    }

    public int getOrderedQuantity() {
        return this.orderedQuantity;
    }
}
