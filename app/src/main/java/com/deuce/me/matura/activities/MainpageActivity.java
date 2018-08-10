package com.deuce.me.matura.activities;

import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deuce.me.matura.adapter.BottomsheetFirebaseAdapter;
import com.deuce.me.matura.util.JSONtoInfo;
import com.deuce.me.matura.models.OpenChatModel;
import com.deuce.me.matura.R;
import com.deuce.me.matura.models.ProfilePictureModel;
import com.deuce.me.matura.models.UserModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class MainpageActivity extends AppCompatActivity {

    private BottomSheetBehavior mBottomSheetBehavior;
    private UserModel clientInfo;
    private Bundle extrasBundle;
    private DatabaseReference databaseReference;
    private RecyclerView recView;
    private ProfilePictureModel picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage_activity);

        Toolbar toolbar = findViewById(R.id.mainpage_toolbar);
        toolbar.setTitle(R.string.mainpage_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        extrasBundle = getIntent().getExtras();

        try {

            clientInfo = new JSONtoInfo(getBaseContext()).createNewItem(new JSONObject(extrasBundle.getString("clientInfo")));
            System.out.println(clientInfo.getPassword());
            databaseReference = FirebaseDatabase.getInstance().getReference().child(String.format("Users/%d", clientInfo.getId()));
            System.out.println(databaseReference);

            picture = new ProfilePictureModel(getBaseContext(), new File(clientInfo.getTempProfilePicturePath()));

            final TextView nameandfirstname_tv = findViewById(R.id.mainpageact_name_textview);
            final TextView description_tv = findViewById(R.id.mainpageact_description_textview);
            final TextView school_tv = findViewById(R.id.mainpageact_school_textview);

            final FloatingActionButton search_bt = findViewById(R.id.mainpageact_searchbutton_floatingactionbutton);

            nameandfirstname_tv.setText(clientInfo.getFirstname() + " " + clientInfo.getName());
            description_tv.setText(clientInfo.getDescription());
            school_tv.setText(clientInfo.getSchool());

            System.out.println(clientInfo.getTempProfilePicturePath());

            final ImageView profilepicture_iv = findViewById(R.id.mainpageact_profilepicture_imageview);
            profilepicture_iv.setImageBitmap(picture.getImageBitmap());


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

            //Configuring RecView
            recView = findViewById(R.id.mainpageact_bottomsheet_recyclerview);
            LinearLayoutManager mManager = new LinearLayoutManager(this);
            recView.setLayoutManager(mManager);


            //Expanding or Collapsing BottomSheet
            expandbut.setOnClickListener(new onExpandListener());

            //Actionlistener Searchbutton
            search_bt.setOnClickListener(new onSearchListener());

        } catch (JSONException e) { e.printStackTrace(); }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainpage_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.settings_menu_button) {
            startSettingsActivity();
        } else if(item.getItemId() == R.id.logout_menu_button) {
            //LOGOUT
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter FCBA = new BottomsheetFirebaseAdapter(
                OpenChatModel.class,
                R.layout.openchat,
                OpenChatViewHolder.class,
                databaseReference,
                MainpageActivity.this,
                String.format("%s %s",clientInfo.getFirstname(), clientInfo.getName()),
                extrasBundle.getString("clientInfo")

        );
        recView.setAdapter(FCBA);
    }

    private void startSettingsActivity() {
        Intent settings_intent = new Intent(MainpageActivity.this, SettingsoverviewActivity.class);
        settings_intent.putExtra("clientInfo", extrasBundle.getString("clientInfo"));
        startActivity(settings_intent);
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

    class onSearchListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent search_intent = new Intent(MainpageActivity.this, SearchfilterActivity.class);
            search_intent.putExtra("clientInfo", extrasBundle.getString("clientInfo"));
            startActivity(search_intent);
        }
    }

    public static class OpenChatViewHolder extends RecyclerView.ViewHolder {

        //https://android.jlelse.eu/click-listener-for-recyclerview-adapter-2d17a6f6f6c9

        public View view;
        private int receiverID;
        private ProfilePictureModel picture;

        public OpenChatViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setChat(String receiverName, String latestMessage) {
            TextView cName_tv = view.findViewById(R.id.openchat_name_textview);
            cName_tv.setText(receiverName);
            TextView cLatest_tv = view.findViewById(R.id.openchat_latest_textview);
            cLatest_tv.setText(latestMessage);
        }

        public void setProfilePicture(ProfilePictureModel picture) {
            ImageView profilePicture_iv = view.findViewById(R.id.openchat_profilepicture_imageView);
            profilePicture_iv.setImageBitmap(picture.getImageBitmap());
        }

        public void setReceiverID(int receiverID) {
            this.receiverID = receiverID;
        }

        public int getReceiverID(){
            return receiverID;
        }
    }
}


