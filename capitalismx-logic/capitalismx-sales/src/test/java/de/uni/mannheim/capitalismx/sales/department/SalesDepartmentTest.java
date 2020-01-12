package de.uni.mannheim.capitalismx.sales.department;

import org.junit.Assert;
import org.testng.annotations.Test;

public class SalesDepartmentTest {

    @Test
    public void levelUpTest() {
        SalesDepartment salesDepartment = SalesDepartment.createInstance();
        for (int i = 1; i<=salesDepartment.getMaxLevel(); i++) {
            salesDepartment.getLevelingMechanism().levelUp();
        }
        Assert.assertEquals(salesDepartment.getAvailableSkills().size(), 8);
    }
    
}
