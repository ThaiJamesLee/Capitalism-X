package de.uni.mannheim.capitalismx.marketing.department;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.department.LevelingMechanism;
import de.uni.mannheim.capitalismx.domain.exception.InconsistentLevelException;
import de.uni.mannheim.capitalismx.domain.exception.LevelingRequirementNotFulFilledException;
import de.uni.mannheim.capitalismx.marketing.consultancy.ConsultancyType;
import de.uni.mannheim.capitalismx.marketing.department.skill.MarketingSkill;
import de.uni.mannheim.capitalismx.marketing.domain.Action;
import de.uni.mannheim.capitalismx.marketing.domain.Campaign;
import de.uni.mannheim.capitalismx.marketing.domain.Media;
import de.uni.mannheim.capitalismx.marketing.domain.PressRelease;
import de.uni.mannheim.capitalismx.marketing.marketresearch.MarketResearch;
import de.uni.mannheim.capitalismx.marketing.marketresearch.Reports;
import de.uni.mannheim.capitalismx.marketing.marketresearch.SurveyTypes;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportDouble;

/**
 * This class represents the marketing department.
 * You can issue press releases, start campaigns, buy consultancy services, and conduct different types of market researches.
 * p. 54 - 63
 * @author duly
 */
public class MarketingDepartment extends DepartmentImpl {

	//TODO CSR campaigns score relative to profit of the year, 
	//the others on quantitiy of this type of campaign per year, mechanism cant deal with this yet...
	//TODO Consultancy is seriously flawed...
	
    private static final Logger logger = LoggerFactory.getLogger(MarketingDepartment.class);

    private static final String[] SOCIAL_ENGAGEMENT = {"CSR", "Support refugee projects"};
    private static final String[] MARKETING_CAMPAIGN = {"Promote environmental friendly supplier", "Promote environmental friendly production", "Green marketing campaign", "Diversity campaign", "Promote products"};

    private static final String[] CAMPAIGN_NAMES = {"CSR", "Support refugee projects", "Promote environmental friendly supplier", "Promote environmental friendly production", "Green marketing campaign", "Diversity campaign", "Promote products"};

    // campaigns that the department issued
    private Map<String, List<Campaign>> issuedActions;
    
    //campaigns that the department issued orderer by release date
    private List<Campaign> campaignsWithDates;

	// press releases the company made
    private List<PressRelease> pressReleases;

    // consultancy services the company used
    private List<ConsultancyType> consultancies;

    // list of issued market researches
    private List<MarketResearch> marketResearches;
    
    //TODO kann das weg?
    private double employerBranding;

  //leveling related stuff
    private static final String LEVELING_PROPERTIES = "marketing-leveling-definition";
    private static final String MAX_LEVEL_PROPERTY = "marketing.department.max.level";
    private static final String SKILL_COST_PROPERTY_PREFIX = "marketing.skill.cost.";
    
    private PropertyChangeSupportDouble currentLevel;
    
    
    private static MarketingDepartment instance = null;

       
    private MarketingDepartment() {
        super("Marketing");
        init();
    }

    private void init() {	
        this.initProperties();
        this.initSkills();
        
        // initialize map
        issuedActions = new HashMap<>();
        for (String c : CAMPAIGN_NAMES) {
        	issuedActions.put(c, new ArrayList<>());
        }

        campaignsWithDates = new ArrayList<>();
        pressReleases = new ArrayList<>();
        consultancies = new ArrayList<>();
        marketResearches = new ArrayList<>();
    }
    
	/**
	 * Init default values from properties.
	 */
    private void initProperties() {
        setMaxLevel(Integer.parseInt(ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(MAX_LEVEL_PROPERTY)));
        this.currentLevel = new PropertyChangeSupportDouble();
        currentLevel.setPropertyChangedName("level");
        currentLevel.setValue(0.0);
    }

