package com.deuce.me.matura.util;

import android.app.Activity;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.deuce.me.matura.activities.RegisterActivity;
import com.deuce.me.matura.requests.BcryptRegisterRequest;

import java.util.List;

/**
 * Created by ingli on 07.10.2018.
 */

public class AccountGenerator {

    private final Context mContext;

    public AccountGenerator(Context mContext) {
        this.mContext = mContext;
    }

    public void generate() {
        MyReader reader = new MyReader(mContext);
        List<String> mLines = reader.read("accounts.txt");



        for(int n = 0; n < mLines.size(); n++) {
            String[] tempData = mLines.get(n).split(":");

            System.out.println(tempData[0] + " " + tempData[1] + " " + tempData[2] + " "+ tempData[3] + " "+ tempData[4] + " "+ tempData[5] + " " + tempData[6]);
            BcryptRegisterRequest reg_request = new BcryptRegisterRequest(tempData[0], tempData[1], tempData[2], tempData[3], Integer.parseInt(tempData[4]), tempData[5], tempData[6], new OnResponseListener());
            RequestQueue request_queue = Volley.newRequestQueue(mContext); //Makeing a Requestqueue
            request_queue.add(reg_request);
        }

    }

    private class OnResponseListener implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {
            System.out.print("Response: " + response);
        }
    }
}
