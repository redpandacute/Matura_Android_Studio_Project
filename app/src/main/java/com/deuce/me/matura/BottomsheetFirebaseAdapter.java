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

public class BottomsheetFirebaseAdapter extends FirebaseRecyclerAdapter<OpenChat, mainpage_activity.OpenChatViewHolder> {

    private Context mContext;
    private String clientName, clientInfo;

    public BottomsheetFirebaseAdapter(Class<OpenChat> modelClass,
                                      int modelLayout,
                                      Class<mainpage_activity.OpenChatViewHolder> viewHolderClass,
                                      Query ref, Context context, String clientName, String clientInfo
    ) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mContext = context;
        this.clientName = clientName;
        this.clientInfo = clientInfo;
    }

    @Override
    protected void populateViewHolder(mainpage_activity.OpenChatViewHolder viewHolder, final OpenChat model, int position) {
        viewHolder.setChat(model.getReceiverName(), model.getLatestMessage());
        System.out.println("RECEIVER: " + model.getReceiverName());
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat_intent = new Intent(mContext, ChatActivity.class);

                //TODO: put extras
                chat_intent.putExtra("subject", model.getSubject());
                chat_intent.putExtra("clientName", clientName);
                chat_intent.putExtra("chatPath", model.getChatPath());
                System.out.println(model.getChatPath());
                chat_intent.putExtra("clientDatabasePath", model.getSenderRef());
                chat_intent.putExtra("receiverDatabasePath", model.getReceiverRef());
                chat_intent.putExtra("parentActivity", "mainpage");
                chat_intent.putExtra("clientInfo", clientInfo);
                chat_intent.putExtra("receiverName", model.getReceiverName());
                mContext.startActivity(chat_intent);
            }
        });
    }

}