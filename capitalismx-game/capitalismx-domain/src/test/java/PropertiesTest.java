import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class PropertiesTest {

    @Test
    public void propertiesTestEn() {
        ResourceBundle bundle = ResourceBundle.getBundle("domain-module", Locale.ENGLISH);
        Assert.assertEquals(bundle.getString("name.salesperson"), "SalesPerson");
        Assert.assertEquals(bundle.getString("name.engineer"), "Engineer");
    }

    @Test
    public void propertiesTestDe() {
        ResourceBundle bundle = ResourceBundle.getBundle("domain-module", Locale.GERMAN);
        Assert.assertEquals(bundle.getString("name.salesperson"), "Verkäufer");
        Assert.assertEquals(bundle.getString("name.engineer"), "Ingenieur");
    }
}
