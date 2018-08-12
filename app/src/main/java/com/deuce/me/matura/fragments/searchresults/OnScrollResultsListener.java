package com.deuce.me.matura.fragments.searchresults;

import android.support.v7.widget.RecyclerView;

import com.deuce.me.matura.adapter.searchresult_recycleradapter;

/**
 * Created by ingli on 12.08.2018.
 */

class OnScrollResultsListener extends RecyclerView.OnScrollListener {

    public OnScrollResultsListener() {

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int totalItemCount = layoutManager.getItemCount();

        firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        childCount = layoutManager.getChildCount();

        for(loop = 0; loop < childCount; loop++) {
            searchresult_recycleradapter.ViewHolder holder = (searchresult_recycleradapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(firstVisibleItemPosition + loop);
            if(holder.getInfo().getTempProfilePicturePath() == null && !isLoading) {
                if(totalItemCount - firstVisibleItemPosition - 1 >= heapsize) {
                    int amount = heapsize;
                    int start = firstVisibleItemPosition + loop;
                    getProfilePictures(start, amount);
                    break;
                } else {
                    int amount = totalItemCount - firstVisibleItemPosition - loop;
                    int start = firstVisibleItemPosition + loop;
                    getProfilePictures(start, amount);
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

