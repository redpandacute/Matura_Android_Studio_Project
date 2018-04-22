package com.deuce.me.matura;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ingli on 22.04.2018.
 */

public class onResponseListener implements Response.Listener<String> {

    private Context mContext;

    public onResponseListener(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onResponse(String response) {

        try {
            System.out.println("Received Response: " + response);
            JSONObject json_response = new JSONObject(response);
            boolean success_user = json_response.getBoolean("success_user");
            boolean success_subj = json_response.getBoolean("success_subjects");

            System.out.println("Success User: " + success_user);
            System.out.println("Success Subjects: " + success_subj);

            if (success_user && success_subj) {
                Intent save_intent = new Intent(mContext, settingsOverview.class);
                save_intent.putExtra("clientInfo", response);
                mContext.startActivity(save_intent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
