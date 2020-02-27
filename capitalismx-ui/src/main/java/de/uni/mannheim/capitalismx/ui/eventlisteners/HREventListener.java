package de.uni.mannheim.capitalismx.ui.eventlisteners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import de.uni.mannheim.capitalismx.hr.domain.employee.Employee;
import de.uni.mannheim.capitalismx.hr.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameView;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.GameModuleType;
import de.uni.mannheim.capitalismx.ui.controller.module.hr.HrStatisticsController;
import de.uni.mannheim.capitalismx.ui.controller.module.hr.TeamDetailController;

public class HREventListener implements PropertyChangeListener {

	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		GameView hrView = UIManager.getInstance().getGameView(GameViewType.HR);

		for (EmployeeType employeeType : EmployeeType.values()) {
			if (evt.getPropertyName().equals(employeeType.getTeamEventPropertyChangedKey())) {
				// update list of employees
				TeamDetailController employeeController = (TeamDetailController) hrView
						.getModule(GameModuleType.HR_TEAM_DETAIL).getController();
				employeeController.updateTeamList(employeeType, (List<Employee>) evt.getNewValue());

				// Update total number of employees in the hud
				UIManager.getInstance().getGameHudController().updateNumOfEmployees();

				// Update Statistics Module
				HrStatisticsController statsController = (HrStatisticsController) hrView.getModule(GameModuleType.HR_STATISTICS)
						.getController();
				statsController.update();
			}
		}

	}

}
