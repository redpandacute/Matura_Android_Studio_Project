package com.deuce.me.matura;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

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

            final String username = username_et.getText().toString();
            final String password = password_et.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()) {

                System.out.println("Making request");
                login_request log_request = new login_request(username, password, new onResponseListener());
                RequestQueue request_queue = Volley.newRequestQueue(login_activity.this); //Request Queue
                request_queue.add(log_request);

            } else {
                //Login not inserted correctly
                System.out.println("Not inserted Correctly");
            }

        }
    }


    private class onResponseListener implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {

            try {

                System.out.println("my_response" + response);
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