	/**
	 * Initialize {@link MarketingSkill}s.
	 */
    private void initSkills() {
        Map<Integer, Double> costMap = initCostMap();
        try {
            setLevelingMechanism(new LevelingMechanism(this, costMap));
        } catch (InconsistentLevelException e) {
            String error = "The costMap size " + costMap.size() +  " does not match the maximum level " + this.getMaxLevel() + " of this department!";
            logger.error(error, e);
        }

//        ResourceBundle skillBundle = ResourceBundle.getBundle(LEVELING_PROPERTIES);
        for(int i = 1; i <= getMaxLevel(); i++) {
            skillMap.put(i, new MarketingSkill(i));
        }
    }
 
	/**
	 * Initializes the cost map. This is used for the {@link LevelingMechanism}.
	 */
    private Map<Integer, Double> initCostMap() {
        Map<Integer, Double> costMap = new HashMap<>();
        ResourceBundle bundle = ResourceBundle.getBundle(LEVELING_PROPERTIES);
        for(int i = 1; i <= getMaxLevel(); i++) {
            double cost = Integer.parseInt(bundle.getString(SKILL_COST_PROPERTY_PREFIX + i));
            costMap.put(i, cost);
        }
        return costMap;
    }

    @Override //TODO wirklich nötig?
    public void setLevel(int level) throws LevelingRequirementNotFulFilledException {
        super.setLevel(level);
        this.currentLevel.setValue(new Double(level));
//       this.updateWarehouseSlots();TODO
    }


    public static MarketingDepartment getInstance() {
        if(instance == null) {
            instance = new MarketingDepartment();
        }
        return instance;
    }

