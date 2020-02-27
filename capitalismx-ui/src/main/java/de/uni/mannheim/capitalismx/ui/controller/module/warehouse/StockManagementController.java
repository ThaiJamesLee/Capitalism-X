package de.uni.mannheim.capitalismx.ui.controller.module.warehouse;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentCategory;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import de.uni.mannheim.capitalismx.procurement.component.Unit;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.ProductCategory;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.warehouse.ComponentStockCell;
import de.uni.mannheim.capitalismx.ui.components.warehouse.ProductStockCell;
import de.uni.mannheim.capitalismx.ui.controller.component.TradeComponentPopoverController;
import de.uni.mannheim.capitalismx.ui.controller.component.ProductStockPopoverController;
import de.uni.mannheim.capitalismx.ui.controller.component.ProductStockPopoverController.TradeMode;
import de.uni.mannheim.capitalismx.ui.eventlisteners.WarehouseEventlistener;
import de.uni.mannheim.capitalismx.ui.utils.AnchorPaneHelper;
import de.uni.mannheim.capitalismx.ui.utils.PopOverFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * {@link Initializable} for the module allowing the management of the
 * {@link Component}s. Buying and selling components.
 * 
 * @author Jonathan
 *
 */
public class StockManagementController implements Initializable {

	private PopOver tradePopover;
	private TradeComponentPopoverController tradePopoverController;

	private PopOver productStockPopover;
	private ProductStockPopoverController tradeProductUnitPopoverController;

	private VBox productBox;

	private HashMap<ComponentType, ComponentStockCell> componentCells;
	private HashMap<Product, ProductStockCell> productCells;

	@FXML
	private TabPane productTabPane;

	/**
	 * Creates and initializes the {@link Tab} for the given {@link ProductCategory}
	 * and populates it.
	 * 
	 * @param productCategory The {@link ProductCategory} to create the content for.
	 * @return The resulting {@link Tab}.
	 */
	private Tab createComponentTabForProductCategory(ProductCategory productCategory) {
		Accordion productAccordion = new Accordion();
		for (ComponentCategory componentCategory : ProductCategory.getComponentCategories(productCategory)) {
			TitledPane componentPane = new TitledPane(componentCategory.toString(),
					createTitledPaneForComponentTab(componentCategory));
			productAccordion.getPanes().add(componentPane);
		}

		AnchorPane anchor = new AnchorPane(productAccordion);
		AnchorPaneHelper.snapNodeToAnchorPane(productAccordion);
		Tab productTab = new Tab(productCategory.name());
		productTab.setContent(anchor);
		productAccordion.autosize();
		return productTab;
	}

	/**
	 * Creates and initializes the {@link Tab} for an overview of the stored
	 * Products.
	 *
	 * @return The newly created {@link Tab}.
	 */
	private Tab createProductTab() {
		Tab productTab = new Tab(UIManager.getLocalisedString("warehouse.stock.tab.products"));

		productBox = new VBox();
		productBox.setSpacing(8.0);

		PopOverFactory factory = new PopOverFactory();
		factory.createStandardPopover("fxml/popover/product_stock_popover.fxml");
		productStockPopover = factory.getPopover();
		productStockPopover.setArrowLocation(ArrowLocation.TOP_LEFT);
		tradeProductUnitPopoverController = (ProductStockPopoverController) factory.getPopoverController();

		/**
		 * // Prepare a gird with labels that functions as a title row for the 'list of
		 * products' GridPane titleGrid = new GridPane(); ColumnConstraints cTitle = new
		 * ColumnConstraints(); cTitle.setPercentWidth(35); ColumnConstraints cStock =
		 * new ColumnConstraints(); cStock.setPercentWidth(15);
		 * titleGrid.getColumnConstraints().add(cTitle);
		 * titleGrid.getColumnConstraints().add(cStock); Label titleLabel = new
		 * Label(UIManager.getLocalisedString("warehouse.stock.product.name"));
		 * titleLabel.getStyleClass().add("label_large"); Label stockLabel = new
		 * Label(UIManager.getLocalisedString("warehouse.stock.product.amount"));
		 * stockLabel.getStyleClass().add("label_large"); titleGrid.add(titleLabel, 0,
		 * 0); titleGrid.add(stockLabel, 1, 0); titleGrid.setStyle("-fx-padding-bottom:
		 * 12;");
		 * 
		 * productBox.getChildren().add(titleGrid);
		 */
		AnchorPane anchor = new AnchorPane(productBox);
		AnchorPaneHelper.snapNodeToAnchorPane(productBox, 4);
		productTab.setContent(anchor);

		return productTab;
	}

