package com.deuce.me.matura.fragments.searchresults;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.deuce.me.matura.R;
import com.deuce.me.matura.activities.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchresultsFragment extends Fragment {

    private MainActivity mActivity;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private SearchResultsAdapter recAdapter;
    private static final int heapsize = 20;

    public SearchresultsFragment() {
        // Required empty public constructor
        this.mActivity = (MainActivity) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_searchresults_fragment, container, false);

        Toolbar toolbar = view.findViewById(R.id.searchresult_toolbar);
        toolbar.setTitle(R.string.searchresults_title);
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = view.findViewById(R.id.searchresult_fragment_reclistview);

        layoutManager = new LinearLayoutManager(mActivity.getBaseContext());
        recyclerView.setLayoutManager(layoutManager);
        recAdapter = new SearchResultsAdapter(mActivity.getSearchResultDataset(), this);
        recyclerView.setAdapter(recAdapter);
        recyclerView.addOnScrollListener(new OnScrollResultsListener());

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                mActivity.setFragment(mActivity.getSearchoverviewFragment());
                mActivity.setSearchResultDataset(null);
                mActivity.setSearchresultsFragment(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
