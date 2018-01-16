package com.deuce.me.matura;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class settings_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_activity);

        //Objects
        final EditText firstname_et = (EditText) findViewById(R.id.settingsact_firstname_edittext);
        final EditText name_et = (EditText) findViewById(R.id.settingsact_name_edittext);
        final EditText email_et = (EditText) findViewById(R.id.settingsact_email_edittext);
        final EditText description_et = (EditText) findViewById(R.id.settingsact_description_edittext);
        final EditText oldpassword_et = (EditText) findViewById(R.id.settingsact_oldpassword_edittext);
        final EditText newpassword_et = (EditText) findViewById(R.id.settingsact_newpassword_edittext);
        final EditText newconfpassword_et = (EditText) findViewById(R.id.settingsact_confnewpassword_edittext);

        final Spinner school_sp = (Spinner) findViewById(R.id.settingsact_school_spinner);

        final CheckBox german_cb = (CheckBox) findViewById(R.id.settingsact_german_checkbox);
        final CheckBox spanish_cb = (CheckBox) findViewById(R.id.settingsact_spanish_checkbox);
        final CheckBox english_cb = (CheckBox) findViewById(R.id.settingsact_english_checkbox);
        final CheckBox french_cb = (CheckBox) findViewById(R.id.settingsact_french_checkbox);
        final CheckBox biology_cb = (CheckBox) findViewById(R.id.settingsact_biology_checkbox);
        final CheckBox music_cb = (CheckBox) findViewById(R.id.settingsact_music_checkbox);
        final CheckBox chemics_cb = (CheckBox) findViewById(R.id.settingsact_chemics_checkbox);
        final CheckBox maths_cb = (CheckBox) findViewById(R.id.settingsact_maths_checkbox);
        final CheckBox physics_cb = (CheckBox) findViewById(R.id.settingsact_physics_checkbox);

        final Button savechanges_bt = (Button) findViewById(R.id.settingsact_savechanges_button);



        //Getting Extras ----------------
        Bundle extras_bundle = getIntent().getExtras();

        int id = 0;

        if(!extras_bundle.get("user_id").toString().isEmpty()) {

            id = Integer.parseInt(extras_bundle.getString("user_id"));

            System.out.println("id" + id);
            firstname_et.setText(extras_bundle.getString("user_firstname"));
            name_et.setText(extras_bundle.getString("user_name"));
            email_et.setText(extras_bundle.getString("user_email"));
            description_et.setText(extras_bundle.getString("user_description"));

            //TODO
            //setting school -----------------

            //TODO
            //setting profile picture

            german_cb.setChecked((boolean) extras_bundle.get("subj_german"));
            spanish_cb.setChecked((boolean) extras_bundle.get("subj_spanish"));
            french_cb.setChecked((boolean) extras_bundle.get("subj_french"));
            english_cb.setChecked((boolean) extras_bundle.get("subj_english"));
            maths_cb.setChecked((boolean) extras_bundle.get("subj_maths"));
            biology_cb.setChecked((boolean) extras_bundle.get("subj_biology"));
            music_cb.setChecked((boolean) extras_bundle.get("subj_music"));
            chemics_cb.setChecked((boolean) extras_bundle.get("subj_chemics"));
            physics_cb.setChecked((boolean) extras_bundle.get("subj_physics"));

            savechanges_bt.setOnClickListener(new onSaveListener());
        } else {
            //Error with extras
        }
    }

    class onSaveListener implements View.OnClickListener {

        Bundle extras_bundle = getIntent().getExtras();

        int id = Integer.parseInt((extras_bundle.getString("user_id")));

        final EditText firstname_et = (EditText) findViewById(R.id.settingsact_firstname_edittext);
        final EditText name_et = (EditText) findViewById(R.id.settingsact_name_edittext);
        final EditText email_et = (EditText) findViewById(R.id.settingsact_email_edittext);
        final EditText description_et = (EditText) findViewById(R.id.settingsact_description_edittext);
        final EditText oldpassword_et = (EditText) findViewById(R.id.settingsact_oldpassword_edittext);
        final EditText newpassword_et = (EditText) findViewById(R.id.settingsact_newpassword_edittext);
        final EditText newconfpassword_et = (EditText) findViewById(R.id.settingsact_confnewpassword_edittext);

        final Spinner school_sp = (Spinner) findViewById(R.id.settingsact_school_spinner);

        final CheckBox german_cb = (CheckBox) findViewById(R.id.settingsact_german_checkbox);
        final CheckBox spanish_cb = (CheckBox) findViewById(R.id.settingsact_spanish_checkbox);
        final CheckBox english_cb = (CheckBox) findViewById(R.id.settingsact_english_checkbox);
        final CheckBox french_cb = (CheckBox) findViewById(R.id.settingsact_french_checkbox);
        final CheckBox biology_cb = (CheckBox) findViewById(R.id.settingsact_biology_checkbox);
        final CheckBox music_cb = (CheckBox) findViewById(R.id.settingsact_music_checkbox);
        final CheckBox chemics_cb = (CheckBox) findViewById(R.id.settingsact_chemics_checkbox);
        final CheckBox maths_cb = (CheckBox) findViewById(R.id.settingsact_maths_checkbox);
        final CheckBox physics_cb = (CheckBox) findViewById(R.id.settingsact_physics_checkbox);

        @Override
        public void onClick(View view) {

            String name = name_et.getText().toString();
            String surname = firstname_et.getText().toString();
            String email = email_et.getText().toString();
            String description = description_et.getText().toString();

            // String school = school_sp.getSelectedItem().toString();
            String school = "";

            boolean german = german_cb.isChecked();
            boolean spanish = spanish_cb.isChecked();
            boolean english = english_cb.isChecked();
            boolean french = french_cb.isChecked();
            boolean biology = biology_cb.isChecked();
            boolean chemics = chemics_cb.isChecked();
            boolean maths = maths_cb.isChecked();
            boolean physics = physics_cb.isChecked();
            boolean music = music_cb.isChecked();

            if(newpassword_et.getText().toString().isEmpty()) {

                //TODO no pw request

            } else if(newpassword_et.getText().toString().equals(newconfpassword_et.getText().toString()) && !newpassword_et.getText().toString().isEmpty() && !newconfpassword_et.getText().toString().isEmpty() ) {

                String oldpassword = oldpassword_et.getText().toString();
                String newpassword = newpassword_et.getText().toString();


                System.out.println("Making save request");

                savesettings_pw_request save_request = new savesettings_pw_request(id, surname, name, email, school, description, oldpassword, newpassword, german, spanish, english, french, biology, chemics, music, maths, physics, new onResponseListener());
                RequestQueue request_queue = Volley.newRequestQueue(settings_activity.this); //Request Queue
                request_queue.add(save_request);

            } else {

                //TODO conf pw failed
                System.out.println("Not inserted Correctly");
            }
        }
    }

    class onResponseListener implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {

            try {
                System.out.println("Received Response: " + response);
                JSONObject json_response = new JSONObject(response);
                boolean success = json_response.getBoolean("success");

                System.out.println("Success: " + success);

                if (success) {

                    //Retreiving data from response
                    String user_username = json_response.getString("user_username");
                    String user_name = json_response.getString("user_name");
                    String user_firstname = json_response.getString("user_firstname");
                    String user_school = json_response.getString("user_school");
                    String user_email = json_response.getString("user_email");

                    String user_description = "";

                    try {
                        user_description = json_response.getString("user_description");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Warning: No Description");
                    }

                    int user_id = json_response.getInt("user_id");

                    boolean subj_german = json_response.getBoolean("subj_german");
                    boolean subj_spanish = json_response.getBoolean("subj_spanish");
                    boolean subj_english = json_response.getBoolean("subj_english");
                    boolean subj_french = json_response.getBoolean("subj_french");
                    boolean subj_music = json_response.getBoolean("subj_music");
                    boolean subj_chemics = json_response.getBoolean("subj_chemics");
                    boolean subj_biology = json_response.getBoolean("subj_biology");
                    boolean subj_physics = json_response.getBoolean("subj_physics");
                    boolean subj_maths = json_response.getBoolean("subj_chemics");

                    Intent save_intent = new Intent(settings_activity.this, mainpage_activity.class);

                    save_intent.putExtra("user_username", user_username);
                    save_intent.putExtra("user_name", user_name);
                    save_intent.putExtra("user_firstname", user_firstname);
                    save_intent.putExtra("user_username", user_name);
                    save_intent.putExtra("user_school", user_school);
                    save_intent.putExtra("user_email", user_email);
                    save_intent.putExtra("user_description", user_description);
                    save_intent.putExtra("user_id", user_id);

                    save_intent.putExtra("subj_german", subj_german);
                    save_intent.putExtra("subj_french", subj_french);
                    save_intent.putExtra("subj_english", subj_english);
                    save_intent.putExtra("subj_music", subj_music);
                    save_intent.putExtra("subj_spanish", subj_spanish);
                    save_intent.putExtra("subj_maths", subj_maths);
                    save_intent.putExtra("subj_biology", subj_biology);
                    save_intent.putExtra("subj_chemics", subj_chemics);
                    save_intent.putExtra("subj_physics", subj_physics);

                    startActivity(save_intent);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}



