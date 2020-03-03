package de.uni.mannheim.capitalismx.ui.controller.module.logistics;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.logistic.support.ProductSupport;
import de.uni.mannheim.capitalismx.ui.controller.popover.logistics.ProductSupportDetailListController;
import de.uni.mannheim.capitalismx.ui.controller.popover.logistics.SupportPartnerDetailListController;
import de.uni.mannheim.capitalismx.ui.util.PopOverFactory;
import javafx.scene.control.ListView;
import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameModuleType;
import de.uni.mannheim.capitalismx.ui.component.logistics.LogisticsPartnerDetailListViewCell;
import de.uni.mannheim.capitalismx.ui.component.logistics.SupportPartnerListViewCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * This class represents the external support partner in the logistics UI. It allows the user to hire an external
 * support partner.
 *
 * @author sdupper
 */
public class SupportPartnerController implements Initializable {

    @FXML
    private Button supportPartnerButton;

    @FXML
    private Button fireSupportPartnerButton;

    @FXML
    private ListView<ProductSupport.ExternalSupportPartner> externalPartnerListView;

    /**
     * The popover for the external support partner selection.
     */
    private PopOver popover;

    /**
     * The popover factory for the support partner selection.
     */
    private PopOverFactory factory;

    public SupportPartnerController() {
    }

    /*
     * Displays information about the current external support partner and generates the external support partner
     * selection popover. The popover is displayed after clicking the supportPartnerButton.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        ProductSupport.ExternalSupportPartner externalSupportPartner = controller.getExternalSupportPartner();
        if ((externalSupportPartner != null) && (externalSupportPartner != ProductSupport.ExternalSupportPartner.NO_PARTNER)) {
            fireSupportPartnerButton.setVisible(true);
            externalPartnerListView.getItems().add(externalSupportPartner);
            supportPartnerButton.setText(UIManager.getLocalisedString("logistics.support.pselection.hiredifferent"));
        } else {
            fireSupportPartnerButton.setVisible(false);
            supportPartnerButton.setText(UIManager.getLocalisedString("logistics.support.pselection.hire"));
        }

        factory = new PopOverFactory();
        factory.createStandardOverlay("fxml/popover/support_partner_popover.fxml");
        popover = factory.getPopover();

        supportPartnerButton.setOnAction(e -> {
            this.updateAvailablePartners();
            showPopover();
        });

        fireSupportPartnerButton.setOnAction(e -> {
            controller.removeExternalSupportPartner();
            externalPartnerListView.getItems().clear();
            fireSupportPartnerButton.setVisible(false);
            supportPartnerButton.setText(UIManager.getLocalisedString("logistics.support.pselection.hire"));
            ProductSupportController uiController = (ProductSupportController) UIManager.getInstance()
                    .getModule(GameModuleType.LOGISTICS_SUPPORT_TYPE_OVERVIEW).getController();
            uiController.removeAllSupportTypes();
        });

        externalPartnerListView.setCellFactory(partnerListView -> {
            SupportPartnerListViewCell cell = new SupportPartnerListViewCell(externalPartnerListView);
            cell.setAddMouseListener(false);
            return cell;
        });
        externalPartnerListView.setPlaceholder(new Label(UIManager.getLocalisedString("list.placeholder.externalsupportpartner")));
    }

    /**
     * Updates the logistics UI when an external support partner is hired.
     * @param externalPartner The new external support partner.
     */
    public void addExternalPartner(ProductSupport.ExternalSupportPartner externalPartner) {
        externalPartnerListView.getItems().clear();
        fireSupportPartnerButton.setVisible(true);
        externalPartnerListView.getItems().add(externalPartner);
        supportPartnerButton.setText(UIManager.getLocalisedString("logistics.support.pselection.hiredifferent"));
        popover.hide();
    }

    /**
     * Displays the external support partner selection popover.
     */
    private void showPopover() {
        popover.show(UIManager.getInstance().getStage());
    }

    /**
     * Updates the list of available support partners. In particular, the currently employed partner is removed from
     * the list of available partners.
     */
    public void updateAvailablePartners(){
        ((SupportPartnerDetailListController) this.factory.getPopoverController()).updateAvailablePartners();
    }

}