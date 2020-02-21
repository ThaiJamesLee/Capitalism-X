package de.uni.mannheim.capitalismx.ui.controller.module.hr;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.hr.domain.employee.Employee;
import de.uni.mannheim.capitalismx.hr.domain.employee.EmployeeGenerator;
import de.uni.mannheim.capitalismx.hr.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameModule;
import de.uni.mannheim.capitalismx.ui.components.hr.RecruitingListViewCell;
import de.uni.mannheim.capitalismx.ui.eventlisteners.HREventListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Controller for the {@link GameModule} holding the lists of {@link Employee}s
 * that could be hired by the player.
 * 
 * @author Jonathan
 */
public class RecruitingListController implements Initializable {

	private Map<EmployeeType, ListView<Employee>> listViews;
	private Map<EmployeeType, ObservableList<Employee>> observableLists;

	@FXML
	private TabPane recruitingTabPane;

	private HREventListener hrEventListener;

	public RecruitingListController() {
		hrEventListener = new HREventListener();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GameState.getInstance().getHrDepartment().getHired().addPropertyChangeListener(hrEventListener);
		observableLists = new HashMap<EmployeeType, ObservableList<Employee>>();
		listViews = new HashMap<EmployeeType, ListView<Employee>>();

		for (EmployeeType employeeType : EmployeeType.values()) {
			recruitingTabPane.getTabs().add(createEmployeeTypeTab(employeeType));
		}

		regenerateRecruitingProspects();
	}

	/**
	 * Regenerate the currently displayed recruiting prospects.
	 */
	public void regenerateRecruitingProspects() {
		HRDepartment hrDep = GameState.getInstance().getHrDepartment();
		if (hrDep.getLevel() <= 0)
			return;
		Platform.runLater(() -> {
			EmployeeGenerator generator = EmployeeGenerator.getInstance();
			generator.setDepartment(hrDep);

			for (EmployeeType employeeType : EmployeeType.values()) {
				observableLists.get(employeeType).clear();
				// TODO change number of prospects with level of department?
				for (int i = 0; i < 8; i++) {
					observableLists.get(employeeType).add(generator.createRandomEmployee(employeeType));
				}
			}
		});
	}

	/**
	 * Create a new {@link Tab} for the given {@link EmployeeType}.
	 * 
	 * @param employeeType The {@link EmployeeType} to generate a List of recruiting
	 *                     prospects for.
	 * @return {@link Tab} with a {@link ListView} for the given
	 *         {@link EmployeeType}.
	 */
	private Tab createEmployeeTypeTab(EmployeeType employeeType) {
		Tab recruitingTab = new Tab();

		ListView<Employee> recruitingList = new ListView<Employee>();
		listViews.put(employeeType, recruitingList);
		Label placeholder = new Label(UIManager.getLocalisedString("list.placeholder.recruiting"));
		placeholder.setWrapText(true);
		recruitingList.setPlaceholder(placeholder);
		ObservableList<Employee> prospectList = FXCollections.observableArrayList();
		observableLists.put(employeeType, prospectList);

		recruitingTab.setText(employeeType.getName(UIManager.getResourceBundle().getLocale()));
		recruitingTab.setContent(recruitingList);

		recruitingList.setItems(prospectList);
		recruitingList.setCellFactory(employeeListView -> new RecruitingListViewCell());
		recruitingList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		return recruitingTab;
	}

	/**
	 * Remove the given {@link Employee} from the lists and reset the
	 * {@link SelectionModel} of the {@link ListView}.
	 * 
	 * @param employee The {@link Employee} to remove.
	 */
	public void removeEmployee(Employee employee) {
		observableLists.get(employee.getEmployeeType()).remove(employee);
		listViews.get(employee.getEmployeeType()).getSelectionModel().clearSelection();
	}

}
