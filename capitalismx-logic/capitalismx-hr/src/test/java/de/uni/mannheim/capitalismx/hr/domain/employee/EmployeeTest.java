package de.uni.mannheim.capitalismx.hr.domain.employee;

import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.hr.domain.employee.impl.HRWorker;
import de.uni.mannheim.capitalismx.hr.training.EmployeeTraining;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author duly
 */
public class EmployeeTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeTest.class);

    private HRDepartment hrDepartment;

    @BeforeTest
    public void setUp() {
        hrDepartment = HRDepartment.createInstance();
        hrDepartment.getLevelingMechanism().levelUp();
    }

    @Test
    public void hrWorkerTest() {
        EmployeeGenerator generator = EmployeeGenerator.createInstance();
        generator.setDepartment(hrDepartment);
        HRWorker worker = (HRWorker) generator.createRandomEmployee(EmployeeType.HR_WORKER);
        Assert.assertNotNull(worker);

        int expected = (int)(worker.getSkillLevel()/2.0);
        Assert.assertEquals(worker.getCapacity(), expected);
    }

    @Test
    public void hrWorkerTrainingTest() {
        EmployeeGenerator generator = EmployeeGenerator.createInstance();
        generator.setDepartment(hrDepartment);
        HRWorker worker = (HRWorker) generator.createRandomEmployee(EmployeeType.HR_WORKER);
        Assert.assertNotNull(worker);

        Assert.assertEquals(worker.getCapacity(),  (int)(worker.getSkillLevel()/2.0));

        EmployeeTraining.getInstance().trainEmployee(worker, Training.WORKSHOP);
        EmployeeTraining.getInstance().trainEmployee(worker, Training.WORKSHOP);
        EmployeeTraining.getInstance().trainEmployee(worker, Training.WORKSHOP);

        Assert.assertEquals(worker.getCapacity(),  (int)(worker.getSkillLevel()/2.0));
        String message = "Employee skillLevel=" + worker.getSkillLevel() + "; capacity=" + worker.getCapacity();
        LOGGER.info(message);
    }
}
