package de.uni.mannheim.capitalismx.ui.controller.module.hr;

import java.net.URL;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.hr.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.general.CapXPieChart;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import de.uni.mannheim.capitalismx.ui.util.AnchorPaneHelper;
import de.uni.mannheim.capitalismx.ui.util.CapCoinFormatter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for the module displaying statistics for the HR-Department
 * 
 * @author Jonathan
 *
 */
public class HrStatisticsController implements UpdateableController {

	private HRDepartment hrDep;

	private HashMap<EmployeeType, PieChart.Data> dataSegments;

	@FXML
	private Label hrWorkerLabel, employeesLabel, firingLabel, hiringLabel, salaryLabel, satisfactionLabel, qowLabel;

	@FXML
	private AnchorPane pieAnchor;

	@Override
	public void update() {
		Platform.runLater(() -> {

			NumberFormat intFormat = NumberFormat.getIntegerInstance(UIManager.getResourceBundle().getLocale());

			employeesLabel.setText(intFormat.format(hrDep.getTotalNumberOfEmployees()) + "/"
					+ intFormat.format(hrDep.getTotalEmployeeCapacity()));
			hrWorkerLabel.setText(intFormat.format(hrDep.getTeamByEmployeeType(EmployeeType.HR_WORKER).getTeam().size())
					+ "/" + intFormat.format(hrDep.getHrCapacity()));
			salaryLabel.setText(CapCoinFormatter.getCapCoins(hrDep.calculateTotalSalaries()));
			firingLabel.setText(CapCoinFormatter.getCapCoins(hrDep.getFiringCost()));
			hiringLabel.setText(CapCoinFormatter.getCapCoins(hrDep.getHiringCost()));
			qowLabel.setText(NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale())
					.format(hrDep.getTotalQualityOfWork()));
			satisfactionLabel.setText(NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale())
					.format(hrDep.getTotalJSS()));

			for (EmployeeType employeeType : EmployeeType.values()) {
				dataSegments.get(employeeType).setPieValue(hrDep.getTeamByEmployeeType(employeeType).getTeam().size());
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		hrDep = GameState.getInstance().getHrDepartment();
		dataSegments = new HashMap<EmployeeType, PieChart.Data>();

		Locale locale = UIManager.getResourceBundle().getLocale();
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		for (EmployeeType employeeType : EmployeeType.values()) {
			dataSegments.put(employeeType, new PieChart.Data(employeeType.getName(locale),
					hrDep.getTeamByEmployeeType(employeeType).getTeam().size()));
			pieChartData.add(dataSegments.get(employeeType));
		}

		CapXPieChart chart = new CapXPieChart(pieChartData, UIManager.getLocalisedString("hr.stats.pie.empty"));
		pieAnchor.getChildren().add(chart);
		AnchorPaneHelper.snapNodeToAnchorPane(chart);

		update();

	}

}
