package de.uni.mannheim.capitalismx.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MessageSubjectController {

    @FXML
    private Label subjectSender;
    @FXML
    private Label subjectDate;
    @FXML
    private Label subjectSubject;

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
