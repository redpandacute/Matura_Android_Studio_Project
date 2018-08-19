package com.deuce.me.matura.fragments.searchresults;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.util.ProfilePictureLoader;

/**
 * Created by ingli on 12.08.2018.
 */

class OnScrollResultsListener extends RecyclerView.OnScrollListener {

    private SearchresultsFragment mFragment;
    private MainActivity mActivity;
    private LinearLayoutManager mLayoutManager;

    public OnScrollResultsListener(LinearLayoutManager mLayoutManager, SearchresultsFragment mFragment) {
        this.mLayoutManager = mLayoutManager;
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int totalItemCount = mLayoutManager.getItemCount();

        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
        int childCount = mLayoutManager.getChildCount();

        for(int loop = 0; loop < childCount; loop++) {
            ResultViewHolder holder = (ResultViewHolder) recyclerView.findViewHolderForAdapterPosition(firstVisibleItemPosition + loop);
            if(holder.getModel().getTempProfilePicturePath() == null && mFragment.getReadyState()) {
                if(totalItemCount - firstVisibleItemPosition - 1 >= mFragment.getHeapsize()) {
                    int amount = mFragment.getHeapsize();
                    int start = firstVisibleItemPosition + loop;
                    new ProfilePictureLoader(mFragment).load(start, amount, mActivity.getSearchResultDataset(), new OnProfilePicturesResponseListener(mFragment));
                    break;
                } else {
                    int amount = totalItemCount - firstVisibleItemPosition - loop;
                    int start = firstVisibleItemPosition + loop;
                    new ProfilePictureLoader(mFragment).load(start, amount, mActivity.getSearchResultDataset(), new OnProfilePicturesResponseListener(mFragment));
                    break;
                }
            }
        }
            // Load more if we have reach the end to the recyclerView
            //if (recyclerView.getAdapter().getItemViewType(2) && firstVisibleItemPosition >= 0) {
            //loadMoreItems();
            //}
    }
}

