package com.deuce.me.matura.fragments.openchat;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.deuce.me.matura.R;
import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.models.ChatModel;
import com.deuce.me.matura.models.UserModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class OpenchatFragment extends Fragment {

    private ChatModel mChatModel;
    private UserModel mMainprofileModel, mUserprofileModel;
    private MainActivity mActivity;

    private RecyclerView recyclerView;
    private EditText textInput_et;
    private FloatingActionButton sendButton_fab;
    private FirebaseChatAdapter firebaseAdapter;

    public OpenchatFragment() {
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
        View view = inflater.inflate(R.layout.fragment_openchat_fragment, container, false);

        Toolbar toolbar = view.findViewById(R.id.chat_toolbar);
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.chat_recyclerview);
        textInput_et = view.findViewById(R.id.chat_message_edittext);
        sendButton_fab = view.findViewById(R.id.chat_sendbutton_floatingactionbutton);
        sendButton_fab.setOnClickListener(new OnSendListener(view, this));

        mChatModel = mActivity.getOpenChat();
        mMainprofileModel = mChatModel.getMainprofileModel();
        mUserprofileModel = mChatModel.getUserprofileModel();

        mActivity.getSupportActionBar().setTitle(getString(R.string.chat_title) + " " + mUserprofileModel.getName());

        setHasOptionsMenu(true);

        //RecView Layout
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity.getBaseContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseAdapter = new FirebaseChatAdapter(
                ChatMessageModel.class,
                R.layout.message_right,
                ChatMessageHolder.class,
                mChatModel.getChatRef().child("Messages"),
                mMainprofileModel
        );

        recyclerView.setAdapter(firebaseAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (mActivity.getNavigation().getSelectedItemId() == R.id.searchoverview_botnav) {
                    mActivity.setFragment(mActivity.getOpenprofileFragment());
                    mActivity.setOpenChat(null);
                    mActivity.setOpenchatFragment(null);
                    return true;
                } else {
                    mActivity.setFragment(mActivity.getChatoverviewFragment());
                    mActivity.setOpenchatFragment(null);
                    mActivity.setOpenChat(null);
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ChatModel getOpenChatModel() {
        return mChatModel;
    }
}
