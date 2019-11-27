package de.uni.mannheim.capitalismx.resdev.department;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.production.ProductCategory;
import de.uni.mannheim.capitalismx.resdev.department.skills.ResDevSkillImpl;

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
        Map<Integer, DepartmentSkill> skillMap = getSkillMap();
        skillMap.put(1, new ResDevSkillImpl(1, 1990));
        skillMap.put(2, new ResDevSkillImpl(2, 1990));
        skillMap.put(3, new ResDevSkillImpl(3, 1990));
        skillMap.put(4, new ResDevSkillImpl(4, 1990));
        skillMap.put(5, new ResDevSkillImpl(5, 1990));
        skillMap.put(6, new ResDevSkillImpl(6, 1990));
        skillMap.put(7, new ResDevSkillImpl(7, 1990));
        skillMap.put(8, new ResDevSkillImpl(8, 1990));
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
}
