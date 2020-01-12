package de.uni.mannheim.capitalismx.sales.department;

import de.uni.mannheim.capitalismx.production.ProductionDepartment;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author duly
 */
public class SalesDepartmentTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesDepartmentTest.class);

    private ProductionDepartment productionDepartment;

    @BeforeTest
    public void setUp() {
        productionDepartment = ProductionDepartment.getInstance();
    }

    @Test
    public void levelUpTest() {
        SalesDepartment salesDepartment = SalesDepartment.createInstance();
        for (int i = 1; i<=salesDepartment.getMaxLevel(); i++) {
            salesDepartment.getLevelingMechanism().levelUp();
        }
        Assert.assertEquals(salesDepartment.getAvailableSkills().size(), 8);
    }

    @Test
    public void salesDepartmentSkillTest() {

    }
    
}
