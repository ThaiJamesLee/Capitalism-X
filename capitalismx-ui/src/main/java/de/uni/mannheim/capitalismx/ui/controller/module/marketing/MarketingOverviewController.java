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
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
import javafx.application.Platform;
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
	//TODO replace current pressReleasePopover

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
		GameController controller = GameController.getInstance();
		compImValueLbl.setText(DecimalRound.round(controller.computeCompanyImage(), 2) + "");
		emplBrValueLbl.setText(DecimalRound.round(controller.getEmployerBranding(), 2) + "");
		
		consChangeBtn.setOnAction(e -> {
			//showPopover();
			
		});
		//consChangeBtn.setDisable(true);
	
	}

	private void showPopover() {
		popover.show(UIManager.getInstance().getStage());
	}
	
	public void setCompanyImageLabel(String text) {
		Platform.runLater(new Runnable() {
			public void run() {
				compImValueLbl.setText(text);
			}
		});
	}
	
	public void setEmployerBrandingLabel(String text) {
		Platform.runLater(new Runnable() {
			public void run() {
				emplBrValueLbl.setText(text);
			}
		});
	}
	
	
	public void hidePopover() {
		popover.hide();
	}
}
