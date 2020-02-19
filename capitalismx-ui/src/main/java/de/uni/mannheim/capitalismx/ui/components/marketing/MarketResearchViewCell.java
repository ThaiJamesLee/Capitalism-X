package de.uni.mannheim.capitalismx.ui.components.marketing;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import de.uni.mannheim.capitalismx.marketing.marketresearch.MarketResearch;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.popover.marketing.NewMarketResearchController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;


/**
 *  List Cells displaying info on all possibles {@link MarketResearch}s that can be published 
 *  showing info on the type of the obtained report, the issue date and the costs of publishing.
 *  
 *  Used in the Overlay {@link NewMarketResearchController} TODO
 * 
 * @author Alex
 *
 */

public class MarketResearchViewCell extends ListCell<MarketResearch> {

    @FXML
    private Label titleLabel;

    @FXML
    private Label costLabel;
    
    @FXML
    private Label dateLabel;

    @FXML
    private GridPane gridPane;

    private FXMLLoader loader;

    private ListView<MarketResearch> MarketResearchList;

    public MarketResearchViewCell(ListView<MarketResearch> MarketResearchList){
        this.MarketResearchList = MarketResearchList;
    }

    @Override
    protected void updateItem(MarketResearch mr, boolean empty) {
        super.updateItem(mr, empty);
        if(empty || mr == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/mkt_report_cell.fxml"), UIManager.getResourceBundle());
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            
            titleLabel.setText(mr.getReportName());
            titleLabel.setTextAlignment(TextAlignment.LEFT);

            String dateString = mr.getFormattedDate();
            dateLabel.setText(dateString);
            
            costLabel.setText(mr.getCost() + " CC");

            setText(null);
            setGraphic(gridPane);
        }
    }

}