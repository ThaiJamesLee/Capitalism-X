package de.uni.mannheim.capitalismx.ui.controller.module.hr;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.domain.employee.Employee;
import de.uni.mannheim.capitalismx.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.domain.employee.Team;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.hr.EmployeeListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.HREventListener;
import de.uni.mannheim.capitalismx.ui.utils.AnchorPaneHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class EmployeeListController extends GameModuleController {

	@FXML
	private TabPane employeeTabPane;

	private Map<EmployeeType, ListView<Employee>> listViews;

	@Override
	public void update() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HRDepartment hrDep = GameState.getInstance().getHrDepartment();
		listViews = new HashMap<EmployeeType, ListView<Employee>>();

		for (EmployeeType type : EmployeeType.values()) {
			prepareTeamTab(hrDep.getTeamByEmployeeType(type));
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
		Tab teamTab = new Tab(team.getType().getName(UIManager.getResourceBundle().getLocale()));
		employeeTabPane.getTabs().add(teamTab);

		ObservableList<Employee> teamList = FXCollections.observableArrayList(team.getTeam());
		ListView<Employee> teamListView = new ListView<Employee>(teamList);
		teamListView.setCellFactory(employeeListView -> new EmployeeListViewCell());
		teamListView.setPlaceholder(new Label(UIManager.getResourceBundle().getString("hr.list.placeholder")));
		listViews.put(team.getType(), teamListView);
		AnchorPaneHelper.snapNodeToAnchorPane(teamListView);

		AnchorPane rootNode = new AnchorPane();
		rootNode.getChildren().add(teamListView);
		teamTab.setContent(rootNode);

		updateEmployeeListView(team.getType(), team.getTeam());
	}

	/**
	 * Updates the Items of the {@link ListView} of the given {@link EmployeeType}.
	 * 
	 * @param type      The {@link EmployeeType} to update the ListView for.
	 * @param employees {@link List} of {@link Employee}s containing the new items.
	 */
	private void updateEmployeeListView(EmployeeType type, List<Employee> employees) {
		ListView<Employee> listview = listViews.get(type);
		listview.setItems(FXCollections.observableArrayList(employees));
	}

	/**
	 * Update the {@link Team} of engineers with the List of teammembers.
	 * 
	 * @param engineerTeam {@link List} of {@link Employee}s that are now in the
	 *                     Team.
	 */
	public void updateEngineerList(List<Employee> engineerTeam) {
		updateEmployeeListView(EmployeeType.ENGINEER, engineerTeam);
	}

	/**
	 * Update the {@link Team} of salesPeople with the List of teammembers.
	 * 
	 * @param salesTeam {@link List} of {@link Employee}s that are now in the Team.
	 */
	public void updateSalesPeopleList(List<Employee> salesTeam) {
		updateEmployeeListView(EmployeeType.SALESPERSON, salesTeam);
	}

}
