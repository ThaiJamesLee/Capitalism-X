package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class FinanceOverviewController extends GameModuleController {

    @FXML
    Label cashLabel;

    @FXML
    Label assetsLabel;

    @FXML
    Label liabilitesLabel;

    @FXML
    Label netWorthLabel;

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        cashLabel.setText(controller.getCash() + "");
        //assetsLabel.setText(controller.getAssets() + "");
        //liabilitesLabel.setText(controller.getLiabilities() + "");
        //netWorthLabel.setText(controller.getNetWorth(GameState.getInstance().getGameDate()) + "");
    }
}
