package de.uni.mannheim.capitalismx.ui.controller.component;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class TradeComponentPopoverController implements Initializable {

	private Component component;
	
	@FXML
	private Button buyButton, sellButton;
	
	@FXML 
	private AnchorPane root;
	
	@FXML 
	private Label componentName;
	
	@FXML
	private TextField buyAmountField, sellAmountField;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CssHelper.replaceStylesheets(root.getStylesheets());
		buyButton.setText(UIManager.getLocalisedString("component.buy").replace("XXX", "15 CC")); //TODO get price
		sellButton.setText(UIManager.getLocalisedString("component.sell").replace("XXX", "10 CC")); 
	}
	
	public void updateComponent(Component component) {
		this.component = component;
		componentName.setText(UIManager.getLocalisedString("component.trade") + component.getComponentName(UIManager.getResourceBundle().getLocale()));
		buyAmountField.setText("0");
		sellAmountField.setText("0");
	}
	
	@FXML
	private void buyComponent() {
		String input = buyAmountField.getText();
		try {
			int amount = Integer.parseInt(input);
			if(amount > GameController.getInstance().getFreeStorage()) {
				return;
				//TODO error messages?
			}
			GameController.getInstance().buyComponents(component, amount);
		} catch (NumberFormatException e) {
			//ignore incorrect input and reset text
			buyAmountField.setText("0");
			return;
		}
	}
	

	@FXML
	private void sellComponent() {
		String input = sellAmountField.getText();
		try {
			int amount = Integer.parseInt(input);
			//TODO check capacity
			//TODO implement selling?
		} catch (NumberFormatException e) {
			//ignore incorrect input and reset text
			sellAmountField.setText("0");
			return;
		}
	}
	
}
