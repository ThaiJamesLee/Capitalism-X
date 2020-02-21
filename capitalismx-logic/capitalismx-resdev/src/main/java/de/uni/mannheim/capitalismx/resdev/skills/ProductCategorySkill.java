package de.uni.mannheim.capitalismx.resdev.skills;

import de.uni.mannheim.capitalismx.production.ProductCategory;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This skill is used to unlock a product category.
 * @author duly
 *
 * @since 1.0.0
 */
public class ProductCategorySkill extends ResDevSkillImpl {

    private ProductCategory unlockedProductCategory;

    private static final String PROPERTIES_FILE = "resdev-module";
    private static final String DESCRIPTION_PROPERTY_PREFIX = "resdev.skill.category.description.";

    public ProductCategorySkill(double cost) {
        super(cost);
    }

    @Override
    public String getDescription() {
        ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_FILE);
        return bundle.getString(DESCRIPTION_PROPERTY_PREFIX + unlockedProductCategory.toString().replace(" ",  "_"));
    }

    @Override
    public String getDescription(Locale l) {
        ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_FILE, l);
        return bundle.getString(DESCRIPTION_PROPERTY_PREFIX + unlockedProductCategory.toString().replace(" ",  "_"));
    }

    /**
     *
     * @return Not supported!
     * @throws UnsupportedOperationException This method is not supported in this class.
     */
    @Override
    public int getLevel() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param productCategory Set the {@link ProductCategory} to unlock with this skill.
     * @return Returns this instance.
     */
    public ProductCategorySkill setUnlockedProductCategory(ProductCategory productCategory) {
        this.unlockedProductCategory = productCategory;
        return this;
    }

    /**
     *
     * @return Returns the {@link ProductCategory} that this skill unlocks.
     */
    public ProductCategory getUnlockedProductCategory() {
        return unlockedProductCategory;
    }
}
