package com.deuce.me.matura.models;

/**
 * Created by Flo on 09.03.2018.
 */

public class ChatMessageModel {
    private String messageText;
    private String messageUser;
    private String messageTime;


    public ChatMessageModel(String messageUser, String messageText, String messageTime) {
        this.messageUser = messageUser;
        this.messageText = messageText;
        this.messageTime = messageTime;

    }

    public ChatMessageModel() {}

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
