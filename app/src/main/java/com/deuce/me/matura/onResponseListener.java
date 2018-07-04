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

            boolean success = json_response.getBoolean("success");

            if (success) {
                Intent save_intent = new Intent(mContext, settingsOverview.class);
                save_intent.putExtra("clientInfo", response);
                mContext.startActivity(save_intent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
