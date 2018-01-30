package com.deuce.me.matura;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class userprofile_activity extends AppCompatActivity {


    boolean math = true, spanish = false, biology = false, chemics = true, physics = true, german = true; //Boolean for subjects :: Values are for testing prpses

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile_activity);

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

        ImageView chemics_medal = findViewById(R.id.mainpageact_chemistry_imageview);
        if(chemics == false) {
            chemics_medal.setVisibility(View.GONE);
        }

//HomeButton
// -------------------------------------------------------------------------------------------------
        Button home_button = (Button) findViewById(R.id.userprofileact_homebutton_floatingactionbutton);

        home_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent home_intent = new Intent(userprofile_activity.this, mainpage_activity.class);
                startActivity(home_intent);
            }
        });




    }
}
