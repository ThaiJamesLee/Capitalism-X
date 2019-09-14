package de.uni.mannheim.capitalismx.ecoindex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CompanyEcoIndexTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyEcoIndexTest.class);

    @Test
    public void calculateAllTest () {
        CompanyEcoIndex companyEcoIndex = new CompanyEcoIndex();

        companyEcoIndex.calculateAll();
    }

    //@Test(dependsOnMethods = "calculateAllTest")
    @Test
    public void decreaseEcoIndexTest () {
        CompanyEcoIndex companyEcoIndex = new CompanyEcoIndex();
        companyEcoIndex.calculateAll();

        companyEcoIndex.decreaseEcoIndex(10);
        Assert.assertEquals(companyEcoIndex.getEcoIndex().getPoints(), 90);
        Assert.assertEquals(companyEcoIndex.getEcoIndex().getIndex(), CompanyEcoIndex.EcoIndex.GOOD.getIndex());

        companyEcoIndex.decreaseEcoIndex(10);
        Assert.assertEquals(companyEcoIndex.getEcoIndex().getPoints(), 80);
        Assert.assertEquals(companyEcoIndex.getEcoIndex().getIndex(), CompanyEcoIndex.EcoIndex.GOOD.getIndex());

        companyEcoIndex.decreaseEcoIndex(1);
        Assert.assertEquals(companyEcoIndex.getEcoIndex().getPoints(), 79);
        Assert.assertEquals(companyEcoIndex.getEcoIndex().getIndex(), CompanyEcoIndex.EcoIndex.MODERATE.getIndex());
    }

    @Test
    public void increaseEcoIndexTest () {
        CompanyEcoIndex companyEcoIndex = new CompanyEcoIndex();
        companyEcoIndex.calculateAll();

        companyEcoIndex.increaseEcoIndex(10);
        Assert.assertEquals(companyEcoIndex.getEcoIndex().getPoints(), 100);
        Assert.assertEquals(companyEcoIndex.getEcoIndex().getIndex(), CompanyEcoIndex.EcoIndex.GOOD.getIndex());

        companyEcoIndex.decreaseEcoIndex(50);
        companyEcoIndex.increaseEcoIndex(10);
        Assert.assertEquals(companyEcoIndex.getEcoIndex().getPoints(), 60);
        Assert.assertEquals(companyEcoIndex.getEcoIndex().getIndex(), CompanyEcoIndex.EcoIndex.MODERATE.getIndex());
    }

}