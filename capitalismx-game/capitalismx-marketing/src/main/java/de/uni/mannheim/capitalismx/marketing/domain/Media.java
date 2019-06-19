package de.uni.mannheim.capitalismx.marketing.domain;

public enum Media {

    NEWSPAPER("Newspaper", 5000), TELEVISION("Television", 10000), ONLINE("Online", 100000), NONE("", 1000);

    private String name;
    private int cost;

    Media(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public String toString() {
        return name;
    }
}
