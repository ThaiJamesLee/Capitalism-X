package de.uni.mannheim.capitalismx.marketing.marketresearch;

import de.uni.mannheim.capitalismx.utils.data.Range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * This class defines the market research.
 * One instance can only contain one report / table.
 * For each new research a new MarketResearch object must be created.
 * @author duly
 */
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


    /**
     * Saves the price sensitivity report in the table variable.
     * @param values Is a map of String and Double that maps the name of the corresponding value. E.g. sales price: 100.
     * @return Returns the cost of the price sensitivity report.
     */
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

    /**
     * TODO Remove this when test not needed.
     * Print the table.
     */
    public void printTable() {
        for(Map.Entry<String, List<Double>> entry : table.entrySet()) {
            System.out.print(entry.getKey() + "; ");
            for(Double d : entry.getValue()) {
                System.out.print(d + "; ");
            }
            System.out.println();
        }
    }

    /**
     *
     * @return Returns the table.
     */
    public Map<String, List<Double>> getTable() {
        return table;
    }
}
