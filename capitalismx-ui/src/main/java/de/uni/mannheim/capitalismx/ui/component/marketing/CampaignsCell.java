package de.uni.mannheim.capitalismx.ui.component.marketing;

import java.io.IOException;

import de.uni.mannheim.capitalismx.marketing.domain.Campaign;
import de.uni.mannheim.capitalismx.marketing.domain.PressRelease;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.popover.marketing.NewPressReleaseController;
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

public class CampaignsCell extends ListCell<Campaign> {

    @FXML
    private Label titleLabel;

    @FXML
    private Label mediaLabel;
    
    @FXML
    private Label costLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button sellButton;

    private FXMLLoader loader;

   // private ListView<Campaign> campaignsList;

    public CampaignsCell(){
    }

    @Override
    protected void updateItem(Campaign camp, boolean empty) {
        super.updateItem(camp, empty);
        if(empty || camp == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component/mkt_campaigns_cell.fxml"), UIManager.getResourceBundle());
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            
            titleLabel.setText(camp.getName());
            titleLabel.setTextAlignment(TextAlignment.LEFT);
            titleLabel.setStyle("-fx-font-weight: bold");
            
            mediaLabel.setText(camp.getMedia().getName());
            costLabel.setText(camp.getMedia().getCost() + " CC");
//            costLabel.setText(camp.getCost() + " CC");

            setText(null);
            setGraphic(gridPane);
        }
    }

}