    public double getTotalMarketingCosts() {
        double totalCosts = 0.0;

        totalCosts += getTotalConsultancyCosts();
        totalCosts += getTotalCampaignCosts();
        totalCosts += getTotalPressReleaseCosts();
        totalCosts += getTotalMarketResearchCost();
        //TODO Lobbyist ? How should we handle him. Influence the taxRate is a bad design.

        return totalCosts;
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
            campaignsWithDates.add(c);
            return c.getMedia().getCost();
        } else {
            throw new NullPointerException("There exist not such a campaign!");
        }
    }

    /* Campaigns */

    /**
     * Iterates over all campaigns and social engagements and calculates the company image score.
     * @return Returns the Company Image score.
     */
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

    /**
     *
     * @param social List of social campaigns.
     * @return Returns the total points for social engagements.
     */
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

    /**
     *
     * @param marketing List of marketing campaigns.
     * @return Returns the total points for marketing campaigns.
     */
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

    /**
     *
     * @param campaigns List of Campaigns.
     * @param name Name of the campaign.
     * @param media The media used for the campaign.
     * @return Returns the total points for campaigns that meet the parameters name and media.
     */
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
    
    
    /**
     * see report First Team page 30, computes increase of Eco-Campaigns on EcoIndex
     * 
     * @param campaigns
     * @return ecoCampaignPoints
     */
    public double getPointsFromEcoCampaigns(List<Campaign> campaigns) {
    	double ecoPoints = 0;
    	
    	String[] namesEcoCampaigns = {"Promote environmental friendly supplier", "Promote environmental friendly production", "Green marketing campaign"};
    	Media[] mediaEcoCampaigns = {Media.TELEVISION, Media.ONLINE, Media.NEWSPAPER};
    	for (String name : namesEcoCampaigns) {
    		for (Media media : mediaEcoCampaigns) {
    			ecoPoints += getPointsByCampaignAndMedia(campaigns, name, media);
    		}
    	}
    	return ecoPoints;
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

    /**
     *
     * @return Return the campaign names.
     */
    public String[] getCampaignNames() {
        return CAMPAIGN_NAMES;
    }

    /**
     * 
     * @return Returns list of all campaigns that were run
     */
    public List<Campaign> getCampaignsWithDates() {
		return campaignsWithDates;
	}
    
    /**
     * Delete all campaigns that were issued.
     */
    public void resetIssuedActions() {
        for (Map.Entry<String, List<Campaign>> entry: issuedActions.entrySet()) {
            entry.getValue().clear();
        }
    }

    /* Press Release */

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
     * Adds the press release into the pressRelease list.
     * @param release The {@link PressRelease} Press Release that should be done.
     *@return Returns the cost of the 
     */
    public int makePressRelease(PressRelease release) {
        pressReleases.add(release);
        return release.getCost();
    }

    /**
     *
     * @return Returns the list of issued press releases.
     */
    public List<PressRelease> getPressReleases() {
        return pressReleases;
    }


    /**
     *
     * @return Returns all defined press releases.
     */
    public PressRelease[] getAllPossibiblePressReleases() {
        return PressRelease.values();
    }

    /* Consultancy */

    /**
     *
     * @return Returns cost of consultancy taken.
     */
    public double getTotalConsultancyCosts() {
        double cost = 0.0;

        for (ConsultancyType con : consultancies) {
            cost += con.getCost();
        }

        return cost;
    }


    public void useConsultantService(ConsultancyType con) {
        consultancies.add(con);
    }

    /**
     *
     * @return Returns all consultancies.
     */
    public ConsultancyType[] getAllConsultancies() {
        return ConsultancyType.values();
    }

    /**
     * Computes the relatively weakest KPI (supposed) as described in the report of the first team on pages 62 and 63.
     * 
     */
    public  String orderConsultantReport(ConsultancyType conType, Double[] metrics) {
    	//TODO Sven fragen wegen totalSupportQuality...
    	double[] weights = conType.getWeights();
    	
    	String[] metricNames = {"totalSupportQuality", "logisticIndex", "companyImage", "productionTechnology", "manufactureEfficiency", "totalJobSatisfaction"};
    	
    	int minIndex = 0;
    	double min = 100;
    	
    	for(int i = 0; i < metrics.length; i++) {
    		metrics[i] = metrics[i] * weights[i];
    		if(metrics[i] < min) {
    			min = metrics[i];
    			minIndex = i;		
    		}
    	}
    	
    	return metricNames[minIndex];	
    }  
    
    
    /* Market Research */

    /**
     *
     * @return Return all possible reports for market research.
     */
    public Reports[] getMarketResearchReports() {
        return Reports.values();
    }

    /**
     *
     * @return Return all survey types.
     */
    public SurveyTypes[] getMarketResearchSurveyTypes() {
        return SurveyTypes.values();
    }

    /**
     *
     * @param internal Set true if the research is conducted internally.
     * @param report The report type.
     * @param surveyType The survey type.
     * @param data The data (be careful, since they all take the same type, to select the correct report type!)
     * @return Returns the cost.
     */
    public double issueMarketResearch(boolean internal, Reports report, SurveyTypes surveyType, Map<String, Double> data, LocalDate date) {
        MarketResearch mr = new MarketResearch(internal, surveyType, date);
        double cost = 0.0;
        switch (report) {
            case PRICE_SENSITIVE_REPORT:
                mr.conductPriceSensitivityResearch(data);
                marketResearches.add(mr);
                break;
            case CUSTOMER_SATISFACTION_REPORT:
                mr.conductCustomerSatisfactionResearch(data);
                //marketResearches.add(mr);
                logger.error("Not implemented yet.");
                throw new UnsupportedOperationException();
                //break;
            case MARKET_STATISTIC_RESEARCH_REPORT:

                mr.conductMarketStatisticResearch(data);
                //marketResearches.add(mr);
                logger.error("Not implemented yet.");
                throw new UnsupportedOperationException();
                //break;
            default: break;
        }
        return cost;
    }

    public double getTotalMarketResearchCost() {
        double costs = 0.0;
        for(MarketResearch mr : marketResearches) {
            costs += mr.getCost();
        }

        return costs;
    }

    public List<MarketResearch> getMarketResearches() {
        return marketResearches;
    }
    
    public void setEmployerBranding(double employerBranding) {
    	this.employerBranding = employerBranding;
    }

    public static MarketingDepartment createInstance() {
        return new MarketingDepartment();
    }

    public static void setInstance(MarketingDepartment instance) {
        MarketingDepartment.instance = instance;
    }
    
    @Override
    public void registerPropertyChangeListener(PropertyChangeListener listener) {
    	currentLevel.addPropertyChangeListener(listener);
    }
}
