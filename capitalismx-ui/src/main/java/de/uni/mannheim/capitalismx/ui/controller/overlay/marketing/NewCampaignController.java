package de.uni.mannheim.capitalismx.ui.controller.overlay.marketing;


import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.customer.CustomerSatisfaction;
import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.marketing.department.MarketingDepartment;
import de.uni.mannheim.capitalismx.marketing.domain.Campaign;
import de.uni.mannheim.capitalismx.marketing.domain.Media;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameModule;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.components.marketing.CampaignsCell;
import de.uni.mannheim.capitalismx.ui.controller.module.marketing.CampaignsOverviewController;
import de.uni.mannheim.capitalismx.ui.controller.module.marketing.MarketingOverviewController;
import de.uni.mannheim.capitalismx.ui.controller.overlay.GameOverlayController;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Controller of the overlay that pops up when starting a new {@link Campaign}
 * 
 * @author Alex
 *
 */

public class NewCampaignController extends GameOverlayController {
	//TODO Social: CSR mit prozenten des Umsatzes statt 10000 als festem Betrag

    @FXML 
    Button csrBtn;
    
    @FXML 
    Button refugeeBtn;
	
    @FXML
    ListView<Campaign> newCampaignList;
    
    @FXML 
    Label campCostLabel;
    
    @FXML 
    Button runBtn; 
    
    
    @Override
    public void update() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        newCampaignList.setCellFactory(campaignListView -> new CampaignsCell());
    
        List<Campaign> campaignOptions = controller.getAllMarketingCampaigns();
   
        for(Campaign c : campaignOptions) {
        	newCampaignList.getItems().add(c);		
        }


    	//enable Publish Button only if a Campaign is Selected	
    	runBtn.disableProperty().bind(Bindings.isEmpty(newCampaignList.getSelectionModel().getSelectedItems()));
    	
    	runBtn.setOnAction(e -> {
    		//start new Campaign
    		Campaign camp = newCampaignList.getSelectionModel().getSelectedItem();
    		controller.makeCampaign(camp.getName(), camp.getMedia());
    		
    		//update Campaigns Module
    		updateCampaignsList();
    	});
    	
    	//buttons for starting social actions
    	csrBtn.setOnAction(e -> {
    		controller.makeCampaign(Campaign.SOCIAL_CAMPAIGN_1.getName(), Media.NONE2);
    		updateCampaignsList();
    	});
    	refugeeBtn.setOnAction(e -> {
    		controller.makeCampaign(Campaign.SOCIAL_CAMPAIGN_2.getName(), Media.NONE);
    		updateCampaignsList();
    	});
    }

    @Override
    public Properties getProperties() {
        return this.properties;
    }

    @Override
    public void updateProperties(Properties properties) {
        this.properties = properties;
    }
    
    /**
     * Function updates the campaigns ListView in the {@link CampaignsOverviewController} Modul
     */
    private void updateCampaignsList() {
		updateCompanyImage();
    	
    	CampaignsOverviewController uiController = (CampaignsOverviewController) UIManager.getInstance().getGameView(GameViewType.MARKETING).getModule(UIElementType.MARKETING_CAMPAIGNS).getController();
		uiController.updateList();
		uiController.hidePopover();
		
		//update CompanyImage View
//		MarketingOverviewController mktController = (MarketingOverviewController) UIManager.getInstance().getGameView(GameViewType.MARKETING).getModule(UIElementType.MARKETING_OVERVIEW).getController();
//		mktController.setCompanyImageLabel(text);
		
    }
    
    /**
     * Function that updates the companyImage and EmployerBranding Labels in the Marketing Overview {@link GameModule} 
     */
    //TODO refactor to prevent nullpointer if OverviewModule is not displayed
    private void updateCompanyImage() {    	
    	
    	GameModule modul = UIManager.getInstance().getGameView(GameViewType.MARKETING).getModule(UIElementType.MARKETING_OVERVIEW);
    	
    	if(modul.isActivated()) {
    		GameController controller = GameController.getInstance();
        	double companyImage = controller.computeCompanyImage();
        	double employerBranding =  controller.getEmployerBranding();
        	
        	MarketingOverviewController con = (MarketingOverviewController) modul.getController();
        	con.setCompanyImageLabel(DecimalRound.round(companyImage, 2) + "");
        	con.setEmployerBrandingLabel(DecimalRound.round(employerBranding, 2) + "");		
    	}
    }
}