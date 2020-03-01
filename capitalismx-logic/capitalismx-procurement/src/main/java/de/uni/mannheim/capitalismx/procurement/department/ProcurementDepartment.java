package de.uni.mannheim.capitalismx.procurement.department;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentCategory;
import de.uni.mannheim.capitalismx.procurement.component.ComponentOrder;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportList;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The class represents the procurement department.
 * It implements the ordering and reception of components.
 * Based on the report of predecessor group on p.31 - 35
 *
 * @author dzhao
 */
public class ProcurementDepartment extends DepartmentImpl {

    /**
     * The singleton pointer.
     */
    private static ProcurementDepartment instance;

    /**
     * All components which have a availability date that was in the past.
     */
    private List<ComponentType> allAvailableComponents;

    /**
     * A list of the component orders.
     */
    private List<ComponentOrder> componentOrders;

    /**
     * The received components.
     */
    private Map<Component, Integer> receivedComponents;

    /**
     * Notifies the GUI about updates to the component orders.
     */
    private PropertyChangeSupportList<ComponentOrder> componentOrdersChange;

    /**
     * Instantiates the procurement department.
     * Initializes all variables, including the PropertyChangeSupport variables.
     *
     */
    private ProcurementDepartment() {
        super ("Procurement");
        this.allAvailableComponents = new CopyOnWriteArrayList<>();
        this.componentOrders = new CopyOnWriteArrayList<>();
        this.receivedComponents = new ConcurrentHashMap<>();

        this.componentOrdersChange = new PropertyChangeSupportList();
        this.componentOrdersChange.setList(this.componentOrders);
        this.componentOrdersChange.setAddPropertyName("componentOrdersChange");
    }

    /**
     * Gets the singleton.
     *
     * @return the singleton instance
     */
    public static synchronized  ProcurementDepartment getInstance() {
        if (ProcurementDepartment.instance == null) {
            ProcurementDepartment.instance = new ProcurementDepartment();
        }
        return ProcurementDepartment.instance;
    }

    /**
     * Gets all available components based on availability date.
     *
     * @param gameDate the game date
     * @return list of all available components
     */
    public List<ComponentType> getAllAvailableComponents(LocalDate gameDate) {
        List<ComponentType> allAvailableComponents = new ArrayList<>();
        ComponentType[] allComponents = ComponentType.values();
        for (int i = 0; i < allComponents.length; i++) {
            if(allComponents[i].getAvailabilityDate() <= gameDate.getYear()) {
                allAvailableComponents.add(allComponents[i]);
            }
        }
        this.allAvailableComponents = allAvailableComponents;
        return this.allAvailableComponents;
    }

    /**
     * Gets available components of a component category based on availability date.
     *
     * @param gameDate          the game date
     * @param componentCategory the component category
     * @return list of the available components of component category
     */
    public List<ComponentType> getAvailableComponentsOfComponentCategory(LocalDate gameDate, ComponentCategory componentCategory) {
        List<ComponentType> availableComponentsOfComponentType = new ArrayList<>();
        this.getAllAvailableComponents(gameDate);
        for (ComponentType component : this.allAvailableComponents) {
            if (component.getComponentCategory() == componentCategory) {
                availableComponentsOfComponentType.add(component);
            }
        }
        return availableComponentsOfComponentType;
    }

    /**
     * Buys components and orders them.
     * It also checks whether there is enough free storage considering the already ordered components.
     *
     * @param gameDate    the game date
     * @param component   the component
     * @param quantity    the quantity
     * @param freeStorage the free storage
     * @return the quantity times the component base cost.
     */
    public double buyComponents(LocalDate gameDate, Component component, int quantity, int freeStorage) {
        if (freeStorage >= (quantity + this.getQuantityOfOrderedComponents())) {
            ComponentOrder componentOrder = new ComponentOrder(gameDate, component, quantity);
            this.componentOrders.add(componentOrder);
        }
        return quantity * component.getBaseCost();
    }

    /**
     * Receives the components of the component orders.
     * It checks whether the the arrival date is met and adds them to receivedComponents.
     *
     * @param gameDate the game date
     */
    public void receiveComponents(LocalDate gameDate) {
        for (ComponentOrder componentOrder : this.componentOrders) {
            if (gameDate.equals(componentOrder.getArrivalDate())) {
                int newQuantity = componentOrder.getOrderedQuantity();
                for (Map.Entry<Component, Integer> entry : this.receivedComponents.entrySet()) {
                    if (entry.getKey().sameComponent(componentOrder.getOrderedComponent())) {
                        newQuantity += entry.getValue();
                    }
                }
                this.receivedComponents.put(componentOrder.getOrderedComponent(), newQuantity);
                this.componentOrders.remove(componentOrder);
            }
        }
    }

    /**
     * Gets quantity of ordered components.
     *
     * @return the quantity of ordered components
     */
    public int getQuantityOfOrderedComponents() {
        int orderedQuantities = 0;
        for (ComponentOrder componentOrder : this.componentOrders) {
            orderedQuantities += componentOrder.getOrderedQuantity();
        }
        return orderedQuantities;
    }

    /**
     * Clear received components from the receivedComponents HashMap.
     */
    public void clearReceivedComponents() {
        this.receivedComponents.clear();
    }

    /**
     * Gets received components.
     *
     * @return the received components
     */
    public Map<Component, Integer> getReceivedComponents() {
        return this.receivedComponents;
    }

    /**
     * Gets component orders.
     *
     * @return the component orders
     */
    public List<ComponentOrder> getComponentOrders() {
        return this.componentOrders;
    }

    /**
     * Update all.
     *
     * @param gameDate the game date
     */
    public void updateAll(LocalDate gameDate) {
        this.receiveComponents(gameDate);
    }

    /**
     * Gets component orders change.
     *
     * @return the component orders change
     */
    public PropertyChangeSupportList getComponentOrdersChange() {
        return componentOrdersChange;
    }

    /**
     * Sets instance.
     *
     * @param instance the instance
     */
    public static void setInstance(ProcurementDepartment instance) {
        ProcurementDepartment.instance = instance;
    }

    /**
     * Create instance of procurement department.
     *
     * @return the procurement department
     */
    public static ProcurementDepartment createInstance() {
        return new ProcurementDepartment();
    }

    @Override
    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        this.componentOrdersChange.addPropertyChangeListener(listener);
    }
}
