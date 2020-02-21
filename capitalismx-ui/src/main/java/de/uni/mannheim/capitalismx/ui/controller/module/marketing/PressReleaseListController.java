package de.uni.mannheim.capitalismx.ui.controller.module.marketing;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.marketing.domain.PressRelease;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.marketing.NewPressReleaseViewCell;
import de.uni.mannheim.capitalismx.ui.utils.PopOverFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * Controller of the Press Releases Module from the Marketing View. 
 * 
 * @author Alex
 *
 */

public class PressReleaseListController implements Initializable {

	//TODO Label mit gesamten Kosten
	//TODO Unterschiedliche Zellen für published (mit Datum???) und possible Releases
	@FXML
	private ListView<PressRelease> pressReleaseList;

	@FXML
	private Button newReleaseBtn;
	
	private PopOver popover;
	
	public PressReleaseListController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		
		pressReleaseList.setCellFactory(pressReleaseList -> new NewPressReleaseViewCell(pressReleaseList));

		updateList();
		
		PopOverFactory helper = new PopOverFactory();
		helper.createStandardOverlay("fxml/popover/mkt_newPR_options.fxml");
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
	 * Gets the current list of published Press Releases from {@link Gamecontroller}
	 * and updates listView
	 */
	public void updateList() {
		GameController controller = GameController.getInstance();
		List<PressRelease> pressReleases = controller.getPressReleases();

		pressReleaseList.setItems(FXCollections.observableArrayList(pressReleases));
	}
}
