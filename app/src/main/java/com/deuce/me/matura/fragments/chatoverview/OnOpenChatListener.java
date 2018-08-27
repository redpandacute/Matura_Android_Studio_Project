package com.deuce.me.matura.fragments.chatoverview;

import android.view.View;

import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.fragments.openchat.OpenchatFragment;
import com.deuce.me.matura.fragments.openprofile.OpenprofileFragment;
import com.deuce.me.matura.models.ChatModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by ingli on 13.08.2018.
 */

class OnOpenChatListener implements View.OnClickListener {

    private OpenChatModel mModel;
    private ChatoverviewFragment mFragment;
    private MainActivity mActivity;

    public OnOpenChatListener(ChatoverviewFragment mFragment, OpenChatModel mModel) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
        this.mModel = mModel;
    }

    @Override
    public void onClick(View view) {
        DatabaseReference senderRef = FirebaseDatabase.getInstance().getReference(mModel.getSenderRef());
        DatabaseReference receiverRef = FirebaseDatabase.getInstance().getReference(mModel.getReceiverRef());
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference(mModel.getChatRef());
        mActivity.setOpenChat(new ChatModel(mActivity.getMainprofileModel(), mModel.getReceiverModel(mActivity), senderRef, receiverRef, chatRef));
        mActivity.changeFragmentwithBackstack(new OpenchatFragment(), "chat");
    }
}
