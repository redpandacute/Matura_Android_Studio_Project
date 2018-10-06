package com.deuce.me.matura.fragments.chatoverview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deuce.me.matura.R;
import com.deuce.me.matura.models.ProfilePictureModel;

import java.io.File;
import java.util.Map;

/**
 * Created by ingli on 19.08.2018.
 */

class ChatoverviewAdapterV2 extends RecyclerView.Adapter<OpenChatViewHolderV2>{

    private ChatoverviewFragment mFragment;
    private Map<Integer, OpenChatModelV2> mDataset;

    public ChatoverviewAdapterV2(ChatoverviewFragment mFragment) {
        this.mFragment = mFragment;
        this.mDataset = mFragment.getDataset();
    }

    @Override
    public OpenChatViewHolderV2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.openchat, parent, false);
        OpenChatViewHolderV2 viewHolder = new OpenChatViewHolderV2(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OpenChatViewHolderV2 holder, int position) {
        OpenChatModelV2 model = mDataset.get(mDataset.keySet().toArray()[position]);
        holder.validate(model);
        holder.getView().setOnClickListener(new OnOpenChatListenerV2(mFragment, model));
        holder.setProfilePicture(new ProfilePictureModel(mFragment.getActivity().getBaseContext(), new File(model.getUserModel().getTempProfilePicturePath())));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
