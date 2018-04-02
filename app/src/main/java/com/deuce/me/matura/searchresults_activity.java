package com.deuce.me.matura;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Flo on 11.02.2018.
 */

public class searchresults_activity extends AppCompatActivity {

    private Bundle extrasBundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlist_activity);
        getSupportActionBar().setHomeButtonEnabled(true);

        ListView listview = findViewById(R.id.searchlistact_listview_listview);
        extrasBundle = getIntent().getExtras();

        try {
            JSONArray json_arr = (new JSONObject(extrasBundle.getString("results"))).getJSONArray("results");
            listview.setAdapter(new searchresults_adapter(getApplicationContext(), json_arr));
            listview.setOnItemClickListener(new onItemClickListener());
            System.out.println(listview.getOnItemClickListener().toString());
        } catch (JSONException e) { e.printStackTrace(); }


    }


    private class onItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            try {
                Bundle extras_bundle = getIntent().getExtras();
                JSONArray json_arr = (new JSONObject(extras_bundle.getString("results"))).getJSONArray("results");

                Intent showuser_intent = new Intent(searchresults_activity.this, userprofile_activity.class);
                showuser_intent.putExtra("profileInfo", json_arr.getJSONObject(i).toString());
                showuser_intent.putExtra("clientInfo", extras_bundle.getString("clientInfo"));
                startActivity(showuser_intent);

            } catch (JSONException e) { e.printStackTrace(); }
        }
    }

}