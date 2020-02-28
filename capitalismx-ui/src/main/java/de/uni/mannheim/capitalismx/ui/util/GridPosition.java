package de.uni.mannheim.capitalismx.ui.util;

/**
 * Determines the position of a UI-Element on the 20x20 content grid of the
 * GamePage.
 * 
 * @author Jonathan
 *
 */
public class GridPosition {

	// Starting column position in the grid.
	private int xStart;
	// Starting row position in the grid.
	private int yStart;
	// Number of columns spanned by the module in the grid.
	private int xSpan;
	// Number of rows spanned by the module in the grid.
	private int ySpan;

	public int getxStart() {
		return xStart;
	}

	public void setxStart(int xStart) {
		this.xStart = xStart;
	}

	public int getyStart() {
		return yStart;
	}

	public void setyStart(int yStart) {
		this.yStart = yStart;
	}

	public int getxSpan() {
		return xSpan;
	}

	public void setxSpan(int xSpan) {
		this.xSpan = xSpan;
	}

	public int getySpan() {
		return ySpan;
	}

	public void setySpan(int ySpan) {
		this.ySpan = ySpan;
	}

	/**
	 * Create a new {@link GridPosition} specifying where a module will be displayed
	 * on the 20x20 grid on the GamePage.
	 * 
	 * @param colStart In which column the module starts.
	 * @param colSpan  How many columns it spans.
	 * @param rowStart In which row the module starts.
	 * @param rowSpan  How many rows it spans.
	 */
	public GridPosition(int colStart, int colSpan, int rowStart, int rowSpan) {
		this.xSpan = colSpan;
		this.xStart = colStart;
		this.ySpan = rowSpan;
		this.yStart = rowStart;
	}

}
