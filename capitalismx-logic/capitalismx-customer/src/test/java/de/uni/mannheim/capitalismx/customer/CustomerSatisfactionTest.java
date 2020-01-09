package de.uni.mannheim.capitalismx.customer;

import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.ProductCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerSatisfactionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerSatisfaction.class);

    private LocalDate initDate;

    @BeforeTest
    public void setUp() {
        initDate = LocalDate.of(1990,11,1);
    }

    @Test
    public void calculateCustomerSatisfactionTest() {
        CustomerSatisfaction customerSatisfaction = CustomerSatisfaction.createInstance();
        double custSatistfactionScore = customerSatisfaction.calculateCustomerSatisfaction(initDate);
        String message = "CustomerSatisfaction: " +custSatistfactionScore;

        LOGGER.info(message);
        Assert.assertNotNull(custSatistfactionScore);
    }

    @Test
    public void calculateCustomerSatisfactionTestII() {
        double maxJSS = 100;
        double maxCompanyImage = 100;
        double employerBranding = maxCompanyImage * 0.4 + maxJSS * 0.6;

        double logisticIndex = 100;
        double totalSupportQuality = 100;

        List<Component> components = new ArrayList<>();
        components.add(new Component(ComponentType.G_DISPLAYCASE_LEVEL_1));
        components.add(new Component(ComponentType.G_POWERSUPPLY_LEVEL_1));
        components.add(new Component(ComponentType.G_CPU_LEVEL_1));
        components.add(new Component(ComponentType.G_CONNECTIVITY_LEVEL_1));
        components.add(new Component(ComponentType.G_CAMERA_LEVEL_1));

        Product p = new Product("test", ProductCategory.GAME_BOY, components);
        p.setLaunchDate(LocalDate.of(1990,1,1));

        List<Product> launchedProducts = new ArrayList<>();
        launchedProducts.add(p);

        CustomerSatisfaction customerSatisfaction = CustomerSatisfaction.getInstance();
        customerSatisfaction.setLogisticIndex(logisticIndex);
        customerSatisfaction.setEmployerBranding(employerBranding);
        customerSatisfaction.setCompanyImage(maxCompanyImage);
        customerSatisfaction.setTotalSupportQuality(totalSupportQuality);
        customerSatisfaction.setProducts(launchedProducts);

        customerSatisfaction.calculateAll(initDate);

        CustomerDemand customerDemand = CustomerDemand.createInstance();


        double salesQoW = 100;
        customerDemand.calculateOverallAppealDemand(salesQoW, initDate);
        customerDemand.calculateDemandPercentage(salesQoW, initDate);

        Map<Product, Double> demandPercentage = customerDemand.getDemandPercentage();

        for(Map.Entry<Product, Double> entry : demandPercentage.entrySet()) {
            System.out.println(entry.getKey().toString() + "; " + entry.getValue());
        }


    }
}
