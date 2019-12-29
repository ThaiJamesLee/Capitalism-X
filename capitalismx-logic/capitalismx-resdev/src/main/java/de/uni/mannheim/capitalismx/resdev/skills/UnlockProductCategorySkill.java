package de.uni.mannheim.capitalismx.resdev.skills;

import de.uni.mannheim.capitalismx.production.ProductCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duly
 *
 * @since 1.0.0
 */
public class UnlockProductCategorySkill extends ResDevSkillImpl {

    private List<ProductCategory> unlockedProductCategories;

    public UnlockProductCategorySkill(int level, int year) {
        super(level, year);
        unlockedProductCategories = new ArrayList<>();
    }

    public List<ProductCategory> getUnlockedProductCategories() {
        return unlockedProductCategories;
    }

    public void setUnlockedProductCategories(List<ProductCategory> unlockedProductCategories) {
        this.unlockedProductCategories = unlockedProductCategories;
    }
}
