package de.uni.mannheim.capitalismx.hr.department;

import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.domain.department.LevelingMechanism;
import de.uni.mannheim.capitalismx.hr.domain.employee.Employee;
import de.uni.mannheim.capitalismx.hr.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.hr.domain.employee.impl.Engineer;
import de.uni.mannheim.capitalismx.hr.domain.employee.impl.HRWorker;
import de.uni.mannheim.capitalismx.hr.domain.employee.impl.SalesPerson;
import de.uni.mannheim.capitalismx.hr.department.skill.HRSkill;
import de.uni.mannheim.capitalismx.hr.domain.Benefit;
import de.uni.mannheim.capitalismx.hr.domain.BenefitType;
import de.uni.mannheim.capitalismx.hr.domain.employee.training.Training;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.*;

/**
 * @author duly
 */
public class HRDepartmentTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HRDepartmentTest.class);
    private List<Employee> employeeStack;

    private HRDepartment department;

    /**
     * Mock some data for test.
     */
    private void mockEmployeeStack() {
        employeeStack.add(new HRWorker("HRW", "NO1", "male", "mr", 2000, 10).setEmployeeType(EmployeeType.HR_WORKER));

        employeeStack.add(new SalesPerson("SA", "NO1", "male", "mr", 3000, 20).setEmployeeType(EmployeeType.SALESPERSON));
        employeeStack.add(new SalesPerson("SAL", "NO2", "female", "mr", 3000, 20).setEmployeeType(EmployeeType.SALESPERSON));

        employeeStack.add(new Engineer("Eng", "NO1", "female", "ms", 4000, 25).setEmployeeType(EmployeeType.ENGINEER));
        employeeStack.add(new Engineer("Engi", "NO2", "male", "ms", 4000, 25).setEmployeeType(EmployeeType.ENGINEER));
    }

    /**
     * Setup environment for test.
     */
    @BeforeTest
    public void setUp() {
        department = HRDepartment.createInstance();
        employeeStack = new ArrayList<>();
        mockEmployeeStack();
    }

    /**
     * Test hiring method.
     */
    @Test
    public void hireTest() {
        Assert.assertTrue(department.hire(employeeStack.get(0)) > 5000);
        Assert.assertTrue(department.hire(employeeStack.get(1)) > 5000);
        Assert.assertTrue(department.hire(employeeStack.get(2)) > 5000);
        Assert.assertTrue(department.hire(employeeStack.get(3)) > 5000);
        Assert.assertTrue(department.hire(employeeStack.get(4)) > 5000);

        Assert.assertEquals(department.getHired().size(), 5);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void hireExceptionTest() {
        department.hire(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void fireExceptionTest() {
        department.fire(null);
    }


    /**
     * Test training hired engineers function.
     */
    @Test(dependsOnMethods = "hireTest")
    public void trainingTest() {
        /*
        * Hired engineers are accessible through the teams.
        */
        Employee salesman1 = department.getSalesTeam().getTeam().get(0);
        Employee salesman2 = department.getSalesTeam().getTeam().get(1);
        Employee engineer1 = department.getEngineerTeam().getTeam().get(0);
        Employee engineer2 = department.getEngineerTeam().getTeam().get(1);

        /*
        * Train engineers with the trainings provided by the enum Training.
        */
        department.trainEmployee(salesman1, Training.WORKSHOP);
        department.trainEmployee(salesman2, Training.COURSES);

        department.trainEmployee(engineer1, Training.WORKSHOP);
        department.trainEmployee(engineer2, Training.COURSES);

        Assert.assertEquals(salesman1.getSkillLevel(), 22 );
        Assert.assertEquals(salesman2.getSkillLevel(), 21);

        Assert.assertEquals(engineer1.getSkillLevel(), 27);
        Assert.assertEquals(engineer2.getSkillLevel(), 26);

    }

    /**
     * Test training.
     */
    @Test(dependsOnMethods = "trainingTest")
    public void getTotalQualityOfWorkByEmployeeTypeTest() {
        department = HRDepartment.getInstance();

        department.calculateAndUpdateEmployeesMeta();

        PropertyChangeSupportList<Employee> hiredEmployees = department.getHired();

        for(Employee e : hiredEmployees.getList()) {
            Assert.assertTrue(e.getQualityOfWork() > 0);
        }

    }

    /**
     * Test impact of benefit on job satisfaction score.
     */
    @Test
    public void benefitSettingsTest() {
        HRDepartment testDepartment = HRDepartment.createInstance();
        BenefitSettings benefitSettings = testDepartment.getBenefitSettings();
        Set<Map.Entry<BenefitType, Benefit>> setting = benefitSettings.getBenefits().entrySet();

        double totalBenefit = 0;
        for(Map.Entry<BenefitType, Benefit> entry :  setting) {
            totalBenefit += entry.getValue().getPoints();
        }
        Assert.assertEquals(totalBenefit, 0.0);
    }

    @Test(dependsOnMethods = "benefitSettingsTest")
    public void upgradeBenefitSettingsTest() {
        HRDepartment testDepartment = HRDepartment.createInstance();

        testDepartment.changeBenefitSetting(Benefit.getMaxTierBenefitByType(BenefitType.SALARY));
        testDepartment.changeBenefitSetting(Benefit.getMaxTierBenefitByType(BenefitType.WORKING_TIME_MODEL));
        testDepartment.changeBenefitSetting(Benefit.getMaxTierBenefitByType(BenefitType.COMPANY_CAR));
        testDepartment.changeBenefitSetting(Benefit.getMaxTierBenefitByType(BenefitType.GYM_AND_SPORTS));
        testDepartment.changeBenefitSetting(Benefit.getMaxTierBenefitByType(BenefitType.IT_EQUIPMENT));
        testDepartment.changeBenefitSetting(Benefit.getMaxTierBenefitByType(BenefitType.WORKTIME));
        testDepartment.changeBenefitSetting(Benefit.getMaxTierBenefitByType(BenefitType.FOOD_AND_COFFEE));

        BenefitSettings benefitSettings = testDepartment.getBenefitSettings();
        Set<Map.Entry<BenefitType, Benefit>> setting = benefitSettings.getBenefits().entrySet();

        double totalBenefit = 0;
        for(Map.Entry<BenefitType, Benefit> entry :  setting) {
            totalBenefit += entry.getValue().getPoints();
        }
        Assert.assertEquals(totalBenefit, 28.0);

        Assert.assertTrue(testDepartment.getTotalJSS() >= 1.0);
    }

    @Test
    public void checkMaxLevelTest() {
        Assert.assertEquals(department.getMaxLevel(), 8);
    }

    @Test
    public void checkLevelingMechanismCostMapTest() {
        LevelingMechanism lM = department.getLevelingMechanism();
        Map<Integer, Double> costMap = lM.getLevelCostMap();

        Assert.assertEquals(costMap.size(), 8);
    }

    /**
     * Test the defined skills if they are loaded correctly.
     */
    @Test(dependsOnMethods = {"checkMaxLevelTest", "checkLevelingMechanismCostMapTest"})
    public void skillMapTest() {
        HRDepartment testDepartment = HRDepartment.createInstance();
        Map<Integer, DepartmentSkill> skillMap = testDepartment.getSkillMap();

        Assert.assertEquals(skillMap.size(), 8);

        for(Map.Entry<Integer, DepartmentSkill> entry : skillMap.entrySet()) {
            HRSkill skill = (HRSkill) entry.getValue();
            Assert.assertTrue(skill.getNewEmployeeCapacity() > 0);
            for(Map.Entry<String, Double> distribution : skill.getSkillDistribution().entrySet()) {
                String info = "level: "+ entry.getKey()  +"; key:"+ distribution.getKey() + "; distribution: " + distribution.getValue();
                LOGGER.info(info);
                Assert.assertTrue(distribution.getValue() > 0);
            }
        }
    }

    /**
     * Test the leveling mechanism.
     */
    @Test(dependsOnMethods = "skillMapTest")
    public void levelUpTest() {
        HRDepartment testDepartment = HRDepartment.createInstance();
        LevelingMechanism mechanism = testDepartment.getLevelingMechanism();

        int tmpCapacity = 0;

        for(int i = 0; i<testDepartment.getMaxLevel(); i++) {
            Assert.assertTrue(mechanism.levelUp() > 0);
            Assert.assertEquals(testDepartment.getAvailableSkills().size(), i+1);
            Assert.assertTrue(testDepartment.getHrCapacity() > tmpCapacity );
            tmpCapacity = testDepartment.getHrCapacity();
        }

        Assert.assertEquals(testDepartment.getLevel(), 8);

        Assert.assertNull(mechanism.levelUp());
    }

    /**
     * Test when there is a entry that is 30 days ago.
     */
    @Test
    public void employeeHistoryTestI() {
        HRDepartment testDepartment = HRDepartment.createInstance();

        // hire some employees
        testDepartment.hire(employeeStack.get(0));
        testDepartment.updateEmployeeHistory(LocalDate.now().minusDays(30));
        testDepartment.updateEmployeeHistory(LocalDate.now().minusDays(29));
        testDepartment.updateEmployeeHistory(LocalDate.now().minusDays(28));
        testDepartment.hire(employeeStack.get(1));
        testDepartment.hire(employeeStack.get(2));
        testDepartment.updateEmployeeHistory(LocalDate.now().minusDays(27));
        testDepartment.updateEmployeeHistory(LocalDate.now().minusDays(26));
        testDepartment.hire(employeeStack.get(3));
        testDepartment.updateEmployeeHistory(LocalDate.now().minusDays(25));
        testDepartment.hire(employeeStack.get(4));
        testDepartment.updateEmployeeHistory(LocalDate.now());

        Assert.assertEquals(testDepartment.getEmployeeDifference(LocalDate.now()), 4);
    }

    @Test
    public void employeeHistoryTestII() {
        HRDepartment testDepartment = HRDepartment.createInstance();

        // hire some employees
        testDepartment.hire(employeeStack.get(0));
        testDepartment.hire(employeeStack.get(1));
        testDepartment.updateEmployeeHistory(LocalDate.now().minusDays(28));
        testDepartment.hire(employeeStack.get(2));
        testDepartment.updateEmployeeHistory(LocalDate.now().minusDays(27));
        testDepartment.updateEmployeeHistory(LocalDate.now().minusDays(26));
        testDepartment.hire(employeeStack.get(3));
        testDepartment.updateEmployeeHistory(LocalDate.now().minusDays(25));
        testDepartment.hire(employeeStack.get(4));
        testDepartment.updateEmployeeHistory(LocalDate.now());

        Assert.assertEquals(testDepartment.getEmployeeDifference(LocalDate.now()), 3);
    }


}
