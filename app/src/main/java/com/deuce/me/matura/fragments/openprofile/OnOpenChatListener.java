package com.deuce.me.matura.fragments.openprofile;

import android.view.View;

import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.fragments.openchat.OpenchatFragment;
import com.deuce.me.matura.models.ChatModel;
import com.deuce.me.matura.models.UserModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        DatabaseReference databaseSenderReference = FirebaseDatabase.getInstance().getReference(String.format("Users/%d/%d>>%d", mMainprofileModel.getId(), mMainprofileModel.getId(), mMainprofileModel.getId()));
        DatabaseReference databaseReceiverReference = FirebaseDatabase.getInstance().getReference(String.format("Users/%d/%d>>%d", mOpenuserModel.getId(), mOpenuserModel.getId(), mOpenuserModel.getId()));

        databaseSenderReference.child("receiverName").setValue(String.format("%s %s",mOpenuserModel.getFirstname(), mOpenuserModel.getName()));
        databaseSenderReference.child("receiverID").setValue(mOpenuserModel.getId());
        databaseSenderReference.child("senderRef").setValue(String.format("Users/%d/%d>>%d", mMainprofileModel.getId(), mMainprofileModel.getId(), mOpenuserModel.getId()));
        databaseSenderReference.child("receiverRef").setValue(String.format("Users/%d/%d>>%d", mOpenuserModel.getId(), mMainprofileModel.getId(), mOpenuserModel.getId()));
        databaseReceiverReference.child("senderName").setValue(String.format("%s %s",mMainprofileModel.getFirstname(), mMainprofileModel.getName()));
        databaseReceiverReference.child("receiverID").setValue(mMainprofileModel.getId());
        databaseReceiverReference.child("senderRef").setValue(String.format("Users/%d/%d>>%d", mOpenuserModel.getId(), mMainprofileModel.getId(), mOpenuserModel.getId()));
        databaseReceiverReference.child("receiverRef").setValue(String.format("Users/%d/%d>>%d", mMainprofileModel.getId(), mMainprofileModel.getId(), mOpenuserModel.getId()));
        databaseSenderReference.child("chatPath").setValue(String.format("Chats/%d>>%d", mMainprofileModel.getId(), mOpenuserModel.getId()));
        databaseReceiverReference.child("chatPath").setValue(String.format("Chats/%d>>%d", mMainprofileModel.getId(), mOpenuserModel.getId()));

        DatabaseReference openChatReference = FirebaseDatabase.getInstance().getReference(String.format("Chats/%d>>%d", mMainprofileModel.getId(), mOpenuserModel.getId()));

        mActivity.setOpenChat(new ChatModel(mMainprofileModel, mOpenuserModel, databaseSenderReference, databaseReceiverReference, openChatReference));
        mActivity.setOpenchatFragment(new OpenchatFragment());
        mActivity.setFragment(mActivity.getOpenchatFragment());

    }
}
