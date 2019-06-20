package de.uni.mannheim.capitalismx.marketing.domain;
/**
 * @author duly
 */
public enum PressRelease {

    PRIVACY_AND_SECURITY_EFFORTS("Privacy and security efforts", 200),
    AFFORDABLE_PRICES("Affordable prices", 100),
    GUARANTEED_DELIVERY_TIMES("Guaranteed delivery times", 100),
    APOLOGY("apology", 150);

    private String name;
    private int cost;

    PressRelease(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }
}
