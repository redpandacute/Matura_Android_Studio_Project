package com.deuce.me.matura.fragments.chatoverview;

import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.models.ProfilePictureModel;
import com.deuce.me.matura.models.UserModel;
import com.deuce.me.matura.util.JSONtoInfo;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Flo on 12.03.2018.
 */

public class OpenChatModel {

    private ProfilePictureModel profilePicture;
    private String latestMessage;
    String receiverRef, senderRef, chatRef, receiverJSON;

    public OpenChatModel(String receiverRef, String senderRef, String chatRef, String receiverJSON, String latestMessage) {
        this.receiverRef = receiverRef;
        this.senderRef = senderRef;
        this.chatRef = chatRef;
        this.receiverJSON = receiverJSON;
        this.latestMessage = latestMessage;
    }

    public OpenChatModel(){}

    public String getReceiverRef() {
        return receiverRef;
    }

    public void setReceiverRef(String receiverRef) {
        this.receiverRef = receiverRef;
    }

    public String getSenderRef() {
        return senderRef;
    }

    public void setSenderRef(String senderRef) {
        this.senderRef = senderRef;
    }

    public String getChatRef() {
        return chatRef;
    }

    public void setChatRef(String chatRef) {
        this.chatRef = chatRef;
    }

    public String getReceiverJSON() {
        return receiverJSON;
    }

    public void setReceiverJSON(String receiverJSON) {
        this.receiverJSON = receiverJSON;
    }

    public void setLatestMessage(String latestMessage){
        this.latestMessage = latestMessage;
    }

    public UserModel getReceiverModel(MainActivity mActivity) {
        try {
            return new JSONtoInfo(mActivity).createNewItem(new JSONObject(this.receiverJSON));
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getLatestMessage(){
        return this.latestMessage;
    }

    public void setProfilePicture(ProfilePictureModel profilePicture) {
        this.profilePicture = profilePicture;
    }

    public ProfilePictureModel getProfilePicture(){
        return this.profilePicture;
    }
}
