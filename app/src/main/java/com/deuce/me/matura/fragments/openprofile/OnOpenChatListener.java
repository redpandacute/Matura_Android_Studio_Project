package com.deuce.me.matura.fragments.openprofile;

import android.view.View;

import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.fragments.openchat.OpenchatFragment;
import com.deuce.me.matura.models.ChatModel;
import com.deuce.me.matura.models.UserModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

/**
 * Created by ingli on 12.08.2018.
 */

class OnOpenChatListener implements View.OnClickListener {

    private UserModel mMainprofileModel, mOpenuserModel;
    private OpenprofileFragment mFragment;
    private MainActivity mActivity;

    public OnOpenChatListener(OpenprofileFragment mFragment) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
        this.mOpenuserModel = mActivity.getOpenprofileModel();
        this.mMainprofileModel = mActivity.getMainprofileModel();
    }

    @Override
    public void onClick(View view) {

        //DatabaseReference databaseSenderReference = FirebaseDatabase.getInstance().getReference(String.format("Users/%d/%d>>%d", mMainprofileModel.getId(), mMainprofileModel.getId(), mMainprofileModel.getId()));
        //DatabaseReference databaseReceiverReference = FirebaseDatabase.getInstance().getReference(String.format("Users/%d/%d>>%d", mOpenuserModel.getId(), mOpenuserModel.getId(), mOpenuserModel.getId()));

        DatabaseReference databaseSenderReference = mActivity.getMainProfileFirebaseRef().child(String.format("chats/%d->%d", mMainprofileModel.getId(), mOpenuserModel.getId()));
        DatabaseReference databaseReceiverReference = FirebaseDatabase.getInstance().getReference(String.format("Users/%d/chats/%d->%d", mOpenuserModel.getId(), mMainprofileModel.getId(), mOpenuserModel.getId()));

        databaseSenderReference.child("receiverID").setValue(mOpenuserModel.getId());
        try {
            databaseSenderReference.child("receiverJSON").setValue(mActivity.getOpenprofileModel().getCleanJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        databaseSenderReference.child("senderRef").setValue(String.format("Users/%d/chats/%d->%d", mMainprofileModel.getId(), mMainprofileModel.getId(), mOpenuserModel.getId()));
        databaseSenderReference.child("receiverRef").setValue(String.format("Users/%d/chats/%d->%d", mOpenuserModel.getId(), mMainprofileModel.getId(), mOpenuserModel.getId()));
        databaseSenderReference.child("chatRef").setValue(String.format("Chats/%d->%d", mMainprofileModel.getId(), mOpenuserModel.getId()));

        databaseReceiverReference.child("receiverID").setValue(mMainprofileModel.getId());
        try {
            databaseReceiverReference.child("receiverJSON").setValue(mActivity.getMainprofileModel().getCleanJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        databaseReceiverReference.child("senderRef").setValue(String.format("Users/%d/chats/%d->%d", mOpenuserModel.getId(), mMainprofileModel.getId(), mOpenuserModel.getId()));
        databaseReceiverReference.child("receiverRef").setValue(String.format("Users/%d/chats/%d->%d", mMainprofileModel.getId(), mMainprofileModel.getId(), mOpenuserModel.getId()));
        databaseReceiverReference.child("chatRef").setValue(String.format("Chats/%d->%d", mMainprofileModel.getId(), mOpenuserModel.getId()));

        DatabaseReference openChatReference = FirebaseDatabase.getInstance().getReference(String.format("Chats/%d->%d", mMainprofileModel.getId(), mOpenuserModel.getId()));

        mActivity.setOpenChat(new ChatModel(mMainprofileModel, mOpenuserModel, databaseSenderReference, databaseReceiverReference, openChatReference));
        mActivity.setFragment(new OpenchatFragment());
    }
}
