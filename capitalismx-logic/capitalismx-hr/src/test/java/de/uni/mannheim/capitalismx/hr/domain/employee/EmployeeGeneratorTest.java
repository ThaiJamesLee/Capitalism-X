package de.uni.mannheim.capitalismx.hr.domain.employee;

import de.uni.mannheim.capitalismx.hr.domain.employee.impl.Engineer;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author duly
 */
public class EmployeeGeneratorTest {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeGeneratorTest.class);

    private List<Integer> validInitLevels;

    @BeforeTest
    public void setUp() {
        Integer[] tmp = {20, 30, 40, 60, 80};
        validInitLevels = Arrays.asList(tmp);

    }

    @Test
    public void generateEngineerTest () {
        EmployeeGenerator generator = EmployeeGenerator.getInstance();
        HRDepartment hrDepartment = HRDepartment.createInstance();
        hrDepartment.getLevelingMechanism().levelUp();
        generator.setDepartment(hrDepartment);

        for (int i = 0; i < 5 ; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
            Engineer engineer = (Engineer) generator.createRandomEmployee(EmployeeType.ENGINEER);
            Assert.assertNotNull(engineer);

        }
    }

    //@Test(expectedExceptions = NoDefinedTierException.class)
    @Test
    public void generateEngineerSkillLevelTestI () {
        EmployeeGenerator generator = EmployeeGenerator.getInstance();


        for (int i = 101; i < 110; i++) {
            Assert.assertNull(generator.generateEngineer(i));

        }
     }

    //@Test(expectedExceptions = NoDefinedTierException.class)
    @Test
    public void generateEngineerSkillLevelTestII () {
        EmployeeGenerator generator = EmployeeGenerator.getInstance();


        for (int i = -10; i < -1; i++) {
            Assert.assertNull(generator.generateEngineer(i));

        }
    }

    @Test
    public void employeeGeneratorTest() {
        HRDepartment department = HRDepartment.createInstance();
        department.getLevelingMechanism().levelUp();

        EmployeeGenerator generator = EmployeeGenerator.getInstance();
        generator.setDepartment(department);
        
        List<Employee> generated = new ArrayList<>();

        for(int i = 0; i < 20; i++) {
            Employee e = generator.createRandomEmployee(EmployeeType.ENGINEER);
            generated.add(e);
        }

        for(Employee e : generated) {
            Assert.assertTrue(validInitLevels.contains(e.getSkillLevel()));
        }
    }

    @AfterTest
    public void cleanUp() {
        HRDepartment.setInstance(null);
    }
}
