package de.uni.mannheim.capitalismx.marketing.marketresearch;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author duly
 */
public enum Reports {

    PRICE_SENSITIVE_REPORT("price sensitive report", "report.sensitivity,price", 2500, 3200),
    CUSTOMER_SATISFACTION_REPORT("customer satisfaction report", "report.satisfaction.customer", 2000, 2500),
    MARKET_STATISTIC_RESEARCH_REPORT("market statistic research", "report.statistics.market", 1000, 1150);

    private String name;
    private String propertyKey;
    private int internalPrice;
    private int externalPrice;

    Reports(String name, String  propertyKey,int internalPrice, int externalPrice) {
        this.name = name;
        this.propertyKey = propertyKey;
        this.internalPrice = internalPrice;
        this.externalPrice = externalPrice;
    }

    public String getName() {
        return name;
    }

    public String getLocalizedName(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("marketing-module", locale);
        return bundle.getString(propertyKey);
    }
    
    public int getInternalPrice() {
        return internalPrice;
    }

    public int getExternalPrice() {
        return externalPrice;
    }
}
