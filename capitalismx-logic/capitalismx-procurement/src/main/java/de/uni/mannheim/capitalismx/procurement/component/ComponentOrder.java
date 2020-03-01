package de.uni.mannheim.capitalismx.procurement.component;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A component order.
 * It is created when components are ordered.
 * It has an order date and an arrival date, which is based on the order date and the delivery time in days.
 * It also includes information about which component was ordered and in which quantity.
 *
 * @author dzhao
 */
public class ComponentOrder implements Serializable {

    private LocalDate orderDate;
    private LocalDate arrivalDate;
    private Component orderedComponent;
    private int orderedQuantity;

    /**
     * The time it takes for an order to be delivered.
     */
    public static final int DELIVERY_TIME = 3;

    /**
     * Instantiates a new component order.
     * An arrival date set based on the order date and the delivery time.
     *
     * @param orderDate        the order date
     * @param orderedComponent the ordered component
     * @param orderedQuantity  the ordered quantity
     */
    public ComponentOrder(LocalDate orderDate, Component orderedComponent, int orderedQuantity) {
        this.orderDate = orderDate;
        this.arrivalDate = orderDate.plusDays(DELIVERY_TIME);
        this.orderedComponent = orderedComponent;
        this.orderedQuantity = orderedQuantity;
    }

    /**
     * Gets order date.
     *
     * @return the order date
     */
    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    /**
     * Gets arrival date.
     *
     * @return the arrival date
     */
    public LocalDate getArrivalDate() {
        return this.arrivalDate;
    }

    /**
     * Gets ordered component.
     *
     * @return the ordered component
     */
    public Component getOrderedComponent() {
        return this.orderedComponent;
    }

    /**
     * Gets ordered quantity.
     *
     * @return the ordered quantity
     */
    public int getOrderedQuantity() {
        return this.orderedQuantity;
    }
}
