package de.uni.mannheim.capitalismx.marketing.domain;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Defines media for Campaigns.
 * @see Campaign
 * @author duly
 */
public enum Media {

    NEWSPAPER("Newspaper", "media.newspaper", 5000), TELEVISION("Television", "media.television", 10000), ONLINE("Online", "media.online", 100000), NONE("",  "media.none", 1000), NONE2("", "media.none", 10000);

    private String name;
    private String propertyKey;
    private int cost;

    Media(String name, String propertyKey, int cost) {
        this.name = name;
        this.propertyKey=propertyKey;
        this.cost = cost;
    }

    /**
     * @return Returns the cost of the media.
     */
    public int getCost() {
        return cost;
    } 

    public String getName() {
        return name;
    }
    
    public String getLocalizedName(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("marketing-module", locale);
        return bundle.getString(propertyKey);
    }

    public String toString() {
        return name;
    }
}
