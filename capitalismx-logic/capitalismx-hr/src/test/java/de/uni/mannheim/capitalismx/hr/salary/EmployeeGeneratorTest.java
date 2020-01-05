package de.uni.mannheim.capitalismx.hr.salary;

import de.uni.mannheim.capitalismx.domain.employee.Employee;
import de.uni.mannheim.capitalismx.domain.employee.EmployeeGenerator;
import de.uni.mannheim.capitalismx.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class EmployeeGeneratorTest {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeGeneratorTest.class);

    @Test
    public void generateEngineerTest () {
        EmployeeGenerator generator = EmployeeGenerator.getInstance();

        for (int i = 0; i < 5 ; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
            Assert.assertNotNull(generator.generateEngineer(i*10));

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
            System.out.println(e.getSkillLevel() + "; " + e.getSalary());
            generated.add(e);
        }
    }

    @AfterTest
    public void cleanUp() {
        HRDepartment.setInstance(null);
    }
}
