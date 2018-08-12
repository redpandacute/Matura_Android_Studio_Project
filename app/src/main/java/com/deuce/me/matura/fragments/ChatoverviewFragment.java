package com.deuce.me.matura.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deuce.me.matura.R;
import com.deuce.me.matura.activities.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatoverviewFragment extends Fragment {


    public ChatoverviewFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chatoverview_fragment, container, false);
    }

}
