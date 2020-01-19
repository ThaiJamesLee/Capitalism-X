package de.uni.mannheim.capitalismx.ui.components.general;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;

/**
 * Custom {@link PieChart}, that automatically configures the chart and installs
 * {@link Tooltip}s.
 * 
 * @author Jonathan
 *
 */
public class CapXPieChart extends PieChart {

	public CapXPieChart() {
		this.setLegendVisible(true);
		this.setLabelsVisible(false);
	}

	public CapXPieChart(ObservableList<PieChart.Data> data) {
		this();
		updateData(data);
	}

	/**
	 * Use this instead of setData(), if the PieChart should have {@link Tooltip}s
	 * containing the names of the pieces.
	 * 
	 * @param data {@link ObservableList} of the data to set.
	 */
	public void updateData(ObservableList<PieChart.Data> data) {
		setData(data);
		TooltipFactory factory = new TooltipFactory();
		for (PieChart.Data date : data) {
			Tooltip tip = factory.createTooltip(date.getName());
			Tooltip.install(date.getNode(), tip);
		}
	}

	/**
	 * Use this to add data, if the PieChart should have {@link Tooltip}s containing
	 * the names of the pieces.
	 * 
	 * @param data {@link PieChart.Data} to add to the {@link CapXPieChart}.
	 */
	public void addData(PieChart.Data data) {
		this.getData().add(data);
		Tooltip tip = new Tooltip();
		tip.setText(data.getName() + ": " + data.getPieValue());
		Tooltip.install(data.getNode(), tip);
	}

}
