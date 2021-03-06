package de.uni.mannheim.capitalismx.ui.controller.module.marketing;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.marketing.department.MarketingDepartment;
import de.uni.mannheim.capitalismx.marketing.marketresearch.MarketResearch;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.marketing.MarketResearchViewCell;
import de.uni.mannheim.capitalismx.ui.controller.popover.marketing.NewMarketResearchController;
import de.uni.mannheim.capitalismx.ui.eventlistener.MarketingEventListener;
import de.uni.mannheim.capitalismx.ui.util.PopOverFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * Controller of the MarketResearch Module from the Marketing View. 
 * 
 * @author Alex
 *
 */

public class MarketResearchListController implements Initializable {

	@FXML
	private ListView<MarketResearch> reportsList;

	@FXML
	private Button conductNewBtn;
	
	private PopOver popover;
	
	private NewMarketResearchController popoverController;
	
	public MarketResearchListController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		
		reportsList.setCellFactory(reportsList -> new MarketResearchViewCell(reportsList));

		updateList();
		
		PopOverFactory helper = new PopOverFactory();
		helper.createStandardOverlay("fxml/popover/mkt_newMarketResearch_options.fxml");
		popover = helper.getPopover();
		popoverController = (NewMarketResearchController) helper.getPopoverController();
		
		conductNewBtn.setOnAction(e -> {
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
	 * Gets the current list of published Press Releases from {@link Gamecontroller}
	 * and updates listView
	 */
	public void updateList() {
		GameController controller = GameController.getInstance();
		List<MarketResearch> conductedReports = controller.getConductedMarketResearch();

		reportsList.setItems(FXCollections.observableArrayList(conductedReports));
	}
	
	/**
	 * Enables the button for choosing Internal Market Research in the overlay/popover
	 */
	public void enableInternalMarketResearch (){
		popoverController.enableInternalMarketResearch();
	}
}
