package com.deuce.me.matura;

/**
 * Created by Flo on 12.03.2018.
 */

public class OpenChat {

    private String subject, receiverName, latestMessage, chatPath;
    private int receiverID;


    public OpenChat(String subject, String receiverName, String latestMessage, String chatPath, int receiverID) {
        this.subject = subject;
        this.receiverName = receiverName;
        this.receiverID = receiverID;
        this.latestMessage = latestMessage;
        this.chatPath = chatPath;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public String getChatPath() {
        return chatPath;
    }

    public void setChatPath(String chatPath) {
        this.chatPath = chatPath;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

}
