package com.deuce.me.matura;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by Flo on 09.03.2018.
 */

public class FirebaseCustomAdapter extends FirebaseRecyclerAdapter<ChatMessage, ChatActivity.MessageViewHolder> {

    private static final int LAYOUT_ONE = 1;
    private static final int LAYOUT_TWO = 2;
    private String senderUsername;


    public FirebaseCustomAdapter(Class<ChatMessage> chatMessageClass,
                                 int modelLayout,
                                 Class<ChatActivity.MessageViewHolder> viewHolderClass,
                                 Query reference, String senderUsername
    ) {
        super(chatMessageClass, modelLayout, viewHolderClass, reference);
        this.senderUsername = senderUsername;
    }

    @Override
    public ChatActivity.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        if (viewType == LAYOUT_ONE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_left, parent, false);
            return new  ChatActivity.MessageViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_right, parent, false);
            return new  ChatActivity.MessageViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

    ChatMessage model = getItem(position);
    if(model == null) {return 0;}

        if(!model.getMessageUser().equals(senderUsername)) {
        return LAYOUT_ONE;
    }
        return LAYOUT_TWO;
    }

    @Override
    protected void populateViewHolder(ChatActivity.MessageViewHolder viewHolder, ChatMessage model, int position) {
        viewHolder.setMessage(model.getMessageUser(), model.getMessageText(), model.getMessageTime());
    }


}
