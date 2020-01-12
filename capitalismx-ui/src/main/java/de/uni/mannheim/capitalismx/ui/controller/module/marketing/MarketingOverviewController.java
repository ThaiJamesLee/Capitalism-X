package de.uni.mannheim.capitalismx.ui.controller.module.marketing;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.marketing.domain.PressRelease;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.utils.PopOverFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Controller of the Overview an d Consultancy Module from the Marketing View. 
 * 
 * @author Alex
 *
 */

public class MarketingOverviewController extends GameModuleController {

	//TODO Was tun mit den Consultancies???
	@FXML
	private ListView<PressRelease> pressReleaseList;

	@FXML
	private Label compImValueLbl;
	
	@FXML
	private Label emplBrValueLbl;
	
	@FXML
	private Label consCurrentLbl;
	
	@FXML
	private Button consChangeBtn;
	
	private PopOver popover;
	
	public MarketingOverviewController() {
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		
		PopOverFactory helper = new PopOverFactory();
		helper.createStandardOverlay("fxml/overlay/mkt_newPR_options.fxml");
		popover = helper.getPopover();
				
		//TODO ändern und korrekt formatieren...
		compImValueLbl.setText("100");
		emplBrValueLbl.setText("100");
		
		consChangeBtn.setOnAction(e -> {
			showPopover();
			
		});
		consChangeBtn.setDisable(true);
	
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
		List<PressRelease> pressReleases = controller.getPressReleases();

		pressReleaseList.setItems(FXCollections.observableArrayList(pressReleases));
	}
	
	public void updateCompanyImageLabel() {
		compImValueLbl.setText(GameController.getInstance().computeCompanyImage() + "");
	}
}
