package de.uni.mannheim.capitalismx.ui.utils;

public class MessageObject {

    private String sender;
    private String date;
    private String subject;
    private String content;
    private boolean isInternal;

    public MessageObject(String sender, String date, String subject, String content, boolean isInternal){
        this.content = content;
        this.sender = sender;
        this.subject = subject;
        this.date = date;
        this.isInternal = isInternal;
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

}
