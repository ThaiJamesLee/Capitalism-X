package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.finance.finance.FinanceDepartment;
import de.uni.mannheim.capitalismx.finance.finance.Investment;
import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.controller.overlay.finance.LoanRequestListController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.FinanceEventListener;
import de.uni.mannheim.capitalismx.ui.utils.PopOverFactory;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FinanceOverviewController extends GameModuleController {

	@FXML
	Label cashLabel;

	@FXML
	Label assetsLabel;

	@FXML
	Label liabilitiesLabel;

	@FXML
	Label netWorthLabel;

	@FXML
	LineChart<String, Number> cashChart;

	@FXML
	LineChart<String, Number> assetsChart;

	@FXML
	LineChart<String, Number> liabilitiesChart;

	@FXML
	LineChart<String, Number> netWorthChart;

	XYChart.Series<String, Number> cashSeries;
	XYChart.Series<String, Number> assetsSeries;
	XYChart.Series<String, Number> liabilitiesSeries;
	XYChart.Series<String, Number> netWorthSeries;

	private FinanceEventListener financeEventListener;
	private LoanRequestListController loanRequestListController;

	private double loanAmount;

	private PopOver loanRequestPopover;

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GameController controller = GameController.getInstance();
		financeEventListener = new FinanceEventListener();

		FinanceDepartment f = GameState.getInstance().getFinanceDepartment();
		f.registerPropertyChangeListener(financeEventListener);

		cashLabel.setText(DecimalRound.round(controller.getCash(), 2) + "");
		assetsLabel.setText(DecimalRound.round(controller.getAssets(), 2) + "");
		liabilitiesLabel.setText(DecimalRound.round(controller.getLiabilities(), 2) + "");
		netWorthLabel.setText(DecimalRound.round(controller.getNetWorth(), 2) + "");

		// Prepare the Popover for the trainButton
		PopOverFactory factory = new PopOverFactory();
		
		factory.createStandardPopover("fxml/overlay/loan_request_list.fxml");
		loanRequestPopover = factory.getPopover();
		loanRequestListController = (LoanRequestListController)factory.getPopoverController();


		cashSeries = new XYChart.Series();
		for(int i = 1; i < 13; i++){
			cashSeries.getData().add(new XYChart.Data(String.valueOf(i), 0));
		}
		assetsSeries = new XYChart.Series();
		for(int i = 1; i < 13; i++){
			assetsSeries.getData().add(new XYChart.Data(String.valueOf(i), 0));
		}
		liabilitiesSeries = new XYChart.Series();
		for(int i = 1; i < 13; i++){
			liabilitiesSeries.getData().add(new XYChart.Data(String.valueOf(i), 0));
		}
		netWorthSeries = new XYChart.Series();
		for(int i = 1; i < 13; i++){
			netWorthSeries.getData().add(new XYChart.Data(String.valueOf(i), 0));
		}

		cashChart.setAnimated(false);
		assetsChart.setAnimated(false);
		liabilitiesChart.setAnimated(false);
		netWorthChart.setAnimated(false);

		cashChart.getData().add(cashSeries);
		assetsChart.getData().add(assetsSeries);
		liabilitiesChart.getData().add(liabilitiesSeries);
		netWorthChart.getData().add(netWorthSeries);
	}

	public void setNetWorthLabel(String text) {
		Platform.runLater(new Runnable() {
			public void run() {
				netWorthLabel.setText(text);
			}
		});
	}

	public void setCashLabel(String text) {
		Platform.runLater(new Runnable() {
			public void run() {
				cashLabel.setText(text);
			}
		});
	}

	public void setAssetsLabel(String text) {
		Platform.runLater(new Runnable() {
			public void run() {
				assetsLabel.setText(text);
			}
		});
	}

	public void setLiabilitiesLabel(String text) {
		Platform.runLater(new Runnable() {
			public void run() {
				liabilitiesLabel.setText(text);
			}
		});
	}

	public void updateCharts(String rowName, String[] yValues, String[] xNames){
		Platform.runLater(new Runnable() {
			public void run() {
				switch (rowName){
					case "cash":
						for(int i = 0; i < yValues.length; i++){
							cashSeries.getData().get(i).setXValue(xNames[i]);
							cashSeries.getData().get(i).setYValue(Double.valueOf(yValues[i]));
						}
						break;
					case "assets":
						for(int i = 0; i < yValues.length; i++){
							assetsSeries.getData().get(i).setXValue(xNames[i]);
							assetsSeries.getData().get(i).setYValue(Double.valueOf(yValues[i]));
						}
						break;
					case "liabilities":
						for(int i = 0; i < yValues.length; i++){
							liabilitiesSeries.getData().get(i).setXValue(xNames[i]);
							liabilitiesSeries.getData().get(i).setYValue(Double.valueOf(yValues[i]));
						}
						break;
					case "netWorth":
						for(int i = 0; i < yValues.length; i++){
							netWorthSeries.getData().get(i).setXValue(xNames[i]);
							netWorthSeries.getData().get(i).setYValue(Double.valueOf(yValues[i]));
						}
						break;
				}
			}
		});
	}
}
