package com.deuce.me.matura.fragments.openchat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.deuce.me.matura.R;

/**
 * Created by ingli on 12.08.2018.
 */

class ChatMessageHolder extends RecyclerView.ViewHolder {

    View view;

    public ChatMessageHolder(View itemView) {
        super(itemView);
        view = itemView;
    }

    public void setMessage(String messageUser, String messageText, String messageTime) {
        TextView mText_tv = view.findViewById(R.id.message_textview);
        mText_tv.setText(messageText);
        TextView mUser_tv = view.findViewById(R.id.messagesender_textview);
        mUser_tv.setText(messageUser);
        TextView mTime_tv = view.findViewById(R.id.sendingtime_textview);
        mTime_tv.setText(messageTime);
    }
}
