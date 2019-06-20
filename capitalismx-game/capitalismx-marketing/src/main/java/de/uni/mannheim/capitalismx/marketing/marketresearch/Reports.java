package de.uni.mannheim.capitalismx.marketing.marketresearch;
/**
 * @author duly
 */
public enum Reports {

    PRICE_SENSITIVE_REPORT("price sensitive report", 2500, 3200),
    CUSTOMER_SATISFACTION_REPORT("customer satisfaction report", 2000, 2500),
    MARKET_STATISTIC_RESEARCH_REPORT("market statistic research", 1000, 1150);

    private String name;
    private int internalPrice;
    private int externalPrice;

    Reports(String name, int internalPrice, int externalPrice) {
        this.name = name;
        this.internalPrice = internalPrice;
        this.externalPrice = externalPrice;
    }

    public String getName() {
        return name;
    }

    public int getInternalPrice() {
        return internalPrice;
    }

    public int getExternalPrice() {
        return externalPrice;
    }
}
