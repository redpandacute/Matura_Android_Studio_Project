package com.deuce.me.matura.adapter;

/**
 * Created by ingli on 13.08.2018.
 */

public class OldOpenChatModel {

    private String subject, receiverName, latestMessage, chatPath, senderRef, receiverRef;
    private int receiverID;


    public OldOpenChatModel(String subject, String receiverName, String latestMessage, String chatPath, String receiverRef, String senderRef, int receiverID) {
        this.subject = subject;
        this.receiverName = receiverName;
        this.receiverID = receiverID;
        this.latestMessage = latestMessage;
        this.chatPath = chatPath;
        this.senderRef = senderRef;
        this.receiverRef = receiverRef;
    }

    public String getSenderRef() {
        return senderRef;
    }

    public OldOpenChatModel() {}

    public void setSenderRef(String senderRef) {
        this.senderRef = senderRef;
    }

    public String getReceiverRef() {
        return receiverRef;
    }

    public void setReceiverRef(String receiverRef) {
        this.receiverRef = receiverRef;
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
