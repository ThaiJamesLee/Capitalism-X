package de.uni.mannheim.capitalismx.ui.component.warehouse;

import java.io.IOException;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameModuleType;
import de.uni.mannheim.capitalismx.ui.controller.module.warehouse.StockManagementController;
import de.uni.mannheim.capitalismx.ui.controller.popover.warehouse.ProductStockPopoverController.TradeMode;
import de.uni.mannheim.capitalismx.ui.util.TooltipFactory;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
		StockManagementController stockController = (StockManagementController)UIManager.getInstance().getModule(GameModuleType.WAREHOUSE_STOCK_MANAGEMENT).getController();
		this.product = product;

		// load fxml
		FXMLLoader loader = new FXMLLoader(
				getClass().getClassLoader().getResource("fxml/component/product_stock_cell.fxml"),
				UIManager.getResourceBundle());
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		productName.setText(product.getProductName());
		productCount.setText(warehouse.getAmountStored(this.product) + "");
		TooltipFactory factory = new TooltipFactory();
		discardProducts.setTooltip(
				factory.createTooltip(UIManager.getLocalisedString("warehouse.stock.product.discard")));
		buyAllComponents.setOnAction(e -> {
			stockController.showProductPopover(product, TradeMode.BUY_ALL_COMPONENTS);
		});
		discardProducts.setOnAction(e -> {
			stockController.showProductPopover(product, TradeMode.SELL_PRODUCT);
		});

	}

	public AnchorPane getRoot() {
		return root;
	}

	/**
	 * Update the displayed current stock of this {@link Product}.
	 */
	public void updateStock() {
		productCount.setText(warehouse.getAmountStored(product) + "");
	}


}
