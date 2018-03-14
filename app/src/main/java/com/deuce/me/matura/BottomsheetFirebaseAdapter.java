package com.deuce.me.matura;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by Flo on 12.03.2018.
 */

public class BottomsheetFirebaseAdapter extends FirebaseRecyclerAdapter<OpenChat, OpenChatViewHolder> {

    private Context mContext;
    private String clientName;

    public BottomsheetFirebaseAdapter(Class<OpenChat> modelClass,
                                      int modelLayout,
                                      Class<OpenChatViewHolder> viewHolderClass,
                                      Query ref,Context context, String clientName) {

        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mContext = context;
        this.clientName = clientName;
    }

    @Override
    protected void populateViewHolder(OpenChatViewHolder viewHolder, final OpenChat model, int position) {
        viewHolder.setChat(model.getReceiverName(), model.getLatestMessage());
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat_intent = new Intent(mContext, ChatActivity.class);

                //TODO: put extras
                chat_intent.putExtra("subject", model.getSubject());
                chat_intent.putExtra("clientName", clientName);
                chat_intent.putExtra("chatPath", model.getChatPath());
                chat_intent.putExtra("clientDatabasePath", model.getSenderRef());
                chat_intent.putExtra("receiverDatabasePath", model.getReceiverRef());
                mContext.startActivity(chat_intent);
            }
        });
    }

}


class OpenChatViewHolder extends RecyclerView.ViewHolder {

    //https://android.jlelse.eu/click-listener-for-recyclerview-adapter-2d17a6f6f6c9

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