	/**
	 * Creates and initializes the {@link TitledPane} inside the {@link Accordion}
	 * for the given {@link ComponentCategory} and populates it with content.
	 * 
	 * @param category The {@link ComponentCategory} to generate the content for.
	 * @return {@link TitledPane} for the component wrapped in an AnchorPane.
	 */
	private Node createTitledPaneForComponentTab(ComponentCategory category) {
		// Create grid with 40%, 20%, 20%, 20% columns
		GridPane grid = new GridPane();
		ColumnConstraints c1 = new ColumnConstraints();
		c1.setPercentWidth(40.0);
		ColumnConstraints c2 = new ColumnConstraints();
		c2.setPercentWidth(20.0);
		grid.getColumnConstraints().addAll(c1, c2, c2, c2);
		grid.setHgap(5);
		grid.setVgap(5);

		grid.add(new Label("Level"), 0, 0);
		grid.add(new Label(SupplierCategory.CHEAP.getName(UIManager.getResourceBundle().getLocale())), 1, 0);
		grid.add(new Label(SupplierCategory.REGULAR.getName(UIManager.getResourceBundle().getLocale())), 2, 0);
		grid.add(new Label(SupplierCategory.PREMIUM.getName(UIManager.getResourceBundle().getLocale())), 3, 0);

		// create row constraints and fill rows
		List<ComponentType> types = ComponentType.getTypesByCategory(category);
		RowConstraints rc = new RowConstraints();
		rc.setPercentHeight(1.0 / types.size());
		List<RowConstraints> constraints = new ArrayList<RowConstraints>();
		constraints.add(rc);
		for (int i = 0; i < types.size(); i++) {
			constraints.add(rc);
			ComponentStockCell cell = new ComponentStockCell(types.get(i));
			componentCells.put(types.get(i), cell);
			grid.add(cell.getRoot(), 0, i + 1, 4, 1);
			if (!new Component(types.get(i)).isAvailable(GameState.getInstance().getGameDate())) {
				cell.setComponentAvailable(false);
			}
		}

		grid.getRowConstraints().addAll(constraints);

		AnchorPane gridAnchor = new AnchorPane();
		gridAnchor.setPrefWidth(0);
		gridAnchor.getChildren().add(grid);
		AnchorPaneHelper.snapNodeToAnchorPaneNoBottom(grid);
		return gridAnchor;
	}

	/**
	 * Hide the {@link PopOver} managing the stock of Products.
	 */
	public void hideProductPopover() {
		productStockPopover.hide();
	}

	/**
	 * Hides the {@link PopOver} for trading {@link Component}s, if it is currently
	 * displayed.
	 */
	public void hideTradePopover() {
		tradePopover.hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GameState.getInstance().getWarehousingDepartment().registerPropertyChangeListener(new WarehouseEventlistener());
		componentCells = new HashMap<ComponentType, ComponentStockCell>();
		productCells = new HashMap<Product, ProductStockCell>();

		productTabPane.getTabs().add(createProductTab());
		for (ProductCategory productCat : ProductCategory.values()) {
			productTabPane.getTabs().add(createComponentTabForProductCategory(productCat));
		}

		// Prepare the Popover for the trade buttons
		FXMLLoader popoverLoader = new FXMLLoader(
				getClass().getClassLoader().getResource("fxml/components/trade_component_popover.fxml"),
				UIManager.getResourceBundle());
		tradePopover = new PopOver();
		try {
			tradePopover.setContentNode(popoverLoader.load());
			tradePopover.setArrowLocation(ArrowLocation.TOP_RIGHT);
			tradePopover.setFadeInDuration(Duration.millis(50));
			tradePopoverController = ((TradeComponentPopoverController) popoverLoader.getController());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Displays the {@link PopOver} for managing {@link Product} stock.
	 * 
	 * @param product   The {@link Product} to show the {@link PopOver} for.
	 * @param tradeMode The {@link TradeMode} of the {@link PopOver}.
	 */
	public void showProductPopover(Product product, TradeMode tradeMode) {
		tradeProductUnitPopoverController.update(product, tradeMode);
		productStockPopover.show(productCells.get(product).getRoot());
		tradeProductUnitPopoverController.focus();
	}

	/**
	 * Displays the {@link PopOver} for trading a component with a given supplier.
	 * 
	 * @param component The {@link Component} to trade.
	 * @param node      The {@link Node} to display the {@link PopOver} on.
	 * @param price     The price of the {@link Component} to trade.
	 */
	public void showTradePopover(Component component, Node node, double price) {
		tradePopoverController.updatePopover(component, price);
		tradePopover.show(node);
		tradePopoverController.focus();
	}

	/**
	 * Update the stock of a particular {@link Component}.
	 * 
	 * @param component The {@link Component} to update the stored amount for.
	 */
	private void updateComponent(Component component) {
		componentCells.get(component.getComponentType()).updateQuality(component.getSupplierCategory());
	}

	/**
	 * Update the availability of the {@link Component}s. Enables/Disables the
	 * Buttons in the {@link ComponentStockCell}s.
	 */
	public void updateComponentAvailability() {
		Platform.runLater(() -> {
			LocalDate date = GameState.getInstance().getGameDate();
			for (Entry<ComponentType, ComponentStockCell> entry : componentCells.entrySet()) {
				entry.getValue().setComponentAvailable(new Component(entry.getKey()).isAvailable(date));
			}
		});
	}

	/**
	 * Update the amount in stock of a specific {@link Product}.
	 * 
	 * @param product The product to update the stock for.
	 */
	private void updateProduct(Product product) {
		if (productCells.containsKey(product)) {
			productCells.get(product).updateStock();
		} else {
			ProductStockCell stockCell = new ProductStockCell(product);
			productCells.put(product, stockCell);
			productBox.getChildren().add(stockCell.getRoot());
		}
	}

	/**
	 * Update the amount in stock of a specific {@link Unit}.
	 * 
	 * @param unit The {@link Unit} to update the stock for.
	 */
	public void updateUnitStock(Unit unit) {
		if (unit instanceof Product) {
			updateProduct((Product) unit);
		} else if (unit instanceof Component) {
			updateComponent((Component) unit);
		}
	}

}
