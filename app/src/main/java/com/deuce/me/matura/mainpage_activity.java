package com.deuce.me.matura;

import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class mainpage_activity extends AppCompatActivity {

    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage_activity);


        final TextView nameandfirstname_tv = findViewById(R.id.userprofileact_name_textview);
        final TextView description_tv = findViewById(R.id.userprofileact_description_textview);
        final TextView school_tv = findViewById(R.id.userprofileact_school_textview);
        final TextView userid_tv = findViewById(R.id.userprofileact_userid_textview);

        final ImageButton settings_bt = findViewById(R.id.mainpageact_settingsbutton_imagebutton);
        final FloatingActionButton search_bt = findViewById(R.id.mainpageact_searchbutton_floatingactionbutton);

        Map<String, String> params = getInfo();
        Map<String, Boolean> subjs = getSubj();

        nameandfirstname_tv.setText(params.get("user_name") + " " + params.get("user_firstname"));
        description_tv.setText(params.get("user_description"));
        school_tv.setText(params.get("user_school"));
        userid_tv.setText(params.get("user_id"));


//SubjectMedals
// -------------------------------------------------------------------------------------------------
        ImageView math_medal = findViewById(R.id.mainpageact_math_imageview);
        if(!subjs.get("subj_maths")) { math_medal.setVisibility(View.GONE); }

        ImageView spanish_medal = findViewById(R.id.mainpageact_spanish_imageview);
        if(!subjs.get("subj_spanish")) { spanish_medal.setVisibility(View.GONE); }

        ImageView physics_medal = findViewById(R.id.mainpageact_physics_imageview);
        if(!subjs.get("subj_physics")) { physics_medal.setVisibility(View.GONE); }

        ImageView german_medal = findViewById(R.id.mainpageact_german_imageview);
        if(!subjs.get("subj_german")) { german_medal.setVisibility(View.GONE); }

        ImageView biology_medal = findViewById(R.id.mainpageact_biology_imageview);
        if(!subjs.get("subj_biology")) { biology_medal.setVisibility(View.GONE); }

        ImageView chemistry_medal = findViewById(R.id.mainpageact_chemistry_imageview);
        if(!subjs.get("subj_chemistry")) { chemistry_medal.setVisibility(View.GONE); }

        ImageView music_medal = findViewById(R.id.mainpageact_music_imageview);
        if(!subjs.get("subj_music")) { music_medal.setVisibility(View.GONE); }

        ImageView french_medal = findViewById(R.id.mainpageact_french_imageview);
        if(!subjs.get("subj_french")) { french_medal.setVisibility(View.GONE); }

        ImageView english_medal = findViewById(R.id.mainpageact_english_imageview);
        if(!subjs.get("subj_english")) { english_medal.setVisibility(View.GONE); }



//BottomSheet
// -------------------------------------------------------------------------------------------------
        View bottomsheet = findViewById(R.id.mainpageact_bottomsheet_nestedscrollview);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);

        FloatingActionButton expandbut = findViewById(R.id.mainpageact_notificationsbutton_floatingactionbutton);
        mBottomSheetBehavior.setPeekHeight((getWindowManager().getDefaultDisplay().getHeight())/20);  //Setting height for BottomSheet

        //Expanding or Collapsing BottomSheet
        expandbut.setOnClickListener(new onExpandListener());

        //Actionlistener for settings Button
        settings_bt.setOnClickListener(new onSettingsListener());

        //Actionlistener Searchbutton
        search_bt.setOnClickListener(new onSearchListener());
    }

    class onExpandListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            if(mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }

        }
    }

    class onSettingsListener implements View.OnClickListener  {

        @Override
        public void onClick(View view) {
            Intent settings_intent = new Intent(mainpage_activity.this, settings_activity.class);

            Map<String, String> params = getInfo();
            Map<String, Boolean> subjs = getSubj();

            settings_intent.putExtra("user_id", params.get("user_id") + "");
            settings_intent.putExtra("user_username", params.get("user_username"));
            settings_intent.putExtra("user_name", params.get("user_name"));
            settings_intent.putExtra("user_firstname", params.get("user_firstname"));
            settings_intent.putExtra("user_school", params.get("user_school"));
            settings_intent.putExtra("user_email", params.get("user_email"));
            settings_intent.putExtra("user_description", params.get("user_description"));

            settings_intent.putExtra("subj_german", subjs.get("subj_german"));
            settings_intent.putExtra("subj_spanish", subjs.get("subj_spanish"));
            settings_intent.putExtra("subj_french", subjs.get("subj_french"));
            settings_intent.putExtra("subj_english", subjs.get("subj_english"));
            settings_intent.putExtra("subj_maths", subjs.get("subj_maths"));
            settings_intent.putExtra("subj_physics", subjs.get("subj_physics"));
            settings_intent.putExtra("subj_chemistry", subjs.get("subj_chemistry"));
            settings_intent.putExtra("subj_biology", subjs.get("subj_biology"));
            settings_intent.putExtra("subj_music", subjs.get("subj_music"));

            startActivity(settings_intent);
        }
    }

    class onSearchListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent search_intent = new Intent(mainpage_activity.this, search_activity.class);
            startActivity(search_intent);
        }
    }

    private Map<String, String> getInfo() {

        Bundle extras_bundle = getIntent().getExtras();
        Map<String, String> Map = new HashMap<>();

        if (!extras_bundle.get("user_name").toString().isEmpty()) {
            Map.put("user_id", extras_bundle.get("user_id") + "");
            Map.put("user_username", extras_bundle.getString("user_username"));
            Map.put("user_name", extras_bundle.getString("user_name"));
            Map.put("user_firstname", extras_bundle.getString("user_firstname"));
            Map.put("user_school", extras_bundle.getString("user_school"));
            Map.put("user_yearofbirth", extras_bundle.get("user_yearofbirth") + "");
            Map.put("user_description", extras_bundle.getString("user_description"));
            Map.put("user_email", extras_bundle.getString("user_email"));

        } else {

            //TODO: There was an issue, try again (Get from database)

            Map.put("user_id", "0");
            Map.put("user_username", "");
            Map.put("user_name", "");
            Map.put("user_surname", "");
            Map.put("user_school", "");
            Map.put("user_yearofbirth", "0");
            Map.put("user_description", "");
            Map.put("user_email", "");

        }

        return Map;
    }

    private Map<String, Boolean> getSubj() {

        Bundle extras_bundle = getIntent().getExtras();
        Map<String, Boolean> Map = new HashMap<>();

        if (!extras_bundle.get("user_name").toString().isEmpty()) {

            Map.put("subj_german", extras_bundle.getBoolean("subj_german"));
            Map.put("subj_english", extras_bundle.getBoolean("subj_english"));
            Map.put("subj_spanish", extras_bundle.getBoolean("subj_spanish"));
            Map.put("subj_music", extras_bundle.getBoolean("subj_music"));
            Map.put("subj_chemistry", extras_bundle.getBoolean("subj_chemistry"));
            Map.put("subj_physics", extras_bundle.getBoolean("subj_physics"));
            Map.put("subj_maths", extras_bundle.getBoolean("subj_maths"));
            Map.put("subj_biology", extras_bundle.getBoolean("subj_biology"));
            Map.put("subj_french", extras_bundle.getBoolean("subj_french"));


        } else {
            //TODO: There was an issue, try again (Get from database)
        }

        return Map;
    }
}
