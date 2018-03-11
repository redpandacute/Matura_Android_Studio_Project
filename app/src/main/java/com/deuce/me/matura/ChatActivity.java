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
    private DatabaseReference databaseReference;
    private Button sendButton;
    private RecyclerView ChatRecView;

    private int senderID, receiverID;
    private String senderUsername, receiverUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        editMessage = (EditText) findViewById(R.id.message_edittext);

        //Button
        sendButton = (Button) findViewById(R.id.sendbutton_button);
        sendButton.setOnClickListener(new onSendListener());

        //Extras *EXPERIMENTAL*
        try {
            Bundle extrasBundle = getIntent().getExtras();
            userInfo senderInfo = new JSONtoInfo().createNewItem(new JSONObject(extrasBundle.getString("senderInfo")));
            userInfo receiverInfo = new JSONtoInfo().createNewItem(new JSONObject(extrasBundle.getString("receiverInfo")));
            senderID = senderInfo.getId();
            receiverID = receiverInfo.getId();
            senderUsername = String.format("%s %s", senderInfo.getFirstname(), senderInfo.getName());
            receiverUsername = String.format("%s %s", receiverInfo.getFirstname(), receiverInfo.getName());

            //Firebasereference
            String referencePath = String.format("Chats/%d>>%d", senderID, receiverID);
            databaseReference = FirebaseDatabase.getInstance().getReference().child(referencePath);

            //RecView
            ChatRecView = (RecyclerView) findViewById(R.id.message_recyclerview);
            ChatRecView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setStackFromEnd(true);
            ChatRecView.setLayoutManager(linearLayoutManager);
        } catch (JSONException e) { e.printStackTrace(); }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseCustomAdapter FBCA = new FirebaseCustomAdapter(
                ChatMessage.class,
                R.layout.message_right,
                MessageViewHolder.class,
                databaseReference.child("Messages"),
                senderUsername
        );
        ChatRecView.setAdapter(FBCA);
    }

    //onSendListener
    private class onSendListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            final String messageText = editMessage.getText().toString().trim();
            final String messageUser = senderUsername;
            final String messageTime = new SimpleDateFormat("MMM dd - hh:mm:dd").format(System.currentTimeMillis()).toString();
            if(!messageText.isEmpty()) {
                final DatabaseReference newPost = databaseReference.child("Messages").push();
                newPost.child("messageUser").setValue(messageUser);
                newPost.child("messageText").setValue(messageText);
                newPost.child("messageTime").setValue(messageTime);

                editMessage.setText("");
                editMessage.clearFocus();
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
