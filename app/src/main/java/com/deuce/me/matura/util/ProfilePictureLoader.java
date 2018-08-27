package com.deuce.me.matura.util;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.fragments.chatoverview.ChatoverviewFragment;
import com.deuce.me.matura.fragments.searchresults.OnProfilePicturesResponseListener;
import com.deuce.me.matura.fragments.searchresults.SearchresultsFragment;
import com.deuce.me.matura.interfaces.LoaderFragment;
import com.deuce.me.matura.interfaces.ProfilePicturesOnResponseListener;
import com.deuce.me.matura.models.UserModel;
import com.deuce.me.matura.requests.SmallProfilePicturesRequest;

/**
 * Created by ingli on 13.08.2018.
 */

public class ProfilePictureLoader {

    private LoaderFragment mFragment;
    private MainActivity mActivity;

    public ProfilePictureLoader(LoaderFragment mFragment) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
    }


    public void load(int start, int amount, UserModel[] mDataset, ProfilePicturesOnResponseListener mResponseListener) {
        int[] ids = new int[amount];
        mFragment.setReadyState(false);
        mResponseListener.setRange(start, amount);

        for(int n = start; n < amount; n++) {
            ids[n] = mDataset[n].getId();
            System.out.println(ids[n]);
        }

        SmallProfilePicturesRequest request = new SmallProfilePicturesRequest(ids, mResponseListener);
        RequestQueue queue = Volley.newRequestQueue(mActivity.getBaseContext());
        queue.add(request);
    }
}
