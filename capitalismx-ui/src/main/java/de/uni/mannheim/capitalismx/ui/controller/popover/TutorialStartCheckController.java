package de.uni.mannheim.capitalismx.ui.controller.popover;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.tutorial.Tutorial;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class TutorialStartCheckController implements Initializable {

	private PopOver popover;

	@FXML
	private Button accept, refuse;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Tutorial tutorial = new Tutorial();
		accept.setOnAction(e -> {
			UIManager.getInstance().setTutorial(tutorial);
			tutorial.nextChapter();
			popover.hide();
		});
		refuse.setOnAction(e -> {
			popover.hide();
		});
	}

	public void setPopover(PopOver p) {
		popover = p;
	}

}
