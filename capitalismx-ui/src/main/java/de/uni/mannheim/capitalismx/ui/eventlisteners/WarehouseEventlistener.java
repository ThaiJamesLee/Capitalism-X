package de.uni.mannheim.capitalismx.ui.eventlisteners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameView;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.warehouse.StockManagementController;
import javafx.application.Platform;

/**
 * 
 * @author Jonathan
 *
 */
public class WarehouseEventlistener implements PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("inventoryChange")) {
			Platform.runLater(() -> {
				/**
				 * TODO update currently deactivated 
				 * @see issue #123
				 */
				GameView warehouse = UIManager.getInstance().getGameView(GameViewType.WAREHOUSE);
				if (evt.getNewValue() instanceof Component) {
					((StockManagementController) warehouse.getModule(UIElementType.WAREHOUSE_STOCK_MANAGEMENT)
							.getController()).updateComponent((Component) evt.getNewValue());
				}
//				warehouse.getModule(UIElementType.WAREHOUSE_SEGMENTS).getController().update();
			});
		}
	}

}
