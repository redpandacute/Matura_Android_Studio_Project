package com.deuce.me.matura.fragments.chatoverview;

import android.view.View;

import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.fragments.openchat.OpenchatFragment;
import com.deuce.me.matura.models.ChatModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ingli on 20.08.2018.
 */

class OnOpenChatListenerV2 implements View.OnClickListener {

    private OpenChatModelV2 mModel;
    private MainActivity mActivity;
    private ChatoverviewFragment mFragment;

    public OnOpenChatListenerV2(ChatoverviewFragment mFragment, OpenChatModelV2 mModel) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
        this.mModel = mModel;
    }

    @Override
    public void onClick(View view) {
        DatabaseReference senderRef = mModel.getSenderRef();
        DatabaseReference receiverRef = mModel.getReceiverRef();
        DatabaseReference chatRef = mModel.getChatRef();
        mActivity.setOpenChat(new ChatModel(mActivity.getMainprofileModel(), mModel.getUserModel(), senderRef, receiverRef, chatRef));
        mActivity.setFragment(new OpenchatFragment());
        mFragment.onPause();
    }
}
