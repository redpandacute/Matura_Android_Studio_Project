package com.deuce.me.matura.fragments.searchoverview;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.deuce.me.matura.R;
import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.activities.SearchfilterActivity;
import com.deuce.me.matura.models.UserModel;
import com.deuce.me.matura.requests.SearchRequest;
import com.deuce.me.matura.util.JSONtoInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ingli on 12.08.2018.
 */

public class OnSearchListener implements View.OnClickListener {

    private View view;
    private SearchoverviewFragment mFragment;
    private MainActivity mActivity;

    public OnSearchListener(View view, SearchoverviewFragment mFragment) {
        this.view = view;
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
    }

    EditText name_et = view.findViewById(R.id.searchactivity_searchforname_edittext);

    //Spinner school_sp = findViewById(R.id.searchactivity_filterbyschool_spinner);
    CheckBox german_cb = view.findViewById(R.id.searchactivity_german_checkbox);
    CheckBox spanish_cb = view.findViewById(R.id.searchactivity_spanish_checkbox);
    CheckBox french_cb = view.findViewById(R.id.searchactivity_french_checkbox);
    CheckBox music_cb = view.findViewById(R.id.searchactivity_music_checkbox);
    CheckBox english_cb = view.findViewById(R.id.searchactivity_english_checkbox);
    CheckBox maths_cb = view.findViewById(R.id.searchactivity_maths_checkbox);
    CheckBox physics_cb = view.findViewById(R.id.searchactivity_physics_checkbox);
    CheckBox chemistry_cb = view.findViewById(R.id.searchactivity_chemistry_checkbox);
    CheckBox biology_cb = view.findViewById(R.id.searchactivity_biology_checkbox);

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
        try {
            UserModel clientInfo = new JSONtoInfo(mActivity.getBaseContext()).createNewItem(new JSONObject(mActivity.getIntent().getExtras().getString("clientInfo")));
            SearchRequest search_request = new SearchRequest(clientInfo.getId(), name /*,School*/, map, new OnSearchResponseListener(mFragment));
            RequestQueue request_queue = Volley.newRequestQueue(mActivity); //Request Queue
            request_queue.add(search_request);
        } catch (JSONException e) { e.printStackTrace(); }
    }
}

