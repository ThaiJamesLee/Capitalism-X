package de.uni.mannheim.capitalismx.ui.controller.component;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameAlert;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class TradeComponentPopoverController implements Initializable {

	private double componentPrice;
	private Component component = null;

	@FXML
	private Button buyButton, sellButton;

	@FXML
	private AnchorPane root;

	@FXML
	private Label componentName, priceLabel, qualityLabel;

	@FXML
	private TextField amountField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CssHelper.replaceStylesheets(root.getStylesheets());
	}

	public void updatePopover(Component component, double price) {
		this.componentPrice = price;
		this.component = component;
		componentName.setText(UIManager.getLocalisedString("component.trade")
				+ component.getComponentType().getName(UIManager.getResourceBundle().getLocale()));
		amountField.setText("0");
		this.qualityLabel.setText(component.getSupplierCategoryShortForm()); // TODO localize
		// force the field to be numeric only
		amountField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					amountField.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if (newValue.equals("")) {
					amountField.setText("0");
				}
				updatePrice();
			}
		});
		updatePrice();
	}

	private void updatePrice() {
		double cost = componentPrice * Integer.parseInt(amountField.getText());
		priceLabel.setText(UIManager.getLocalisedString("component.price") + CapCoinFormatter.getCapCoins(cost));
	}

	@FXML
	private void buyComponent() {
		String input = amountField.getText();
		try {
			int amount = Integer.parseInt(input);
			int freeStorage = GameController.getInstance().getFreeStorage();
			if (amount > freeStorage) {
				GameAlert alert = new GameAlert(AlertType.INFORMATION, "Not enough free space in the warehouse.",
						"Will buy as much as possible (" + freeStorage + ") for now.");
				alert.showAndWait();
				if (freeStorage != 0) {
					GameController.getInstance().buyComponents(component, freeStorage); //TODO what happens when there is not enough cash
				}
			} else { // TODO costs for component
				GameController.getInstance().buyComponents(component, amount);
			}
		} catch (NumberFormatException e) {
			// ignore incorrect input and reset text
			amountField.setText("0");
			return;
		}
	}

	@FXML
	private void sellComponent() {
		String input = amountField.getText();
		try {
			int amount = Integer.parseInt(input);
			// TODO implement selling?
		} catch (NumberFormatException e) {
			// ignore incorrect input and reset text
			amountField.setText("0");
			return;
		}
	}

}
