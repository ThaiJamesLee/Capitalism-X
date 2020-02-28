package de.uni.mannheim.capitalismx.ui.component.logistics;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.support.ProductSupport;
import de.uni.mannheim.capitalismx.logistic.support.exception.NoExternalSupportPartnerException;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameModuleType;
import de.uni.mannheim.capitalismx.ui.controller.module.logistics.ProductSupportController;
import de.uni.mannheim.capitalismx.ui.util.CapCoinFormatter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.text.NumberFormat;

/**
 * This class represents an entry in the list of support types provided by the company / the list of support types
 * available. It displays information like the quality of a specific support type.
 *
 * @author sdupper
 */
public class ProductSupportListViewCell extends ListCell<ProductSupport.SupportType> {

    @FXML
    private Label costsLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label qualityLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button removeButton;

    private FXMLLoader loader;

    private boolean addMouseListener;
    private boolean removeRemoveButton;

    /**
     * The ListView of all support types provided/available.
     */
    private ListView<ProductSupport.SupportType> supportTypeListView;

    /**
     * Constructor
     * @param supportTypeListView The ListView of all support types provided/available.
     */
    public ProductSupportListViewCell(ListView<ProductSupport.SupportType> supportTypeListView){
        this.supportTypeListView = supportTypeListView;
        this.addMouseListener = true;
        this.removeRemoveButton = false;
    }

    /*
     * Generates an entry in the list of support types provided/available for each new support type added to the
     * supportTypeListView according to the support type's characteristics. If the user clicks on the remove button of a
     * support type, the type is removed from the list of provided support types. The remove button is only displayed
     * for entries in the list of provided support types. The mouse listener is only added to entries in the list of
     * available support types.
     */
    @Override
    protected void updateItem(ProductSupport.SupportType supportType, boolean empty) {
        super.updateItem(supportType, empty);
        if(empty || supportType == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component/product_support_list_cell.fxml"), UIManager.getResourceBundle());
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            GameController controller = GameController.getInstance();
            nameLabel.setText(supportType.getLocalizedName(UIManager.getResourceBundle().getLocale()));
            costsLabel.setText(
                    UIManager.getLocalisedString("logistics.support.pselection.costs") + ": " + CapCoinFormatter.getCapCoins(supportType.getCostsSupportType()));
            qualityLabel.setText(UIManager.getLocalisedString("logistics.support.pselection.quality") + ": " + NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale())
                    .format(supportType.getSupportTypeQuality() / 100.0));

            if(addMouseListener){
                gridPane.setOnMouseClicked(e -> {
                    try {
                        controller.addSupport(supportType);
                    } catch (NoExternalSupportPartnerException ex) {
                        ex.printStackTrace();
                    }
                    ProductSupportController uiController = (ProductSupportController) UIManager.getInstance()
                            .getModule(GameModuleType.LOGISTICS_SUPPORT_TYPE_OVERVIEW).getController();
                    uiController.addSupportType(supportType);

                    supportTypeListView.getSelectionModel().clearSelection();
                });
            }

            if(removeRemoveButton){
                this.gridPane.getChildren().remove(this.removeButton);
                //this.sellButton.setVisible(false);
            }else{
                removeButton.setOnAction(e -> {
                    controller.removeSupport(supportType);
                    this.supportTypeListView.getItems().remove(supportType);
                    ProductSupportController uiController = (ProductSupportController) UIManager.getInstance()
                            .getModule(GameModuleType.LOGISTICS_SUPPORT_TYPE_OVERVIEW).getController();
                    uiController.updateAvailableSupportTypes();
                });
            }

            setText(null);
            setGraphic(gridPane);
        }
    }

    public void setAddMouseListener(boolean addMouseListener){
        this.addMouseListener = addMouseListener;
    }

    /**
     * Removes the removeButton from the entries in the list of provided/available support types.
     */
    public void removeRemoveButton(){
        this.removeRemoveButton = true;
    }

}