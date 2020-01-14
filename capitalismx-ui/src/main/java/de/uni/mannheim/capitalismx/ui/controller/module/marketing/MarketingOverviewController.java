package de.uni.mannheim.capitalismx.ui.controller.module.marketing;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.marketing.department.MarketingDepartment;
import de.uni.mannheim.capitalismx.marketing.domain.PressRelease;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.MarketingEventListener;
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
	
	private GameController controller;
	
	public MarketingOverviewController() {
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		
		controller = GameController.getInstance();
		
		PopOverFactory helper = new PopOverFactory();
		helper.createStandardOverlay("fxml/overlay/mkt_newPR_options.fxml");
		popover = helper.getPopover();
		
		MarketingDepartment dep = MarketingDepartment.getInstance();
		dep.registerPropertyChangeListener(new MarketingEventListener());
				
		//TODO ändern und korrekt formatieren...
		GameController controller = GameController.getInstance();
		updateCompanyImageLabel();
		updateEmployerBrandingLabel();
		
		consChangeBtn.setOnAction(e -> {
			//showPopover();			
		});
		//TODO
		consChangeBtn.setDisable(true);
	
	}

	private void showPopover() {
		popover.show(UIManager.getInstance().getStage());
	}
	
	public void updateCompanyImageLabel() {	
		final String value = (MarketingDepartment.getInstance().getLevel() >= 1) ? DecimalRound.round(controller.computeCompanyImage(), 2)+"" : "----";
		Platform.runLater(new Runnable() {
			public void run() {
				compImValueLbl.setText(value);
			}
		});
	}
	
	public void updateEmployerBrandingLabel() {
		final String value = (MarketingDepartment.getInstance().getLevel() >= 1) ? DecimalRound.round(controller.getEmployerBranding(), 2) + "" : "----";
		Platform.runLater(new Runnable() {
			public void run() {
				emplBrValueLbl.setText(value);
			}
		});
	}
	
	public void updateInfoLabels() {
		updateCompanyImageLabel();
		updateEmployerBrandingLabel();
	}
	
	public void hidePopover() {
		popover.hide();
	}
}
