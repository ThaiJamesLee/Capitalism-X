package de.uni.mannheim.capitalismx.marketing.department;

import de.uni.mannheim.capitalismx.marketing.consultancy.ConsultancyType;
import de.uni.mannheim.capitalismx.marketing.domain.Action;
import de.uni.mannheim.capitalismx.marketing.domain.Campaign;
import de.uni.mannheim.capitalismx.marketing.domain.Media;
import de.uni.mannheim.capitalismx.marketing.domain.PressRelease;
import de.uni.mannheim.capitalismx.marketing.marketresearch.Reports;
import de.uni.mannheim.capitalismx.marketing.marketresearch.SurveyTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * p. 54 - 63
 * @author duly
 */
public class MarketingDepartment {

    private static final Logger logger = LoggerFactory.getLogger(MarketingDepartment.class);

    private static final String[] SOCIAL_ENGAGEMENT = {"CSR", "Support refugee projects"};
    private static final String[] MARKETING_CAMPAIGN = {"Promote environmental friendly supplier", "Green marketing campaign", "Diversity campaign", "Promote products"};

    private static final String[] CAMPAIGN_NAMES = {"CSR", "Support refugee projects", "Promote environmental friendly supplier", "Green marketing campaign", "Diversity campaign", "Promote products"};

    // campaigns that the department issued
    private Map<String, List<Campaign>> issuedActions;

    // press releases the company made
    private List<PressRelease> pressReleases;

    // consultancy services the company used
    private List<ConsultancyType> consultancies;

    private static MarketingDepartment instance = null;

    private MarketingDepartment() {
        init();
    }

    private void init() {
        // initialize map
        issuedActions = new HashMap<>();
        for (String c : CAMPAIGN_NAMES) {
            issuedActions.put(c, new ArrayList<>());
        }

        pressReleases = new ArrayList<>();
        consultancies = new ArrayList<>();
    }

    public static MarketingDepartment getInstance() {
        if(instance == null) {
            instance = new MarketingDepartment();
        }
        return instance;
    }

    /**
     *
     * Adds the press release into the pressRelease list.
     * @param release The Press Release that should be done.
     */
    public void makePressRelease(PressRelease release) {
        pressReleases.add(release);
    }

    /**
     *
     * @param campaignName Name of the campaign that you want to start.
     * @param media The type of media you want to use.
     * @return Returns the cost of the campaign.
     * @throws NullPointerException Throws Nullpointer if there is no campaign with the specified name and media tuple.
     */
    public int startCampaign(String campaignName, Media media) throws NullPointerException{
        Campaign c = Campaign.getCampaignsByNameAndMedia(campaignName, media);

        if (c != null) {
            issuedActions.get(campaignName).add(c);
            return c.getMedia().getCost();
        } else {
            throw new NullPointerException("There exist not such a campaign!");
        }
    }

    public void useConsultantService(ConsultancyType con) {
        consultancies.add(con);
    }

    public double getCompanyImageScore() {
        double originalScore = 0.0;
        List<Campaign> marketing = new ArrayList<>();
        List<Campaign> social = new ArrayList<>();

        for (Map.Entry<String, List<Campaign>> entry : issuedActions.entrySet()) {
            for (Campaign c : entry.getValue()) {
                if (c.getAction().equals(Action.MARKETING_CAMPAIGNS)) {
                    marketing.add(c);
                } else {
                    if (c.getAction().equals(Action.SOCIAL_ENGAGEMENT)) {
                        social.add(c);
                    }
                }
            }
        }

        originalScore = getMarketingPoints(marketing) + getSocialPoints(social);

        return Math.pow(originalScore, 2) / 7.84;
    }

    private double getSocialPoints(List<Campaign> social) {
        //TODO CSR is unclear

        List<Campaign> refugee = new ArrayList<>();
        for (Campaign c : social) {
            if (c.getName().equals("Support refugee projects")) {
                refugee.add(c);
            }
        }

        int numRefugee = refugee.size();

        Campaign refugeeCampaign = Campaign.getCampaignsByNameAndMedia("Support refugee projects", Media.NONE);

        double points = refugeeCampaign.getPointsByRequirement(numRefugee);

        return points;
    }

    private double getMarketingPoints(List<Campaign> marketing) {
        double newspaper = 0.0;
        double tv = 0.0;
        double online = 0.0;

        List<Campaign> allMarketing = Campaign.getMarketingCampaigns();
        for (Campaign c : allMarketing) {
            switch (c.getMedia()) {
                case ONLINE: online += getPointsByCampaignAndMedia(marketing, c.getName(), c.getMedia()); break;
                case NEWSPAPER: newspaper += getPointsByCampaignAndMedia(marketing, c.getName(), c.getMedia()); break;
                case TELEVISION: tv += getPointsByCampaignAndMedia(marketing, c.getName(), c.getMedia()); break;
                default: break;
            }
        }
        return newspaper + tv + online;
    }

    private double getPointsByCampaignAndMedia(List<Campaign> campaigns, String name, Media media) {
        List<Campaign> filteredByMedia = new ArrayList<>();

        // Only get the campaigns with specified name and media
        for (Campaign c : campaigns) {
            if (c.getMedia().equals(media) && c.getName().equals(name)) {
                filteredByMedia.add(c);
            }
        }

        // number of campaigns issued with specified name and media
        int numberCampaigns = filteredByMedia.size();
        Campaign prime = Campaign.getCampaignsByNameAndMedia(name, media);

        // calculate the points
        double points = 0.0;
        try {
            points = prime.getPointsByRequirement(numberCampaigns);
        } catch (NullPointerException e) {
            logger.error("Not exsisting campaign.");
        }

        return points;
    }

    public List<PressRelease> getPressReleases() {
        return pressReleases;
    }

    /**
     *
     * @return Returns total cost of press releases.
     */
    public double getTotalPressReleaseCosts() {
        double cost = 0.0;
        for (PressRelease pr : pressReleases) {
            cost += pr.getCost();
        }
        return cost;
    }

    /**
     *
     * @return Returns total cost of campaigns.
     */
    public double getTotalCampaignCosts() {
        double cost = 0.0;

        for (Map.Entry<String, List<Campaign>> entry: issuedActions.entrySet()) {
            List<Campaign> campaigns = entry.getValue();

            for(Campaign c : campaigns) {
                cost += c.getMedia().getCost();
            }
        }

        return cost;
    }

    public double getTotalConsultancyCosts() {
        double cost = 0.0;

        for (ConsultancyType con : consultancies) {
            cost += con.getCost();
        }

        return cost;
    }

    public String[] getCampaignNames() {
        return CAMPAIGN_NAMES;
    }

    /**
     * Delete all campaigns that were issued.
     */
    public void resetIssuedActions() {
        for (Map.Entry<String, List<Campaign>> entry: issuedActions.entrySet()) {
            entry.getValue().clear();
        }
    }

    /**
     *
     * @return Returns all consultancies.
     */
    public ConsultancyType[] getConsultancies() {
        return ConsultancyType.values();
    }

    /**
     *
     * @return Return all possible reports for market research.
     */
    public Reports[] getMarketResearchReports() {
        return Reports.values();
    }

    public SurveyTypes[] getMarketResearchSurveyTypes() {
        return SurveyTypes.values();
    }



}
