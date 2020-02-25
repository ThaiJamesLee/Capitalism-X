package de.uni.mannheim.capitalismx.ui.controller.module.logistics;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.logistic.support.ProductSupport;
import de.uni.mannheim.capitalismx.ui.components.general.GameAlert;
import de.uni.mannheim.capitalismx.ui.components.logistics.ProductSupportListViewCell;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.utils.PopOverFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * This class represents the product support in the logistics UI. It allows the user to add new support types to the
 * list of provided support types.
 *
 * @author sdupper
 */
public class ProductSupportController implements Initializable {

    @FXML
    ListView<ProductSupport.SupportType> supportTypeListView;

    @FXML
    private Button addSupportButton;

    /**
     * The popover for the support type selection.
     */
    private PopOver popover;

    public ProductSupportController() {
    }

    /*
     * Displays information about the support types currently provided by the company and generates the support type
     * selection popover. The popover is displayed after clicking the addSupportButton.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();

        //load support types
        for(ProductSupport.SupportType supportType : controller.getSupportTypes()){
            if(supportType != ProductSupport.SupportType.NO_PRODUCT_SUPPORT){
                supportTypeListView.getItems().add(supportType);
            }
        }

        supportTypeListView.setCellFactory(partnerListView -> {
            ProductSupportListViewCell cell = new ProductSupportListViewCell(supportTypeListView);
            cell.setAddMouseListener(false);
            return cell;
        });
        supportTypeListView.setPlaceholder(new Label(UIManager.getLocalisedString("list.placeholder.support")));

        PopOverFactory helper = new PopOverFactory();
        helper.createStandardOverlay("fxml/popover/product_support_popover.fxml");
        popover = helper.getPopover();

        addSupportButton.setOnAction(e -> {
            if(controller.getExternalSupportPartner() != ProductSupport.ExternalSupportPartner.NO_PARTNER){
                showPopover();
            }else{
                GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("logistics.support.error.partner.title"),
                        UIManager.getLocalisedString("logistics.support.error.partner.description"));
                error.showAndWait();
            }
        });
    }

    /**
     * Updates the list of support types provided by the company when a new support type is added.
     * @param supportType The support type to be added.
     */
    public void addSupportType(ProductSupport.SupportType supportType) {
        supportTypeListView.getItems().add(supportType);
        popover.hide();
    }

    /**
     * Removes all support types from the list of provided support types. This is required when the external support
     * partner is fired.
     */
    public void removeAllSupportTypes(){
        Platform.runLater(new Runnable() {
            public void run() {
                supportTypeListView.getItems().clear();
            }
        });    }

    /**
     * Displays the support type selection popover.
     */
    private void showPopover() {
        popover.show(UIManager.getInstance().getStage());
    }

}