package de.uni.mannheim.capitalismx.ui.eventlistener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.List;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.gamecontroller.external_events.ExternalEvents;
import de.uni.mannheim.capitalismx.gamecontroller.external_events.ExternalEvents.ExternalEvent;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameModuleType;
import de.uni.mannheim.capitalismx.ui.component.GameViewType;
import de.uni.mannheim.capitalismx.ui.controller.gamepage.GameHudController;
import de.uni.mannheim.capitalismx.ui.controller.module.hr.RecruitingListController;
import de.uni.mannheim.capitalismx.ui.controller.module.sales.SalesContractController;
import de.uni.mannheim.capitalismx.ui.controller.module.sales.SalesKPIController;
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
			GameHudController hudController = UIManager.getInstance().getGameHudController();
			hudController.update();
			
			LocalDate date = (LocalDate) evt.getNewValue();
			StockManagementController stockController = ((StockManagementController) UIManager.getInstance()
					.getModule(GameModuleType.WAREHOUSE_STOCK_MANAGEMENT).getController());

			List<ExternalEvents.ExternalEvent> events = GameController.getInstance()
					.getExternalEvents(date.minusDays(1));
			// TODO display on the same day -> create propertyChangeAttribute for External
			// Events to update them
			// issue here is that the list is not yet updated when the gamedate is updated
			if (events != null) {
				for (ExternalEvent event : events) {
					GameState.getInstance().getMessages()
							.add(new MessageObject("Your assistant", ((LocalDate) evt.getNewValue()).toString(),
									event.getTitle(), "This is a more detailed description of the event ...", false));
				
					// handle special events
					switch (event) {
					case EVENT_19: // GAME LOST
						UIManager.getInstance().gameOver(false);
						break;
					default:
						break;
					}
				}
			}

			// update yearly tasks
			if (date.getDayOfYear() == 1) {
				stockController.updateComponentAvailability();
			}

			// update quarterly
			if (date.getDayOfMonth() == 1 && date.getMonthValue() % 3 == 1) {
				((RecruitingListController) UIManager.getInstance().getGameView(GameViewType.HR)
						.getModule(GameModuleType.HR_RECRUITING_OVERVIEW).getController())
								.regenerateRecruitingProspects();
				((SalesKPIController) UIManager.getInstance().getGameView(GameViewType.SALES).getModule(GameModuleType.SALES_KPI_OVERVIEW).getController()).updateFailedKPIs();
				((SalesKPIController) UIManager.getInstance().getGameView(GameViewType.SALES).getModule(GameModuleType.SALES_KPI_OVERVIEW).getController()).updateFulfilledKPIs();
			}
		}

	}
}
