package com.deuce.me.matura;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by Flo on 12.03.2018.
 */

public class BottomsheetFirebaseAdapter extends FirebaseRecyclerAdapter<OpenChat, OpenChatViewHolder> {

    public BottomsheetFirebaseAdapter(Class<OpenChat> modelClass,
                                      int modelLayout,
                                      Class<OpenChatViewHolder> viewHolderClass,
                                      Query ref) {

        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(OpenChatViewHolder viewHolder, OpenChat model, int position) {
        viewHolder.setChat(model.getReceiverName(), model.getLatestMessage());
    }
}

class OpenChatViewHolder extends RecyclerView.ViewHolder {

    View view;

    public OpenChatViewHolder(View itemView) {
        super(itemView);
        view = itemView;
    }

    public void setChat(String receiverName, String latestMessage) {
        TextView cName_tv = view.findViewById(R.id.openchat_name_textview);
        cName_tv.setText(receiverName);
        TextView cLatest_tv = view.findViewById(R.id.openchat_latest_textview);
        cLatest_tv.setText(latestMessage);
    }
}