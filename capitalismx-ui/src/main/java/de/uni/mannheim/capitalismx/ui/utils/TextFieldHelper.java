package de.uni.mannheim.capitalismx.ui.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Helper class that provides some useful methods for {@link TextField}s.
 * 
 * @author Jonathan
 *
 */
public class TextFieldHelper {

	/**
	 * Turns a given TextField into a numeric one, so that it will only accept
	 * numbers as input.
	 * 
	 * @param textField {@link TextField} to make numeric.
	 */
	public static void makeTextFieldNumeric(TextField textField) {
		textField.setText("0");
		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					textField.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if (newValue.equals("")) {
					textField.setText("0");
				}
				if(oldValue.equals("0") && newValue.startsWith("0")) {
					textField.setText(newValue.replace("0", ""));
				}
			}
		});
	}

}
