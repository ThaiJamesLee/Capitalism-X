package de.uni.mannheim.capitalismx.ui.eventlisteners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.List;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.external_events.ExternalEvents;
import de.uni.mannheim.capitalismx.gamecontroller.external_events.ExternalEvents.ExternalEvent;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameNotification;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.warehouse.StockManagementController;
import de.uni.mannheim.capitalismx.utils.data.MessageObject;

/**
 * Ein GameState Event Listener.
 * 
 * @author duly
 */
public class GameStateEventListener implements PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("gameWon")) {
			UIManager.getInstance().gameOver(true);
		}

		if (evt.getPropertyName().equals("gameDate")) {
			UIManager.getInstance().getGameHudController().update();
			LocalDate date = (LocalDate) evt.getNewValue();
			StockManagementController stockController = ((StockManagementController) UIManager.getInstance()
					.getGameView(GameViewType.WAREHOUSE).getModule(UIElementType.WAREHOUSE_STOCK_MANAGEMENT)
					.getController());

			List<ExternalEvents.ExternalEvent> events = GameController.getInstance()
					.getExternalEvents(date.minusDays(1));
			// TODO display on the same day -> create propertyChangeAttribute for External
			// Events to update them
			// issue here is that the list is not yet updated when the gamedate is updated
			if (events != null) {
				for (ExternalEvent event : events) {
					UIManager.getInstance().getGameHudController()
							.addNotification(new GameNotification(new MessageObject("your assistant",
									((LocalDate) evt.getNewValue()).toString(), event.getTitle(),
									"This is a more detailed description of the event ...", false)));
				}
			}

			// update yearly tasks
			if (date.getDayOfYear() == 1) {
				stockController.updateComponentAvailability();
			}

			// update component prices every three months
			if (date.getDayOfMonth() == 1 && date.getMonthValue() % 3 == 0) {
				stockController.updateComponentPrices(date);
			}
		}

	}
}
