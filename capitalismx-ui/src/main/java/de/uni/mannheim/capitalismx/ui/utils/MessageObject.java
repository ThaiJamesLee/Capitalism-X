package de.uni.mannheim.capitalismx.ui.utils;

import de.uni.mannheim.capitalismx.ui.controller.MessageSubjectController;
import javafx.scene.Parent;

public class MessageObject {

    private String sender;
    private String date;
    private String subject;
    private String content;
    private boolean isInternal;
    Parent subjectPanel;
    Parent messageContent;

    public MessageObject(String sender, String date, String subject, String content, boolean isInternal, Parent subjectPanel, Parent messageContent){
        this.content = content;
        this.sender = sender;
        this.subject = subject;
        this.date = date;
        this.isInternal = isInternal;
        this.subjectPanel = subjectPanel;
        this.messageContent = messageContent;
    }

    public String getSender() {
        return sender;
    }

    public String getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public boolean isInternal() {
        return isInternal;
    }

    public Parent getSubjectPanel() {
        return subjectPanel;
    }

    public Parent getMessageContent() {
        return messageContent;
    }
}
