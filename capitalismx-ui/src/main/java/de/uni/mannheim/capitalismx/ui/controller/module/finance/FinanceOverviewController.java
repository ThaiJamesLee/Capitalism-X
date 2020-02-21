package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import java.net.URL;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.finance.finance.FinanceDepartment;
import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.controller.popover.finance.LoanRequestListController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.FinanceEventListener;
import de.uni.mannheim.capitalismx.ui.utils.PopOverFactory;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

/**
 * This class represents the finance overview including the charts in the finance UI. It provides an overview of the
 * most important financial figures.
 *
 * @author sdupper
 */
public class FinanceOverviewController implements Initializable {

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

	/**
	 * The data for the cash chart.
	 */
	XYChart.Series<Number, Number> cashSeries;

	/**
	 * The data for the assets chart.
	 */
	XYChart.Series<Number, Number> assetsSeries;

	/**
	 * The data for the liabilities chart.
	 */
	XYChart.Series<Number, Number> liabilitiesSeries;

	/**
	 * The data for the net worth chart.
	 */
	XYChart.Series<Number, Number> netWorthSeries;

	/**
	 * The EventListener for finance events (changes relevant for the finance UI).
	 */
	private FinanceEventListener financeEventListener;

	/*
	 * Initializes the cash, assets, liabilities, and net worth labels and charts.
	 */
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

	/**
	 * Initializes/Formats the chart axes. In particular, to ensure a correct order of the values, the current month was
	 * assigned the number 11, the previous month the number 10, and so on. Here, the numbers are converted back to the
	 * correct month name.
	 */
	private void initAxes(){
		xAxisCash.setTickLabelRotation(90);
		xAxisCash.setTickLabelFormatter(new StringConverter<Number>() {
			@Override
			public String toString(Number number) {
				try {
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

	/**
	 * Updates the layout of the x axis in the cash chart (necessary when data is updated).
	 * @param lowerBound The lower bound of the x axis in the cash chart.
	 * @param upperBound The upper bound of the x axis in the cash chart.
	 */
	private void updateCashAxis(int lowerBound, int upperBound){
		xAxisCash.setAutoRanging(false);
		xAxisCash.setLowerBound(lowerBound);
		xAxisCash.setUpperBound(upperBound);
		xAxisCash.setTickUnit(1);
		xAxisCash.setMinorTickVisible(false);
	}

	/**
	 * Updates the layout of the x axis in the assets chart (necessary when data is updated).
	 * @param lowerBound The lower bound of the x axis in the assets chart.
	 * @param upperBound The upper bound of the x axis in the assets chart.
	 */
	private void updateAssetsAxis(int lowerBound, int upperBound){
		xAxisAssets.setAutoRanging(false);
		xAxisAssets.setLowerBound(lowerBound);
		xAxisAssets.setUpperBound(upperBound);
		xAxisAssets.setTickUnit(1);
		xAxisAssets.setMinorTickVisible(false);
	}

	/**
	 * Updates the layout of the x axis in the liabilities chart (necessary when data is updated).
	 * @param lowerBound The lower bound of the x axis in the liabilities chart.
	 * @param upperBound The upper bound of the x axis in the liabilities chart.
	 */
	private void updateLiabilitiesAxis(int lowerBound, int upperBound){
		xAxisLiabilities.setAutoRanging(false);
		xAxisLiabilities.setLowerBound(lowerBound);
		xAxisLiabilities.setUpperBound(upperBound);
		xAxisLiabilities.setTickUnit(1);
		xAxisLiabilities.setMinorTickVisible(false);
	}

	/**
	 * Updates the layout of the x axis in the net worth chart (necessary when data is updated).
	 * @param lowerBound The lower bound of the x axis in the net worth chart.
	 * @param upperBound The upper bound of the x axis in the net worth chart.
	 */
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

	/**
	 * Updates the data of the specified chart.
	 * @param chartName The name of the chart to be updated.
	 * @param yValues The new y values of the chart to be updated.
	 * @param xNames The new x values (month values/names) of the chart to be updated.
	 */
	public void updateCharts(String chartName, String[] yValues, String[] xNames){
		Platform.runLater(new Runnable() {
			public void run() {
				initAxes();
				switch (chartName){
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
