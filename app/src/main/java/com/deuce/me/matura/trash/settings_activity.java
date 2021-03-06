package com.deuce.me.matura.trash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.deuce.me.matura.R;
import com.deuce.me.matura.activities.MainpageActivity;
import com.deuce.me.matura.models.UserModel;
import com.deuce.me.matura.requests.SaveSettingsRequest;
import com.deuce.me.matura.util.JSONtoInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class settings_activity extends AppCompatActivity {

    private Bundle extrasBundle;
    private UserModel clientInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Objects
        final EditText firstname_et = findViewById(R.id.settingsact_firstname_edittext);
        final EditText name_et = findViewById(R.id.settingsact_name_edittext);
        final EditText email_et = findViewById(R.id.settingsact_email_edittext);
        final EditText description_et = findViewById(R.id.settingsact_description_edittext);
        final EditText oldpassword_et = findViewById(R.id.settingsact_oldpassword_edittext);
        final EditText newpassword_et = findViewById(R.id.settingsact_newpassword_edittext);
        final EditText newconfpassword_et = findViewById(R.id.settingsact_confnewpassword_edittext);

        final Spinner school_sp = findViewById(R.id.settingsact_school_spinner);

        final CheckBox german_cb = findViewById(R.id.settingsact_german_checkbox);
        final CheckBox spanish_cb = findViewById(R.id.settingsact_spanish_checkbox);
        final CheckBox english_cb = findViewById(R.id.settingsact_english_checkbox);
        final CheckBox french_cb = findViewById(R.id.settingsact_french_checkbox);
        final CheckBox biology_cb = findViewById(R.id.settingsact_biology_checkbox);
        final CheckBox music_cb = findViewById(R.id.settingsact_music_checkbox);
        final CheckBox chemistry_cb = findViewById(R.id.settingsact_chemics_checkbox);
        final CheckBox maths_cb = findViewById(R.id.settingsact_maths_checkbox);
        final CheckBox physics_cb = findViewById(R.id.settingsact_physics_checkbox);

        final Button savechanges_bt = findViewById(R.id.settingsact_savechanges_button);



        //Getting Extras ----------------
        extrasBundle = getIntent().getExtras();

        try {
            clientInfo = new JSONtoInfo(getBaseContext()).createNewItem(new JSONObject(extrasBundle.getString("clientInfo")));


            int id = clientInfo.getId();

                System.out.println("id" + id);
                firstname_et.setText(clientInfo.getFirstname());
                name_et.setText(clientInfo.getName());
                email_et.setText(clientInfo.getEmail());
                description_et.setText(clientInfo.getDescription());

                //TODO
                //setting school -----------------

                //TODO
                //setting profile picture

                german_cb.setChecked(clientInfo.isGerman());
                spanish_cb.setChecked(clientInfo.isSpanish());
                french_cb.setChecked(clientInfo.isFrench());
                english_cb.setChecked(clientInfo.isEnglish());
                maths_cb.setChecked(clientInfo.isMaths());
                biology_cb.setChecked(clientInfo.isBiology());
                music_cb.setChecked(clientInfo.isMusic());
                chemistry_cb.setChecked(clientInfo.isChemistry());
                physics_cb.setChecked(clientInfo.isPhysics());

                savechanges_bt.setOnClickListener(new onSaveListener());

        } catch (JSONException e) { e.printStackTrace(); }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(settings_activity.this, MainpageActivity.class);
                intent.putExtra("clientInfo", extrasBundle.getString("clientInfo"));
                startActivity(intent);
                System.out.println("::BACK BUTTON::");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class onSaveListener implements View.OnClickListener {

        Bundle extras_bundle = getIntent().getExtras();

        int id = clientInfo.getId();

        final EditText firstname_et = (EditText) findViewById(R.id.settingsact_firstname_edittext);
        final EditText name_et = (EditText) findViewById(R.id.settingsact_name_edittext);
        final EditText email_et = (EditText) findViewById(R.id.settingsact_email_edittext);
        final EditText description_et = (EditText) findViewById(R.id.settingsact_description_edittext);
        final EditText oldpassword_et = (EditText) findViewById(R.id.settingsact_oldpassword_edittext);
        final EditText newpassword_et = (EditText) findViewById(R.id.settingsact_newpassword_edittext);
        final EditText newconfpassword_et = (EditText) findViewById(R.id.settingsact_confnewpassword_edittext);

        final Spinner school_sp = (Spinner) findViewById(R.id.settingsact_school_spinner);
        //final Spinner grade_sp = (Spinner) findViewById(R.id.settingsact_grade_spinner);


        final CheckBox german_cb = (CheckBox) findViewById(R.id.settingsact_german_checkbox);
        final CheckBox spanish_cb = (CheckBox) findViewById(R.id.settingsact_spanish_checkbox);
        final CheckBox english_cb = (CheckBox) findViewById(R.id.settingsact_english_checkbox);
        final CheckBox french_cb = (CheckBox) findViewById(R.id.settingsact_french_checkbox);
        final CheckBox biology_cb = (CheckBox) findViewById(R.id.settingsact_biology_checkbox);
        final CheckBox music_cb = (CheckBox) findViewById(R.id.settingsact_music_checkbox);
        final CheckBox chemistry_cb = (CheckBox) findViewById(R.id.settingsact_chemics_checkbox);
        final CheckBox maths_cb = (CheckBox) findViewById(R.id.settingsact_maths_checkbox);
        final CheckBox physics_cb = (CheckBox) findViewById(R.id.settingsact_physics_checkbox);

        @Override
        public void onClick(View view) {

            String name = name_et.getText().toString();
            String firstname = firstname_et.getText().toString();
            String email = email_et.getText().toString();
            String description = description_et.getText().toString();

            String school = school_sp.getSelectedItem().toString();
            int grade = 2;

            boolean german = german_cb.isChecked();
            boolean spanish = spanish_cb.isChecked();
            boolean english = english_cb.isChecked();
            boolean french = french_cb.isChecked();
            boolean biology = biology_cb.isChecked();
            boolean chemistry = chemistry_cb.isChecked();
            boolean maths = maths_cb.isChecked();
            boolean physics = physics_cb.isChecked();
            boolean music = music_cb.isChecked();

            if(newpassword_et.getText().toString().isEmpty() && !name_et.getText().toString().isEmpty() && !firstname_et.getText().toString().isEmpty() && !email_et.getText().toString().isEmpty() && email_et.getText().toString().contains("@") && email_et.getText().toString().contains(".")) {

                System.out.println("Making save request");

                savesettings_nopw_request save_request = new savesettings_nopw_request(id, firstname, name, email, school, description, german, spanish, english, french, biology, chemistry, music, maths, physics, new onResponseListener());
                RequestQueue request_queue = Volley.newRequestQueue(settings_activity.this); //Request Queue
                request_queue.add(save_request);

            } else if(newpassword_et.getText().toString().equals(newconfpassword_et.getText().toString()) && !newpassword_et.getText().toString().isEmpty() && !oldpassword_et.getText().toString().isEmpty() && !name_et.getText().toString().isEmpty() && !firstname_et.getText().toString().isEmpty() && !email_et.getText().toString().isEmpty() && email_et.getText().toString().contains("@") && email_et.getText().toString().contains(".")) {

                String oldpassword = oldpassword_et.getText().toString();
                String newpassword = newpassword_et.getText().toString();


                System.out.println("Making save request");

                SaveSettingsRequest save_request = new SaveSettingsRequest(id, firstname, name, email, school, grade, description, oldpassword, newpassword, german, spanish, english, french, biology, chemistry, music, maths, physics, new onResponseListener());
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
                boolean success_user = json_response.getBoolean("success_user");
                boolean success_subj = json_response.getBoolean("success_subjects");

                System.out.println("Success User: " + success_user);
                System.out.println("Success Subjects: " + success_subj);

                if (success_user && success_subj) {
                    Intent save_intent = new Intent(settings_activity.this, MainpageActivity.class);
                    save_intent.putExtra("clientInfo", response);
                    startActivity(save_intent);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}



