package de.uni.mannheim.capitalismx.hr.salary;

import de.uni.mannheim.capitalismx.hr.exceptions.NoDefinedTierException;
import org.testng.Assert;
import org.testng.annotations.Test;


public class SalaryGeneratorTest {


    @Test
    public void rangeTestTier0() {
        SalaryGenerator sg = new SalaryGenerator();

        for (int i = 0; i < Salary.TIER_0.getLowerLevel(); i++) {
            try {
                Assert.assertTrue(sg.getSalary(i) <= Salary.TIER_0.getUpperSalary());
                Assert.assertTrue(sg.getSalary(i) >= Salary.TIER_0.getLowerLevel());
            } catch (NoDefinedTierException e) {

            }
        }
    }

    @Test
    public void rangeTestTier1() {
        SalaryGenerator sg = new SalaryGenerator();

        for (int i = 0; i < Salary.TIER_1.getLowerLevel(); i++) {
            try {
                Assert.assertTrue(sg.getSalary(i) <= Salary.TIER_1.getUpperSalary());
                Assert.assertTrue(sg.getSalary(i) >= Salary.TIER_1.getLowerLevel());
            } catch (NoDefinedTierException e) {

            }
        }
    }

    @Test
    public void rangeTestTier2() {
        SalaryGenerator sg = new SalaryGenerator();

        for (int i = 0; i < Salary.TIER_2.getLowerLevel(); i++) {
            try {
                Assert.assertTrue(sg.getSalary(i) <= Salary.TIER_2.getUpperSalary());
                Assert.assertTrue(sg.getSalary(i) >= Salary.TIER_2.getLowerLevel());
            } catch (NoDefinedTierException e) {

            }
        }
    }

    @Test
    public void rangeTestTier3() {
        SalaryGenerator sg = new SalaryGenerator();

        for (int i = 0; i < Salary.TIER_3.getLowerLevel(); i++) {
            try {
                Assert.assertTrue(sg.getSalary(i) <= Salary.TIER_3.getUpperSalary());
                Assert.assertTrue(sg.getSalary(i) >= Salary.TIER_3.getLowerLevel());
            } catch (NoDefinedTierException e) {

            }
        }
    }

    @Test
    public void rangeTestTier4() {
        SalaryGenerator sg = new SalaryGenerator();

        for (int i = 0; i < Salary.TIER_4.getLowerLevel(); i++) {
            try {
                Assert.assertTrue(sg.getSalary(i) <= Salary.TIER_4.getUpperSalary());
                Assert.assertTrue(sg.getSalary(i) >= Salary.TIER_4.getLowerLevel());
            } catch (NoDefinedTierException e) {

            }
        }
    }

    @Test(expectedExceptions = NoDefinedTierException.class)
    public void bigInputTest() throws NoDefinedTierException {
        new SalaryGenerator().getSalary(999);
        new SalaryGenerator().getSalary(999999);

    }

    @Test(expectedExceptions = NoDefinedTierException.class)
    public void smallInputTest() throws NoDefinedTierException {
        new SalaryGenerator().getSalary(-999);
        new SalaryGenerator().getSalary(-9);

    }


}
