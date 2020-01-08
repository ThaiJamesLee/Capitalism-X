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
			if(evt.getPropertyName().equals(employeeType.getTeamEventPropertyChangedKey())) {
				//update list of employees
				EmployeeListController employeeController = (EmployeeListController) hrView
						.getModule(UIElementType.HR_EMPLOYEES_OVERVIEW).getController();
				employeeController.updateEmployeeListView(employeeType, (List<Employee>)evt.getNewValue());
				
				((HrStatisticsController) hrView.getModule(UIElementType.HR_STATISTICS).getController())
				.updateTeam(employeeType, ((List<Employee>) evt.getNewValue()).size());
				
				//Update total number of employees in the hud
				UIManager.getInstance().getGameHudController().updateNumOfEmployees();
			}
		}
		
//		if (evt.getPropertyName().equals("engineerChange")) {
//			// Update list of engineers
//			EmployeeListController contr = (EmployeeListController) hrView
//					.getModule(UIElementType.HR_EMPLOYEES_OVERVIEW).getController();
////			contr.updateEngineerList(((Team) evt.getNewValue()).getTeam());
//
//			// Update statistics module
//			((HrStatisticsController) hrView.getModule(UIElementType.HR_STATISTICS).getController())
//					.updateTeam(EmployeeType.ENGINEER, ((Team) evt.getNewValue()).getTeam().size());
//			System.out.println("old e-team: " + evt.getOldValue() + ", new e-Team: " + evt.getNewValue().toString());
//		} else if (evt.getPropertyName().equals("salesPersonChange")) {
//			// Update list of salespeople
//			EmployeeListController contr = (EmployeeListController) hrView
//					.getModule(UIElementType.HR_EMPLOYEES_OVERVIEW).getController();
////			contr.updateEngineerList(((Team) evt.getNewValue()).getTeam());
//
//			// Update statistics module
//			((HrStatisticsController) hrView.getModule(UIElementType.HR_STATISTICS).getController())
//					.updateTeam(EmployeeType.SALESPERSON, ((Team) evt.getNewValue()).getTeam().size());
//			System.out.println("old s-team: " + evt.getOldValue() + ", new s-Team: " + evt.getNewValue().toString());
//		} else if (evt.getPropertyName().equals("engineerTeamChanged")) {
//			List<Employee> newTeam = (List<Employee>) evt.getNewValue();
//			EmployeeListController contr = (EmployeeListController) hrView
//					.getModule(UIElementType.HR_EMPLOYEES_OVERVIEW).getController();
//			contr.updateEngineerList(newTeam);
//			// Update statistics module
//			((HrStatisticsController) hrView.getModule(UIElementType.HR_STATISTICS).getController())
//					.updateTeam(EmployeeType.ENGINEER, newTeam.size());
//			UIManager.getInstance().getGameHudController().updateNumOfEmployees();
//		} else if (evt.getPropertyName().equals("salespersonTeamChanged")) {
//			List<Employee> newTeam = (List<Employee>) evt.getNewValue();
//			EmployeeListController contr = (EmployeeListController) hrView
//					.getModule(UIElementType.HR_EMPLOYEES_OVERVIEW).getController();
//			contr.updateSalesPeopleList(newTeam);
//
//			// Update statistics module
//			((HrStatisticsController) hrView.getModule(UIElementType.HR_STATISTICS).getController())
//					.updateTeam(EmployeeType.SALESPERSON, newTeam.size());
//			UIManager.getInstance().getGameHudController().updateNumOfEmployees();
//
//		}
	}

}
