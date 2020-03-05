package de.uni.mannheim.capitalismx.ui.controller.popover.warehouse;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameModuleType;
import de.uni.mannheim.capitalismx.ui.component.general.GameAlert;
import de.uni.mannheim.capitalismx.ui.controller.module.warehouse.StockManagementController;
import de.uni.mannheim.capitalismx.ui.util.CapCoinFormatter;
import de.uni.mannheim.capitalismx.ui.util.CssHelper;
import de.uni.mannheim.capitalismx.ui.util.TextFieldHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	private Label componentName, priceLabelBuy, priceLabelSell, qualityLabel;

	@FXML
	private TextField amountField;

	/**
	 * Buy the given amount of the {@link Component}, that is currently set in the
	 * {@link TradeComponentPopoverController}.
	 */
	@FXML
	private void buyComponent() {
		String input = amountField.getText();
		LocalDate date = GameState.getInstance().getGameDate();
		GameController gc = GameController.getInstance();
		try {
			int amount = Integer.parseInt(input);
			int freeStorage = gc.getFreeStorage();
			if (amount > freeStorage) {
				// Handle if not enough free storage in the warehouse
				if (freeStorage != 0) {
					GameAlert alert = new GameAlert(AlertType.NONE,
							UIManager.getLocalisedString("warehouse.alert.capacity.title"),
							UIManager.getLocalisedString("warehouse.alert.capacity.description").replace("AMOUNT",
									freeStorage + ""));
					alert.getButtonTypes().add(ButtonType.YES);
					alert.getButtonTypes().add(ButtonType.NO);
					Optional<ButtonType> response = alert.showAndWait();
					if (response.isPresent() && response.get().equals(ButtonType.YES)) {
						gc.decreaseCash(date, gc.buyComponents(component, freeStorage));
					}
				} else {
					GameAlert alert = new GameAlert(AlertType.INFORMATION,
							UIManager.getLocalisedString("warehouse.alert.capacity.title"),
							UIManager.getLocalisedString("warehouse.alert.capacity.description.full"));
					alert.showAndWait();
				}
			} else {
				gc.decreaseCash(date, gc.buyComponents(component, amount));
			}
			((StockManagementController) UIManager.getInstance().getModule(GameModuleType.WAREHOUSE_STOCK_MANAGEMENT)
					.getController()).hideTradePopover();
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
		amountField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				updatePrice();
			}
		});

	}

	@FXML
	/**
	 * Sell the components in
	 */
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
		this.qualityLabel.setText(component.getSupplierCategoryShortForm());
		// force the field to be numeric only
		TextFieldHelper.makeTextFieldNumeric(amountField);
		updatePrice();
	}

	/**
	 * Update the price given the value in the {@link TextField}.
	 */
	private void updatePrice() throws NumberFormatException {
		try {
			int amount = Integer.parseInt(amountField.getText());

			double cost = componentPrice * amount;
			priceLabelSell.setText(UIManager.getLocalisedString("component.price.sell")
					+ CapCoinFormatter.getCapCoins(component.getWarehouseSalesPrice() * amount));
			priceLabelBuy
					.setText(UIManager.getLocalisedString("component.price.buy") + CapCoinFormatter.getCapCoins(cost));
		} catch (NumberFormatException e) {
			amountField.setText("0");
		}
	}
}
