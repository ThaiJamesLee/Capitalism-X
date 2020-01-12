package de.uni.mannheim.capitalismx.ui.controller.module.resdev;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryUpgraderController extends GameModuleController {

    @FXML
    private GridPane categoryGrid;

    @FXML
    private Button btnTV;

    @FXML
    private Button btnConsole;

    @FXML
    private Button btnNotebook;

    @FXML
    private Button btnPhone;

    @FXML
    public void unlockTV(){
        //todo: add something to do.
        categoryGrid.getChildren().remove(btnTV);
        categoryGrid.add(new Label("Unlocked"), 0,1);
    }
    @FXML
    public void unlockConsole(){
        //todo: add something to do.
        categoryGrid.getChildren().remove(btnConsole);
        categoryGrid.add(new Label("Unlocked"), 0,1);
    }
    @FXML
    public void unlockNotebook(){
        //todo: add something to do.
        categoryGrid.getChildren().remove(btnNotebook);
        categoryGrid.add(new Label("Unlocked"), 0,1);
    }
    @FXML
    public void unlockPhone(){
        //todo: add something to do.
        categoryGrid.getChildren().remove(btnPhone);
        categoryGrid.add(new Label("Unlocked"), 0,1);
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    public CategoryUpgraderController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameState.getInstance().getResearchAndDevelopmentDepartment().getInstance();
    }


    @Override
    public void update() {

    }
}
