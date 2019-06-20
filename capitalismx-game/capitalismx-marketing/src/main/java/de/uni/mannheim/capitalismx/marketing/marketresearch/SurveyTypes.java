package de.uni.mannheim.capitalismx.marketing.marketresearch;

import de.uni.mannheim.capitalismx.utils.data.Range;
/**
 * @author duly
 */
public enum SurveyTypes {

    PERSONAL_INTERVIEWS("PInt", 1.5, 1, new Range(0, 0), 2, 2, 1, -2, 2, 0, -2, 2),
    TELEPHONE_INTERVIEWS("TInt", 1.25, 3, new Range(-0.1, 0.1), 1, -1, 0, -1, 1, 1, -1, 1),
    MAIL_SURVEYS("MSur", 1.1, 7, new Range(-0.2, 0.2), -1, -1, -1, 0, -2, -1, 1, -1),
    ONLINE_SURVEYS("OSur", 1, 12, new Range(-0.4, 0.4), -2, -1, -1, 0, -2, -1, 2, -2);

    private String name;

    private double influencePrice;
    private int time;
    private Range influenceDataQuality;

    private int explainComplexIssue;
    private int demonstrateTrialProducts;
    private int responseRate;
    private int influenceOfInterviewer;
    private int possibilityOfAskingQuestions;
    private int lengthOfFieldPhase;
    private int cost;
    private int qualityOfData;

    SurveyTypes(String name, double influencePrice, int time, Range influenceDataQuality, int explainComplexIssue, int demonstrateTrialProducts, int responseRate, int influenceOfInterviewer, int possibilityOfAskingQuestions, int lengthOfFieldPhase, int cost, int qualityOfData) {
        this.cost = cost;
        this.name = name;
        this.influencePrice = influencePrice;
        this.influenceDataQuality = influenceDataQuality;

        this.explainComplexIssue = explainComplexIssue;
        this.demonstrateTrialProducts = demonstrateTrialProducts;
        this.responseRate = responseRate;
        this.influenceOfInterviewer = influenceOfInterviewer;
        this.possibilityOfAskingQuestions = possibilityOfAskingQuestions;
        this.lengthOfFieldPhase = lengthOfFieldPhase;
        this.qualityOfData = qualityOfData;
    }

    public String getName() {
        return name;
    }

    public double getInfluencePrice() {
        return influencePrice;
    }

    public int getTime() {
        return time;
    }

    public Range getInfluenceDataQuality() {
        return influenceDataQuality;
    }

    public int getExplainComplexIssue() {
        return explainComplexIssue;
    }

    public int getDemonstrateTrialProducts() {
        return demonstrateTrialProducts;
    }

    public int getResponseRate() {
        return responseRate;
    }

    public int getInfluenceOfInterviewer() {
        return influenceOfInterviewer;
    }

    public int getPossibilityOfAskingQuestions() {
        return possibilityOfAskingQuestions;
    }

    public int getLengthOfFieldPhase() {
        return lengthOfFieldPhase;
    }

    public int getCost() {
        return cost;
    }

    public int getQualityOfData() {
        return qualityOfData;
    }
}
