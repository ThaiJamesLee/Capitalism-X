package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import java.net.URL;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.StringConverter;
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
	LineChart<Number, Number> cashChart;

	@FXML
	LineChart<Number, Number> assetsChart;

	@FXML
	LineChart<Number, Number> liabilitiesChart;

	@FXML
	LineChart<Number, Number> netWorthChart;

	@FXML
	NumberAxis xAxisCash;

	@FXML
	NumberAxis xAxisAssets;

	@FXML
	NumberAxis xAxisLiabilities;

	@FXML
	NumberAxis xAxisNetWorth;

	XYChart.Series<Number, Number> cashSeries;
	XYChart.Series<Number, Number> assetsSeries;
	XYChart.Series<Number, Number> liabilitiesSeries;
	XYChart.Series<Number, Number> netWorthSeries;

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


		cashSeries = new XYChart.Series<>();
		for(int i = 1; i < 13; i++){
			cashSeries.getData().add(new XYChart.Data<Number,Number>(i, 0));
		}
		assetsSeries = new XYChart.Series<>();
		for(int i = 1; i < 13; i++){
			assetsSeries.getData().add(new XYChart.Data<Number,Number>(i, 0));
		}
		liabilitiesSeries = new XYChart.Series<>();
		for(int i = 1; i < 13; i++){
			liabilitiesSeries.getData().add(new XYChart.Data<Number,Number>(i, 0));
		}
		netWorthSeries = new XYChart.Series<>();
		for(int i = 1; i < 13; i++){
			netWorthSeries.getData().add(new XYChart.Data<Number,Number>(i, 0));
		}

		cashChart.setAnimated(false);
		assetsChart.setAnimated(false);
		liabilitiesChart.setAnimated(false);
		netWorthChart.setAnimated(false);

		cashChart.getData().add(cashSeries);
		assetsChart.getData().add(assetsSeries);
		liabilitiesChart.getData().add(liabilitiesSeries);
		netWorthChart.getData().add(netWorthSeries);

		this.initAxes();
	}

	private void initAxes(){
		xAxisCash.setTickLabelRotation(90);
		xAxisCash.setTickLabelFormatter(new StringConverter<Number>() {
			@Override
			public String toString(Number number) {
				try {
					System.out.println("updated");
					int month = number.intValue();
					return GameState.getInstance().getGameDate().minusMonths(11-month).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
				}catch (Exception e){
					return "invalid";
				}
			}

			@Override
			public Number fromString(String s) {
				return 0;
			}
		});

		xAxisAssets.setTickLabelRotation(90);
		xAxisAssets.setTickLabelFormatter(new StringConverter<Number>() {
			@Override
			public String toString(Number number) {
				try {
					System.out.println("updated");
					int month = number.intValue();
					return GameState.getInstance().getGameDate().minusMonths(11-month).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
				}catch (Exception e){
					return "invalid";
				}
			}

			@Override
			public Number fromString(String s) {
				return 0;
			}
		});

		xAxisLiabilities.setTickLabelRotation(90);
		xAxisLiabilities.setTickLabelFormatter(new StringConverter<Number>() {
			@Override
			public String toString(Number number) {
				try {
					System.out.println("updated");
					int month = number.intValue();
					return GameState.getInstance().getGameDate().minusMonths(11-month).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
				}catch (Exception e){
					return "invalid";
				}
			}

			@Override
			public Number fromString(String s) {
				return 0;
			}
		});

		xAxisNetWorth.setTickLabelRotation(90);
		xAxisNetWorth.setTickLabelFormatter(new StringConverter<Number>() {
			@Override
			public String toString(Number number) {
				try {
					System.out.println("updated");
					int month = number.intValue();
					return GameState.getInstance().getGameDate().minusMonths(11-month).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
				}catch (Exception e){
					return "invalid";
				}
			}

			@Override
			public Number fromString(String s) {
				return 0;
			}
		});
	}

	private void updateCashAxis(int lowerBound, int upperBound){
		xAxisCash.setAutoRanging(false);
		xAxisCash.setLowerBound(lowerBound);
		xAxisCash.setUpperBound(upperBound);
		xAxisCash.setTickUnit(1);
		xAxisCash.setMinorTickVisible(false);
	}

	private void updateAssetsAxis(int lowerBound, int upperBound){
		xAxisAssets.setAutoRanging(false);
		xAxisAssets.setLowerBound(lowerBound);
		xAxisAssets.setUpperBound(upperBound);
		xAxisAssets.setTickUnit(1);
		xAxisAssets.setMinorTickVisible(false);
	}

	private void updateLiabilitiesAxis(int lowerBound, int upperBound){
		xAxisLiabilities.setAutoRanging(false);
		xAxisLiabilities.setLowerBound(lowerBound);
		xAxisLiabilities.setUpperBound(upperBound);
		xAxisLiabilities.setTickUnit(1);
		xAxisLiabilities.setMinorTickVisible(false);
	}

	private void updateNetWorthAxis(int lowerBound, int upperBound){
		xAxisNetWorth.setAutoRanging(false);
		xAxisNetWorth.setLowerBound(lowerBound);
		xAxisNetWorth.setUpperBound(upperBound);
		xAxisNetWorth.setTickUnit(1);
		xAxisNetWorth.setMinorTickVisible(false);
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
				initAxes();
				switch (rowName){
					case "cash":
						updateCashAxis(Integer.valueOf(xNames[0]), Integer.valueOf(xNames[11]));
						for(int i = 0; i < yValues.length; i++){
							cashSeries.getData().get(i).setXValue(Integer.valueOf(xNames[i]));
							cashSeries.getData().get(i).setYValue(Double.valueOf(yValues[i]));
						}
						break;
					case "assets":
						updateAssetsAxis(Integer.valueOf(xNames[0]), Integer.valueOf(xNames[11]));
						for(int i = 0; i < yValues.length; i++){
							assetsSeries.getData().get(i).setXValue(Integer.valueOf(xNames[i]));
							assetsSeries.getData().get(i).setYValue(Double.valueOf(yValues[i]));
						}
						break;
					case "liabilities":
						updateLiabilitiesAxis(Integer.valueOf(xNames[0]), Integer.valueOf(xNames[11]));
						for(int i = 0; i < yValues.length; i++){
							liabilitiesSeries.getData().get(i).setXValue(Integer.valueOf(xNames[i]));
							liabilitiesSeries.getData().get(i).setYValue(Double.valueOf(yValues[i]));
						}
						break;
					case "netWorth":
						updateNetWorthAxis(Integer.valueOf(xNames[0]), Integer.valueOf(xNames[11]));
						for(int i = 0; i < yValues.length; i++){
							netWorthSeries.getData().get(i).setXValue(Integer.valueOf(xNames[i]));
							netWorthSeries.getData().get(i).setYValue(Double.valueOf(yValues[i]));
						}
						break;
				}
			}
		});
	}
}
