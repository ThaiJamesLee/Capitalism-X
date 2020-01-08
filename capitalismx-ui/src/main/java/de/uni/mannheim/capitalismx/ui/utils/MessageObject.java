package de.uni.mannheim.capitalismx.ui.utils;

import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.controller.MessageSubjectController;
import javafx.scene.Parent;

/**
 *
 */
public class MessageObject {

    private String sender;
    private String date;
    private String subject;
    private String content;
    private boolean isInternal;
    private GameViewType jumpTo;
    Parent subjectPanel;
    Parent messageContent;

    /**
     * Use this object constructor to pass messages system wide. It has no jummp to view button.
     * @param sender
     * @param date
     * @param subject
     * @param content
     * @param isInternal
     */
    public MessageObject(String sender, String date, String subject, String content, boolean isInternal){
        this.content = content;
        this.sender = sender;
        this.subject = subject;
        this.date = date;
        this.isInternal = isInternal;
        this.jumpTo =  null;
        this.subjectPanel = null;
        this.messageContent = null;
    }

    /**
     * Use this object constructor to pass messages system wide.
     * @param sender Sender of the message.
     * @param date Game date of the message.
     * @param subject Subject of the message.
     * @param content Text content of the message.
     * @param isInternal Did the message come from internal department or is it an outside message about outside incidents.
     */
    public MessageObject(String sender, String date, String subject, String content, boolean isInternal, GameViewType jumpTo){
        this.content = content;
        this.sender = sender;
        this.subject = subject;
        this.date = date;
        this.isInternal = isInternal;
        this.jumpTo =  jumpTo;
        this.subjectPanel = null;
        this.messageContent = null;
    }

    public MessageObject(String sender, String date, String subject, String content, boolean isInternal, GameViewType jumpTo, Parent subjectPanel, Parent messageContent){
        this.content = content;
        this.sender = sender;
        this.subject = subject;
        this.date = date;
        this.isInternal = isInternal;
        this.jumpTo =  jumpTo;
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

    public GameViewType getJumpTo() { return jumpTo; }

    public Parent getSubjectPanel() {
        return subjectPanel;
    }

    public Parent getMessageContent() { return messageContent; }

    public void setJumpTo(GameViewType jumpTo) { this.jumpTo = jumpTo; }

    public void setSubjectPanel(Parent subjectPanel) { this.subjectPanel = subjectPanel; }

    public void setMessageContent(Parent messageContent) { this.messageContent = messageContent; }
}
