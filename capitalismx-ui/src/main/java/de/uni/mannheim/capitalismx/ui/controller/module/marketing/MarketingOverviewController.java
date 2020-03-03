package de.uni.mannheim.capitalismx.ui.controller.module.marketing;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.marketing.consultancy.ConsultancyType;
import de.uni.mannheim.capitalismx.marketing.department.MarketingDepartment;
import de.uni.mannheim.capitalismx.ui.eventlistener.MarketingEventListener;
import de.uni.mannheim.capitalismx.ui.util.PopOverFactory;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller of the Overview an d Consultancy Module from the Marketing View. 
 * 
 * @author Alex
 *
 */

public class MarketingOverviewController implements Initializable {

	//TODO Was tun mit den Consultancies???
	//TODO replace current pressReleasePopover

	@FXML
	private Label compImValueLbl;
	
	@FXML
	private Label emplBrValueLbl;
	
	@FXML
	private Label consCurrentLbl;

	@FXML
	private Label consTitleLbl;

	@FXML
	private Label consTextLbl;
	
	@FXML
	private Button consChangeBtn;
	
	private PopOver popover;
	
	private GameController controller;
	
	public MarketingOverviewController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		
		controller = GameController.getInstance();
		
		PopOverFactory helper = new PopOverFactory();
		helper.createStandardOverlay("fxml/popover/mkt_newPR_options.fxml");
		popover = helper.getPopover();
		
		//TODO ändern und korrekt formatieren...
		GameController controller = GameController.getInstance();
		updateCompanyImageLabel();
		updateEmployerBrandingLabel();
		
		consChangeBtn.setOnAction(e -> {
			ConsultancyType con = ConsultancyType.LOCAL_CONSULTANCY;
			
			String weakest = controller.orderConsultancyReport(con);	
			consCurrentLbl.setText(con.getName());
			consTextLbl.setText(weakest);			
		});
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
