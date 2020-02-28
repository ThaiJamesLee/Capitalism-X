package de.uni.mannheim.capitalismx.ui.controller.module.resdev;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ResearchInfoController implements Initializable {

    @FXML
    Label yearTV;

    @FXML
    Label yearConsole;

    @FXML
    Label yearNotebook;

    @FXML
    Label yearPhone;

    @FXML
    Label tv1, tv2, tv3, tv4, tv5, tv6, tv7;

    @FXML
    Label console1, console2, console3, console4, console5, console6, console7;

    @FXML
    Label notebook1, notebook2, notebook3, notebook4, notebook5, notebook6, notebook7;

    @FXML
    Label phone1, phone2, phone3, phone4, phone5, phone6, phone7;

    public void yearUp(ActionEvent e){
        System.out.println(e.getTarget());
        System.out.println(e.toString());
    }

    public void yearDown(ActionEvent e){
        System.out.println(e.getTarget());
        System.out.println(e.toString());
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
