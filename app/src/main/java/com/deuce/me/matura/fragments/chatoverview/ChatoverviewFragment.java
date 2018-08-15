package com.deuce.me.matura.fragments.chatoverview;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deuce.me.matura.R;
import com.deuce.me.matura.activities.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatoverviewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MainActivity mActivity;

    public ChatoverviewFragment() {
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
        View view = inflater.inflate(R.layout.fragment_chatoverview_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.chatoverview_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity.getBaseContext());
        linearLayoutManager.setStackFromEnd(false);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        Toolbar toolbar = view.findViewById(R.id.chatoverview_toolbar);
        toolbar.setTitle(R.string.chatoverview_title);
        mActivity.setSupportActionBar(toolbar);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ChatOverviewFirebaseAdapter mAdapter = new ChatOverviewFirebaseAdapter(
                OpenChatModel.class,
                R.layout.openchat,
                OpenChatViewHolder.class,
                mActivity.getMainProfileFirebaseRef().child("chats/"),
                this
        );
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

}
