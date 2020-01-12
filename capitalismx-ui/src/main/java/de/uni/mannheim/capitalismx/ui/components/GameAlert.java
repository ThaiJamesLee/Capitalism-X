package de.uni.mannheim.capitalismx.ui.components;

import de.uni.mannheim.capitalismx.ui.application.CapXApplication;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

public class GameAlert extends Alert {

	public GameAlert(AlertType alertType) {
		super(alertType);
		this.initOwner(UIManager.getInstance().getStage());
		this.initStyle(StageStyle.UNDECORATED);
		this.getDialogPane().getStylesheets().add(CapXApplication.class.getResource("/css/1080p/general1080p.css").toExternalForm());
		this.getDialogPane().getStylesheets().add(CapXApplication.class.getResource("/css/dialog.css").toExternalForm());
	}

}
