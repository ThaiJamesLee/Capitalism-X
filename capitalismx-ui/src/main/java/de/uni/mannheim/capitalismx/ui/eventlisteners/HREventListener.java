package de.uni.mannheim.capitalismx.ui.eventlisteners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import de.uni.mannheim.capitalismx.domain.employee.Employee;
import de.uni.mannheim.capitalismx.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.domain.employee.Team;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameView;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.hr.EmployeeListController;
import de.uni.mannheim.capitalismx.ui.controller.module.hr.HrStatisticsController;

public class HREventListener implements PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		GameView hrView = UIManager.getInstance().getGameView(GameViewType.HR);

		for (EmployeeType employeeType : EmployeeType.values()) {
			if (evt.getPropertyName().equals(employeeType.getTeamEventPropertyChangedKey())) {

				// update list of employees
				EmployeeListController employeeController = (EmployeeListController) hrView
						.getModule(UIElementType.HR_EMPLOYEES_OVERVIEW).getController();
				employeeController.updateTeamList(employeeType, (List<Employee>) evt.getNewValue());

				// Update total number of employees in the hud
				UIManager.getInstance().getGameHudController().updateNumOfEmployees();
			}
		}

	}

}
