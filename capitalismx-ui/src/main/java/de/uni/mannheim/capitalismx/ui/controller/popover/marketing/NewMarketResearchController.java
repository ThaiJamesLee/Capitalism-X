package de.uni.mannheim.capitalismx.ui.controller.popover.marketing;


import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.control.ToggleSwitch;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.marketing.marketresearch.MarketResearch;
import de.uni.mannheim.capitalismx.marketing.marketresearch.Reports;
import de.uni.mannheim.capitalismx.marketing.marketresearch.SurveyTypes;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import de.uni.mannheim.capitalismx.ui.controller.module.marketing.MarketResearchListController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * Controller of the overlay that pops up when starting a new {@link MarketResearch}
 * 
 * @author Alex
 *
 */

public class NewMarketResearchController implements UpdateableController {
    @FXML 
    ToggleSwitch externToggle;
    
    @FXML 
    ComboBox<SurveyTypes> dataCollectCombo;
    
    @FXML 
    ComboBox<Reports> reportTypeCombo;
	
   
    @FXML 
    Label costLbl;
    
    @FXML 
    Button runBtn; 
    
    
    @Override
    public void update() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();

        externToggle.setDisable(true);
        
        dataCollectCombo.getItems().addAll(controller.getAllSurveyTypes());
        dataCollectCombo.getSelectionModel().select(0);
        
        reportTypeCombo.getItems().addAll(controller.getAllMarketResearchReports());
        reportTypeCombo.getSelectionModel().select(0);
                
    	runBtn.setOnAction(e -> {
    		//conduct new MarketResearch
    		boolean internal = externToggle.isSelected();
    		SurveyTypes survey = dataCollectCombo.getSelectionModel().getSelectedItem();
    		Reports report = reportTypeCombo.getSelectionModel().getSelectedItem();
    		
    		//TODO Where to collect neeeded data (metrics? - not clearly defined in the report, some not mentioned at all...)
    		Map<String, Double> data  = new HashMap<String, Double>();
    		data.put("key1", 0.0);
    		data.put("key2", 0.0);
	
    		controller.conductMarketResearch(internal, report, survey, data);
    		
    		//update MarketResearch Module
    		updateMarketResearchsList();
    	});	
    }

    /**
     * Enables the button which can be used create an internal MarketResearch instead of the default external.
     */
    public void enableInternalMarketResearch() {
    	externToggle.setDisable(false);
    }
    
    /**
     * Function updates the MarketResearchs ListView in the {@link MarketResearchListController} Module
     */
    private void updateMarketResearchsList() {

    	MarketResearchListController uiController = (MarketResearchListController) UIManager.getInstance().getGameView(GameViewType.MARKETING).getModule(UIElementType.MARKETING_MARKETRESEARCH).getController();
		uiController.updateList();
		uiController.hidePopover();
    }

}