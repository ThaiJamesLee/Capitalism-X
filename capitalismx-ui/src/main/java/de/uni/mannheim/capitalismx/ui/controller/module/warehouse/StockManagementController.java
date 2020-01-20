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
import de.uni.mannheim.capitalismx.production.ProductCategory;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.warehouse.ComponentStockCell;
import de.uni.mannheim.capitalismx.ui.controller.component.TradeComponentPopoverController;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.WarehouseEventlistener;
import de.uni.mannheim.capitalismx.ui.utils.AnchorPaneHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.util.Duration;

/**
 * {@link GameModuleController} for the module allowing the management of the
 * {@link Component}s. Buying and selling components.
 * 
 * @author Jonathan
 *
 */
public class StockManagementController extends GameModuleController {

	private PopOver tradePopover;
	private TradeComponentPopoverController tradePopoverController;

	private HashMap<ComponentType, ComponentStockCell> cells;

	@FXML
	private TabPane productTabPane;

	/**
	 * Creates and initializes the {@link Tab} for the given {@link ProductCategory}
	 * and populates it.
	 * 
	 * @param productCategory The {@link ProductCategory} to create the content for.
	 * @return The resulting {@link Tab}.
	 */
	private Tab createTabForProduct(ProductCategory productCategory) {
		Accordion productAccordion = new Accordion();
		for (ComponentCategory componentCategory : ProductCategory.getComponentCategories(productCategory)) {
			TitledPane componentPane = new TitledPane(componentCategory.toString(),
					createTitledPaneForComponent(componentCategory));
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
	 * Creates and initializes the {@link TitledPane} inside the {@link Accordion}
	 * for the given {@link ComponentCategory} and populates it with content.
	 * 
	 * @param category The {@link ComponentCategory} to generate the content for.
	 * @return {@link TitledPane} for the component wrapped in an AnchorPane.
	 */
	private Node createTitledPaneForComponent(ComponentCategory category) {
		// Create grid with 40%, 20%, 20%, 20% columns
		GridPane grid = new GridPane();
		ColumnConstraints c1 = new ColumnConstraints();
		c1.setPercentWidth(40.0);
		ColumnConstraints c2 = new ColumnConstraints();
		c2.setPercentWidth(20.0);
		grid.getColumnConstraints().addAll(c1, c2, c2, c2);
		grid.setHgap(5);
		grid.setVgap(5);

		// fill header row TODO locale
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
			cells.put(types.get(i), cell);
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GameState.getInstance().getWarehousingDepartment().registerPropertyChangeListener(new WarehouseEventlistener());
		cells = new HashMap<ComponentType, ComponentStockCell>();

		for (ProductCategory productCat : ProductCategory.values()) {
			productTabPane.getTabs().add(createTabForProduct(productCat));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	/**
	 * Update the stock of a particular {@link Component}.
	 * 
	 * @param component The {@link Component} to update the stored amount for.
	 */
	public void updateComponent(Component component) {
		cells.get(component.getComponentType()).updateQuality(component.getSupplierCategory());
	}

	/**
	 * Update the availability of the {@link Component}s. Enables/Disables the
	 * Buttons in the {@link ComponentStockCell}s.
	 */
	public void updateComponentAvailability() {
		Platform.runLater(() -> {
			LocalDate date = GameState.getInstance().getGameDate();
			for (Entry<ComponentType, ComponentStockCell> entry : cells.entrySet()) {
				entry.getValue().setComponentAvailable(new Component(entry.getKey()).isAvailable(date));
			}
		});
	}

	/**
	 * Recalculates prices for the {@link Component}s of each
	 * {@link SupplierCategory}.
	 * 
	 * @param date The {@link LocalDate} to calculate prices for.
	 */
	public void updateComponentPrices(LocalDate date) {
		for (Entry<ComponentType, ComponentStockCell> entry : cells.entrySet()) {
			entry.getValue().updateQuarterlyComponentPrices(date);
		}
	}

}
