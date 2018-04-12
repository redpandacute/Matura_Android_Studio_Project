package com.deuce.me.matura;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class profileSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        getSupportActionBar().setTitle(R.string.profilesettings_title);
    }
}
