package de.uni.mannheim.capitalismx.ui.components;

public enum GameElementType {

	HR_EMPLOYEES_OVERVIEW("Employees"),
	HR_RECRUITING_OVERVIEW("Recruiting");

	// The title of the GameElement, that will be displayed on top.
	public final String title;
	
	
	private GameElementType(String title) {
		this.title = title;
	}
}
