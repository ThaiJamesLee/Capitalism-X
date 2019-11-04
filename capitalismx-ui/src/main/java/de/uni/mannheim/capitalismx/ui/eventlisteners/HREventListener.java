package de.uni.mannheim.capitalismx.ui.eventlisteners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.uni.mannheim.capitalismx.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.domain.employee.Team;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameView;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.hr.EmployeeListController;
import de.uni.mannheim.capitalismx.ui.controller.module.hr.HrStatisticsController;
import javafx.application.Platform;

public class HREventListener implements PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("Event: " + evt.getPropertyName());
		GameView hrView = UIManager.getInstance().getGameView(GameViewType.HR);
		if (evt.getPropertyName().equals("engineerChange")) {
			// Update list of engineers
			EmployeeListController contr = (EmployeeListController) hrView
					.getModule(UIElementType.HR_EMPLOYEES_OVERVIEW).getController();
			contr.updateEngineerList(((Team) evt.getNewValue()).getTeam());

			// Update statistics module
			((HrStatisticsController) hrView.getModule(UIElementType.HR_STATISTICS).getController())
					.updateTeam(EmployeeType.ENGINEER, ((Team) evt.getNewValue()).getTeam().size());
			System.out.println("old e-team: " + evt.getOldValue() + ", new e-Team: " + evt.getNewValue().toString());
		} else if (evt.getPropertyName().equals("salesPersonChange")) {
			// Update list of salespeople
			EmployeeListController contr = (EmployeeListController) hrView
					.getModule(UIElementType.HR_EMPLOYEES_OVERVIEW).getController();
			contr.updateEngineerList(((Team) evt.getNewValue()).getTeam());

			// Update statistics module
			((HrStatisticsController) hrView.getModule(UIElementType.HR_STATISTICS).getController())
					.updateTeam(EmployeeType.SALESPERSON, ((Team) evt.getNewValue()).getTeam().size());
			System.out.println("old s-team: " + evt.getOldValue() + ", new s-Team: " + evt.getNewValue().toString());
		}
	}

}
