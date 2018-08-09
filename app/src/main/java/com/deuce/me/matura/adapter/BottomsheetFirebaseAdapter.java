package com.deuce.me.matura.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.deuce.me.matura.activities.ChatActivity;
import com.deuce.me.matura.activities.MainpageActivity;
import com.deuce.me.matura.models.OpenChatModel;
import com.deuce.me.matura.models.ProfilePictureModel;
import com.deuce.me.matura.util.tempFileGenerator;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by Flo on 12.03.2018.
 */

public class BottomsheetFirebaseAdapter extends FirebaseRecyclerAdapter<OpenChatModel, MainpageActivity.OpenChatViewHolder> {

    private Context mContext;
    private String clientName, clientInfo;

    public BottomsheetFirebaseAdapter(Class<OpenChatModel> modelClass,
                                      int modelLayout,
                                      Class<MainpageActivity.OpenChatViewHolder> viewHolderClass,
                                      Query ref, Context context, String clientName, String clientInfo
    ) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mContext = context;
        this.clientName = clientName;
        this.clientInfo = clientInfo;
    }

    @Override
    public OpenChatModel getItem(int position) {
        return super.getItem(position);
    }

    @Override
    protected void populateViewHolder(MainpageActivity.OpenChatViewHolder viewHolder, final OpenChatModel model, int position) {
        viewHolder.setChat(model.getReceiverName(), model.getLatestMessage());
        viewHolder.setReceiverID(model.getReceiverID());
        viewHolder.setProfilePicture(new ProfilePictureModel(mContext, new tempFileGenerator().getTempFilePath(mContext, null)));
        System.out.println("RECEIVER: " + model.getReceiverName());
        viewHolder.setReceiverID(model.getReceiverID());
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