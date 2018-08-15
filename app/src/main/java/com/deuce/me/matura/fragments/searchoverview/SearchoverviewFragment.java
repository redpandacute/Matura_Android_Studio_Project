package com.deuce.me.matura.fragments.searchoverview;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.deuce.me.matura.R;
import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.activities.MainpageActivity;
import com.deuce.me.matura.activities.SearchfilterActivity;
import com.deuce.me.matura.models.ProfilePictureModel;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchoverviewFragment extends Fragment {

    private MainActivity mActivity;

    public SearchoverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_searchoverview_fragment, container, false);

        Toolbar toolbar = view.findViewById(R.id.searchoverview_toolbar);
        toolbar.setTitle(R.string.search_title);
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ImageButton searchbutton = view.findViewById(R.id.searchoverview_searchbutton_imagebutton);

        searchbutton.setOnClickListener(new OnSearchListener(view, this));

        setHasOptionsMenu(true);

        return view;
    }
}
