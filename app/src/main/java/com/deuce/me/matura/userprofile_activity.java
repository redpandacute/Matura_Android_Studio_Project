package com.deuce.me.matura;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class userprofile_activity extends AppCompatActivity {

    private Bundle extras;
    private String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile_activity);

         extras = getIntent().getExtras();

        try {

            result_item info = new result_iteminfo().createNewItem(new JSONObject(extras.getString("profileInfo")));

//Infos
// -------------------------------------------------------------------------------------------------

            TextView name_tv = findViewById(R.id.userprofileact_name_textview);
            name_tv.setText(info.getFirstname() + " " + info.getName());

            TextView school_tv = findViewById(R.id.userprofileact_school_textview);
            school_tv.setText("" + info.getYearofbirth());

            TextView desc_tv = findViewById(R.id.userprofileact_description_textview);
            desc_tv.setText(info.getDescription());

//SubjectMedals
// -------------------------------------------------------------------------------------------------
            ImageView math_medal = findViewById(R.id.userprofileact_math_imageview);
            if (!info.isMaths()) { math_medal.setVisibility(View.GONE); }
            math_medal.setOnClickListener(new onRequestListener());

            ImageView spanish_medal = findViewById(R.id.userprofileact_spanish_imageview);
            if (!info.isSpanish()) { spanish_medal.setVisibility(View.GONE); }
            spanish_medal.setOnClickListener(new onRequestListener());

            ImageView physics_medal = findViewById(R.id.userprofileact_physics_imageview);
            if (!info.isPhysics()) { physics_medal.setVisibility(View.GONE); }
            physics_medal.setOnClickListener(new onRequestListener());

            ImageView german_medal = findViewById(R.id.userprofileact_german_imageview);
            if (!info.isGerman()) { german_medal.setVisibility(View.GONE); }
            german_medal.setOnClickListener(new onRequestListener());

            ImageView biology_medal = findViewById(R.id.userprofileact_biology_imageview);
            if (!info.isBiology()) { biology_medal.setVisibility(View.GONE); }
            biology_medal.setOnClickListener(new onRequestListener());

            ImageView chemistry_medal = findViewById(R.id.userprofileact_chemistry_imageview);
            if (!info.isChemistry()) { chemistry_medal.setVisibility(View.GONE); }
            chemistry_medal.setOnClickListener(new onRequestListener());

            ImageView music_medal = findViewById(R.id.userprofileact_music_imageview);
            if (!info.isMusic()) { music_medal.setVisibility(View.GONE); }
            music_medal.setOnClickListener(new onRequestListener());

            ImageView english_medal = findViewById(R.id.userprofileact_english_imageview);
            if (!info.isEnglish()) { english_medal.setVisibility(View.GONE); }
            english_medal.setOnClickListener(new onRequestListener());

            ImageView french_medal = findViewById(R.id.userprofileact_french_imageview);
            if (!info.isFrench()) { french_medal.setVisibility(View.GONE); }
            french_medal.setOnClickListener(new onRequestListener());

//HomeButton
// -------------------------------------------------------------------------------------------------
            FloatingActionButton home_button = (FloatingActionButton) findViewById(R.id.userprofileact_homebutton_floatingactionbutton);

            home_button.setOnClickListener(new onHomeListener());


        } catch (JSONException e) { e.printStackTrace(); }
    }

    class onHomeListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            System.out.println("Making request");
            home_request home_request = new home_request(Integer.parseInt(getIntent().getExtras().getString("user_id")), getIntent().getExtras().getString("user_password"), new onResponseListener());
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

                    //Could be implemented into the extra directly
                    //getting UserData from Response
                    String user_username = json_response.getString("user_username");
                    String user_password = json_response.getString("user_password");
                    String user_name = json_response.getString("user_name");
                    String user_firstname = json_response.getString("user_firstname");
                    String user_school = json_response.getString("user_school");
                    String user_email = json_response.getString("user_email");
                    String user_description = "";

                    boolean subj_maths = 0 != json_response.getInt("subj_maths");
                    boolean subj_german = 0 != json_response.getInt("subj_german");
                    boolean subj_french = 0 != json_response.getInt("subj_french");
                    boolean subj_spanish = 0 != json_response.getInt("subj_spanish");
                    boolean subj_physics = 0 != json_response.getInt("subj_physics");
                    boolean subj_chemistry = 0 != json_response.getInt("subj_chemistry");
                    boolean subj_biology = 0 != json_response.getInt("subj_biology");
                    boolean subj_music = 0 != json_response.getInt("subj_music");
                    boolean subj_english = 0 != json_response.getInt("subj_english");


                    try {
                        user_description = json_response.getString("user_description");
                        System.out.println("desc: " + user_description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Warning: No Description");
                    }

                    int user_yearofbirth = json_response.getInt("user_yearofbirth");
                    int user_id = json_response.getInt("user_id");

                    //changing to mainpage_activity
                    Intent home_intent = new Intent(userprofile_activity.this, mainpage_activity.class);

                    //passing Response Data to mainpage activity
                    home_intent.putExtra("user_username", user_username);
                    home_intent.putExtra("user_name", user_name);
                    home_intent.putExtra("user_firstname", user_firstname);
                    home_intent.putExtra("user_username", user_name);
                    home_intent.putExtra("user_school", user_school);
                    home_intent.putExtra("user_email", user_email);
                    home_intent.putExtra("user_description", user_description);

                    home_intent.putExtra("user_yearofbirth", user_yearofbirth);
                    home_intent.putExtra("user_id", user_id);
                    home_intent.putExtra("user_password", user_password);


                    home_intent.putExtra("subj_german", subj_german);
                    home_intent.putExtra("subj_spanish", subj_spanish);
                    home_intent.putExtra("subj_french", subj_french);
                    home_intent.putExtra("subj_english", subj_english);
                    home_intent.putExtra("subj_maths", subj_maths);
                    home_intent.putExtra("subj_physics", subj_physics);
                    home_intent.putExtra("subj_chemistry", subj_chemistry);
                    home_intent.putExtra("subj_biology", subj_biology);
                    home_intent.putExtra("subj_music", subj_music);


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

        private result_item senderInfo;
        private  result_item receiverInfo;


        @Override
        public void onClick(View view) {
            try {
                senderInfo = new result_iteminfo().createNewItem(new JSONObject(extras.getString("clientInfo")));
                receiverInfo = new result_iteminfo().createNewItem(new JSONObject(extras.getString("profileInfo")));


                Intent chatIntent = new Intent(userprofile_activity.this, ChatMessage.class);

                if (view == findViewById(R.id.userprofileact_math_imageview)) { chatIntent.putExtra("subject", "math"); }
                else if (view == findViewById(R.id.userprofileact_french_imageview)) { chatIntent.putExtra("subject", "french"); }
                else if (view == findViewById(R.id.userprofileact_spanish_imageview)) { chatIntent.putExtra("subject", "spanish"); }
                else if (view == findViewById(R.id.userprofileact_english_imageview)) { chatIntent.putExtra("subject", "english"); }
                else if (view == findViewById(R.id.userprofileact_german_imageview)) { chatIntent.putExtra("subject", "german"); }
                else if (view == findViewById(R.id.userprofileact_biology_imageview)) { chatIntent.putExtra("subject", "biology"); }
                else if (view == findViewById(R.id.userprofileact_chemistry_imageview)) { chatIntent.putExtra("subject", "chemistry"); }
                else if (view == findViewById(R.id.userprofileact_physics_imageview)) { chatIntent.putExtra("subject", "physics"); }
                else if (view == findViewById(R.id.userprofileact_music_imageview)) { chatIntent.putExtra("subject", "music"); }
                else { chatIntent.putExtra("subject", "nil"); }

                chatIntent.putExtra("senderInfo", extras.getString("clientInfo"));
                chatIntent.putExtra("receiverInfo", extras.getString("profileInfo"));
                startActivity(chatIntent);
            } catch (JSONException e) { e.printStackTrace(); }
        }
    }
}
