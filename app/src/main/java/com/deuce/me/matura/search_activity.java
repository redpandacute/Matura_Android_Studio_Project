package com.deuce.me.matura;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class search_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);
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
            search_request search_request = new search_request(name /*,School*/, map, new onResponseListener());

        }
    }

    class onResponseListener implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {

            try {

                JSONObject json_response = new JSONObject(response);
                boolean success = json_response.getBoolean("success");

                if(success) {

                    Integer[] results = new Integer[json_response.length()];

                    for(int l = 0; l < json_response.length(); l++) {
                        results[l] = json_response.getInt(l + "");
                    }

                    Intent view_results = new Intent(search_activity.this, viewresults_activity.class);
                    view_results.putExtra("result_array", results);

                } else {

                    System.out.println("There was an issue with your search request");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
