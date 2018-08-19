package com.deuce.me.matura.fragments.chatoverview;

import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.interfaces.ProfilePicturesOnResponseListener;
import com.deuce.me.matura.models.UserModel;
import com.deuce.me.matura.util.tempFileGenerator;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ingli on 16.08.2018.
 */

public class OnChatProfilePicturesListener implements ProfilePicturesOnResponseListener {

    private UserModel[] mDataset;
    private int start, amount;
    private ChatoverviewFragment mFragment;
    private MainActivity mActivity;

    public OnChatProfilePicturesListener(ChatoverviewFragment mFragment, UserModel[] mDataset) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
        this.mDataset = mDataset;
    }

    @Override
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
                tempFileGenerator gen = new tempFileGenerator();
                for(int n = 0; n < data.length(); n++) {

                    mFragment.getPaths().put(
                            mDataset[n].getId(),
                            gen.getTempFilePath(mActivity.getBaseContext(), data.getJSONObject(n).getString("blob_profilepicture_small"))
                    );

                    mFragment.getDataset().get(mDataset[n].getId()).getUserModel().setTempProfilePicturePath(mFragment.getPaths().get(mDataset[n].getId()));
                }

                mFragment.getRecyclerView().getAdapter().notifyDataSetChanged();
                mFragment.setReadyState(true);
            } else {
                System.out.println("::ERROR WITH REQUESTING IDS:: ");
                System.out.println(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
