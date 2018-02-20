package com.deuce.me.matura;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flo on 11.02.2018.
 */

public class searchresults_activity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlist_activity);

        ListView listview = findViewById(R.id.searchlistact_listview_listview);
        Bundle extras_bundle = getIntent().getExtras();

        try {
            JSONArray json_arr = new JSONObject(extras_bundle.getString("results")).getJSONArray("results");
            listview.setAdapter(new searchresults_adapter(getApplicationContext(), json_arr));
        } catch (JSONException e) { e.printStackTrace(); }


    }
}