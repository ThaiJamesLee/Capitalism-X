package de.uni.mannheim.capitalismx.marketing.domain;
/**
 * Defines media for Campaigns.
 * @see Campaign
 * @author duly
 */
public enum Media {

    NEWSPAPER("Newspaper", 5000), TELEVISION("Television", 10000), ONLINE("Online", 100000), NONE("", 1000);

    private String name;
    private int cost;

    Media(String name, int cost) {
        this.name = name;
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

    public String toString() {
        return name;
    }
}
