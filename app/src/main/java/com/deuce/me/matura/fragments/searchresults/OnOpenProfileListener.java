package com.deuce.me.matura.fragments.searchresults;

import android.view.View;

import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.fragments.openprofile.OpenprofileFragment;

/**
 * Created by ingli on 12.08.2018.
 */

class OnOpenProfileListener implements View.OnClickListener {

    private ResultViewHolder mHolder;
    private SearchresultsFragment mFragment;
    private MainActivity mActivity;

    public OnOpenProfileListener(SearchresultsFragment mFragment, ResultViewHolder mHolder) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
        this.mHolder = mHolder;
    }

    @Override
    public void onClick(View view) {
        mHolder.getModel().setTempProfilePicturePath("0");
        mActivity.setOpenprofileModel(mHolder.getModel());
        mActivity.setOpenprofileFragment(new OpenprofileFragment());
        mActivity.setFragment(mActivity.getOpenprofileFragment());
    }
}
