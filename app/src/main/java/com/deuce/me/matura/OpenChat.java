package com.deuce.me.matura;

/**
 * Created by Flo on 12.03.2018.
 */

public class OpenChat {

    private String subject, receiverName, senderName, latestMessage;
    private int receiverID, senderID;


    public OpenChat(String subject, String receiverName, String senderName, String latestMessage, int receiverID, int senderID) {
        this.subject = subject;
        this.receiverName = receiverName;
        this.senderName = senderName;
        this.receiverID = receiverID;
        this.senderID = senderID;
        this.latestMessage = latestMessage;
    }

    public String getLatestMessage() {
        return latestMessage;
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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }
}
