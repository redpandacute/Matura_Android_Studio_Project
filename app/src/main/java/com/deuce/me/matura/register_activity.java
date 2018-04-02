package com.deuce.me.matura;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class register_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        getActionBar().setHomeButtonEnabled(true);

        final Spinner yearofbirth_sp = findViewById(R.id.registeract_yearofbirth_spinner);
        final Spinner school_sp = findViewById(R.id.registeract_school_spinner);

        final Button registerbutton_bu = findViewById(R.id.registeract_signup_button);

        SpinnerYears(yearofbirth_sp);

        registerbutton_bu.setOnClickListener(new onRegisterListener());

    }


    //OnResponseListener Register ------------------------------------------------------------------
    private class onResponseListener implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {

            try {
                System.out.println(response);
                JSONObject json_response = new JSONObject(response);
                boolean success = json_response.getBoolean("success");

                if (success) {
                    Intent intent = new Intent(register_activity.this, login_activity.class); //if success, change to login screen
                    register_activity.this.startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(register_activity.this);
                    builder.setMessage("Ooops, something went wrong with your registration. Retry?").setNegativeButton("Retry", null).create().show(); //Error Message if something went
                    // wrong with JSON Object, with
                }                                                                                                                                                   // retry button.
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    //OnClickListener Register ---------------------------------------------------------------------
    private class onRegisterListener implements View.OnClickListener {

        //Objects
        final EditText username_et = findViewById(R.id.registeract_username_edittext);
        final EditText name_et = findViewById(R.id.registeract_name_edittext);
        final EditText firstname_et = findViewById(R.id.registeract_firstname_edittext);
        final EditText email_et = findViewById(R.id.registeract_email_edittext);
        final EditText password_et = findViewById(R.id.registeract_password_edittext);
        final EditText confpassword_et = findViewById(R.id.registeract_confpassword_edittext);

        final Spinner yearofbirth_sp = findViewById(R.id.registeract_yearofbirth_spinner);
        final Spinner school_sp = findViewById(R.id.registeract_school_spinner);

        final CheckBox termsofservice_cb = findViewById(R.id.registeract_termsofservice_checkbox);

        @Override
        public void onClick(View view) {

            String username = username_et.getText().toString();
            String name = name_et.getText().toString();
            String surname = firstname_et.getText().toString();
            String email = email_et.getText().toString();
            String password = password_et.getText().toString();
            String confpassword = confpassword_et.getText().toString();
            String school = "";
            int yearofbirth = (int) yearofbirth_sp.getSelectedItem();

            try {
                yearofbirth = Integer.parseInt(yearofbirth_sp.getSelectedItem().toString());
                //school = school_sp.getSelectedItem().toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (password.equals(confpassword)) {
                if (termsofservice_cb.isChecked() && !username.isEmpty() && !name.isEmpty() && !surname.isEmpty() /*&& !school.isEmpty() */ && !email.isEmpty() && !password.isEmpty()){



                    System.out.println("Creating request");
                    //Creating Request
                    register_request reg_request = new register_request(username, name, surname, school, yearofbirth,email, password, new onResponseListener());
                    RequestQueue request_queue = Volley.newRequestQueue(register_activity.this); //Makeing a Requestqueue
                    request_queue.add(reg_request);

                } else {
                    System.out.println("not accepting termsoS");
                    //Error not accepting Terms o Service
                }
            } else {
                System.out.println("confpw unsuccessful");
                //Error if confpw was not successful
            }


        }
    }


    //Adding years function ------------------------------------------------------------------------
    private void SpinnerYears (Spinner yearSpinner) {


        List years = new ArrayList();
        for (int i = Calendar.getInstance().get(Calendar.YEAR); i > Calendar.getInstance().get(Calendar.YEAR) - 100; i--){
            years.add(i);
        }

        final ArrayList<Integer> yearlist = new ArrayList<>();
        yearlist.addAll(years);
        final ArrayAdapter<Integer> year_spinner_adp = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, yearlist);
        year_spinner_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(year_spinner_adp);
        year_spinner_adp.notifyDataSetChanged();

    }
}
