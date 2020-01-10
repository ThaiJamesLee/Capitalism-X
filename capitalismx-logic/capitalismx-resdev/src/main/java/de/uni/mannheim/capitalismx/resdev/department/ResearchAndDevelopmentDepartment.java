package de.uni.mannheim.capitalismx.resdev.department;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.domain.department.LevelingMechanism;
import de.uni.mannheim.capitalismx.domain.exception.InconsistentLevelException;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.production.ProductCategory;
import de.uni.mannheim.capitalismx.resdev.skills.UnlockComponentSkill;
import de.uni.mannheim.capitalismx.resdev.skills.UnlockProductCategorySkill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ResearchAndDevelopmentDepartment.class);

    private static final String PROPERTIES_FILE = "resdev-module";
    private static final String MAX_LEVEL_PROPERTY = "resdev.department.max.level";

    private static final String COST_PROPERTY_PREFIX = "resdev.skill.cost.";
    private static final String UNLOCK_YEAR_PROPERTY_PREFIX = "resdev,skill.components.unlock.year.";

    private static final String UNLOCK_CATEGORY_DEFAULT_COST = "resdev.skill.captegory.cost.default";

    private static ResearchAndDevelopmentDepartment instance;

    private ResearchAndDevelopmentDepartment() {
        super("Research and Development");
        this.allCategoriesSkills = new ArrayList<>();
        this.allComponentSkills = new ConcurrentHashMap<>();
        this.unlockedProductCategorySkills = new ArrayList<>();
        init();
    }

    private void init() {
        int maxLevel = Integer.parseInt(ResourceBundle.getBundle(PROPERTIES_FILE).getString(MAX_LEVEL_PROPERTY));
        setMaxLevel(maxLevel);

        initProperties();
    }


    /**
     * Initialize the {@link de.uni.mannheim.capitalismx.domain.department.DepartmentSkill}s in this method.
     * Initialize the cost for leveling in this method
     */
    private void initProperties() {
        Map<Integer, Double> costMap = new ConcurrentHashMap<>();
        ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_FILE);

        for (int i=1; i<=getMaxLevel(); i++) {
            // init costs for leveling
            double cost = Integer.parseInt(bundle.getString(COST_PROPERTY_PREFIX + i));
            costMap.put(i, cost);

            // init skills
            int year = Integer.parseInt(bundle.getString(UNLOCK_YEAR_PROPERTY_PREFIX + i));
            skillMap.put(i, new UnlockComponentSkill(i, year));
        }
        try {
            setLevelingMechanism(new LevelingMechanism(this, costMap));
        } catch (InconsistentLevelException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void initUnlockProductCategorySkills() {
        ProductCategory[] productCategories = ProductCategory.values();
        ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_FILE);
        double cost = Integer.parseInt(bundle.getString(UNLOCK_CATEGORY_DEFAULT_COST));
        for(ProductCategory productCategory : productCategories) {

           // allCategoriesSkills.add(new UnlockProductCategorySkill())
        }
    }

    public static ResearchAndDevelopmentDepartment getInstance() {
        if(instance == null) {
            return new ResearchAndDevelopmentDepartment();
        }
        return instance;
    }

    /**
     * The instance returned by this class is independent from the singleton.
     * This is used for testing purposes.
     * @return Return an instance of this class.
     */
    public static ResearchAndDevelopmentDepartment createInstance() {
        return new ResearchAndDevelopmentDepartment();
    }

    public List<ProductCategory> getUnlockableProductCategories() {
        List<ProductCategory> unlockables = new ArrayList<>();

        return unlockables;
    }

    /**
     *
     * @param component The component to check.
     * @return Returns true, if the departments level is high enough to use the component.
     */
    public boolean isComponentUnlocked(Component component) {
        List<DepartmentSkill> unlockedSkills = getAvailableSkills();

        for(DepartmentSkill skill : unlockedSkills) {
            UnlockComponentSkill unlockComponentSkill = (UnlockComponentSkill) skill;

            if(unlockComponentSkill.getYear() >= component.getAvailabilityDate()) {
                return true;
            }
        }
        return false;
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
}
