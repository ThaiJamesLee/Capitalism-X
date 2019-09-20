package de.uni.mannheim.capitalismx.ui.utils;

/**
 * Determines the position of a UI-Element on the 20x20 content grid of the GamePage.
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
	
	public GridPosition(int xStart, int yStart, int xSpan, int ySpan) {
		this.xSpan = xSpan;
		this.xStart = xStart;
		this.ySpan = ySpan;
		this.yStart = yStart;
	}
	
}
