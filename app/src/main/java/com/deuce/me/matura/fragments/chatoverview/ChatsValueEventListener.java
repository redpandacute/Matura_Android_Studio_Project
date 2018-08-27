package com.deuce.me.matura.fragments.chatoverview;

import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.models.UserModel;
import com.deuce.me.matura.util.ProfilePictureLoader;
import com.deuce.me.matura.util.TempFileGenerator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ingli on 19.08.2018.
 */

class ChatsValueEventListener implements ValueEventListener {

    private ChatoverviewAdapterV2 mAdapter;
    private ChatoverviewFragment mFragment;
    private MainActivity mActivity;
    private Map<Integer, OpenChatModelV2> mDataset;
    private Map<Integer, String> mPaths;

    public ChatsValueEventListener(ChatoverviewFragment mFragment) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
        this.mDataset = mFragment.getDataset();
        this.mAdapter = (ChatoverviewAdapterV2) mFragment.getRecyclerView().getAdapter();
        this.mPaths = mFragment.getPaths();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        //long count = dataSnapshot.getChildrenCount();
        // TODO: show the count in the UI
        Iterator<DataSnapshot> mSnapshots = dataSnapshot.getChildren().iterator();
        if(mFragment.isResumed()) {
            TempFileGenerator gen = new TempFileGenerator();

            while (mSnapshots.hasNext()) {
                try {
                    DataSnapshot mSnapshot = mSnapshots.next();

                    if (mSnapshot.hasChild("latestMessage")) {
                        OpenChatModelV2 mChatModel = new OpenChatModelV2(mActivity.getBaseContext(), mSnapshot);
                        if (mPaths.containsKey(mChatModel.getUserModel().getId())) {
                            mChatModel.getUserModel().setTempProfilePicturePath(mPaths.get(mChatModel.getUserModel().getId()));
                        } else {
                            mChatModel.getUserModel().setTempProfilePicturePath(gen.getTempFilePath(mActivity.getBaseContext(), "0"));
                        }

                        mDataset.put(mChatModel.getUserModel().getId(), mChatModel);
                    }
                } catch(JSONException e){
                    e.printStackTrace();
                }
            }

            mAdapter.notifyDataSetChanged();
            loadProfilePictures();
        }
    }

    private void loadProfilePictures() {
        int n;
        ArrayList<OpenChatModelV2> toLoad = new ArrayList<>();

        for(n = 0; n < mDataset.size(); n++) {
            if(!mPaths.containsKey(mDataset.keySet().toArray()[n])) {
                toLoad.add(mDataset.get(mDataset.keySet().toArray()[n]));
            }
        }

        UserModel[] loadArray = new UserModel[toLoad.size()];

        for(int i = 0; i < toLoad.size(); i++) {
            loadArray[i] = toLoad.get(i).getUserModel();
        }

        new ProfilePictureLoader(mFragment).load(0, loadArray.length, loadArray, new OnChatProfilePicturesListener(mFragment, loadArray));

    }


    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
