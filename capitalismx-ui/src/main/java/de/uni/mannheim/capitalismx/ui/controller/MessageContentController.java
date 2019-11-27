package de.uni.mannheim.capitalismx.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MessageContentController {

    @FXML
    private Label contentSender;
    @FXML
    private Label contentDate;
    @FXML
    private Label contentSubject;
    @FXML
    private Label contentContent;

    public void setContentSender(String sender){
        contentSender.setText(sender);
    }
    public void setContentDate(String date){
        contentDate.setText(date);
    }
    public void setContentSubject(String subject){
        contentSubject.setText(subject);
    }
    public void setContentContent(String content){
        contentContent.setText(content);
    }
}
