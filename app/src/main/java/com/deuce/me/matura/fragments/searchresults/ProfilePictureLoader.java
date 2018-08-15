package com.deuce.me.matura.fragments.searchresults;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.models.UserModel;
import com.deuce.me.matura.requests.SmallProfilePicturesRequest;

/**
 * Created by ingli on 13.08.2018.
 */

class ProfilePictureLoader {

    private SearchresultsFragment mFragment;
    private MainActivity mActivity;

    public ProfilePictureLoader(SearchresultsFragment mFragment) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
    }


    public void load(int start, int amount, OnProfilePicturesResponseListener mResponseListener) {
        int[] ids = new int[amount];
        mFragment.setReadyToLoad(false);
        mResponseListener.setRange(start, amount);

        UserModel[] mDataset = mActivity.getSearchResultDataset();

            for(int n = start; n < amount; n++) {
                ids[n] = mDataset[n].getId();
            }

            SmallProfilePicturesRequest request = new SmallProfilePicturesRequest(ids, mResponseListener);
            RequestQueue queue = Volley.newRequestQueue(mActivity);
            queue.add(request);

    }
}
