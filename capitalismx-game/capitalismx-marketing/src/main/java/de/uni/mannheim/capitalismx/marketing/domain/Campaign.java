package de.uni.mannheim.capitalismx.marketing.domain;


import java.util.ArrayList;
import java.util.List;

/**
 * See table at p.55
 * @author duly
 */
public enum Campaign {

    SOCIAL_CAMPAIGN_1("CSR", Action.SOCIAL_ENGAGEMENT, Media.NONE, new double[]{0, 1, 2, 5, 100}, new double[]{0, 1, 2, 3, 4}),
    SOCIAL_CAMPAIGN_2("Support refugee projects", Action.SOCIAL_ENGAGEMENT, Media.NONE, new double[]{0, 1, 2}, new double[]{0, 1, 2}),

    MKT_CAMPAIGN_1("Promote environmental friendly supplier", Action.MARKETING_CAMPAIGNS, Media.NEWSPAPER, new double[]{0, 1, 2}, new double[]{0, 1, 1.5}),
    MKT_CAMPAIGN_2("Promote environmental friendly supplier", Action.MARKETING_CAMPAIGNS, Media.TELEVISION, new double[]{0, 1, 2}, new double[]{0, 1.5, 2}),
    MKT_CAMPAIGN_3("Promote environmental friendly supplier", Action.MARKETING_CAMPAIGNS, Media.ONLINE, new double[]{0, 1, 2}, new double[]{0, 2, 2.5}),

    MKT_CAMPAIGN_4("Promote environmental friendly production", Action.MARKETING_CAMPAIGNS, Media.NEWSPAPER, new double[]{0, 1, 2}, new double[]{0, 1, 1.5}),
    MKT_CAMPAIGN_5("Promote environmental friendly production", Action.MARKETING_CAMPAIGNS, Media.TELEVISION, new double[]{0, 1, 2}, new double[]{0, 1.5, 2}),
    MKT_CAMPAIGN_6("Promote environmental friendly production", Action.MARKETING_CAMPAIGNS, Media.ONLINE, new double[]{0, 1, 2}, new double[]{0, 2, 2.5}),

    MKT_CAMPAIGN_7("Green marketing campaign", Action.MARKETING_CAMPAIGNS, Media.NEWSPAPER, new double[]{0, 1, 2, 3, 4}, new double[]{0, 1, 2, 3, 3.5}),
    MKT_CAMPAIGN_8("Green marketing campaign", Action.MARKETING_CAMPAIGNS, Media.TELEVISION, new double[]{0, 1, 2, 3, 4}, new double[]{0, 1.5, 3, 4.5, 5}),
    MKT_CAMPAIGN_9("Green marketing campaign", Action.MARKETING_CAMPAIGNS, Media.ONLINE, new double[]{0, 1, 2, 3, 4}, new double[]{0, 2, 4, 6, 6.5}),

    MKT_CAMPAIGN_10("Diversity campaign", Action.MARKETING_CAMPAIGNS, Media.NEWSPAPER, new double[]{0, 1, 2}, new double[]{0, 0.5, 1}),
    MKT_CAMPAIGN_11("Diversity campaign", Action.MARKETING_CAMPAIGNS, Media.TELEVISION, new double[]{0, 1, 2}, new double[]{0, 1, 1.5}),
    MKT_CAMPAIGN_12("Diversity campaign", Action.MARKETING_CAMPAIGNS, Media.ONLINE, new double[]{0, 1, 2}, new double[]{0, 1.5, 2}),

    MKT_CAMPAIGN_13("Promote products", Action.MARKETING_CAMPAIGNS, Media.NEWSPAPER, new double[]{0, 1, 2, 3, 4}, new double[]{0, 1.5, 3, 4.5, 5}),
    MKT_CAMPAIGN_14("Promote products", Action.MARKETING_CAMPAIGNS, Media.TELEVISION, new double[]{0, 1, 2, 3, 4}, new double[]{0, 2, 4, 6, 6.5}),
    MKT_CAMPAIGN_15("Promote products", Action.MARKETING_CAMPAIGNS, Media.ONLINE, new double[]{0, 1, 2, 3, 4}, new double[]{0, 2.5, 5.5, 8, 8.5});

    private String name;

    // pair of requirement and points
    private double[] requirement;
    private double[] points;

    private Action action;
    private Media media;

    Campaign(String name, Action action, Media media, double[] requirement, double[] points) {
        this.action = action;
        this.name = name;
        this.points = points;
        this.requirement = requirement;
        this.media = media;
    }

    public String getName() {
        return name;
    }

    public Action getAction() {
        return action;
    }

    public Media getMedia() {
        return media;
    }

    public double[] getPoints() {
        return points;
    }

    public double[] getRequirement() {
        return requirement;
    }

    public List<Campaign> getCampaingsByName(String name) {
        Campaign[] campaigns = Campaign.values();
        List<Campaign> filteredList = new ArrayList<>();
        for(Campaign c : campaigns) {
            if(c.getName().equals(name)) {
                filteredList.add(c);
            }
        }
        return filteredList;
    }







}
