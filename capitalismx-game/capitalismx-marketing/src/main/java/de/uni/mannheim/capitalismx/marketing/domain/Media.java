package de.uni.mannheim.capitalismx.marketing.domain;

public enum Media {

    NEWSPAPER("Newspaper"), TELEVISION("Television"), ONLINE("Online"), NONE("");

    private String name;

    Media(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
