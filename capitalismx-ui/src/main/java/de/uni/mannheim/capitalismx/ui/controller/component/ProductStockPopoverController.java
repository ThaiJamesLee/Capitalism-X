package de.uni.mannheim.capitalismx.ui.controller.component;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameModuleType;
import de.uni.mannheim.capitalismx.ui.controller.module.warehouse.StockManagementController;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import de.uni.mannheim.capitalismx.ui.utils.TextFieldHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller of the {@link PopOver}, that allows selling {@link Product}s or
 * buying all components of a {@link Product}.
 * 
 * @author Jonathan
 *
 */
public class ProductStockPopoverController implements Initializable {

	/**
	 * Specifies if the Popover is currently used for selling products for a cheap
	 * price or buying all components of the Product.
	 * 
	 * @author Jonathan
	 *
	 */
	public enum TradeMode {
		SELL_PRODUCT, BUY_ALL_COMPONENTS;
	}

	private TradeMode tradeMode;
	private Product product;

	@FXML
	private TextField amountInput;

	@FXML
	private Label priceLabel;

	@FXML
	private Button tradeButton;

	/**
	 * Buy the given amount of each {@link Component} necessary for this
	 * {@link Product}.
	 */
	private void buyAllComponents(int amount) {
		GameController contr = GameController.getInstance();
		for (Component component : product.getComponents()) {
			contr.buyComponents(component, amount);
		}
		reset();
	}

	/**
	 * Calculates the price for buying all components of the current
	 * {@link Product}.
	 * 
	 * @param amount The amount of components to buy.
	 * @return The price for buying all components.
	 */
	private int calcComponentPrice(int amount) {
		int price = 0;
		for (Component c : product.getComponents()) {
			price += amount * c.getInitialComponentPrice(); // TODO replace with actual component price, once
															// implemented
		}
		return price;
	}

	/**
	 * Reqeust focus for the {@link TextField}, so that the user can start typing
	 * directly.
	 */
	public void focus() {
		amountInput.requestFocus();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TextFieldHelper.makeTextFieldNumeric(amountInput);
		amountInput.textProperty().addListener(e -> {
			updateInfo();
		});

		tradeButton.setOnAction(e -> {
			switch (tradeMode) {
			case BUY_ALL_COMPONENTS:
				buyAllComponents(Integer.valueOf(amountInput.getText()));
				break;
			case SELL_PRODUCT:
				sellProducts(Integer.valueOf(amountInput.getText()));
			default:
				break;
			}
		});
	}

	/**
	 * Reset the {@link TextField} and hide the {@link PopOver}.
	 */
	private void reset() {
		amountInput.setText("0");
		((StockManagementController) UIManager.getInstance().getModule(GameModuleType.WAREHOUSE_STOCK_MANAGEMENT)
				.getController()).hideProductPopover();
	}

	/**
	 * Sell the given amount of the current {@link Product}.
	 * 
	 * @param amount The amount of {@link Product}s to sell.
	 */
	private void sellProducts(int amount) {
		GameState.getInstance().getWarehousingDepartment().sellWarehouseProducts(product, amount); // TODO check amount?
		reset();
	}

	/**
	 * Update the current {@link Product} and {@link TradeMode} of this
	 * {@link PopOver}.
	 * 
	 * @param product   The {@link Product} to display information for.
	 * @param tradeMode The {@link TradeMode} to use.
	 */
	public void update(Product product, TradeMode tradeMode) {
		this.product = product;
		this.tradeMode = tradeMode;
		updateInfo();
	}

	/**
	 * Update the texts of the price info label and the button.
	 */
	private void updateInfo() {
		if (amountInput.getText() == "")
			return;

		String text = "";
		int amount = Integer.valueOf(amountInput.getText());
		switch (tradeMode) {
		case BUY_ALL_COMPONENTS:
			text = UIManager.getLocalisedString("warehouse.stock.product.component.price");
			text = text.replace("PRODUCT", product.getProductName());
			text = text.replace("AMOUNT", amountInput.getText());
			text = text.replace("PRICE", CapCoinFormatter.getCapCoins(calcComponentPrice(amount)));
			tradeButton.setText(UIManager.getLocalisedString("component.buy"));
			break;
		case SELL_PRODUCT:
			text = UIManager.getLocalisedString("warehouse.stock.product.discard.price");
			text = text.replace("PRODUCT", product.getProductName());
			text = text.replace("AMOUNT", amountInput.getText());
			text = text.replace("PRICE", CapCoinFormatter.getCapCoins(amount * product.getSalesPrice()));
			tradeButton.setText(UIManager.getLocalisedString("component.sell"));
			break;
		default:
			break;
		}
		priceLabel.setText(text);
	}

}
