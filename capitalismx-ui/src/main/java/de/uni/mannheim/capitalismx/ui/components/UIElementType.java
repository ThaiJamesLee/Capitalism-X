package de.uni.mannheim.capitalismx.ui.components;

/**
 * The {@link UIElementType} describes the role that the {@link UIElement}
 * has in its department and contains the title that is displayed on top of each
 * element.
 * 
 * @author Jonathan
 *
 */
public enum UIElementType {

	HR_EMPLOYEES_OVERVIEW("Employees"), 
	HR_EMPLOYEE_SATISFACTION("Employee Satisfaction"), 
	HR_WORKING_CONDITIONS("Working Conditions"), 
	HR_RECRUITING_OVERVIEW("Recruiting");

	// The title of the GameElement, that will be displayed on top.
	public final String title;

	private UIElementType(String title) {
		this.title = title;
	}
}
