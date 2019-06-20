package de.uni.mannheim.capitalismx.marketing.department;

import de.uni.mannheim.capitalismx.marketing.domain.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DepartmentTest {

    private MarketingDepartment department;

    private static final Logger logger = LoggerFactory.getLogger(DepartmentTest.class);

    @BeforeMethod
    public void setup() {
        department = new MarketingDepartment();

    }

    @Test
    public void companyImageScoreTest() {
        department.resetIssuedActions();
        String[] campaignNames = department.getCampaignNames();
        department.startCampaign(campaignNames[3], Media.ONLINE);
        department.startCampaign(campaignNames[3], Media.ONLINE);
        department.startCampaign(campaignNames[4], Media.ONLINE);
        department.startCampaign(campaignNames[4], Media.ONLINE);
        department.startCampaign(campaignNames[5], Media.ONLINE);
        department.startCampaign(campaignNames[5], Media.ONLINE);
        department.startCampaign(campaignNames[5], Media.ONLINE);
        department.startCampaign(campaignNames[5], Media.ONLINE);
        department.startCampaign(campaignNames[5], Media.ONLINE);

        department.startCampaign(campaignNames[1], Media.NONE);

        double score = department.getCompanyImageScore();
        logger.debug(""+score);
        System.out.println(score);
        Assert.assertTrue(score <= 100);
        Assert.assertTrue(score > 0);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void companyImageScoreTestException() {
        department.resetIssuedActions();
        String[] campaignNames = department.getCampaignNames();
        department.startCampaign(campaignNames[3], Media.ONLINE);
        department.startCampaign(campaignNames[1], Media.NONE);
        department.startCampaign(campaignNames[0], Media.ONLINE);

        double score = department.getCompanyImageScore();
        logger.debug(""+score);
        Assert.assertTrue(score <= 100);
        Assert.assertTrue(score > 0);
    }


}
