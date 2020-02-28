package de.uni.mannheim.capitalismx.ui.component.general;

import de.uni.mannheim.capitalismx.ui.util.TooltipFactory;
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

	// Fake placeholder that displays is displayed in the empty chart. If removed,
	// nothing will be displayed when the chart is empty.
	private PieChart.Data placeholder;

	/**
	 * Create a new {@link CapXPieChart} with a String for the default element, when
	 * there is no data.
	 * 
	 * @param placeholder The name of the placeholder Data to display, if the chart
	 *                    is empty.
	 */
	public CapXPieChart(String placeholder) {
		super();
		this.setLegendVisible(false);
		this.setLabelsVisible(false);
		this.placeholder = new PieChart.Data(placeholder, 0.0000001);
		addData(this.placeholder);
	}

	/**
	 * Create a new {@link CapXPieChart}
	 * 
	 * @param data        {@link ObservableList} of {@link PieChart.Data} objects to
	 *                    init the chart with.
	 * @param placeholder The name of the placeholder Data to display, if the chart
	 *                    is empty.
	 */
	public CapXPieChart(ObservableList<PieChart.Data> data, String placeholder) {
		this(placeholder);
		updateData(data);
	}

	/**
	 * Use this instead of setData(), if the PieChart should have {@link Tooltip}s
	 * containing the names of the pieces. (Replaces existing data.)
	 * 
	 * @param data {@link ObservableList} of the data to set.
	 */
	public void updateData(ObservableList<PieChart.Data> data) {
		this.getData().clear();
		this.getData().add(placeholder);
		this.getData().addAll(data);
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
		tip.setText(data.getName());
		Tooltip.install(data.getNode(), tip);
	}

}
