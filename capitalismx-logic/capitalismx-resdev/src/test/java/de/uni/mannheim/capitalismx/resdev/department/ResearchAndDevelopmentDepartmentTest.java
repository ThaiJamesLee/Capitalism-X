package de.uni.mannheim.capitalismx.resdev.department;

import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.production.ProductCategory;
import de.uni.mannheim.capitalismx.resdev.skills.ProductCategorySkill;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class ResearchAndDevelopmentDepartmentTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResearchAndDevelopmentDepartmentTest.class);

    @Test
    public void resDevSkillTest() {
        ResearchAndDevelopmentDepartment researchAndDevelopmentDepartment = ResearchAndDevelopmentDepartment.createInstance();

        List<ProductCategorySkill> allProductCategorySkills = researchAndDevelopmentDepartment.getAllProductCategoriesSkills();
        Map<Integer, DepartmentSkill> allComponentSkills =  researchAndDevelopmentDepartment.getSkillMap();

        Assert.assertEquals(8, allComponentSkills.size());
        Assert.assertEquals(4, allProductCategorySkills.size());
    }


    @Test
    public void resDevLevelUpTest() {
        ResearchAndDevelopmentDepartment researchAndDevelopmentDepartment = ResearchAndDevelopmentDepartment.createInstance();

        int level = 5;

        for(int i = 0; i < level; i++) {
            researchAndDevelopmentDepartment.getLevelingMechanism().levelUp();
        }

        Assert.assertEquals(5, researchAndDevelopmentDepartment.getLevel());
        Assert.assertEquals(5, researchAndDevelopmentDepartment.getAvailableSkills().size());

    }

    @Test
    public void unlockComponentsTest() {
        ResearchAndDevelopmentDepartment researchAndDevelopmentDepartment = ResearchAndDevelopmentDepartment.createInstance();

        int level = 5;

        for(int i = 0; i < level; i++) {
            researchAndDevelopmentDepartment.getLevelingMechanism().levelUp();
        }

        Assert.assertTrue(researchAndDevelopmentDepartment.isComponentUnlocked(new Component(ComponentType.G_CAMERA_LEVEL_1)));
        Assert.assertFalse(researchAndDevelopmentDepartment.isComponentUnlocked(new Component(ComponentType.G_CPU_LEVEL_7)));
    }

    @Test
    public void unlockProductCategory() {
        ResearchAndDevelopmentDepartment researchAndDevelopmentDepartment = ResearchAndDevelopmentDepartment.createInstance();

        Assert.assertTrue(researchAndDevelopmentDepartment.isCategoryUnlocked(ProductCategory.TELEVISION));
        Assert.assertFalse(researchAndDevelopmentDepartment.isCategoryUnlocked(ProductCategory.NOTEBOOK));

        double cost = researchAndDevelopmentDepartment.unlockProductCategory(ProductCategory.NOTEBOOK);

        String message = "Unlock Notebook Category cost = " + cost + "CC";
        LOGGER.info(message);

        Assert.assertTrue(cost > 0);
        Assert.assertTrue(researchAndDevelopmentDepartment.isCategoryUnlocked(ProductCategory.NOTEBOOK));
    }
}
