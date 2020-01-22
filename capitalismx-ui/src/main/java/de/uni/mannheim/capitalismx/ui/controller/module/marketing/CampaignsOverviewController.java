package de.uni.mannheim.capitalismx.ui.controller.module.marketing;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.marketing.domain.Campaign;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.marketing.CampaignsCell;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.utils.PopOverFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;


/**
 * Controller of the Campaigns Module from the Marketing View. 
 * 
 * @author Alex
 *
 */

public class CampaignsOverviewController extends GameModuleController {


	//TODO setCompanyImage in Customer when changed
	//TODO kosten für Kampagne vom Konto abziehen?
	
	@FXML
	private ListView<Campaign> campaignsList;

	@FXML
	private Button newReleaseBtn;
	
	private PopOver popover;


	
	public CampaignsOverviewController() {
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		
		campaignsList.setCellFactory(campaignList -> new CampaignsCell());
		
		updateList();
		
		PopOverFactory helper = new PopOverFactory();
		helper.createStandardOverlay("fxml/overlay/mkt_newCampaign_options.fxml");
		popover = helper.getPopover();
		
		newReleaseBtn.setOnAction(e -> {
			showPopover();
			
		});
	}

	private void showPopover() {
		popover.show(UIManager.getInstance().getStage());
	}
	
	
	public void hidePopover() {
		popover.hide();
	}

	/**
	 * Gets the current list of issued Campaigns from {@link Gamecontroller}
	 * and updates listView
	 */
	public void updateList() {
		GameController controller = GameController.getInstance();
		List<Campaign> campaigns = controller.getIssuedMarketingCampaigns();

		campaignsList.setItems(FXCollections.observableArrayList(campaigns));
	}
}
