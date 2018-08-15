package com.deuce.me.matura.fragments.openchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deuce.me.matura.R;
import com.deuce.me.matura.models.UserModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by ingli on 12.08.2018.
 */

class FirebaseChatAdapter extends FirebaseRecyclerAdapter<ChatMessageModel, ChatMessageHolder> {

    private static final int LAYOUT_ONE = 1;
    private static final int LAYOUT_TWO = 2;
    private UserModel mMainuserModel;


    public FirebaseChatAdapter(Class<ChatMessageModel> chatMessageClass,
                                 int modelLayout,
                                 Class<ChatMessageHolder> viewHolderClass,
                                 Query reference, UserModel mMainuserModel
    ) {
        super(chatMessageClass, modelLayout, viewHolderClass, reference);
        this.mMainuserModel = mMainuserModel;
    }

    @Override
    public ChatMessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        if (viewType == LAYOUT_ONE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_left, parent, false);
            return new ChatMessageHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_right, parent, false);
            return new ChatMessageHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        ChatMessageModel model = getItem(position);
        if(model == null) {return 0;}

        if(model.getMessageUserId() == mMainuserModel.getId()) {
            return LAYOUT_ONE;
        }
        return LAYOUT_TWO;
    }

    @Override
    protected void populateViewHolder(ChatMessageHolder viewHolder, ChatMessageModel model, int position) {
        viewHolder.setMessage(model.getMessageUser(), model.getMessageText(), model.getMessageTime());
    }

}
