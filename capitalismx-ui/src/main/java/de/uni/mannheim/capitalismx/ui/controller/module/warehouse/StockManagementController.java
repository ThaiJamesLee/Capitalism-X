package de.uni.mannheim.capitalismx.ui.controller.module.warehouse;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentCategory;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import de.uni.mannheim.capitalismx.production.ProductCategory;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.warehouse.ComponentStockCell;
import de.uni.mannheim.capitalismx.ui.controller.component.TradeComponentPopoverController;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.utils.AnchorPaneHelper;
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

public class StockManagementController extends GameModuleController {

	private PopOver tradePopover;
	private TradeComponentPopoverController tradePopoverController;
	
	private HashMap<ComponentType, ComponentStockCell> cells;
	
	@FXML
	private TabPane productTabPane;

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cells = new HashMap<ComponentType, ComponentStockCell>();
		
		for (ProductCategory productCat : ProductCategory.values()) {
			productTabPane.getTabs().add(createProductTabContent(productCat));
		}


		// Prepare the Popover for the trade buttons
		FXMLLoader popoverLoader = new FXMLLoader(
				getClass().getClassLoader().getResource("fxml/components/trade_component_popover.fxml"), UIManager.getResourceBundle());
		tradePopover = new PopOver();
		try {
			tradePopover.setContentNode(popoverLoader.load());
			tradePopover.setFadeInDuration(Duration.millis(50));
			tradePopoverController = ((TradeComponentPopoverController) popoverLoader.getController());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private Tab createProductTabContent(ProductCategory cat) {
		Accordion productAccordion = new Accordion();
		for (ComponentCategory category : ProductCategory.getComponentCategories(cat)) {
			TitledPane componentPane = new TitledPane(category.toString(), createComponentInformationPane(category));
			productAccordion.getPanes().add(componentPane);
		}

		AnchorPane anchor = new AnchorPane(productAccordion);
		AnchorPaneHelper.snapNodeToAnchorPane(productAccordion);
		Tab productTab = new Tab(cat.name());
		productTab.setContent(anchor);
		productAccordion.autosize();
		return productTab;
	}
	
	

	private Node createComponentInformationPane(ComponentCategory category) {
		// Create grid with 40%, 20%, 20%, 20% columns
		GridPane grid = new GridPane();
		ColumnConstraints c1 = new ColumnConstraints();
		c1.setPercentWidth(40.0);
		ColumnConstraints c2 = new ColumnConstraints();
		c2.setPercentWidth(20.0);
		grid.getColumnConstraints().addAll(c1, c2, c2, c2);
		grid.setHgap(5);
		grid.setVgap(5);

		// fill header row
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
		}

		grid.getRowConstraints().addAll(constraints);

		AnchorPane gridAnchor = new AnchorPane();
		gridAnchor.setPrefWidth(0);
		gridAnchor.getChildren().add(grid);
		AnchorPaneHelper.snapNodeToAnchorPaneNoBottom(grid);
		return gridAnchor;
	}
	
	public void showTradePopover(Component component, Node node) {
		tradePopoverController.updateComponent(component);
		tradePopover.show(node);
	}

}
