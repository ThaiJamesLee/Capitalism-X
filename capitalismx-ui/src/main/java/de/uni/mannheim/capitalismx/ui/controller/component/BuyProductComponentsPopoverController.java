package de.uni.mannheim.capitalismx.ui.controller.component;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class BuyProductComponentsPopoverController implements Initializable {

	private double componentPrice;
	private Product product = null;

	@FXML
	private Button buyButton;

	@FXML
	private AnchorPane root;

	@FXML
	private Label priceLabel;

	@FXML
	private TextField amountInput;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CssHelper.replaceStylesheets(root.getStylesheets());
	}

	/**
	 * Update the {@link BuyProductComponentsPopoverController} with a new component and
	 * the price per unit.
	 * 
	 * @param component The new {@link Component} to display.
	 * @param price     The price of a single {@link Component}.
	 */
	public void updatePopover(Product product) {
		this.product = product;
		amountInput.setText("0");
		
		// force the field to be numeric only
		amountInput.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					amountInput.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if (newValue.equals("")) {
					amountInput.setText("0");
				}
				updatePrice();
			}
		});
		updatePrice();
	}

	private void updatePrice() {
		double cost = componentPrice * Integer.parseInt(amountInput.getText());
		priceLabel.setText(UIManager.getLocalisedString("component.price") + CapCoinFormatter.getCapCoins(cost));
	}

	public void focus() {
		amountInput.requestFocus();
	}

}
