package com.deuce.me.matura.fragments.chatoverview;

import com.deuce.me.matura.activities.MainActivity;
import com.deuce.me.matura.models.ProfilePictureModel;
import com.deuce.me.matura.models.UserModel;
import com.deuce.me.matura.util.TempFileGenerator;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import java.io.File;

/**
 * Created by ingli on 13.08.2018.
 */

class ChatOverviewFirebaseAdapter extends FirebaseRecyclerAdapter<OpenChatModel, OpenChatViewHolder> {


    private MainActivity mActivity;
    private ChatoverviewFragment mFragment;

    public ChatOverviewFirebaseAdapter(Class<OpenChatModel> modelClass, int modelLayout, Class<OpenChatViewHolder> viewHolderClass, Query ref, ChatoverviewFragment mFragment) {
        super(modelClass, modelLayout, viewHolderClass, ref);

        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();

    }

    @Override
    public OpenChatModel getItem(int position) {
        return super.getItem(position);
    }

    @Override
    protected void populateViewHolder(OpenChatViewHolder viewHolder, OpenChatModel model, int position) {

        System.out.println("ChatRef:: " + model.getChatRef());
        UserModel userModel = model.getReceiverModel(mActivity);
        System.out.println("Latest Message: " + model.getLatestMessage());

        viewHolder.initialize(userModel, model.getLatestMessage());
        viewHolder.setProfilePicture(new ProfilePictureModel(mActivity, new File(new TempFileGenerator().getTempFilePath(mActivity, "0"))));
        viewHolder.view.setOnClickListener(new OnOpenChatListener(mFragment, model));
    }

    public void validatePicture(int position, ProfilePictureModel mProfilePicture) {
        getItem(position).setProfilePicture(mProfilePicture);
        this.notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
