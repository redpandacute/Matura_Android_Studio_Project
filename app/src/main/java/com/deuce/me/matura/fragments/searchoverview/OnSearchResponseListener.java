package com.deuce.me.matura.fragments.searchoverview;

import android.widget.Toast;

import com.android.volley.Response;
import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.models.UserModel;
import com.deuce.me.matura.util.JSONtoInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ingli on 12.08.2018.
 */

class OnSearchResponseListener implements Response.Listener<String> {

    SearchoverviewFragment mFragment;
    MainActivity mActivity;

    public OnSearchResponseListener(SearchoverviewFragment mFragment) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity) mFragment.getActivity();
    }
    @Override
    public void onResponse(String response) {
        try {

            System.out.println("Received response: " + response);
            JSONObject json_resp = new JSONObject(response);
            boolean success = json_resp.getBoolean("success");

            if(success) {

                JSONArray rawDataset = json_resp.getJSONArray("results");
                UserModel[] userModels = new UserModel[rawDataset.length()];
                JSONtoInfo jsn = new JSONtoInfo(mActivity.getBaseContext());
                for(int n = 0; n < rawDataset.length(); n++) {
                    userModels[n] = jsn.createNewItem(rawDataset.getJSONObject(n));
                }
                mActivity.setSearchResultsDataset(userModels);
                mActivity.setSerchoverviewFragment(new SearchoverviewFragment());
                mActivity.setFragment(mActivity.getSearchoverviewFragment());
            } else {
                Toast.makeText(mActivity.getBaseContext(), "There was an issue with the search request (Maybe no results :3)", Toast.LENGTH_LONG);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

