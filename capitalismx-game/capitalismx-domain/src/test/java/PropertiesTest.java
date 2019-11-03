import de.uni.mannheim.capitalismx.domain.employee.EmployeeType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Locale;

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

        Assert.assertEquals(salesperson.getName(Locale.GERMAN), "Verkäufer");
        Assert.assertEquals(engineer.getName(Locale.GERMAN), "Ingenieur");
    }
}
