package de.uni.mannheim.capitalismx.ui.controller;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.utils.MessageObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageSubjectController implements Initializable {

    @FXML
    private Label subjectSender;
    @FXML
    private Label subjectDate;
    @FXML
    private Label subjectSubject;
    private MessageObject messageReference;

    @FXML
    public void showContent(){
        System.out.println("Hier ist der Content");
        UIManager.getInstance().getGamePageController().getMessageController().setContent(messageReference);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public MessageObject getMessageReference() {
        return messageReference;
    }

    public void setMessageReference(MessageObject messageReference) {
        this.messageReference = messageReference;
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
