package com.deuce.me.matura;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class userprofile_activity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile_activity);

        Bundle extras = getIntent().getExtras();

        try {

            result_item info = new result_iteminfo().createNewItem(new JSONObject(extras.getString("user_info")));

//Infos
// -------------------------------------------------------------------------------------------------

            TextView name_tv = findViewById(R.id.userprofileact_name_textview);
            name_tv.setText(info.getFirstname() + "" + info.getName());

            TextView school_tv = findViewById(R.id.userprofileact_school_textview);
            school_tv.setText(info.getYearofbirth());

            TextView desc_tv = findViewById(R.id.userprofileact_description_textview);
            desc_tv.setText(info.getDescription())

//SubjectMedals
// -------------------------------------------------------------------------------------------------
            ImageView math_medal = findViewById(R.id.userprofileact_math_imageview);
            if (!info.isMaths()) { math_medal.setVisibility(View.GONE); }

            ImageView spanish_medal = findViewById(R.id.userprofileact_spanish_imageview);
            if (!info.isSpanish()) { spanish_medal.setVisibility(View.GONE); }

            ImageView physics_medal = findViewById(R.id.userprofileact_physics_imageview);
            if (!info.isPhysics()) { physics_medal.setVisibility(View.GONE); }

            ImageView german_medal = findViewById(R.id.userprofileact_german_imageview);
            if (!info.isGerman()) { german_medal.setVisibility(View.GONE); }

            ImageView biology_medal = findViewById(R.id.userprofileact_biology_imageview);
            if (!info.isBiology()) { biology_medal.setVisibility(View.GONE); }

            ImageView chemistry_medal = findViewById(R.id.userprofileact_chemistry_imageview);
            if (!info.isChemistry()) { chemistry_medal.setVisibility(View.GONE); }

            ImageView music_medal = findViewById(R.id.userprofileact_music_imageview);
            if (!info.isMusic()) { music_medal.setVisibility(View.GONE); }

            ImageView english_medal = findViewById(R.id.userprofileact_english_imageview);
            if (!info.isEnglish()) { english_medal.setVisibility(View.GONE); }

            ImageView french_medal = findViewById(R.id.userprofileact_french_imageview);
            if (!info.isFrench()) { french_medal.setVisibility(View.GONE); }

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


        } catch (JSONException e) { e.printStackTrace(); }
    }
}
