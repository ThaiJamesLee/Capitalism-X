package de.uni.mannheim.capitalismx.marketing.domain;

public enum Action {

    SOCIAL_ENGAGEMENT("Social engagement"), MARKETING_CAMPAIGNS("Marketing Campaings");

    private String name;

    Action(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
