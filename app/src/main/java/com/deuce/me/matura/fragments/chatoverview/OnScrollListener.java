package com.deuce.me.matura.fragments.chatoverview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.util.ProfilePictureLoader;

/**
 * Created by ingli on 16.08.2018.
 */

public class OnScrollListener extends RecyclerView.OnScrollListener {

    private ChatoverviewFragment mFragment;
    private MainActivity mActivity;
    private LinearLayoutManager mLayoutManager;

    public OnScrollListener(ChatoverviewFragment mFragment, LinearLayoutManager mLayoutManager) {
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
            OpenChatViewHolder holder = (OpenChatViewHolder) recyclerView.findViewHolderForAdapterPosition(firstVisibleItemPosition + loop);
            if(holder.getUserModel().getTempProfilePicturePath() == null && mFragment.getReadyState()) {
                if(totalItemCount - firstVisibleItemPosition - 1 >= mFragment.HEAPSIZE) {
                    int amount = mFragment.HEAPSIZE;
                    int start = firstVisibleItemPosition + loop;
                    //new ProfilePictureLoader(mFragment).load(start, amount, mActivity.getSearchResultDataset(), new OnChatProfilePicturesListener(mFragment));
                    break;
                } else {
                    int amount = totalItemCount - firstVisibleItemPosition - loop;
                    int start = firstVisibleItemPosition + loop;
                    //new ProfilePictureLoader(mFragment).load(start, amount, mActivity.getSearchResultDataset(), new OnChatProfilePicturesListener(mFragment));
                    break;
                }
            }
        }
    }
}
