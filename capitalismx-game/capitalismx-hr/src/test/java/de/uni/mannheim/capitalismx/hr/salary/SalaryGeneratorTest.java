package de.uni.mannheim.capitalismx.hr.salary;

import de.uni.mannheim.capitalismx.hr.domain.Salary;
import de.uni.mannheim.capitalismx.hr.exception.NoDefinedTierException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;


public class SalaryGeneratorTest {

    private static final Logger logger = LoggerFactory.getLogger(SalaryGeneratorTest.class);

    @Test
    public void rangeTestTier0() {
        SalaryGenerator sg = SalaryGenerator.getInstance();

        for (int i = 0; i < Salary.TIER_0.getLowerLevel(); i++) {
            try {
                Assert.assertTrue(sg.getSalary(i) <= Salary.TIER_0.getUpperSalary());
                Assert.assertTrue(sg.getSalary(i) >= Salary.TIER_0.getLowerLevel());
            } catch (NoDefinedTierException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Test
    public void rangeTestTier1() {
        SalaryGenerator sg = SalaryGenerator.getInstance();

        for (int i = 0; i < Salary.TIER_1.getLowerLevel(); i++) {
            try {
                Assert.assertTrue(sg.getSalary(i) <= Salary.TIER_1.getUpperSalary());
                Assert.assertTrue(sg.getSalary(i) >= Salary.TIER_1.getLowerLevel());
            } catch (NoDefinedTierException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Test
    public void rangeTestTier2() {
        SalaryGenerator sg = SalaryGenerator.getInstance();

        for (int i = 0; i < Salary.TIER_2.getLowerLevel(); i++) {
            try {
                Assert.assertTrue(sg.getSalary(i) <= Salary.TIER_2.getUpperSalary());
                Assert.assertTrue(sg.getSalary(i) >= Salary.TIER_2.getLowerLevel());
            } catch (NoDefinedTierException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Test
    public void rangeTestTier3() {
        SalaryGenerator sg = SalaryGenerator.getInstance();

        for (int i = 0; i < Salary.TIER_3.getLowerLevel(); i++) {
            try {
                Assert.assertTrue(sg.getSalary(i) <= Salary.TIER_3.getUpperSalary());
                Assert.assertTrue(sg.getSalary(i) >= Salary.TIER_3.getLowerLevel());
            } catch (NoDefinedTierException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Test
    public void rangeTestTier4() {
        SalaryGenerator sg = SalaryGenerator.getInstance();

        for (int i = 0; i < Salary.TIER_4.getLowerLevel(); i++) {
            try {
                Assert.assertTrue(sg.getSalary(i) <= Salary.TIER_4.getUpperSalary());
                Assert.assertTrue(sg.getSalary(i) >= Salary.TIER_4.getLowerLevel());
            } catch (NoDefinedTierException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Test(expectedExceptions = NoDefinedTierException.class)
    public void bigInputTest() throws NoDefinedTierException {
        SalaryGenerator.getInstance().getSalary(999);
        SalaryGenerator.getInstance().getSalary(999999);

    }

    @Test(expectedExceptions = NoDefinedTierException.class)
    public void smallInputTest() throws NoDefinedTierException {
        SalaryGenerator.getInstance().getSalary(-999);
        SalaryGenerator.getInstance().getSalary(-9);
    }


}
