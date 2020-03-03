package de.uni.mannheim.capitalismx.marketing.domain;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Defines the press releases.
 * @author duly
 */
public enum PressRelease {

	PRIVACY_AND_SECURITY_EFFORTS("Privacy & security efforts", "pressrelease.privacy.security", 200),
    AFFORDABLE_PRICES("Affordable prices", "pressrelease.affordable.prices", 100),
    GUARANTEED_DELIVERY_TIMES("Guaranteed delivery times", "pressrelease.guaranteed.delivery",100),
    APOLOGY("Apology", "pressrelease.apology",150);

    private String name;
    private String propertyKey;
    private int cost;

    PressRelease(String name,  String nameProperty,int cost) {
        this.name = name;
        this.propertyKey = nameProperty;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }
    
    public String getLocalizedName(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("marketing-module", locale);
        return bundle.getString(propertyKey);
    }

    /**
     *
     * @return Returns the cost of the press release.
     */
    public int getCost() {
        return cost;
    }
}
