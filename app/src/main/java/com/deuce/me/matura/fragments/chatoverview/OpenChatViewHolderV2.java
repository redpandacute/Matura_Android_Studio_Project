package com.deuce.me.matura.fragments.chatoverview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deuce.me.matura.R;
import com.deuce.me.matura.models.ProfilePictureModel;
import com.deuce.me.matura.models.UserModel;

/**
 * Created by ingli on 13.08.2018.
 */


class OpenChatViewHolderV2 extends RecyclerView.ViewHolder {

    //https://android.jlelse.eu/click-listener-for-recyclerview-adapter-2d17a6f6f6c9

    public View view;
    private UserModel userModel;
    private ProfilePictureModel picture;
    private OpenChatModelV2 mModel;

    public OpenChatViewHolderV2(View itemView) {
        super(itemView);
        view = itemView;
    }

    public void initialize(OpenChatModelV2 model) {
        this.mModel = model;
        this.userModel = mModel.getUserModel();

        TextView cName_tv = view.findViewById(R.id.openchat_name_textview);
        cName_tv.setText(userModel.getFirstname() + " " + userModel.getName());
        TextView cLatest_tv = view.findViewById(R.id.openchat_latest_textview);
        cLatest_tv.setText(mModel.getLatestMessage());
    }

    public void setProfilePicture(ProfilePictureModel picture) {
        ImageView profilePicture_iv = view.findViewById(R.id.openchat_profilepicture_imageView);
        profilePicture_iv.setImageBitmap(picture.getImageBitmap());
    }

    public void setUserModel(UserModel userModel) {
            this.userModel = userModel;
        }

    public UserModel getUserModel(){
            return this.userModel;
    }
}
