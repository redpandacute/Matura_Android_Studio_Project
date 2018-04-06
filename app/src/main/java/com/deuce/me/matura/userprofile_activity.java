package com.deuce.me.matura;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class userprofile_activity extends AppCompatActivity {

    private Bundle extras;
    private userInfo profileInfo, clientInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extras = getIntent().getExtras();

        try {

            profileInfo = new JSONtoInfo().createNewItem(new JSONObject(extras.getString("profileInfo")));
            clientInfo = new JSONtoInfo().createNewItem(new JSONObject(extras.getString("clientInfo")));
//Infos
// -------------------------------------------------------------------------------------------------

            TextView name_tv = findViewById(R.id.userprofileact_name_textview);
            name_tv.setText(profileInfo.getFirstname() + " " + profileInfo.getName());

            TextView school_tv = findViewById(R.id.userprofileact_school_textview);
            school_tv.setText(profileInfo.getSchool() + ", " + profileInfo.getYearofbirth());

            TextView desc_tv = findViewById(R.id.userprofileact_description_textview);
            desc_tv.setText(profileInfo.getDescription());

//SubjectMedals
// -------------------------------------------------------------------------------------------------
            ImageView math_medal = findViewById(R.id.userprofileact_math_imageview);
            if (!profileInfo.isMaths()) { math_medal.setVisibility(View.GONE); }
            math_medal.setOnClickListener(new onRequestListener());

            ImageView spanish_medal = findViewById(R.id.userprofileact_spanish_imageview);
            if (!profileInfo.isSpanish()) { spanish_medal.setVisibility(View.GONE); }
            spanish_medal.setOnClickListener(new onRequestListener());

            ImageView physics_medal = findViewById(R.id.userprofileact_physics_imageview);
            if (!profileInfo.isPhysics()) { physics_medal.setVisibility(View.GONE); }
            physics_medal.setOnClickListener(new onRequestListener());

            ImageView german_medal = findViewById(R.id.userprofileact_german_imageview);
            if (!profileInfo.isGerman()) { german_medal.setVisibility(View.GONE); }
            german_medal.setOnClickListener(new onRequestListener());

            ImageView biology_medal = findViewById(R.id.userprofileact_biology_imageview);
            if (!profileInfo.isBiology()) { biology_medal.setVisibility(View.GONE); }
            biology_medal.setOnClickListener(new onRequestListener());

            ImageView chemistry_medal = findViewById(R.id.userprofileact_chemistry_imageview);
            if (!profileInfo.isChemistry()) { chemistry_medal.setVisibility(View.GONE); }
            chemistry_medal.setOnClickListener(new onRequestListener());

            ImageView music_medal = findViewById(R.id.userprofileact_music_imageview);
            if (!profileInfo.isMusic()) { music_medal.setVisibility(View.GONE); }
            music_medal.setOnClickListener(new onRequestListener());

            ImageView english_medal = findViewById(R.id.userprofileact_english_imageview);
            if (!profileInfo.isEnglish()) { english_medal.setVisibility(View.GONE); }
            english_medal.setOnClickListener(new onRequestListener());

            ImageView french_medal = findViewById(R.id.userprofileact_french_imageview);
            if (!profileInfo.isFrench()) { french_medal.setVisibility(View.GONE); }
            french_medal.setOnClickListener(new onRequestListener());

//HomeButton
// -------------------------------------------------------------------------------------------------
            FloatingActionButton home_button = (FloatingActionButton) findViewById(R.id.userprofileact_homebutton_floatingactionbutton);

            home_button.setOnClickListener(new onHomeListener());


        } catch (JSONException e) { e.printStackTrace(); }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(userprofile_activity.this, mainpage_activity.class);
                intent.putExtra("clientInfo", getIntent().getExtras().getString("clientInfo"));
                intent.putExtra("results", getIntent().getExtras().getString("results"));
                startActivity(intent);
                System.out.println("::BACK BUTTON::");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class onHomeListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            System.out.println("Making request");

            home_request home_request = new home_request(clientInfo.getId(), clientInfo.getPassword(), new onResponseListener());
            RequestQueue request_queue = Volley.newRequestQueue(userprofile_activity.this); //Request Queue
            request_queue.add(home_request);

        }
    }
    private class onResponseListener implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {

            try {

                System.out.println("my_response" + response);
                JSONObject json_response = new JSONObject(response);
                boolean success = json_response.getBoolean("success");

                System.out.println(success);

                if (success) {
                    Intent home_intent = new Intent(userprofile_activity.this, mainpage_activity.class);
                    home_intent.putExtra("clientInfo", extras.getString("clientInfo"));

                    //Starting activity
                    startActivity(home_intent);

                } else {
                    //Incorrect username of password
                    System.out.println("incorrect PW or username");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class onRequestListener implements View.OnClickListener {

        private userInfo senderInfo;
        private userInfo receiverInfo;


        @Override
        public void onClick(View view) {
            try {
                senderInfo = new JSONtoInfo().createNewItem(new JSONObject(extras.getString("clientInfo")));
                receiverInfo = new JSONtoInfo().createNewItem(new JSONObject(extras.getString("profileInfo")));


                Intent chatIntent = new Intent(userprofile_activity.this, ChatActivity.class);

                String subject = "nil";

                if (view == findViewById(R.id.userprofileact_math_imageview)) { subject = "math"; }
                else if (view == findViewById(R.id.userprofileact_french_imageview)) { subject = "french"; }
                else if (view == findViewById(R.id.userprofileact_spanish_imageview)) { subject = "spanish"; }
                else if (view == findViewById(R.id.userprofileact_english_imageview)) { subject = "english"; }
                else if (view == findViewById(R.id.userprofileact_german_imageview)) { subject = "german"; }
                else if (view == findViewById(R.id.userprofileact_biology_imageview)) { subject = "biology"; }
                else if (view == findViewById(R.id.userprofileact_chemistry_imageview)) { subject = "chemistry"; }
                else if (view == findViewById(R.id.userprofileact_physics_imageview)) { subject = "physics"; }
                else if (view == findViewById(R.id.userprofileact_music_imageview)) { subject = "music"; }

                DatabaseReference databaseSenderReference = FirebaseDatabase.getInstance().getReference(String.format("Users/%d/%d>>%d", clientInfo.getId(), clientInfo.getId(), profileInfo.getId()));
                DatabaseReference databaseReceiverReference = FirebaseDatabase.getInstance().getReference(String.format("Users/%d/%d>>%d",profileInfo.getId(), clientInfo.getId(), profileInfo.getId()));

                databaseSenderReference.child("receiverName").setValue(String.format("%s %s",profileInfo.getFirstname(), profileInfo.getName()));
                databaseSenderReference.child("receiverID").setValue(profileInfo.getId());
                databaseSenderReference.child("senderRef").setValue(String.format("Users/%d/%d>>%d", clientInfo.getId(), clientInfo.getId(), profileInfo.getId()));
                databaseSenderReference.child("receiverRef").setValue(String.format("Users/%d/%d>>%d", profileInfo.getId(), clientInfo.getId(), profileInfo.getId()));
                databaseReceiverReference.child("receiverName").setValue(String.format("%s %s",clientInfo.getFirstname(), clientInfo.getName()));
                databaseReceiverReference.child("receiverID").setValue(clientInfo.getId());
                databaseReceiverReference.child("senderRef").setValue(String.format("Users/%d/%d>>%d", profileInfo.getId(), clientInfo.getId(), profileInfo.getId()));
                databaseReceiverReference.child("receiverRef").setValue(String.format("Users/%d/%d>>%d", clientInfo.getId(), clientInfo.getId(), profileInfo.getId()));
                databaseSenderReference.child("chatPath").setValue(String.format("Chats/%d>>%d", clientInfo.getId(), profileInfo.getId()));
                databaseReceiverReference.child("chatPath").setValue(String.format("Chats/%d>>%d", clientInfo.getId(), profileInfo.getId()));

                databaseSenderReference.child("subject").setValue(subject);
                databaseReceiverReference.child("subject").setValue(subject);

                String chatPath = String.format("Chats/%d>>%d", clientInfo.getId(), profileInfo.getId());

                chatIntent.putExtra("chatPath", chatPath);
                chatIntent.putExtra("clientDatabasePath", String.format("Users/%d/%d>>%d", clientInfo.getId(), clientInfo.getId(), profileInfo.getId()));
                chatIntent.putExtra("receiverDatabasePath", String.format("Users/%d/%d>>%d", profileInfo.getId(), clientInfo.getId(), profileInfo.getId()));
                chatIntent.putExtra("clientInfo", extras.getString("clientInfo"));
                chatIntent.putExtra("profileInfo", extras.getString("profileInfo"));
                chatIntent.putExtra("clientName", String.format("%s %s",clientInfo.getFirstname(), clientInfo.getName()));

                chatIntent.putExtra("parentActivity", "userprofile");
                startActivity(chatIntent);
            } catch (JSONException e) { e.printStackTrace(); }
        }
    }
}
