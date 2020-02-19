package de.uni.mannheim.capitalismx.ui.eventlisteners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.uni.mannheim.capitalismx.procurement.component.Unit;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameView;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.GameModuleType;
import de.uni.mannheim.capitalismx.ui.controller.module.warehouse.StockManagementController;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;
import javafx.application.Platform;

/**
 * {@link PropertyChangeListener} for all {@link PropertyChangeEvent}s related
 * to the {@link WarehousingDepartment}.
 * 
 * @author Jonathan
 */
public class WarehouseEventlistener implements PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Platform.runLater(() -> {
			GameView warehouse = UIManager.getInstance().getGameView(GameViewType.WAREHOUSE);
			if (evt.getPropertyName().equals("inventoryChange")) {
				((StockManagementController) warehouse.getModule(GameModuleType.WAREHOUSE_STOCK_MANAGEMENT)
						.getController()).updateUnitStock((Unit) evt.getNewValue());
				warehouse.getModule(GameModuleType.WAREHOUSE_STATISTICS).getController().update();
			} else if (evt.getPropertyName().equals("freeStorageChange")) {
				warehouse.getModule(GameModuleType.WAREHOUSE_STATISTICS).getController().update();
			}
		});
	}

}
