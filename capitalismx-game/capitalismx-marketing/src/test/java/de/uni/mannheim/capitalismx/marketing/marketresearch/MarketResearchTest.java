package de.uni.mannheim.capitalismx.marketing.marketresearch;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class MarketResearchTest {

    @Test
    public void conductMarketResearchTest() {
        Map<String, Double> test = new HashMap<>();
        test.put("score_1", 84.0);
        test.put("score_2", 0.0);
        test.put("score_3", 10.0);

        MarketResearch m = new MarketResearch(true, SurveyTypes.MAIL_SURVEYS);
        m.conductPriceSensitivityReport(test);

        Assert.assertEquals(m.getTable().keySet().size(), test.keySet().size());
    }
}
