package de.uni.mannheim.capitalismx.hr.department;

import de.uni.mannheim.capitalismx.hr.domain.Training;
import de.uni.mannheim.capitalismx.hr.employee.Employee;
import de.uni.mannheim.capitalismx.hr.employee.impl.Engineer;
import de.uni.mannheim.capitalismx.hr.employee.impl.SalesPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duly
 */
public class HRDepartmentTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HRDepartmentTest.class);
    private List<Employee> employeeStack;

    private void mockEmployeeStack() {
        employeeStack.add(new SalesPerson("SA", "NO1", "male", "mr", 3000, 20));
        employeeStack.add(new SalesPerson("SAL", "NO2", "female", "mr", 3000, 20));

        employeeStack.add(new Engineer("Eng", "NO1", "female", "ms", 4000, 25));
        employeeStack.add(new Engineer("Engi", "NO2", "male", "ms", 4000, 25));
    }

    @BeforeTest
    public void setUp() {
        HRDepartment.getInstance();
        employeeStack = new ArrayList<>();
        mockEmployeeStack();
    }

    @Test
    public void hireTest() {
        Assert.assertTrue(HRDepartment.getInstance().hire(employeeStack.get(0)) > 5000);
        Assert.assertTrue(HRDepartment.getInstance().hire(employeeStack.get(1)) > 5000);
        Assert.assertTrue(HRDepartment.getInstance().hire(employeeStack.get(2)) > 5000);
        Assert.assertTrue(HRDepartment.getInstance().hire(employeeStack.get(3)) > 5000);
        Assert.assertEquals(HRDepartment.getInstance().getHired().size(), 4);
    }

    /**
     * Test training hired engineers function.
     */
    @Test(dependsOnMethods = "hireTest")
    public void trainingTest() {
        HRDepartment department = HRDepartment.getInstance();

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

    @Test(dependsOnMethods = "trainingTest")
    public void getTotalQualityOfWorkByEmployeeTypeTest() {
        HRDepartment department = HRDepartment.getInstance();

        department.calculateAndUpdateEmployeesMeta();

        List<Employee> hiredEmployees = department.getHired();

        for(Employee e : hiredEmployees) {
            Assert.assertTrue(e.getQualityOfWork() > 0);
        }

    }

    @Test
    public void jobSatisfactionTest() {

        System.out.println(HRDepartment.getInstance().getTotalJSS());
    }

    /**
     * Test impact of benefit on job satisfaction score.
     */
    @Test
    public void benefitSettingsTest() {

    }




}
