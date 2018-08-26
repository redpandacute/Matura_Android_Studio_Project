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
import com.deuce.me.matura.interfaces.LoaderFragment;
import com.deuce.me.matura.models.ProfilePictureModel;
import com.deuce.me.matura.models.UserModel;
import com.deuce.me.matura.util.JSONtoInfo;
import com.deuce.me.matura.util.ProfilePictureLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatoverviewFragment extends Fragment implements LoaderFragment{

    private RecyclerView mRecyclerView;
    private MainActivity mActivity;
    private boolean readyState;

    public static final int HEAPSIZE = 10;
    private ChatsValueEventListener mValueListener;

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

        readyState = true;

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        /*final ChatOverviewFirebaseAdapter mAdapter = new ChatOverviewFirebaseAdapter(
                OpenChatModel.class,
                R.layout.openchat,
                OpenChatViewHolder.class,
                mActivity.getMainProfileFirebaseRef().child("chats/"),
                this
        );
        */

        ChatoverviewAdapterV2 mAdapter = new ChatoverviewAdapterV2(this);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.addOnScrollListener(new OnScrollListener(this, (LinearLayoutManager) mRecyclerView.getLayoutManager()));
        mValueListener = new ChatsValueEventListener(this);
        mActivity.getMainProfileFirebaseRef().child("chats/").addValueEventListener(new ChatsValueEventListener(this));

        /*
        mAdapter.notifyDataSetChanged();

        if (mAdapter.getItemCount() >= HEAPSIZE) {

            UserModel[] models = new UserModel[HEAPSIZE];
            JSONtoInfo jsn = new JSONtoInfo(mActivity.getBaseContext());

            try {
                for(int n = 0; n < HEAPSIZE; n++) {
                    System.out.println("mAdapter: " + mAdapter.getItem(n).getReceiverJSON());

                    models[n] = jsn.createNewItem(new JSONObject(mAdapter.getItem(n).getReceiverJSON()));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

            UserModel[] models = new UserModel[mAdapter.getItemCount()];
            JSONtoInfo jsn = new JSONtoInfo(mActivity.getBaseContext());
            System.out.println("ItemCount: " + mAdapter.getItemCount());

            try {
                for(int n = 0; n < mAdapter.getItemCount(); n++) {
                    System.out.println("mAdapter: " + mAdapter.getItem(n).getReceiverJSON());

                    models[n] = jsn.createNewItem(new JSONObject(mAdapter.getItem(n).getReceiverJSON()));
                    System.out.println("ModelIDS: " + models[n].getId());

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            new ProfilePictureLoader(this).load(0, mAdapter.getItemCount(), models, new OnChatProfilePicturesListener(this));
        }

        mAdapter.notifyDataSetChanged();
        */
    }


    @Override
    public void onStop() {
        super.onStop();
        mActivity.getMainProfileFirebaseRef().child("chats/").removeEventListener(mValueListener);
    }


    @Override
    public void onPause() {
        super.onPause();
        mActivity.getMainProfileFirebaseRef().child("chats/").removeEventListener(mValueListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.getMainProfileFirebaseRef().child("chats/").addValueEventListener(mValueListener);
    }

    public void refreshList(String[] paths, int start, int amount) {
        for(int n = 0; n < amount; n++) {
            OpenChatViewHolder holder = (OpenChatViewHolder) mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(start + n));
            holder.getUserModel().setTempProfilePicturePath(paths[n]);
            holder.setProfilePicture(new ProfilePictureModel(mActivity.getBaseContext(), new File(paths[n])));
        }
    }


    @Override
    public boolean getReadyState() {
        return this.readyState;
    }

    @Override
    public void setReadyState(boolean readyState) {
        this.readyState = readyState;
    }

    public Map<Integer, OpenChatModelV2> getDataset() {
        return mActivity.getOpenChatsDataset();
    }

    public void setDataset(Map<Integer, OpenChatModelV2> mDataset) {
        mActivity.setOpenChatsDataset(mDataset);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setPaths(Map<Integer, String> map) {
        mActivity.setOpenChatsPaths(map);
    }

    public Map<Integer,String> getPaths() {
        return mActivity.getOpenChatsPaths();
    }
}
