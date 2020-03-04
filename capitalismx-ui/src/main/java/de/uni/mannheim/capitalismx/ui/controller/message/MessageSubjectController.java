package de.uni.mannheim.capitalismx.ui.controller.message;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageSubjectController implements Initializable {

    @FXML
    private Label subjectSender;
    @FXML
    private Label subjectDate;
    @FXML
    private Label subjectSubject;
    private int index;

    @FXML
    public void showContent(){
        UIManager.getInstance().getGamePageController().getMessageController().setContent(this.index);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setSubjectSender(String sender){
        subjectSender.setText(sender);
    }
    public void setSubjectDate(String sDate){
        subjectDate.setText(sDate);
    }
    public void setSubjectSubject(String subject){
        subjectSubject.setText(subject);
    }
}
