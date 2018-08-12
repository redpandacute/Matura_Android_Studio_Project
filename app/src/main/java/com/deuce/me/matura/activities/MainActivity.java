package com.deuce.me.matura.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.deuce.me.matura.R;
import com.deuce.me.matura.fragments.ChatoverviewFragment;
import com.deuce.me.matura.fragments.openchat.OpenchatFragment;
import com.deuce.me.matura.fragments.openprofile.OpenprofileFragment;
import com.deuce.me.matura.fragments.searchresults.SearchresultsFragment;
import com.deuce.me.matura.fragments.mainprofile.MainprofileFragment;
import com.deuce.me.matura.fragments.searchoverview.SearchoverviewFragment;
import com.deuce.me.matura.models.ChatModel;
import com.deuce.me.matura.models.ProfilePictureModel;
import com.deuce.me.matura.models.UserModel;
import com.deuce.me.matura.util.JSONtoInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private SearchoverviewFragment searchoverviewFragment;
    private MainprofileFragment mainprofileFragment;
    private ChatoverviewFragment chatoverviewFragment;
    private SearchresultsFragment searchresultsFragment;
    private OpenprofileFragment openprofileFragment;
    private OpenchatFragment openchatFragment;

    private Bundle extras;

    private UserModel mainprofileModel, openprofileModel;
    private ProfilePictureModel mainprofilePicture, openprofilePicture;
    private ChatModel openChat;
    private UserModel[] searchResultDataset;

    private DatabaseReference chatoverviewDBReference;

    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.searchoverview_botnav:
                    setFragment(searchoverviewFragment);
                    return true;

                case R.id.chatoverview_botnav:
                    setFragment(chatoverviewFragment);
                    return true;

                case R.id.mainprofile_botnav:
                    setFragment(mainprofileFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //initialize all needed Objects
        extras = getIntent().getExtras();
        mainprofileModel = null;
        openprofileModel = null;

        chatoverviewDBReference = FirebaseDatabase.getInstance().getReference().child(String.format("Users/%d", mainprofileModel.getId()));
        openChat = null;

        mainprofilePicture = new ProfilePictureModel(getBaseContext(), new File(extras.getString("temp_profilepicture_path")));
        openprofilePicture = null;

        try {
            mainprofileModel = new JSONtoInfo(getBaseContext()).createNewItem(new JSONObject(extras.getString("clientInfo")));
        } catch (JSONException e) {
            e.printStackTrace();
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
        }

        //Initialize all fragments
        mainprofileFragment = new MainprofileFragment();
        searchoverviewFragment = new SearchoverviewFragment();
        chatoverviewFragment = new ChatoverviewFragment();

        searchresultsFragment = null;
        openprofileFragment = null;
        openchatFragment = null;

        setFragment(mainprofileFragment);
    }

    public void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    public SearchoverviewFragment getSearchoverviewFragment() {
        return searchoverviewFragment;
    }

    public void setSerchoverviewFragment(SearchoverviewFragment searchoverviewFragment) {
        this.searchoverviewFragment = searchoverviewFragment;
    }

    public void setSearchoverviewFragment(SearchoverviewFragment searchoverviewFragment) {
        this.searchoverviewFragment = searchoverviewFragment;
    }

    public MainprofileFragment getMainprofileFragment() {
        return mainprofileFragment;
    }

    public void setMainprofileFragment(MainprofileFragment mainprofileFragment) {
        this.mainprofileFragment = mainprofileFragment;
    }

    public ChatoverviewFragment getChatoverviewFragment() {
        return chatoverviewFragment;
    }

    public void setChatoverviewFragment(ChatoverviewFragment chatoverviewFragment) {
        this.chatoverviewFragment = chatoverviewFragment;
    }

    public SearchresultsFragment getSearchresultsFragment() {
        return searchresultsFragment;
    }

    public void setSearchresultsFragment(SearchresultsFragment searchresultsFragment) {
        this.searchresultsFragment = searchresultsFragment;
    }

    public Bundle getExtras() {
        return extras;
    }

    public void setExtras(Bundle extras) {
        this.extras = extras;
    }

    public UserModel getMainprofileModel() {
        return mainprofileModel;
    }

    public void setMainprofileModel(UserModel mainprofileModel) {
        this.mainprofileModel = mainprofileModel;
    }

    public UserModel getOpenprofileModel() {
        return openprofileModel;
    }

    public void setOpenprofileModel(UserModel openprofileModel) {
        this.openprofileModel = openprofileModel;
    }

    public ProfilePictureModel getMainprofilePicture() {
        return mainprofilePicture;
    }

    public void setMainprofilePicture(ProfilePictureModel mainprofilePicture) {
        this.mainprofilePicture = mainprofilePicture;
    }

    public ProfilePictureModel getOpenprofilePicture() {
        return openprofilePicture;
    }

    public void setOpenprofilePicture(ProfilePictureModel openprofilePicture) {
        this.openprofilePicture = openprofilePicture;
    }

    public DatabaseReference getChatoverviewDBReference() {
        return chatoverviewDBReference;
    }

    public void setChatoverviewDBReference(DatabaseReference chatoverviewDBReference) {
        this.chatoverviewDBReference = chatoverviewDBReference;
    }

    public UserModel[] getSearchResultDataset() {
        return this.searchResultDataset;
    }

    public void setSearchResultDataset(UserModel[] searchResultDataset) {
        this.searchResultDataset = searchResultDataset;
    }

    public OpenprofileFragment getOpenprofileFragment() {
        return this.openprofileFragment;
    }

    public void setOpenprofileFragment(OpenprofileFragment openprofileFragment) {
        this.openprofileFragment = openprofileFragment;
    }

    public ChatModel getOpenChat() {
        return openChat;
    }

    public void setOpenChat(ChatModel openChat) {
        this.openChat = openChat;
    }

    public OpenchatFragment getOpenchatFragment() {
        return openchatFragment;
    }

    public void setOpenchatFragment(OpenchatFragment openchatFragment) {
        this.openchatFragment = openchatFragment;
    }

    public BottomNavigationView getNavigation() {
        return navigation;
    }


}
