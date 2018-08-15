package com.deuce.me.matura.fragments.openprofile;

import android.content.Intent;

import com.android.volley.Response;
import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.activities.MainpageActivity;
import com.deuce.me.matura.activities.UserprofileActivity;
import com.deuce.me.matura.models.ProfilePictureModel;
import com.deuce.me.matura.util.JSONtoInfo;
import com.deuce.me.matura.util.tempFileGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by ingli on 12.08.2018.
 */

class OnBigPictureResponseListener implements Response.Listener<String> {

    private OpenprofileFragment mFragment;
    private MainActivity mActivity;

    public OnBigPictureResponseListener(OpenprofileFragment mFragment) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
    }

    @Override
    public void onResponse(String response) {

        try {

            JSONObject jsn = new JSONObject(response);
            boolean success = jsn.getBoolean("success");
            System.out.println(success);

            if (success) {
                String tempPath = new tempFileGenerator().getTempFilePath(mActivity.getBaseContext(), jsn.getString("blob_profilepicture_big"));
                mFragment.validate(tempPath);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
