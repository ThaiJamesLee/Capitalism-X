package de.uni.mannheim.capitalismx.ui.controller.module.marketing;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.marketing.marketresearch.MarketResearch;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.marketing.MarketResearchViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.utils.PopOverFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * Controller of the MarketResearch Module from the Marketing View. 
 * 
 * @author Alex
 *
 */

public class MarketResearchListController extends GameModuleController {

	@FXML
	private ListView<MarketResearch> reportsList;

	@FXML
	private Button conductNewBtn;
	
	private PopOver popover;
	
	public MarketResearchListController() {
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		

		reportsList.setCellFactory(reportsList -> new MarketResearchViewCell(reportsList));

//		for(PressRelease pr : pressReleases) {
//			pressReleaseListObservable.add(pr);
//		}
		
		PopOverFactory helper = new PopOverFactory();
		helper.createStandardOverlay("fxml/overlay/mkt_newMarketResearch_options.fxml");
		popover = helper.getPopover();
		
		conductNewBtn.setOnAction(e -> {
			showPopover();
			
		});
		//GameState.getInstance().getMarketingDepartment().getPressReleases().addPropertyChangeListener(marketingEventListener);
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
}
