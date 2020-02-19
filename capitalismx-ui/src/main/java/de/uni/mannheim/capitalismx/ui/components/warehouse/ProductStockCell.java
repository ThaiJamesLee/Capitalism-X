package de.uni.mannheim.capitalismx.ui.components.warehouse;

import java.io.IOException;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.general.TooltipFactory;
import de.uni.mannheim.capitalismx.ui.utils.TextFieldHelper;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * A visual representation of the current stock of {@link Product}s. Allows
 * discarding of unwanted products as well as buying all components at the same
 * time.
 * 
 * @author Jonathan
 *
 */
public class ProductStockCell {

	private WarehousingDepartment warehouse;
	private Product product;

	@FXML
	private TextField amountField;

	@FXML
	private Label productName, productCount;

	@FXML
	private Button buyAllComponents, discardProducts;

	@FXML
	private AnchorPane root;

	/**
	 * Constructor for a {@link ProductStockCell}.
	 * 
	 * @param product The {@link Product} to create a cell for.
	 */
	public ProductStockCell(Product product) {
		warehouse = GameState.getInstance().getWarehousingDepartment();
		this.product = product;

		// load fxml
		FXMLLoader loader = new FXMLLoader(
				getClass().getClassLoader().getResource("fxml/components/product_stock_cell.fxml"),
				UIManager.getResourceBundle());
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		amountField.setPromptText("Enter amount..."); //TODO
		TextFieldHelper.makeTextFieldNumeric(amountField);
		productName.setText(product.getProductName());
		productCount.setText(warehouse.getAmountStored(this.product) + "");
		TooltipFactory factory = new TooltipFactory();
		buyAllComponents.setTooltip(factory.createTooltip("")); // TODO
		discardProducts.setTooltip(
				factory.createTooltip("Sell the entered amount of products for less than their production value.")); // TODO
		buyAllComponents.setOnAction(e -> {
			buyAllComponents();
			//TODO give player info about price beforehand
			amountField.setText(0 + "");
		});
		discardProducts.setOnAction(e -> {
			warehouse.sellWarehouseProducts(product, Integer.parseInt(amountField.getText())); //TODO feedback? do I have to check the amount stored? 
			amountField.setText(0 + "");
		});

	}

	public AnchorPane getRoot() {
		return root;
	}

	public void updateStock() {
		productCount.setText(warehouse.getAmountStored(product) + "");
	}

	private void buyAllComponents() {
		GameController contr = GameController.getInstance();
		int amount = Integer.valueOf(amountField.getText());
		for(Component component : product.getComponents() ) {
			contr.buyComponents(component, amount);
		}
	}

}
