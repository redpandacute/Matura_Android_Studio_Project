package com.deuce.me.matura;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class securitySettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_settings);
        getSupportActionBar().setTitle(R.string.securitysettings_title);
    }
}
