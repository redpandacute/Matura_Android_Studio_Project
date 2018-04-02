package com.deuce.me.matura;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class search_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);
        getSupportActionBar().setHomeButtonEnabled(true);

        ImageButton search_bt = findViewById(R.id.searchactivity_searchbutton_imagebutton);
        search_bt.setOnClickListener(new onSearchListener());
    }

    class onSearchListener implements View.OnClickListener {

        EditText name_et = findViewById(R.id.searchactivity_searchforname_edittext);

        //Spinner school_sp = findViewById(R.id.searchactivity_filterbyschool_spinner);

        CheckBox german_cb = findViewById(R.id.searchactivity_german_checkbox);
        CheckBox spanish_cb = findViewById(R.id.searchactivity_spanish_checkbox);
        CheckBox french_cb = findViewById(R.id.searchactivity_french_checkbox);
        CheckBox music_cb = findViewById(R.id.searchactivity_music_checkbox);
        CheckBox english_cb = findViewById(R.id.searchactivity_english_checkbox);
        CheckBox maths_cb = findViewById(R.id.searchactivity_maths_checkbox);
        CheckBox physics_cb = findViewById(R.id.searchactivity_physics_checkbox);
        CheckBox chemistry_cb = findViewById(R.id.searchactivity_chemistry_checkbox);
        CheckBox biology_cb = findViewById(R.id.searchactivity_biology_checkbox);

        @Override
        public void onClick(View view) {

            String name = name_et.getText().toString();

            Map<String, Boolean> map = new HashMap<>();

            map.put("subj_german", german_cb.isChecked());
            map.put("subj_spanish", spanish_cb.isChecked());
            map.put("subj_maths", maths_cb.isChecked());
            map.put("subj_physics", physics_cb.isChecked());
            map.put("subj_music", music_cb.isChecked());
            map.put("subj_french", french_cb.isChecked());
            map.put("subj_biology", biology_cb.isChecked());
            map.put("subj_english", english_cb.isChecked());
            map.put("subj_chemistry",chemistry_cb.isChecked());

            //String school = school_sp.getSelectedItem();

            System.out.println("Making searchrequest");
            Bundle extrasBundle = getIntent().getExtras();
            try {
                userInfo clientInfo = new JSONtoInfo().createNewItem(new JSONObject(extrasBundle.getString("clientInfo")));
                search_request search_request = new search_request(clientInfo.getId(), name /*,School*/, map, new onResponseListener());
                RequestQueue request_queue = Volley.newRequestQueue(search_activity.this); //Request Queue
                request_queue.add(search_request);
            } catch (JSONException e) { e.printStackTrace(); }
        }
    }

    private class onResponseListener implements  Response.Listener<String> {

        @Override
        public void onResponse(String response) {
            try {

                System.out.println("Received response: " + response);
                JSONObject json_resp = new JSONObject(response);
                boolean success = json_resp.getBoolean("success");

                if(success) {

                    Intent intent = new Intent(search_activity.this, searchresults_activity.class);
                    intent.putExtra("clientInfo", getIntent().getExtras().getString("clientInfo"));
                    intent.putExtra("results", response);
                    startActivity(intent);

                } else {
                    System.out.println("There was an issue with the search request (Maybe no results :3)");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
