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

public class mainpage_activity extends AppCompatActivity {

    private BottomSheetBehavior mBottomSheetBehavior;



    boolean math = true, spanish = false, biology = false, chemics = true, physics = true, german = true; //Boolean for subjects :: Values are for testing prpses

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage_activity);


        final TextView nameandsurname_tv = findViewById(R.id.userprofileact_name_textview);
        final TextView description_tv = findViewById(R.id.userprofileact_description_textview);
        final TextView school_tv = findViewById(R.id.userprofileact_school_textview);
        final TextView userid_tv = findViewById(R.id.userprofileact_userid_textview);

        final ImageButton settings_bt = findViewById(R.id.mainpageact_settingsbutton_imagebutton);


        //Retreiving Extras
        Bundle extras_bundle = getIntent().getExtras();

        final int user_id;
        final int user_yearofbirth;
        final String user_username;
        final String user_name;
        final String user_school;
        final String user_firstname;
        final String user_description;
        final String user_email;


        if(!extras_bundle.get("user_name").toString().isEmpty()) {
            user_id = (int) extras_bundle.get("user_id");
            user_yearofbirth = (int) extras_bundle.get("user_yearofbirth");

            user_username = (String) extras_bundle.get("user_username");
            user_name = (String) extras_bundle.get("user_name");
            user_firstname = (String) extras_bundle.get("user_firstname");
            user_school = (String) extras_bundle.get("user_school");
            user_description = (String) extras_bundle.get("user_description");
            user_email = (String) extras_bundle.get("user_email");
            nameandsurname_tv.setText(user_firstname + " " + user_name);
            school_tv.setText(user_school);
            description_tv.setText(user_description);
            userid_tv.setText("#" + user_id);

        } else {
            //TODO:
            //get data from online

            user_id = 0;
            user_yearofbirth = 0;
            user_username = "";
            user_name = "";
            user_school = "";
            user_firstname = "";
            user_description = "";
            user_email = "";

        }


//SubjectMedals
// -------------------------------------------------------------------------------------------------
        ImageView math_medal = findViewById(R.id.mainpageact_math_imageview);
        if(math == false) {
            math_medal.setVisibility(View.GONE);
        }

        ImageView spanish_medal = findViewById(R.id.mainpageact_spanish_imageview);
        if(spanish == false) {
            spanish_medal.setVisibility(View.GONE);
        }

        ImageView physics_medal = findViewById(R.id.mainpageact_physics_imageview);
        if(physics == false) {
            physics_medal.setVisibility(View.GONE);
        }

        ImageView german_medal = findViewById(R.id.mainpageact_german_imageview);
        if(german == false) {
            german_medal.setVisibility(View.GONE);
        }

        ImageView biology_medal = findViewById(R.id.mainpageact_biology_imageview);
        if(biology == false) {
            biology_medal.setVisibility(View.GONE);
        }

        ImageView chemics_medal = findViewById(R.id.mainpageact_chemics_imageview);
        if(chemics == false) {
            chemics_medal.setVisibility(View.GONE);
        }


//BottomSheet
// -------------------------------------------------------------------------------------------------
        View bottomsheet = findViewById(R.id.mainpageact_bottomsheet_nestedscrollview);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);

        FloatingActionButton expandbut = findViewById(R.id.mainpageact_notificationsbutton_floatingactionbutton);
        mBottomSheetBehavior.setPeekHeight((getWindowManager().getDefaultDisplay().getHeight())/20);  //Setting height for BottomSheet

        //Expanding or Collapsing BottomSheet

        expandbut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        //Actionlistener for settings Button
        settings_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent settings_intent = new Intent(mainpage_activity.this, settings_activity.class);
                settings_intent.putExtra("user_id", user_id);
                settings_intent.putExtra("user_username", user_username);
                settings_intent.putExtra("user_name", user_name);
                settings_intent.putExtra("user_firstname", user_firstname);
                settings_intent.putExtra("user_username", user_name);
                settings_intent.putExtra("user_school", user_school);
                settings_intent.putExtra("user_email", user_email);
                settings_intent.putExtra("user_description", user_description);

                settings_intent.putExtra("subj_german", true);
                settings_intent.putExtra("subj_spanish", true);
                settings_intent.putExtra("subj_french", true);
                settings_intent.putExtra("subj_english", true);
                settings_intent.putExtra("subj_maths", true);
                settings_intent.putExtra("subj_physics", true);
                settings_intent.putExtra("subj_chemics", true);
                settings_intent.putExtra("subj_biology", true);
                settings_intent.putExtra("subj_music", false);

                startActivity(settings_intent);

            }
        });


    }
}
