package com.deuce.me.matura;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class ChatActivity extends AppCompatActivity {

    private EditText editMessage;
    private Bundle extras;
    private DatabaseReference databaseReference, databaseSenderReference, databaseReceiverReference;
    private Button sendButton;
    private RecyclerView ChatRecView;
    private String subject, senderName, receiverName, chatDBPath, senderDBPath, receiverDBPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        editMessage = (EditText) findViewById(R.id.message_edittext);

        //Button
        sendButton = (Button) findViewById(R.id.sendbutton_button);
        sendButton.setOnClickListener(new onSendListener());

        //Extras *EXPERIMENTAL*
            extras = getIntent().getExtras();
            subject = extras.getString("subject");
            senderName = extras.getString("clientName");
            chatDBPath = extras.getString("chatPath");
            senderDBPath = extras.getString("clientDatabasePath");
            receiverDBPath = extras.getString("receiverDatabasePath");

            //Firebasereference
            databaseReference = FirebaseDatabase.getInstance().getReference(chatDBPath);
            databaseSenderReference = FirebaseDatabase.getInstance().getReference(senderDBPath);
            databaseReceiverReference = FirebaseDatabase.getInstance().getReference(receiverDBPath);

            //Pushing the Chats

            //RecView
            ChatRecView = (RecyclerView) findViewById(R.id.message_recyclerview);
            ChatRecView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setStackFromEnd(true);
            ChatRecView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseCustomAdapter FBCA = new FirebaseCustomAdapter(
                ChatMessage.class,
                R.layout.message_right,
                MessageViewHolder.class,
                databaseReference.child("Messages"),
                senderName
        );
        ChatRecView.setAdapter(FBCA);
    }

    //onSendListener
    private class onSendListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            final String messageText = editMessage.getText().toString().trim();
            final String messageUser = senderName;
            final String messageTime = new SimpleDateFormat("MMM dd - hh:mm:dd").format(System.currentTimeMillis()).toString();
            if(!messageText.isEmpty()) {
                final DatabaseReference newPost = databaseReference.child("Messages").push();
                newPost.child("messageUser").setValue(messageUser);
                newPost.child("messageText").setValue(messageText);
                newPost.child("messageTime").setValue(messageTime);

                final DatabaseReference syncSender = databaseSenderReference;
                syncSender.child("latestMessage").setValue(messageText);
                final DatabaseReference syncRec = databaseReceiverReference;
                syncRec.child("latestMessage").setValue(messageText);

                editMessage.setText("");
                ChatRecView.smoothScrollToPosition(ChatRecView.getChildCount() - 1); //TODO: RESET SCROLL
            }
        }
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        View view;

        public MessageViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setMessage(String messageUser, String messageText, String messageTime) {
            TextView mText_tv = (TextView) view.findViewById(R.id.message_textview);
            mText_tv.setText(messageText);
            TextView mUser_tv = (TextView) view.findViewById(R.id.messagesender_textview);
            mUser_tv.setText(messageUser);
            TextView mTime_tv = (TextView) view.findViewById(R.id.sendingtime_textview);
            mTime_tv.setText(messageTime);
        }
    }

}
