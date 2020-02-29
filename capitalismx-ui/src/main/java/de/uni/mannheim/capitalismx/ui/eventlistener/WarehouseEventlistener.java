package de.uni.mannheim.capitalismx.ui.eventlistener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.uni.mannheim.capitalismx.procurement.component.Unit;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameModuleType;
import de.uni.mannheim.capitalismx.ui.component.GameView;
import de.uni.mannheim.capitalismx.ui.component.GameViewType;
import de.uni.mannheim.capitalismx.ui.controller.module.warehouse.StockManagementController;
import de.uni.mannheim.capitalismx.ui.controller.module.warehouse.WarehouseStatisticsController;
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
			WarehouseStatisticsController statController = (WarehouseStatisticsController)warehouse.getModule(GameModuleType.WAREHOUSE_STATISTICS).getController();
			if (evt.getPropertyName().equals("inventoryChange")) {
				((StockManagementController) warehouse.getModule(GameModuleType.WAREHOUSE_STOCK_MANAGEMENT)
						.getController()).updateUnitStock((Unit) evt.getNewValue());
				statController.update();
			} else if (evt.getPropertyName().equals("freeStorageChange")) {
				statController.update();
			}
		});
	}

}
