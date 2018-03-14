package com.deuce.me.matura;

import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class mainpage_activity extends AppCompatActivity {

    private BottomSheetBehavior mBottomSheetBehavior;
    private userInfo clientInfo;
    private Bundle extrasBundle;
    private DatabaseReference databaseReference;
    private RecyclerView recView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage_activity);

        extrasBundle = getIntent().getExtras();

        try {

            clientInfo = new JSONtoInfo().createNewItem(new JSONObject(extrasBundle.getString("clientInfo")));
            databaseReference = FirebaseDatabase.getInstance().getReference().child(String.format("Users/%d", clientInfo.getId()));

            final TextView nameandfirstname_tv = findViewById(R.id.userprofileact_name_textview);
            final TextView description_tv = findViewById(R.id.userprofileact_description_textview);
            final TextView school_tv = findViewById(R.id.userprofileact_school_textview);
            final TextView userid_tv = findViewById(R.id.userprofileact_userid_textview);

            final ImageButton settings_bt = findViewById(R.id.mainpageact_settingsbutton_imagebutton);
            final FloatingActionButton search_bt = findViewById(R.id.mainpageact_searchbutton_floatingactionbutton);

            nameandfirstname_tv.setText(clientInfo.getFirstname() + " " + clientInfo.getName());
            description_tv.setText(clientInfo.getDescription());
            school_tv.setText(clientInfo.getSchool());
            userid_tv.setText(clientInfo.getId() + "");


//SubjectMedals
// -------------------------------------------------------------------------------------------------
            ImageView math_medal = findViewById(R.id.mainpageact_math_imageview);
            if (!clientInfo.isMaths()) {
                math_medal.setVisibility(View.GONE);
            }

            ImageView spanish_medal = findViewById(R.id.mainpageact_spanish_imageview);
            if (!clientInfo.isSpanish()) {
                spanish_medal.setVisibility(View.GONE);
            }

            ImageView physics_medal = findViewById(R.id.mainpageact_physics_imageview);
            if (!clientInfo.isPhysics()) {
                physics_medal.setVisibility(View.GONE);
            }

            ImageView german_medal = findViewById(R.id.mainpageact_german_imageview);
            if (!clientInfo.isGerman()) {
                german_medal.setVisibility(View.GONE);
            }

            ImageView biology_medal = findViewById(R.id.mainpageact_biology_imageview);
            if (!clientInfo.isBiology()) {
                biology_medal.setVisibility(View.GONE);
            }

            ImageView chemistry_medal = findViewById(R.id.mainpageact_chemistry_imageview);
            if (!clientInfo.isChemistry()) {
                chemistry_medal.setVisibility(View.GONE);
            }

            ImageView music_medal = findViewById(R.id.mainpageact_music_imageview);
            if (!clientInfo.isMusic()) {
                music_medal.setVisibility(View.GONE);
            }

            ImageView french_medal = findViewById(R.id.mainpageact_french_imageview);
            if (!clientInfo.isFrench()) {
                french_medal.setVisibility(View.GONE);
            }

            ImageView english_medal = findViewById(R.id.mainpageact_english_imageview);
            if (!clientInfo.isEnglish()) {
                english_medal.setVisibility(View.GONE);
            }


//BottomSheet
// -------------------------------------------------------------------------------------------------
            View bottomsheet = findViewById(R.id.mainpageact_bottomsheet_nestedscrollview);
            mBottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);

            FloatingActionButton expandbut = findViewById(R.id.mainpageact_notificationsbutton_floatingactionbutton);
            mBottomSheetBehavior.setPeekHeight((getWindowManager().getDefaultDisplay().getHeight()) / 20);  //Setting height for BottomSheet

            recView = findViewById(R.id.mainpageact_bottomsheet_recyclerview);

            //Expanding or Collapsing BottomSheet
            expandbut.setOnClickListener(new onExpandListener());

            //Actionlistener for settings Button
            settings_bt.setOnClickListener(new onSettingsListener());

            //Actionlistener Searchbutton
            search_bt.setOnClickListener(new onSearchListener());

        } catch (JSONException e) { e.printStackTrace(); }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter FCBA = new BottomsheetFirebaseAdapter(
                OpenChat.class,
                R.layout.openchat,
                OpenChatViewHolder.class,
                databaseReference,
                mainpage_activity.this,
                String.format("%s %s",clientInfo.getFirstname(), clientInfo.getName())
        );
        recView.setAdapter(FCBA);
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
            settings_intent.putExtra("clientInfo", extrasBundle.getString("clientInfo"));

            startActivity(settings_intent);
        }
    }

    class onSearchListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent search_intent = new Intent(mainpage_activity.this, search_activity.class);
            search_intent.putExtra("clientInfo", extrasBundle.getString("clientInfo"));
            startActivity(search_intent);
        }
    }
}

