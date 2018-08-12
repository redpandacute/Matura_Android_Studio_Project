package com.deuce.me.matura.models;

/**
 * Created by Flo on 12.03.2018.
 */

public class OpenChatModel {

    private String subject, receiverName, latestMessage, chatPath, senderRef, receiverRef, profilePicturePath;
    private int receiverID;


    public OpenChatModel(String subject, String receiverName, String latestMessage, String chatPath, String receiverRef, String senderRef, int receiverID) {
        this.subject = subject;
        this.receiverName = receiverName;
        this.receiverID = receiverID;
        this.latestMessage = latestMessage;
        this.chatPath = chatPath;
        this.senderRef = senderRef;
        this.receiverRef = receiverRef;
        this.profilePicturePath = null;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public String getSenderRef() {
        return senderRef;
    }

    public OpenChatModel() {}

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
