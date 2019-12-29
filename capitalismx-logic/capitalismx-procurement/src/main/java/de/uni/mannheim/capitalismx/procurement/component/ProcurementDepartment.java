package de.uni.mannheim.capitalismx.procurement.component;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcurementDepartment extends DepartmentImpl {

    private static ProcurementDepartment instance;
    private List<ComponentType> allAvailableComponents;
    private Map<Component, Integer> orderedComponents;

    private ProcurementDepartment() {
        super("Procurement");
        this.allAvailableComponents = new ArrayList<>();
        this.orderedComponents = new HashMap<>();
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

    public double buyComponents(LocalDate gameDate, Component component, int quantity, int freeStorage) {
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
    }

    public void clearOrderedComponents() {
        this.orderedComponents.clear();
    }

    public Map<Component, Integer> getOrderedComponents() {
        return this.orderedComponents;
    }

    @Override
    public void registerPropertyChangeListener(PropertyChangeListener listener) {

    }
}
