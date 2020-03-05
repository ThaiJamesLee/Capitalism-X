package de.uni.mannheim.capitalismx.procurement.component;

import de.uni.mannheim.capitalismx.procurement.department.ProcurementDepartment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class ProcurementDepartmentTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcurementDepartment.class);

    private Component component;
    private LocalDate orderDate;
    private ProcurementDepartment procurementDepartment;

    @BeforeTest
    public void setUp() {
        this.procurementDepartment = ProcurementDepartment.createInstance();
        this.component = new Component(ComponentType.T_CASE_LEVEL_1, SupplierCategory.CHEAP, LocalDate.of(1990, 1 , 1));
    }

    @Test
    public void buyComponentsTest() {
        this.orderDate = LocalDate.of(1990, 1, 1);
        int freeStorage = 10;
        this.procurementDepartment.buyComponents(orderDate, this.component, 10, freeStorage);
        Assert.assertEquals(this.procurementDepartment.getComponentOrders().size(), 1);
        ComponentOrder componentOrder = this.procurementDepartment.getComponentOrders().get(0);
        Assert.assertEquals(componentOrder.getOrderedComponent(), this.component);
        Assert.assertEquals(componentOrder.getOrderedQuantity(), 10);
        Assert.assertEquals(componentOrder.getOrderDate(), orderDate);
        Assert.assertEquals(this.procurementDepartment.getQuantityOfOrderedComponents(), 10);
    }

    @Test
    public void receiveComponentsTest() {
        LocalDate newDate = this.orderDate.plusDays(this.procurementDepartment.getDeliveryTime());
        this.procurementDepartment.receiveComponents(newDate);
        Assert.assertEquals(this.procurementDepartment.getReceivedComponents().get(this.component), (Integer) 10);
    }
}
