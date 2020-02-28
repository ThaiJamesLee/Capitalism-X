package de.uni.mannheim.capitalismx.ui.controller.popover.logistics;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.logistic.support.ProductSupport;
import de.uni.mannheim.capitalismx.ui.component.logistics.ProductSupportListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * This class represents the support type selection for the product support in the logistics UI. It displays different
 * support types that the user can add to the list of provided support types.
 *
 * @author sdupper
 */
public class ProductSupportDetailListController implements UpdateableController {

    @FXML
    private ListView<ProductSupport.SupportType> supportTypeListView;

    @Override
    public void update() {
    }

    /*
     * Generates an entry in the support type selection for all available support types.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        supportTypeListView.setCellFactory(partnerListView -> {
            ProductSupportListViewCell cell = new ProductSupportListViewCell(supportTypeListView);
            cell.removeRemoveButton();
            return cell;
        });

        ArrayList<ProductSupport.SupportType> supportTypes = controller.generateSupportTypeSelection();

        for(ProductSupport.SupportType supportType : supportTypes) {
            supportTypeListView.getItems().add(supportType);
        }

    }

    /**
     * Updates the list of available support types. In particular, the currently provided support types are removed from
     * the list of available types.
     */
    public void updateAvailableSupportTypes(){
        Platform.runLater(new Runnable() {
            public void run() {
                supportTypeListView.getItems().clear();
                for(ProductSupport.SupportType supportType : GameController.getInstance().generateSupportTypeSelection()){
                    supportTypeListView.getItems().add(supportType);
                }
                supportTypeListView.getItems().removeAll(GameController.getInstance().getSupportTypes());
            }
        });
    }

}