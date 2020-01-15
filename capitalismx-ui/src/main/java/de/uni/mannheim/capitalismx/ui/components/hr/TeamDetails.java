package de.uni.mannheim.capitalismx.ui.components.hr;

import java.io.IOException;
import java.text.NumberFormat;

import de.uni.mannheim.capitalismx.hr.domain.employee.Employee;
import de.uni.mannheim.capitalismx.hr.domain.employee.Team;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.utils.AnchorPaneHelper;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * Creates a new panel with information about a given {@link Team}. Includes a
 * {@link ListView} listing all of the team's employees and a few labels giving
 * more information about some team statistics.
 * 
 * @author Jonathan
 *
 */
public class TeamDetails {

	private Team team;

	private ObservableList<Employee> teamList;
	private ListView<Employee> teamListView;

	private Parent root;

	public ListView<Employee> getTeamList() {
		return teamListView;
	}

	public Parent getRoot() {
		return root;
	}

	@FXML
	private AnchorPane listAnchor;

	@FXML
	private Label teamName, teamBenefits, number, salary, satisfaction, productivity;

	public TeamDetails(Team team) {
		this.team = team;

		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/team_details.fxml"),
				UIManager.getResourceBundle());
		loader.setController(this);

		try {
			this.root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		teamName.setText(team.getType().getName(UIManager.getResourceBundle().getLocale()) + "-Team");
		teamBenefits.setText(
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
		teamBenefits.setWrapText(true);

		updateStats();

		teamList = FXCollections.observableArrayList(team.getTeam());
		teamListView = new ListView<Employee>(teamList);
		teamListView.setCellFactory(employeeListView -> new EmployeeListViewCell());
		teamListView.setPlaceholder(new Label(UIManager.getLocalisedString("list.placeholder.employee")));
		AnchorPaneHelper.snapNodeToAnchorPane(teamListView);

		listAnchor.getChildren().add(teamListView);
	}

	/**
	 * Update all of the {@link Team}'s statistics.
	 */
	public void updateStats() {
		HRDepartment hrDep = GameState.getInstance().getHrDepartment();

		// TODO init with Tooltip explaining kpi
		number.setText(team.getTeam().size() + "");
		satisfaction.setText(NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale()).format(team.getAverageJobSatisfactionScore()));
		salary.setText(CapCoinFormatter.getCapCoins(0));
		productivity.setText(NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale()).format(hrDep.getTotalQualityOfWorkByEmployeeType(team.getType())));

	}

}
