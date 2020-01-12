import de.uni.mannheim.capitalismx.hr.domain.employee.EmployeeType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Locale;

/**
 * @author duly
 */
public class PropertiesTest {

    @Test
    public void propertiesTestEn() {
        EmployeeType salesperson = EmployeeType.SALESPERSON;
        EmployeeType engineer = EmployeeType.ENGINEER;

        Assert.assertEquals(salesperson.getName(Locale.ENGLISH), "SalesPerson");
        Assert.assertEquals(engineer.getName(Locale.ENGLISH), "Engineer");
    }

    @Test
    public void propertiesTestDe() {
        EmployeeType salesperson = EmployeeType.SALESPERSON;
        EmployeeType engineer = EmployeeType.ENGINEER;

        Assert.assertEquals(salesperson.getName(Locale.GERMAN), "Verkaeufer");
        Assert.assertEquals(engineer.getName(Locale.GERMAN), "Ingenieur");
    }
}
