package de.uni.mannheim.capitalismx.ui.controller.module.resdev;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.procurement.component.ComponentCategory;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.sales.contracts.Contract;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class ResearchInfoController implements Initializable {
    final int insertRow = 0;
    @FXML
    Label yearTV;

    @FXML
    Label yearConsole;

    @FXML
    Label yearNotebook;

    @FXML
    Label yearPhone;

    @FXML
    GridPane tvGrid, consoleGrid, notebookGrid, phoneGrid;

    public void tvUp(){
        if(Integer.parseInt(yearTV.getText()) < 2017) {
            yearTV.setText(String.valueOf(Integer.parseInt(yearTV.getText()) + 1));
            updateInfo('t');
        }
    }

    public void tvDown() {
        if (Integer.parseInt(yearTV.getText()) > 1990) {
            yearTV.setText(String.valueOf(Integer.parseInt(yearTV.getText()) - 1));
            updateInfo('t');
        }
    }

    public void consoleUp(){
        if(Integer.parseInt(yearConsole.getText()) < 2017) {
            yearConsole.setText(String.valueOf(Integer.parseInt(yearConsole.getText()) + 1));
            updateInfo('c');
        }
    }

    public void consoleDown(){
        if (Integer.parseInt(yearConsole.getText()) > 1990) {
            yearConsole.setText(String.valueOf(Integer.parseInt(yearConsole.getText()) - 1));
            updateInfo('c');
        }
    }

    public void notebookUp(){
        if(Integer.parseInt(yearNotebook.getText()) < 2017) {
            yearNotebook.setText(String.valueOf(Integer.parseInt(yearNotebook.getText()) + 1));
            updateInfo('n');
        }
    }

    public void notebookDown(){
        if (Integer.parseInt(yearNotebook.getText()) > 1990) {
            yearNotebook.setText(String.valueOf(Integer.parseInt(yearNotebook.getText()) - 1));
            updateInfo('n');
        }
    }

    public void phoneUp(){
        if(Integer.parseInt(yearPhone.getText()) < 2017) {
            yearPhone.setText(String.valueOf(Integer.parseInt(yearPhone.getText()) + 1));
            updateInfo('p');
        }
    }

    public void phoneDown(){
        if (Integer.parseInt(yearPhone.getText()) > 1990) {
            yearPhone.setText(String.valueOf(Integer.parseInt(yearPhone.getText()) - 1));
            updateInfo('p');
        }
    }

    public void updateInfo(char category){
        ArrayList<ComponentType>[] list;
        Label yearSave;
        GridPane gridSave;
        switch (category){
            case 't':
                yearSave = this.yearTV;
                gridSave = this.tvGrid;
                list = new ArrayList[4];
                list[0] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.T_DISPLAY);
                list[1] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.T_SOUND);
                list[2] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.T_OS);
                list[3] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.T_CASE);
                break;
            case 'c':
                yearSave = this.yearConsole;
                gridSave = this.consoleGrid;
                list = new ArrayList[5];
                list[0] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.G_CPU);
                list[1] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.G_DISPLAYCASE);
                list[2] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.G_POWERSUPPLY);
                list[3] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.G_CONNECTIVITY);
                list[4] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.G_CAMERA);
                break;
            case 'n':
                yearSave = this.yearNotebook;
                gridSave = this.notebookGrid;
                list = new ArrayList[5];
                list[0] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.N_CPU);
                list[1] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.N_STORAGE);
                list[2] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.N_DISPLAYCASE);
                list[3] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.N_SOFTWARE);
                list[4] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.N_POWERSUPPLY);
                break;
            case 'p':
                yearSave = this.yearPhone;
                gridSave = this.phoneGrid;
                list = new ArrayList[6];
                list[0] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.P_CPU);
                list[1] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.P_DISPLAYCASE);
                list[2] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.P_CAMERA);
                list[3] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.P_CONNECTIVITY);
                list[4] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.P_POWERSUPPLY);
                list[5] = (ArrayList<ComponentType>) ComponentType.getTypesByCategory(ComponentCategory.P_KEYPAD);
                break;
            default:
                yearSave = new Label("");
                gridSave = new GridPane();
                list = new ArrayList[0];
        }
        removeInfoLabels(gridSave);
        if(list.length > 0){
            for(int i = 0; i < list.length; i++){
                for(ComponentType ct : list[i]){
                    if(ct.getAvailabilityDate() <= Integer.parseInt(yearSave.getText())){
                        gridSave.add(new Label(ct.getComponentName()), i+1, insertRow);
                    }
                }
            }
        }

    }

    public void setInfo(ArrayList<ComponentType> list){

    }

    public void removeInfoLabels(GridPane gridPane) {
        System.out.println("Removed");
        ObservableList<Node> childrens = gridPane.getChildren();
        Iterator<Node> i = childrens.iterator();
        while(i.hasNext()){
            Node node = i.next();
            if(node instanceof Label && gridPane.getColumnIndex(node) > 0 && gridPane.getRowIndex(node) == insertRow) {
                i.remove();
                //Label imageView = (Label) node; // use what you want to remove
                //gridPane.getChildren().remove(imageView);
            }
        }
        /*
        for(Node node : childrens) {
            if(node instanceof Label && gridPane.getColumnIndex(node) > 0 && gridPane.getRowIndex(node) == insertRow) {
                Label imageView = (Label) node; // use what you want to remove
                gridPane.getChildren().remove(imageView);
            }
        }

         */

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
