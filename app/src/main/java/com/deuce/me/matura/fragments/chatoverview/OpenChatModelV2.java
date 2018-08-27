package com.deuce.me.matura.fragments.chatoverview;

import android.content.Context;

import com.deuce.me.matura.models.UserModel;
import com.deuce.me.matura.util.JSONtoInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ingli on 19.08.2018.
 */

public class OpenChatModelV2 {

    private UserModel mUserModel;
    private String mLatestMessage, mProfilePicturePath;
    private DatabaseReference mReceiverReference, mChatRef, mSenderReference;

    public OpenChatModelV2(Context mContext, DataSnapshot mSnapshot) throws JSONException {
        this.mUserModel = new JSONtoInfo(mContext).createNewItem(new JSONObject(mSnapshot.child("receiverJSON").getValue().toString()));
        this.mLatestMessage = mSnapshot.child("latestMessage").getValue().toString();
        this.mReceiverReference = FirebaseDatabase.getInstance().getReference(mSnapshot.child("receiverRef").getValue().toString());
        this.mChatRef = FirebaseDatabase.getInstance().getReference(mSnapshot.child("chatRef").getValue().toString());
        this.mSenderReference = FirebaseDatabase.getInstance().getReference(mSnapshot.child("senderRef").getValue().toString());
    }

    public UserModel getUserModel() {
        return mUserModel;
    }

    public void setUserModel(UserModel mUserModel) {
        this.mUserModel = mUserModel;
    }

    public String getLatestMessage() {
        return mLatestMessage;
    }

    public void setLatestMessage(String mLatestMessage) {
        this.mLatestMessage = mLatestMessage;
    }

    public String getProfilePicturePath() {
        return mProfilePicturePath;
    }

    public void setProfilePicturePath(String mProfilePicturePath) {
        this.mProfilePicturePath = mProfilePicturePath;
    }

    public DatabaseReference getReceiverRef() {
        return mReceiverReference;
    }

    public void setReceiverRef(DatabaseReference mReceiverReference) {
        this.mReceiverReference = mReceiverReference;
    }

    public DatabaseReference getChatRef() {
        return this.mChatRef;
    }

    public void setChatRef(DatabaseReference mChatRef) {
        this.mChatRef = mChatRef;
    }

    public DatabaseReference getSenderRef() {
        return mSenderReference;
    }

    public void setSenderReference(DatabaseReference mSenderReference) {
        this.mSenderReference = mSenderReference;
    }
}
