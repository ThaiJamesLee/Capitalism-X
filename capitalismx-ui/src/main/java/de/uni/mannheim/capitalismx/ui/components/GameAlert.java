package de.uni.mannheim.capitalismx.ui.components;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.uni.mannheim.capitalismx.ui.application.CapXApplication;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

public class GameAlert extends Alert {

	/**
	 * Creates a new {@link GameAlert}. GameAlert is a custom version of the
	 * standard {@link Alert} from JavaFX. It can be configured in the same way
	 * using {@link ButtonType}s etc.
	 * 
	 * @param alertType   The {@link AlertType} sets the icon, as well as the
	 *                    default {@link ButtonType}s.
	 * @param title       The text to be displayed in the top half. (Should be
	 *                    short)
	 * @param description The text explaining the reason, the {@link GameAlert} is
	 *                    displayed or giving instructions to the player.
	 */
	public GameAlert(AlertType alertType, String title, String description) {
		super(alertType);

		FontAwesomeIcon icon = new FontAwesomeIcon();
		icon.setSize("2.5em");
		icon.getStyleClass().add("icon_primary");

		switch (alertType) {
		case CONFIRMATION:
			icon.setIcon(FontAwesomeIconName.QUESTION_CIRCLE);
			break;
		case INFORMATION:
			icon.setIcon(FontAwesomeIconName.INFO_CIRCLE);
			break;
		case WARNING:
			icon.setIcon(FontAwesomeIconName.WARNING);
		default:
			break;
		}

		this.setHeaderText(title);
		this.setContentText(description);
		this.setGraphic(icon);
		this.initOwner(UIManager.getInstance().getStage());
		this.initStyle(StageStyle.UNDECORATED);
		this.getDialogPane().getStylesheets()
				.add(CapXApplication.class.getResource("/css/1080p/general1080p.css").toExternalForm());
		this.getDialogPane().getStylesheets()
				.add(CapXApplication.class.getResource("/css/dialog.css").toExternalForm());
	}

}
