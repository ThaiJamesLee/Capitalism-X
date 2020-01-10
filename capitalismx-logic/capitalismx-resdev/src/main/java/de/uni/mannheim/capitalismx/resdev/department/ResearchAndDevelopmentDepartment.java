package de.uni.mannheim.capitalismx.resdev.department;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.domain.department.LevelingMechanism;
import de.uni.mannheim.capitalismx.production.ProductCategory;
import de.uni.mannheim.capitalismx.resdev.skills.UnlockProductCategorySkill;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Research and Development department.
 * This department must be leveled up to use unlocked components.
 * Additionally, this department leveling is required to unlock product categories.
 * @author duly
 */
public class ResearchAndDevelopmentDepartment extends DepartmentImpl {

    /**
     * List of all {@link UnlockProductCategorySkill}.
     */
    private List<UnlockProductCategorySkill> allCategoriesSkills;

    /**
     * List of unlocked {@link UnlockProductCategorySkill}s.
     */
    private List<UnlockProductCategorySkill> unlockedProductCategorySkills;

    /**
     * Map that contains all {@link de.uni.mannheim.capitalismx.resdev.skills.UnlockComponentSkill}s.
     */
    private Map<Integer, DepartmentSkill> allComponentSkills;


    /**
     * Map that contains unlocked component skills.
     */
    private Map<Integer, DepartmentSkill> unlockedComponentSkill;

    private static final String PROPERTIES_FILE = "resdev-module";
    private static final String MAX_LEVEL_PROPERTY = "resdev.department.max.level";


    public ResearchAndDevelopmentDepartment() {
        super("Research and Development");
        this.allCategoriesSkills = new ArrayList<>();
        this.allComponentSkills = new ConcurrentHashMap<>();
        this.unlockedProductCategorySkills = new ArrayList<>();
        this.unlockedComponentSkill = new ConcurrentHashMap<>();
        init();
    }

    private void init() {
        initSkills();
    }

    /**
     * Initialize the {@link de.uni.mannheim.capitalismx.domain.department.DepartmentSkill}s in this method.
     */
    private void initSkills() {

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
        return allCategoriesSkills.contains(category);
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

    /**
     *
     * @return This method is not supported in this class.
     *
     * @throws UnsupportedOperationException Not supported in this class.
     */
    @Override
    public LevelingMechanism getLevelingMechanism() {
        throw new UnsupportedOperationException();
    }
}
