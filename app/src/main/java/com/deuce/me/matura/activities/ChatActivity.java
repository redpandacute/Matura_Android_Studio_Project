package com.deuce.me.matura.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.deuce.me.matura.fragments.openchat.ChatMessageModel;
import com.deuce.me.matura.adapter.FirebaseCustomAdapter;
import com.deuce.me.matura.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

public class ChatActivity extends AppCompatActivity {

    private EditText editMessage;
    private Bundle extras;
    private DatabaseReference databaseReference, databaseSenderReference, databaseReceiverReference;
    private FloatingActionButton sendButton;
    private RecyclerView ChatRecView;
    private String subject, senderName, receiverName, chatDBPath, senderDBPath, receiverDBPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editMessage = findViewById(R.id.chatact_message_edittext);

        //Button
        sendButton = findViewById(R.id.chatact_sendbutton_floatingactionbutton);
        sendButton.setOnClickListener(new onSendListener());

        //Extras *EXPERIMENTAL*
        extras = getIntent().getExtras();
        subject = extras.getString("subject");
        senderName = extras.getString("clientName");
        chatDBPath = extras.getString("chatPath");
        senderDBPath = extras.getString("clientDatabasePath");
        receiverDBPath = extras.getString("receiverDatabasePath");

        //Title
        getSupportActionBar().setTitle(getString(R.string.chat_title) + " " + extras.getString("receiverName"));

        //Firebasereference
        databaseReference = FirebaseDatabase.getInstance().getReference(chatDBPath);
        databaseSenderReference = FirebaseDatabase.getInstance().getReference(senderDBPath);
        databaseReceiverReference = FirebaseDatabase.getInstance().getReference(receiverDBPath);

        //Pushing the Chats

        //RecView
        //ChatRecView = findViewById(R.id.chat_recyclerview);
        ChatRecView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        ChatRecView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseCustomAdapter FBCA = new FirebaseCustomAdapter(
                ChatMessageModel.class,
                R.layout.message_right,
                MessageViewHolder.class,
                databaseReference.child("Messages"),
                senderName
        );
        ChatRecView.setAdapter(FBCA);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (extras.getString("parentActivity").matches("mainpage")) {
                    Intent intent = new Intent(ChatActivity.this, MainpageActivity.class);
                    intent.putExtra("clientInfo", extras.getString("clientInfo"));
                    startActivity(intent);
                    System.out.println("::BACK BUTTON::");
                } else {
                    Intent intent = new Intent(ChatActivity.this, UserprofileActivity.class);
                    intent.putExtra("clientInfo", extras.getString("clientInfo"));
                    intent.putExtra("profileInfo", extras.getString("profileInfo"));
                    intent.putExtra("results", extras.getString("results"));
                    startActivity(intent);
                    System.out.println("::BACK BUTTON::");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //onSendListener
    private class onSendListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            final String messageText = editMessage.getText().toString().trim();
            final String messageUser = senderName;
            final String messageTime = new SimpleDateFormat("MMM dd - hh:mm:dd").format(System.currentTimeMillis()).toString();
            if (!messageText.isEmpty()) {
                final DatabaseReference newPost = databaseReference.child("Messages").push();
                newPost.child("messageUser").setValue(messageUser);
                newPost.child("messageText").setValue(messageText);
                newPost.child("messageTime").setValue(messageTime);

                final DatabaseReference syncSender = databaseSenderReference;
                syncSender.child("latestMessage").setValue(messageText);
                final DatabaseReference syncRec = databaseReceiverReference;
                syncRec.child("latestMessage").setValue(messageText);

                editMessage.setText("");
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
            TextView mText_tv = view.findViewById(R.id.message_textview);
            mText_tv.setText(messageText);
            //TextView mUser_tv = view.findViewById(R.id.messagesender_textview);
            //mUser_tv.setText(messageUser);
            TextView mTime_tv = view.findViewById(R.id.sendingtime_textview);
            mTime_tv.setText(messageTime);
        }
    }
}
