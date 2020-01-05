package de.uni.mannheim.capitalismx.resdev.department;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.production.ProductCategory;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Research and Development department.
 * This department must be leveled up to use unlocked components.
 * Additionally, this department leveling is required to unlock product categories.
 * @author duly
 */
public class ResearchAndDevelopmentDepartment extends DepartmentImpl {


    private List<ProductCategory> unlockedCategories;



    public ResearchAndDevelopmentDepartment() {
        super("Research and Development");
        this.unlockedCategories = new ArrayList<>();
        init();
    }

    private void init() {

    }

    /**
     * Add Product Category to the unlocked categories.
     * @param category The {@link ProductCategory} to unlock.
     */
    public void unlockProductCategory(ProductCategory category) {
        if(!unlockedCategories.contains(category)) {
            unlockedCategories.add(category);
        }
    }

    public List<ProductCategory> getUnlockableProductCategories() {
        List<ProductCategory> unlockables = new ArrayList<>();

        /*
            TODO
         */


        return unlockables;
    }

    /**
     *
     * @param category The productCategory of interest
     * @return Returns true, if category is unlocked, else return false.
     */
    public boolean isCategoryUnlocked(ProductCategory category) {
        return unlockedCategories.contains(category);
    }

    /**
     * This method is not supported.
     * @throws UnsupportedOperationException This department does not contain PropertyChangeSupport variables.
     * @param listener A PropertyChangeListener.
     */
    @Override
    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        throw new UnsupportedOperationException();
    }
}