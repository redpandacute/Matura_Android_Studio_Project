package com.deuce.me.matura;

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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class profileSettings extends AppCompatActivity {

    EditText firstname_et, name_et, description_et;
    CheckBox german_cb, spanish_cb, french_cb, english_cb, biology_cb, music_cb, chemistry_cb, maths_cb, physics_cb;
    Spinner school_sp;
    Button save_bt;
    Bundle extras;
    userInfo clientInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        getSupportActionBar().setTitle(R.string.profileSettings_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firstname_et = (EditText) findViewById(R.id.profsettings_firstname_edittext);
        name_et = (EditText) findViewById(R.id.profsettings_name_edittext);
        description_et = (EditText) findViewById(R.id.profsettings_description_edittext);

        school_sp = (Spinner) findViewById(R.id.profsettings_school_spinner);

        german_cb = (CheckBox) findViewById(R.id.profsettings_german_checkbox);
        spanish_cb = (CheckBox) findViewById(R.id.profsettings_spanish_checkbox);
        english_cb = (CheckBox) findViewById(R.id.profsettings_english_checkbox);
        french_cb = (CheckBox) findViewById(R.id.profsettings_french_checkbox);
        biology_cb = (CheckBox) findViewById(R.id.profsettings_biology_checkbox);
        music_cb = (CheckBox) findViewById(R.id.profsettings_music_checkbox);
        chemistry_cb = (CheckBox) findViewById(R.id.profsettings_chemics_checkbox);
        maths_cb = (CheckBox) findViewById(R.id.profsettings_maths_checkbox);
        physics_cb = (CheckBox) findViewById(R.id.profsettings_physics_checkbox);

        save_bt = findViewById(R.id.profsettings_save_bt);
        save_bt.setOnClickListener(new onSaveListener());

        extras = getIntent().getExtras();
        try {
            clientInfo = new JSONtoInfo().createNewItem(new JSONObject(extras.getString("clientInfo")));
            firstname_et.setText(clientInfo.getFirstname());
            name_et.setText(clientInfo.getName());
            description_et.setText(clientInfo.getDescription());

            german_cb.setChecked(clientInfo.isGerman());
            spanish_cb.setChecked(clientInfo.isSpanish());
            english_cb.setChecked(clientInfo.isEnglish());
            french_cb.setChecked(clientInfo.isFrench());
            biology_cb.setChecked(clientInfo.isBiology());
            music_cb.setChecked(clientInfo.isMusic());
            chemistry_cb.setChecked(clientInfo.isChemistry());
            maths_cb.setChecked(clientInfo.isMaths());
            physics_cb.setChecked(clientInfo.isPhysics());

        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(profileSettings.this, settingsOverview.class);
                intent.putExtra("clientInfo", getIntent().getExtras().getString("clientInfo"));
                startActivity(intent);
                System.out.println("::BACK BUTTON::");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class onSaveListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String name = name_et.getText().toString();
            String firstname = firstname_et.getText().toString();
            String description = description_et.getText().toString();

            // String school = school_sp.getSelectedItem().toString();
            String school = "";

            boolean german = german_cb.isChecked();
            boolean spanish = spanish_cb.isChecked();
            boolean english = english_cb.isChecked();
            boolean french = french_cb.isChecked();
            boolean biology = biology_cb.isChecked();
            boolean chemistry = chemistry_cb.isChecked();
            boolean maths = maths_cb.isChecked();
            boolean physics = physics_cb.isChecked();
            boolean music = music_cb.isChecked();

            if (!name.isEmpty() && !firstname.isEmpty()) {

                System.out.println("Making save request");

                savesettings_pw_request save_request = new savesettings_pw_request(clientInfo.getId(),
                        firstname,
                        name,
                        clientInfo.getEmail(),
                        school,
                        description,
                        clientInfo.getPassword(),
                        clientInfo.getPassword(),
                        german,
                        spanish,
                        english,
                        french,
                        biology,
                        chemistry,
                        music,
                        maths,
                        physics,
                        new onResponseListener(getApplicationContext()));

                RequestQueue request_queue = Volley.newRequestQueue(profileSettings.this); //Request Queue
                request_queue.add(save_request);
            }
        }
    }
}
