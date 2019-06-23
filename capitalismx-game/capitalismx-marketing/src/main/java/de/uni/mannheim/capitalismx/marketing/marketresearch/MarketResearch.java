package de.uni.mannheim.capitalismx.marketing.marketresearch;

import de.uni.mannheim.capitalismx.utils.data.Range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketResearch {

    private boolean isInternal;
    private Reports report;
    private SurveyTypes surveyType;

    private Map<String, List<Double>> table;

    public MarketResearch(boolean isInternal, SurveyTypes surveyType) {
        this.table = new HashMap<>();
        this.isInternal = isInternal;
        this.surveyType = surveyType;
    }

    public double conductPriceSensitivityReport(Map<String, Double> values) {
        double cost = 0.0;
        this.report = Reports.PRICE_SENSITIVE_REPORT;
        Range range = new Range(-20, 20);


        for(Map.Entry<String, Double> entry : values.entrySet()) {
            List<Double> priceChange = new ArrayList<>();

            for (int i = (int)range.getLowerBound(); i <= range.getUpperBound(); i+=5) {
                double refVal = entry.getValue();
                if (i < 0) {
                    double newVal = ((1.0 + i) / 100) * refVal;
                    priceChange.add((1 - newVal / refVal) / 100);
                }
                else if (i > 0) {
                    double newVal = ((1.0 + i) / 100) * refVal;
                    priceChange.add(((100 * newVal)/ refVal) - 100);
                } else {
                    priceChange.add(refVal);
                }
            }
            table.put(entry.getKey(), priceChange);
        }
        if(isInternal) {
            cost = report.getInternalPrice();
        } else {
            cost = report.getExternalPrice();
        }

        return surveyType.getInfluencePrice() *  cost;
    }

    public void printTable() {
        for(Map.Entry<String, List<Double>> entry : table.entrySet()) {
            System.out.print(entry.getKey() + "; ");
            for(Double d : entry.getValue()) {
                System.out.print(d + "; ");
            }
            System.out.println();
        }
    }

    public Map<String, List<Double>> getTable() {
        return table;
    }
}
