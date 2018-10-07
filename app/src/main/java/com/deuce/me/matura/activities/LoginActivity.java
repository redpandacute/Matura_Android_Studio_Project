package com.deuce.me.matura.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.deuce.me.matura.R;
import com.deuce.me.matura.requests.BcryptLoginRequest;
import com.deuce.me.matura.requests.LoginRequest;
import com.deuce.me.matura.util.AccountGenerator;
import com.deuce.me.matura.util.PasswordHasher;
import com.deuce.me.matura.requests.SaltRequest;
import com.deuce.me.matura.util.TempFileGenerator;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private static String password, username;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        Toolbar toolbar = findViewById(R.id.login_toolbar);
        toolbar.setTitle(R.string.login_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //Objects
        final TextView register_textview = findViewById(R.id.loginact_register_textview);
        final Button signin_button = findViewById(R.id.loginact_signin_button);

        //Change to Registeract on click ---------------------------------------------------------

        register_textview.setOnClickListener(new onRegisterListener());

        //Change to Login on click ---------------------------------------------------------

        signin_button.setOnClickListener(new BcryptOnLoginListener());
    }

    @Override
    public void onBackPressed() {
        //empty backfunction
    }

    @Override
    public void onStart() {
        super.onStart();
        AccountGenerator gen = new AccountGenerator(getBaseContext());
        gen.generate();
    }

    private class onRegisterListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            Intent register_intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(register_intent);
        }
    }

    private class BcryptOnLoginListener implements View.OnClickListener {

        final EditText username_et = findViewById(R.id.loginact_username_edittext);
        final EditText password_et = findViewById(R.id.loginact_password_edittext);

        @Override
        public void onClick(View view) {

            String username = username_et.getText().toString();
            String password = password_et.getText().toString();

            if(!username.isEmpty() || !password.isEmpty()) {
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                BcryptLoginRequest login = new BcryptLoginRequest(username, password, new onLoginResponseListener());
                queue.add(login);
            }
        }
    }

    private class onLoginListener implements View.OnClickListener {

        final EditText username_et = findViewById(R.id.loginact_username_edittext);
        final EditText password_et = findViewById(R.id.loginact_password_edittext);

        @Override
        public void onClick(View view) {

            username = username_et.getText().toString();
            password = password_et.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()) {

                System.out.println("Making request");
                SaltRequest salt = new SaltRequest(username, new onSaltResponseListener());
                queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(salt);

            } else {
                //Login not inserted correctly
                System.out.println("Not inserted Correctly");
            }

        }
    }

    private class onSaltResponseListener implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {
            try {
                System.out.println("SALT RESP: " + response);
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");

                if(success) {
                    PasswordHasher pH = new PasswordHasher();
                    String passwordHash = pH.hashPassword(password, jsonResponse.getString("hash_salt"));
                    System.out.println(passwordHash);
                    LoginRequest login = new LoginRequest(username, passwordHash, new onLoginResponseListener());
                    queue.add(login);
                }
            } catch (JSONException e) {
            e.printStackTrace();
            }
        }
    }

    private class onLoginResponseListener implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {

            try {

                System.out.println("LOGIN RESP: " + response);
                JSONObject json_response = new JSONObject(response);
                boolean success = json_response.getBoolean("success");

                System.out.println(json_response);

                if (success) {
                    String tempPath = new TempFileGenerator().getTempFilePath(getBaseContext(), json_response.getString("blob_profilepicture_big"));
                    System.out.println(tempPath);
                    json_response.remove("blob_profilepicture_big");
                    json_response.put("temp_profilepicture_path", tempPath);
                    System.out.println("TEST::: " + json_response);
                    //Intent login_intent = new Intent(LoginActivity.this, MainpageActivity.class);
                    Intent login_intent = new Intent(LoginActivity.this, MainActivity.class);
                    login_intent.putExtra("clientInfo", json_response.toString());
                    //Starting activity
                    startActivity(login_intent);

                } else {
                    //Incorrect username of password
                    System.out.println("incorrect PW or username");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

