package com.deuce.me.matura;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class register_activity extends AppCompatActivity {

    private static final int SALT_LENGHT = 32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.register_title);

        final Spinner yearofbirth_sp = findViewById(R.id.registeract_yearofbirth_spinner);
        final Spinner school_sp = findViewById(R.id.registeract_school_spinner);

        final Button registerbutton_bu = findViewById(R.id.registeract_signup_button);

        SpinnerYears(yearofbirth_sp);

        registerbutton_bu.setOnClickListener(new onRegisterListener());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(register_activity.this, login_activity.class);
                startActivity(intent);
                System.out.println("::BACK BUTTON::");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

                    passwordHasher hasher = new passwordHasher();
                    byte[] saltBytes = hasher.generateSalt(32);
                    byte[] passwordBytes = hasher.hashPassword(password, saltBytes);
                    String passwordHash = hasher.convertBytesToHex(passwordBytes);
                    String saltHex = hasher.convertBytesToHex(saltBytes);

                    System.out.println("Creating request");
                    //Creating Request
                    register_request reg_request = new register_request(username, name, surname, school, yearofbirth,email, passwordHash, saltHex, new onResponseListener());
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

    /*
    private byte[] generateSalt(int bytes) {
        byte[] salt = new byte[bytes];

        try {

            SecureRandom random = new SecureRandom().getInstance("SHA1PRNG", "SUN");
            random.nextBytes(salt);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        return salt;
    }


    //https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
    private byte[] hashPassword(String password, byte[] salt) {
        try {
            MessageDigest digester = MessageDigest.getInstance("SHA-256");
            digester.update(salt);
            byte[] hash = digester.digest(password.getBytes());

            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    //https://stackoverflow.com/questions/332079/in-java-how-do-i-convert-a-byte-array-to-a-string-of-hex-digits-while-keeping-l/2197650#2197650
    private String convertBytesToHex(byte[] bytes) {

        final char[] hexArray = "0123456789ABCDEF".toCharArray(); //Characters needed for HexValue
        char[] hexChars = new char[bytes.length * 2];

        for(int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xff;
            hexChars[i * 2] = hexArray[v/16];
            hexChars[i * 2 + 1] = hexArray[v%16];
        }

        return new String(hexChars);
    }

    //https://stackoverflow.com/questions/140131/convert-a-string-representation-of-a-hex-dump-to-a-byte-array-using-java
    private byte[] convertHexToBytes(String hex) {
        byte[] output = new byte[hex.length() / 2];
        for(int i = 0; i < hex.length(); i+= 2) {
            output[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i+1), 16));
        }
        return output;
    }
    */
}
