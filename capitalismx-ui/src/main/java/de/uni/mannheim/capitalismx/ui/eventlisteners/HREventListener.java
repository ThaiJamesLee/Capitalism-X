package de.uni.mannheim.capitalismx.ui.eventlisteners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.uni.mannheim.capitalismx.domain.employee.Team;
import de.uni.mannheim.capitalismx.ui.application.CapXApplication;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.hr.EmployeeListController;

public class HREventListener implements PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("Event: " + evt.getPropertyName());

		if (evt.getPropertyName().equals("engineerChange")) {
			EmployeeListController contr = (EmployeeListController) UIManager.getInstance().getGameView(GameViewType.HR)
					.getModule(UIElementType.HR_EMPLOYEES_OVERVIEW).getController();
			contr.updateEngineerList(((Team) evt.getNewValue()).getTeam());
			System.out.println("old e-team: " + evt.getOldValue() + ", new e-Team: " + evt.getNewValue().toString());
		} else if (evt.getPropertyName().equals("salesPersonChange")) {
			System.out.println("old s-team: " + evt.getOldValue() + ", new s-Team: " + evt.getNewValue().toString());
		}
	}

}
