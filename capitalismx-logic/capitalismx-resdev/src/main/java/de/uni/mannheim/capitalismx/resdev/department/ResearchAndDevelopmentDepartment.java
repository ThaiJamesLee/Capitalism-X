package de.uni.mannheim.capitalismx.resdev.department;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.domain.department.LevelingMechanism;
import de.uni.mannheim.capitalismx.domain.exception.InconsistentLevelException;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.production.product.ProductCategory;
import de.uni.mannheim.capitalismx.resdev.skills.ComponentSkill;
import de.uni.mannheim.capitalismx.resdev.skills.ProductCategorySkill;
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
     * List of all {@link ProductCategorySkill}.
     */
    private List<ProductCategorySkill> allProductCategoriesSkills;

    /**
     * List of unlocked {@link ProductCategorySkill}s.
     */
    private List<ProductCategorySkill> unlockedProductCategorySkills;


    private static final Logger LOGGER = LoggerFactory.getLogger(ResearchAndDevelopmentDepartment.class);

    private static final String PROPERTIES_FILE = "resdev-module";
    private static final String MAX_LEVEL_PROPERTY = "resdev.department.max.level";
    private static final String DEFAULT_UNLOCKED_PRODUCT_CATEGORY = "resdev.department.default.product.category";

    private static final String COST_PROPERTY_PREFIX = "resdev.skill.cost.";
    private static final String UNLOCK_YEAR_PROPERTY_PREFIX = "resdev,skill.components.unlock.year.";

    private static final String UNLOCK_CATEGORY_DEFAULT_COST = "resdev.skill.category.cost.default";

    private static ResearchAndDevelopmentDepartment instance;

    private ResearchAndDevelopmentDepartment() {
        super("Research and Development");
        this.allProductCategoriesSkills = new ArrayList<>();
        this.unlockedProductCategorySkills = new ArrayList<>();
        init();
    }

    private void init() {
        ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_FILE);
        int maxLevel = Integer.parseInt(bundle.getString(MAX_LEVEL_PROPERTY));
        setMaxLevel(maxLevel);

        initProperties();
        initProductCategorySkills();
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
            skillMap.put(i, new ComponentSkill(i, year));
        }
        try {
            setLevelingMechanism(new LevelingMechanism(this, costMap));
        } catch (InconsistentLevelException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void initProductCategorySkills() {
        // init ProductCategory skills.
        ProductCategory[] productCategories = ProductCategory.values();
        ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_FILE);

        String productCategoryName = bundle.getString(DEFAULT_UNLOCKED_PRODUCT_CATEGORY);
        ProductCategory defaultProductCategory = ProductCategory.getProductCategoryByName(productCategoryName);

        double cost = Integer.parseInt(bundle.getString(UNLOCK_CATEGORY_DEFAULT_COST));

        for(ProductCategory productCategory : productCategories) {
            ProductCategorySkill skill = new ProductCategorySkill(cost).setUnlockedProductCategory(productCategory);
            allProductCategoriesSkills.add(skill);

            // add the skill to the unlocked skill, if it is the default skill
            if(productCategory.equals(defaultProductCategory)) {
                unlockedProductCategorySkills.add(skill);
            }
        }
    }

    public static ResearchAndDevelopmentDepartment getInstance() {
        if(instance == null) {
            return new ResearchAndDevelopmentDepartment();
        }
        return instance;
    }

    public static void setInstance(ResearchAndDevelopmentDepartment instance) {
        ResearchAndDevelopmentDepartment.instance = instance;
    }

    /**
     * The instance returned by this class is independent from the singleton.
     * This is used for testing purposes.
     * @return Return an instance of this class.
     */
    public static ResearchAndDevelopmentDepartment createInstance() {
        return new ResearchAndDevelopmentDepartment();
    }


    /**
     *
     * @param component The component to check.
     * @return Returns true, if the departments level is high enough to use the component.
     */
    public boolean isComponentUnlocked(Component component) {
        List<DepartmentSkill> unlockedSkills = getAvailableSkills();

        for(DepartmentSkill skill : unlockedSkills) {
            ComponentSkill componentSkill = (ComponentSkill) skill;

            if(componentSkill.getYear() >= component.getAvailabilityDate()) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return Returns all {@link ProductCategorySkill}s.
     */
    public List<ProductCategorySkill> getAllProductCategoriesSkills() {
        return allProductCategoriesSkills;
    }

    /**
     *
     * @return Returns the list of unlocked
     */
    public List<ProductCategorySkill> getUnlockedProductCategorySkills() {
        return unlockedProductCategorySkills;
    }

    /**
     *
     * @param productCategory The {@link ProductCategory} to unlock.
     * @return Returns the cost, if it was unlocked. Returns if no matching skill or already unlocked.
     */
    public Double unlockProductCategory(ProductCategory productCategory) {
        for(ProductCategorySkill productCategorySkill : allProductCategoriesSkills) {
            if(productCategorySkill.getUnlockedProductCategory().equals(productCategory) && !unlockedProductCategorySkills.contains(productCategorySkill)) {
                unlockedProductCategorySkills.add(productCategorySkill);
                return productCategorySkill.getCost();
            }
        }
        return null;
    }

    /**
     *
     * @param category The productCategory of interest
     * @return Returns true, if category is unlocked, else return false.
     */
    public boolean isCategoryUnlocked(ProductCategory category) {
       for(ProductCategorySkill skill : unlockedProductCategorySkills) {
           if (skill.getUnlockedProductCategory().equals(category)) {
               return true;
           }
       }
       return false;
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
