package com.deuce.me.matura.fragments.searchresults;

import com.android.volley.Response;
import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.util.tempFileGenerator;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ingli on 13.08.2018.
 */

class OnProfilePicturesResponseListener implements Response.Listener<String> {

    private MainActivity mActivity;
    private SearchresultsFragment mFragment;
    private int start, amount;

    public OnProfilePicturesResponseListener(SearchresultsFragment mFragment) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
    }

    public void setRange(int start, int amount) {
        this.start = start;
        this.amount = amount;
    }

    @Override
    public void onResponse(String response) {
        try {
            System.out.println(response);
            JSONObject jsn = new JSONObject(response);
            if(jsn.getBoolean("success")) {
                JSONArray data = jsn.getJSONArray("data");
                String[] paths = new String[data.length()];
                tempFileGenerator gen = new tempFileGenerator();
                for(int n = 0; n < data.length(); n++) {
                    paths[n] = gen.getTempFilePath(mActivity.getBaseContext(), data.getJSONObject(n).getString("blob_profilepicture_small"));
                }
                mActivity.refreshDataset(start, amount, paths);
                mFragment.refreshList();
                mFragment.setReadyToLoad(true);
            } else {
                System.out.println("::ERROR WITH REQUESTING IDS:: ");
                System.out.println(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
