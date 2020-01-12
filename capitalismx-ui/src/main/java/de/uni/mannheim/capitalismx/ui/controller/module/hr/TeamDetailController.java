package de.uni.mannheim.capitalismx.ui.controller.module.hr;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.hr.domain.employee.Employee;
import de.uni.mannheim.capitalismx.hr.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.hr.domain.employee.Team;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameModule;
import de.uni.mannheim.capitalismx.ui.components.hr.TeamDetails;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.HREventListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Controller for the {@link GameModule}, listing the {@link Employee}s of the
 * company.
 * 
 * @author Jonathan
 *
 */
public class TeamDetailController extends GameModuleController {

	@FXML
	private TabPane employeeTabPane;

	private Map<EmployeeType, TeamDetails> employeeTypeDetails;

	@Override
	public void update() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HRDepartment hrDep = GameState.getInstance().getHrDepartment();
		employeeTypeDetails = new HashMap<EmployeeType, TeamDetails>();

		for (EmployeeType type : EmployeeType.values()) {
			Team team = hrDep.getTeamByEmployeeType(type);
			TeamDetails teamDetails = new TeamDetails(team);
			employeeTypeDetails.put(type, teamDetails);
			prepareTeamTab(hrDep.getTeamByEmployeeType(type)); // TODO

			Tab teamTab = new Tab(team.getType().getName(UIManager.getResourceBundle().getLocale()));
			employeeTabPane.getTabs().add(teamTab);
			teamTab.setContent(teamDetails.getRoot());
		}

		HREventListener hrEventListener = new HREventListener();
		hrDep.registerPropertyChangeListener(hrEventListener);
	}

	/**
	 * Initially prepares the {@link Tab} for a given {@link Team}, adds the
	 * necessary {@link ListView} and does some styling.
	 * 
	 * @param team The {@link Team} to prepare the {@link Tab} for.
	 */
	private void prepareTeamTab(Team team) {
		updateTeamList(team.getType(), team.getTeam());
	}

	/**
	 * Updates the Items of the {@link ListView} of the given {@link EmployeeType}.
	 * 
	 * @param type      The {@link EmployeeType} to update the ListView for.
	 * @param employees {@link List} of {@link Employee}s containing the new items.
	 */
	public void updateTeamList(EmployeeType type, List<Employee> employees) {
		ListView<Employee> listview = employeeTypeDetails.get(type).getTeamList();
		listview.setItems(FXCollections.observableArrayList(employees));
		
		updateTeamStats(type);
	}

	// TODO update when single employee changes
	/**
	 * Updates the Statistics displayed in the {@link TeamDetails} of the given
	 * {@link EmployeeType}.
	 * 
	 * @param type The {@link EmployeeType} to update the {@link TeamDetails} for.
	 */
	public void updateTeamStats(EmployeeType type) {
		employeeTypeDetails.get(type).updateStats();
	}

}
