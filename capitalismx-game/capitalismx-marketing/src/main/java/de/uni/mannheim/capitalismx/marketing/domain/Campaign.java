package de.uni.mannheim.capitalismx.marketing.domain;

import de.uni.mannheim.capitalismx.marketing.exception.NotSameSizeException;


public class Campaign {

    private String name;

    // pair of requirement and points
    private double requirement;
    private double points;

    private Action action;
    private Media media;

    private Campaign(String name, Action action, double requirement, double points) throws NotSameSizeException {
        this.action = action;
        this.name = name;
        this.points = points;
        this.requirement = requirement;
    }








}
