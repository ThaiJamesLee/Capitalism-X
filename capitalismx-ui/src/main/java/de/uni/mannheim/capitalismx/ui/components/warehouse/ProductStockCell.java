package de.uni.mannheim.capitalismx.ui.components.warehouse;

import java.io.IOException;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * A visual representation of the current stock of {@link Product}s. Allows
 * discarding of unwanted products as well.
 * 
 * @author Jonathan
 *
 */
public class ProductStockCell {

	WarehousingDepartment warehouse;
	
	
	@FXML
	Label productName, productCount;

	@FXML
	private AnchorPane root;

	/**
	 * Constructor for a {@link ProductStockCell}.
	 * 
	 * @param product The {@link Product} to create a cell for.
	 */
	public ProductStockCell(Product product) {
		warehouse = GameState.getInstance().getWarehousingDepartment();

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
	}
	
	public AnchorPane getRoot() {
		return root;
	}

}
