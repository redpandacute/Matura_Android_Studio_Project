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
                    Intent login_intent = new Intent(login_activity.this, mainpage_activity.class);

                    //passing Response Data to mainpage activity
                    login_intent.putExtra("user_username", user_username);
                    login_intent.putExtra("user_name", user_name);
                    login_intent.putExtra("user_firstname", user_firstname);
                    login_intent.putExtra("user_username", user_name);
                    login_intent.putExtra("user_school", user_school);
                    login_intent.putExtra("user_email", user_email);
                    login_intent.putExtra("user_description", user_description);

                    login_intent.putExtra("user_yearofbirth", user_yearofbirth);
                    login_intent.putExtra("user_id", user_id);
                    login_intent.putExtra("user_password", user_password);


                    login_intent.putExtra("subj_german", subj_german);
                    login_intent.putExtra("subj_spanish", subj_spanish);
                    login_intent.putExtra("subj_french", subj_french);
                    login_intent.putExtra("subj_english", subj_english);
                    login_intent.putExtra("subj_maths", subj_maths);
                    login_intent.putExtra("subj_physics", subj_physics);
                    login_intent.putExtra("subj_chemistry", subj_chemistry);
                    login_intent.putExtra("subj_biology", subj_biology);
                    login_intent.putExtra("subj_music", subj_music);


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

