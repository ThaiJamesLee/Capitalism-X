package de.uni.mannheim.capitalismx.procurement.component;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportList;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProcurementDepartment extends DepartmentImpl {

    private static ProcurementDepartment instance;
    private List<ComponentType> allAvailableComponents;
    private List<ComponentOrder> componentOrders;
    private Map<Component, Integer> receivedComponents;
    //private Map<Component, Integer> orderedComponents;
    private static final int DELIVERY_TIME = 3;

    private PropertyChangeSupportList<ComponentOrder> componentOrdersChange;

    private ProcurementDepartment() {
        super("Procurement");
        this.allAvailableComponents = new CopyOnWriteArrayList<>();
        this.componentOrders = new CopyOnWriteArrayList<>();
        this.receivedComponents = new ConcurrentHashMap<>();
        //this.orderedComponents = new HashMap<>();

        this.componentOrdersChange = new PropertyChangeSupportList();
        this.componentOrdersChange.setList(this.componentOrders);
        this.componentOrdersChange.setAddPropertyName("componentOrdersChange");
    }

    public static synchronized  ProcurementDepartment getInstance() {
        if(ProcurementDepartment.instance == null) {
            ProcurementDepartment.instance = new ProcurementDepartment();
        }
        return ProcurementDepartment.instance;
    }

    public List<ComponentType> getAllAvailableComponents(LocalDate gameDate) {
        List<ComponentType> allAvailableComponents = new ArrayList<>();
        ComponentType[] allComponents = ComponentType.values();
        for(int i = 0; i < allComponents.length; i++) {
            if(allComponents[i].getAvailabilityDate() <= gameDate.getYear()) {
                allAvailableComponents.add(allComponents[i]);
            }
        }
        this.allAvailableComponents = allAvailableComponents;
        return this.allAvailableComponents;
    }

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

    /*public double buyComponents(LocalDate gameDate, Component component, int quantity, int freeStorage) {
        if (freeStorage >= quantity) {
            int newQuantity = quantity;
            for(HashMap.Entry<Component, Integer> entry : this.orderedComponents.entrySet()) {
                if(component == entry.getKey()) {
                    newQuantity += this.orderedComponents.get(component);
                }
            }
            this.orderedComponents.put(component, newQuantity);
        }
        return quantity * component.calculateBaseCost(gameDate);
    }*/

    public double buyComponents(LocalDate gameDate, Component component, int quantity, int freeStorage) {
        if (freeStorage >= (quantity + this.getQuantityOfOrderedComponents())) {
            ComponentOrder componentOrder = new ComponentOrder(gameDate, component, quantity);
            this.componentOrders.add(componentOrder);
        }
        return quantity * component.getBaseCost();
    }

    public void receiveComponents(LocalDate gameDate) {
        for(ComponentOrder componentOrder : this.componentOrders) {
            if(gameDate.equals(componentOrder.getOrderDate().plusDays(DELIVERY_TIME))) {
                int newQuantity = componentOrder.getOrderedQuantity();
                for(Map.Entry<Component, Integer> entry : this.receivedComponents.entrySet()) {
                    if(entry.getKey() == componentOrder.getOrderedComponent()) {
                        newQuantity += entry.getValue();
                    }
                }
                this.receivedComponents.put(componentOrder.getOrderedComponent(), newQuantity);
                this.componentOrders.remove(componentOrder);
            }
        }
    }

    public int getQuantityOfOrderedComponents() {
        int orderedQuantities = 0;
        for(ComponentOrder componentOrder : this.componentOrders) {
            orderedQuantities += componentOrder.getOrderedQuantity();
        }
        return orderedQuantities;
    }

    public void clearReceivedComponents() {
        this.receivedComponents.clear();
    }

    public Map<Component, Integer> getReceivedComponents() {
        return this.receivedComponents;
    }

    public List<ComponentOrder> getComponentOrders() {
        return this.componentOrders;
    }

    public void updateAll(LocalDate gameDate) {
        this.receiveComponents(gameDate);
    }

    public PropertyChangeSupportList getComponentOrdersChange() {
        return componentOrdersChange;
    }

    public static void setInstance(ProcurementDepartment instance) {
        ProcurementDepartment.instance = instance;
    }

    public static ProcurementDepartment createInstance() {
        return new ProcurementDepartment();
    }

    @Override
    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        this.componentOrdersChange.addPropertyChangeListener(listener);
    }
}
