package de.uni.mannheim.capitalismx.ui.controller.component;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameAlert;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.warehouse.StockManagementController;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import de.uni.mannheim.capitalismx.ui.utils.TextFieldHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * {@link PopOver} for trading {@link Component}s. Can be used for buying as
 * well as selling of components of a given quality.
 * 
 * @author Jonathan
 *
 */
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

	/**
	 * Buy the given amount of the {@link Component}, that is currently set in the
	 * {@link TradeComponentPopoverController}.
	 */
	@FXML
	private void buyComponent() {
		String input = amountField.getText();
		try {
			int amount = Integer.parseInt(input);
			int freeStorage = GameController.getInstance().getFreeStorage();
			if (amount > freeStorage) {
				// TODO localization
				GameAlert alert = new GameAlert(AlertType.INFORMATION, "Not enough free space in the warehouse.",
						"Will buy as much as possible (" + freeStorage + ") for now.");
				alert.showAndWait();
				if (freeStorage != 0) {
					GameController.getInstance().buyComponents(component, freeStorage); // TODO what happens when there
																						// is not enough cash
				}
			} else { // TODO costs for component
				GameController.getInstance().buyComponents(component, amount);
			}
			((StockManagementController) UIManager.getInstance().getGameView(GameViewType.WAREHOUSE)
					.getModule(UIElementType.WAREHOUSE_STOCK_MANAGEMENT).getController()).hideTradePopover();
		} catch (NumberFormatException e) {
			// ignore incorrect input and reset text
			amountField.setText("0");
			return;
		}
	}

	/**
	 * This method requests focus for the {@link TextField}. Is called when the
	 * {@link PopOver} is shown, so that the player can directly start entering the
	 * amount.
	 */
	public void focus() {
		amountField.requestFocus();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CssHelper.replaceStylesheets(root.getStylesheets());
	}

	@FXML
	private void sellComponent() {
		String input = amountField.getText();
		try {
			int amount = Integer.parseInt(input);
			GameState.getInstance().getWarehousingDepartment().sellWarehouseComponents(component, amount);
		} catch (NumberFormatException e) {
			// ignore incorrect input and reset text
			amountField.setText("0");
			return;
		}
	}

	/**
	 * Update the {@link TradeComponentPopoverController} with a new component and
	 * the price per unit.
	 * 
	 * @param component The new {@link Component} to display.
	 * @param price     The price of a single {@link Component}.
	 */
	public void updatePopover(Component component, double price) {
		this.componentPrice = price;
		this.component = component;
		componentName.setText(UIManager.getLocalisedString("component.trade")
				+ component.getComponentType().getName(UIManager.getResourceBundle().getLocale()));
		amountField.setText("0");
		this.qualityLabel.setText(component.getSupplierCategoryShortForm()); // TODO localize
		// force the field to be numeric only
		TextFieldHelper.makeTextFieldNumeric(amountField);
		updatePrice();
	}

	private void updatePrice() {
		double cost = componentPrice * Integer.parseInt(amountField.getText());
		priceLabel.setText(UIManager.getLocalisedString("component.price") + CapCoinFormatter.getCapCoins(cost));
	}

}
