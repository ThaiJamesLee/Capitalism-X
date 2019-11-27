package de.uni.mannheim.capitalismx.sales;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.sales.contracts.Contract;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportList;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the sales department. Leveling up this department will allow the user to get better
 * contracts and prices.
 *
 * @author duly
 */
public class SalesDepartment extends DepartmentImpl {

    private PropertyChangeSupportList activeContracts;


    public SalesDepartment(String name) {
        super(name);
        activeContracts = new PropertyChangeSupportList();
    }

    @Override
    public void registerPropertyChangeListener(PropertyChangeListener listener) {

    }

    public PropertyChangeSupportList getActiveContracts() {
        return activeContracts;
    }

    public void setActiveContracts(PropertyChangeSupportList activeContracts) {
        this.activeContracts = activeContracts;
    }

}
