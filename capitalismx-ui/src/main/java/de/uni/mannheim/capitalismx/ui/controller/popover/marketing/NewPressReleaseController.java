package de.uni.mannheim.capitalismx.ui.controller.popover.marketing;


import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.marketing.domain.PressRelease;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.GameModuleType;
import de.uni.mannheim.capitalismx.ui.components.marketing.NewPressReleaseViewCell;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import de.uni.mannheim.capitalismx.ui.controller.module.marketing.PressReleaseListController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * Controller of the overlay that pops up when issuing a new PressRelease
 * 
 * @author Alex
 *
 */

public class NewPressReleaseController implements UpdateableController {
	//TODO PressRelease kostet noch nichts
	//TODO PressRelease effekt: Abmilderung ExternalEvent oder Strafe im Company Image

    @FXML
    ListView<PressRelease> newPressReleaseOptionsList;
    
    @FXML 
    Button publishBtn;
    
    @Override
    public void update() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        newPressReleaseOptionsList.setCellFactory(pressReleasesListView -> new NewPressReleaseViewCell(newPressReleaseOptionsList));
    
        PressRelease[] options = controller.getAllPressReleases();

        for(PressRelease pr : options) {
            newPressReleaseOptionsList.getItems().add(pr);
        }
    	

    	
    	//enable Publish Button only if a PressRelease is Selected	
    	publishBtn.disableProperty().bind(Bindings.isEmpty(newPressReleaseOptionsList.getSelectionModel().getSelectedItems()));
    	
    	publishBtn.setOnAction(e -> {
    		//publish new PressRelease

    		PressRelease pr = newPressReleaseOptionsList.getSelectionModel().getSelectedItem();
    		controller.makePressRelease(pr);
    		
    		//update PressReleases Module
    		PressReleaseListController uiController = (PressReleaseListController) UIManager.getInstance().getModule(GameModuleType.MARKETING_PRESSRELEASE).getController();
    		uiController.updateList();
    		uiController.hidePopover();
    	});
    }

}