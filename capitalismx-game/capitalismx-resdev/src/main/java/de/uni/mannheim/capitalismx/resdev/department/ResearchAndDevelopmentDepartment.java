package de.uni.mannheim.capitalismx.resdev.department;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.production.ProductCategory;

import java.util.ArrayList;
import java.util.List;

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

    /**
     *
     * @param category The productCategory of interest
     * @return Returns true, if category is unlocked, else return false.
     */
    public boolean isCategoryUnlocked(ProductCategory category) {
        return unlockedCategories.contains(category);
    }
}
