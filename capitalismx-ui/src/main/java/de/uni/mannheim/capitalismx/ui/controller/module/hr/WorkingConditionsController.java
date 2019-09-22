package de.uni.mannheim.capitalismx.ui.controller.module.hr;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.hr.domain.Benefit;
import de.uni.mannheim.capitalismx.hr.domain.BenefitType;
import de.uni.mannheim.capitalismx.ui.application.Main;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;

public class WorkingConditionsController extends GameModuleController {

	@FXML
	public RadioButton workTime1, workTime2, workTime3, hours6, hours8, hours10, car1, car2, car3, it1, it2, food1,
			food2, gym1, gym2, gym3;
	@FXML
	public ToggleGroup workTimeModelGroup, workTimeGroup, carGroup, itGroup, foodGroup, gymGroup;

	/**
	 * Creates a tooltip containing information about the monetary impact of the
	 * {@link Benefit} of the given {@link RadioButton} to the button.
	 * 
	 * @param radio The {@link RadioButton} to get a custom {@link Tooltip} for.
	 */
	private void createCostTooltip(RadioButton radio) {
		// TODO tooltip gets displayed after way too long -> either upgrade to newer
		// version of JFX, where delay is customizable or use some hack/workaround 
		// -> ControlsFX
		Tooltip tooltip = new Tooltip();
		tooltip.setText(((Benefit) radio.getUserData()).getMonetaryImpact() + "CC per Employee/Month");
		radio.setTooltip(tooltip);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initRadioButtons();
		initToggleGroups();
	}

	/**
	 * Sets the UserData for all the RadioButtons, which is necessary for the
	 * {@link javax.swing.event.ChangeListener} to handle its {@link ToggleGroup}.
	 */
	private void initRadioButtons() {
		workTime1.setUserData(Benefit.WTM_0);
		createCostTooltip(workTime1);
		workTime2.setUserData(Benefit.WTM_1);
		createCostTooltip(workTime2);
		workTime3.setUserData(Benefit.WTM_2);
		createCostTooltip(workTime3);
		hours6.setUserData(Benefit.WORK_TIME_0);
		createCostTooltip(hours6);
		hours8.setUserData(Benefit.WORK_TIME_1);
		createCostTooltip(hours8);
		hours10.setUserData(Benefit.WORK_TIME_2);
		createCostTooltip(hours10);
		car1.setUserData(Benefit.COMPANY_CAR_0);
		createCostTooltip(car1);
		car2.setUserData(Benefit.COMPANY_CAR_1);
		createCostTooltip(car2);
		car3.setUserData(Benefit.COMPANY_CAR_2);
		createCostTooltip(car3);
		it1.setUserData(Benefit.IT_EQUIPMENT_0);
		createCostTooltip(it1);
		it2.setUserData(Benefit.IT_EQUIPMENT_1);
		createCostTooltip(it2);
		food1.setUserData(Benefit.FOOD_AND_COFFEE_0);
		createCostTooltip(food1);
		food2.setUserData(Benefit.FOOD_AND_COFFEE_1);
		createCostTooltip(food2);
		gym1.setUserData(Benefit.GYM_AND_SPORTS_0);
		createCostTooltip(gym1);
		gym2.setUserData(Benefit.GYM_AND_SPORTS_1);
		createCostTooltip(gym2);
		gym3.setUserData(Benefit.GYM_AND_SPORTS_2);
		createCostTooltip(gym3);
	}

	/**
	 * Initiates a {@link ToggleGroup} by getting the
	 * 
	 * @param group
	 */
	private void initToggleGroup(ToggleGroup group, BenefitType type) {
		HRDepartment hrDep = HRDepartment.getInstance();

		// Define a ChangeListener for the Group
		ChangeListener<Toggle> workingConditionChangeListener = new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				hrDep.changeBenefitSetting((Benefit) (newValue.getUserData()));
			}
		};
		group.selectedToggleProperty().addListener(workingConditionChangeListener);

		// set the inititally toggled radiobutton
		for (Toggle toggle : group.getToggles()) {
			Benefit benefitOfToggle = (Benefit) (toggle.getUserData());
			if (hrDep.getBenefitSettings().getBenefits().get(type).equals(benefitOfToggle)) {
				group.selectToggle(toggle);
				return;
			}
		}
	}

	/**
	 * Calls the initToggleGroup() method for each existing {@link ToggleGroup} with
	 * the correct parameters.
	 */
	private void initToggleGroups() {
		initToggleGroup(carGroup, BenefitType.COMPANY_CAR);
		initToggleGroup(foodGroup, BenefitType.FOOD_AND_COFFEE);
		initToggleGroup(workTimeGroup, BenefitType.WORKTIME);
		initToggleGroup(workTimeModelGroup, BenefitType.WORKING_TIME_MODEL);
		initToggleGroup(itGroup, BenefitType.IT_EQUIPMENT);
		initToggleGroup(gymGroup, BenefitType.GYM_AND_SPORTS);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
