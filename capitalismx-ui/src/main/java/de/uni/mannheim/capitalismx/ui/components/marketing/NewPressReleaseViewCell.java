package de.uni.mannheim.capitalismx.ui.components.marketing;

import java.io.IOException;

import de.uni.mannheim.capitalismx.marketing.domain.PressRelease;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.overlay.marketing.NewPressReleaseController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;


/**
 *  List Cells displaying info on all possibles {@link PressRelease}s that can be published 
 *  showing info on the Title and the costs of publishing.
 *  
 *  Used in the Overlay {@link NewPressReleaseController}
 * 
 * @author Alex
 *
 */

public class NewPressReleaseViewCell extends ListCell<PressRelease> {

    @FXML
    private Label titleLabel;

    @FXML
    private Label costLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button sellButton;

    private FXMLLoader loader;

    private ListView<PressRelease> pressReleaseList;

    public NewPressReleaseViewCell(ListView<PressRelease> pressReleaseList){
        this.pressReleaseList = pressReleaseList;
    }

    @Override
    protected void updateItem(PressRelease pr, boolean empty) {
        super.updateItem(pr, empty);
        if(empty || pr == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/newPressRelease_cell.fxml"), UIManager.getResourceBundle());
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            
            titleLabel.setText(pr.getName());
            titleLabel.setTextAlignment(TextAlignment.LEFT);
            costLabel.setText(pr.getCost() + "CC");

            setText(null);
            setGraphic(gridPane);
        }
    }

}