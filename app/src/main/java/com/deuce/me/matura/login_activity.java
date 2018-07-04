package com.deuce.me.matura;

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

import org.json.JSONException;
import org.json.JSONObject;

public class login_activity extends AppCompatActivity {

    private static String password, username;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        toolbar.setTitle(R.string.login_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Objects
        final TextView register_textview = (TextView) findViewById(R.id.loginact_register_textview);
        final Button signin_button = (Button) findViewById(R.id.loginact_signin_button);

        //Change to Registeract on click ---------------------------------------------------------

        register_textview.setOnClickListener(new onRegisterListener());

        //Change to Mainpageact on click ---------------------------------------------------------

        signin_button.setOnClickListener(new onLoginListener());
    }

    private class onRegisterListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            Intent register_intent = new Intent(login_activity.this, register_activity.class);
            startActivity(register_intent);
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
                saltRequest salt = new saltRequest(username, new onSaltResponseListener());
                queue = Volley.newRequestQueue(login_activity.this);
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
                    passwordHasher pH = new passwordHasher();
                    String passwordHash = pH.hashPassword(password, jsonResponse.getString("hash_salt"));
                    System.out.println(passwordHash);
                    login_request login = new login_request(username, passwordHash, new onLoginResponseListener());
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
                    Intent login_intent = new Intent(login_activity.this, mainpage_activity.class);
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

