package com.deuce.me.matura.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.deuce.me.matura.util.JSONtoInfo;
import com.deuce.me.matura.R;
import com.deuce.me.matura.util.passwordHasher;
import com.deuce.me.matura.requests.SaveSettingsRequest;
import com.deuce.me.matura.models.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

public class SecuritysettingsActivity extends AppCompatActivity {

    private Bundle extras;
    private UserModel clientInfo;
    private EditText email_et, oldPW_et, newPW_et, confPW_et;
    private Button save_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_settings);

        Toolbar toolbar = findViewById(R.id.security_toolbar);
        toolbar.setTitle(R.string.securitySettings_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email_et = findViewById(R.id.secsettings_email_et);
        oldPW_et = findViewById(R.id.secsettings_oldpw_et);
        newPW_et = findViewById(R.id.secsettings_newpw_et);
        confPW_et = findViewById(R.id.secsettings_confpw_et);
        save_bt = findViewById(R.id.secsettings_save_bt);
        save_bt.setOnClickListener(new onSaveListener());

        extras = getIntent().getExtras();

        try {
            clientInfo = new JSONtoInfo(getBaseContext()).createNewItem(new JSONObject(extras.getString("clientInfo")));
            email_et.setText(clientInfo.getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(SecuritysettingsActivity.this, SettingsoverviewActivity.class);
                intent.putExtra("clientInfo", extras.getString("clientInfo"));
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

            String email = email_et.getText().toString();
            String oldPW = oldPW_et.getText().toString();
            String newPW = newPW_et.getText().toString();
            String confPW = confPW_et.getText().toString();

            if(!email.isEmpty() && oldPW.isEmpty() && newPW.isEmpty() && confPW.isEmpty() && email.contains(".") && email.contains("@")) {
                //SAVEREQUEST NO PW

                System.out.println("Making save request");

                SaveSettingsRequest save_request = new SaveSettingsRequest(clientInfo.getId(),
                        clientInfo.getFirstname(),
                        clientInfo.getName(),
                        email,
                        clientInfo.getSchool(),
                        clientInfo.getDescription(),
                        clientInfo.getPassword(),
                        clientInfo.getPassword(),
                        clientInfo.isGerman(),
                        clientInfo.isSpanish(),
                        clientInfo.isEnglish(),
                        clientInfo.isFrench(),
                        clientInfo.isBiology(),
                        clientInfo.isChemistry(),
                        clientInfo.isMusic(),
                        clientInfo.isMaths(),
                        clientInfo.isPhysics(),
                        new SettingsoverviewActivity.onSaveResponseListener(getBaseContext(), 0));

                RequestQueue request_queue = Volley.newRequestQueue(SecuritysettingsActivity.this); //Request Queue
                request_queue.add(save_request);

            } else if(!email.isEmpty() && !oldPW.isEmpty() && !newPW.isEmpty() && !confPW.isEmpty() && (newPW.equals(confPW)) && email.contains(".") && email.contains("@")) {
                //WITH PW

                passwordHasher pwH = new passwordHasher();
                String oldPasswordHash = pwH.hashPassword(oldPW, clientInfo.getSalt());
                String newPasswordHash = pwH.hashPassword(newPW, clientInfo.getSalt());

                System.out.println("Making save request");

                SaveSettingsRequest save_request = new SaveSettingsRequest(clientInfo.getId(),
                        clientInfo.getFirstname(),
                        clientInfo.getName(),
                        email,
                        clientInfo.getSchool(),
                        clientInfo.getDescription(),
                        oldPasswordHash,
                        newPasswordHash,
                        clientInfo.isGerman(),
                        clientInfo.isSpanish(),
                        clientInfo.isEnglish(),
                        clientInfo.isFrench(),
                        clientInfo.isBiology(),
                        clientInfo.isChemistry(),
                        clientInfo.isMusic(),
                        clientInfo.isMaths(),
                        clientInfo.isPhysics(),
                        new SettingsoverviewActivity.onSaveResponseListener(getBaseContext(), 0));

                RequestQueue request_queue = Volley.newRequestQueue(SecuritysettingsActivity.this); //Request Queue
                request_queue.add(save_request);
            }

        }
    }
}
