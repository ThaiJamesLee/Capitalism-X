package de.uni.mannheim.capitalismx.logistic.support;

import de.uni.mannheim.capitalismx.logistic.support.exception.NoExternalSupportPartnerException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;


public class ProductSupportTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSupportTest.class);

    @Test
    public void generateSupportTypeSelectionTest() {
        ProductSupport productSupport = new ProductSupport();

        Assert.assertEquals(productSupport.generateSupportTypeSelection().size(), 5);
    }

    @Test
    public void addSupportTest() {
        ProductSupport productSupport = new ProductSupport();

        try {
            productSupport.addSupport(ProductSupport.SupportType.ONLINE_SUPPORT);
        } catch (NoExternalSupportPartnerException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(productSupport.getSupportTypes().size(), 1);

        productSupport.addExternalSupportPartner(ProductSupport.ExternalSupportPartner.PARTNER_1);
        try {
            productSupport.addSupport(ProductSupport.SupportType.ONLINE_SUPPORT);
        } catch (NoExternalSupportPartnerException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(productSupport.getSupportTypes().size(), 1);

        try {
            productSupport.addSupport(ProductSupport.SupportType.STORE_SUPPORT);
        } catch (NoExternalSupportPartnerException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(productSupport.getSupportTypes().size(), 2);

        try {
            productSupport.addSupport(ProductSupport.SupportType.ONLINE_SUPPORT);
        } catch (NoExternalSupportPartnerException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(productSupport.getSupportTypes().size(), 2);

    }

    @Test
    public void generateExternalSupportPartnerSelectionTest() {
        ProductSupport productSupport = new ProductSupport();

        Assert.assertEquals(productSupport.generateExternalSupportPartnerSelection().size(), 6);
    }

    @Test
    public void calculateTotalSupportTypeQualityTest() {
        ProductSupport productSupport = new ProductSupport();

        Assert.assertEquals(productSupport.calculateTotalSupportTypeQuality(), -10.0);

        productSupport.addExternalSupportPartner(ProductSupport.ExternalSupportPartner.PARTNER_1);

        try {
            productSupport.addSupport(ProductSupport.SupportType.ONLINE_SUPPORT);
        } catch (NoExternalSupportPartnerException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(productSupport.calculateTotalSupportTypeQuality(), 20.0);

        try {
            productSupport.addSupport(ProductSupport.SupportType.STORE_SUPPORT);
        } catch (NoExternalSupportPartnerException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(productSupport.calculateTotalSupportTypeQuality(), 60.0);
    }

    @Test
    public void calculateTotalSupportQualityTest() {
        ProductSupport productSupport = new ProductSupport();

        Assert.assertEquals(productSupport.calculateTotalSupportQuality(), 0.6 * (-10.0));

        productSupport.addExternalSupportPartner(ProductSupport.ExternalSupportPartner.PARTNER_6);
        Assert.assertEquals(productSupport.calculateTotalSupportQuality(), (0.4 * 40 + 0.6 * (-10)));

        productSupport.addExternalSupportPartner(ProductSupport.ExternalSupportPartner.PARTNER_1);
        Assert.assertEquals(productSupport.calculateTotalSupportQuality(), (0.3 * 100 + 0.7 * (-10)));

        try {
            productSupport.addSupport(ProductSupport.SupportType.ONLINE_SUPPORT);
        } catch (NoExternalSupportPartnerException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(productSupport.calculateTotalSupportQuality(), (0.3 * 100 + 0.7 * 20));
    }

    @Test
    public void calculateTotalSupportCostsTest() {
        ProductSupport productSupport = new ProductSupport();

        Assert.assertEquals(productSupport.calculateTotalSupportCosts(), 0.0);

        productSupport.addExternalSupportPartner(ProductSupport.ExternalSupportPartner.PARTNER_1);
        Assert.assertEquals(productSupport.calculateTotalSupportCosts(), 1200.0);

        try {
            productSupport.addSupport(ProductSupport.SupportType.ONLINE_SUPPORT);
        } catch (NoExternalSupportPartnerException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(productSupport.calculateTotalSupportCosts(), 1200.0 + 100);

        try {
            productSupport.addSupport(ProductSupport.SupportType.STORE_SUPPORT);
        } catch (NoExternalSupportPartnerException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(productSupport.calculateTotalSupportCosts(), 1200.0 + 100 + 400);
    }

}
