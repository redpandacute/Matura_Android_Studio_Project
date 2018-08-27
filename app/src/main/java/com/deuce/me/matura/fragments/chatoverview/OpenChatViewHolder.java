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


class OpenChatViewHolder extends RecyclerView.ViewHolder {

    //https://android.jlelse.eu/click-listener-for-recyclerview-adapter-2d17a6f6f6c9

    public View view;
    private UserModel userModel;
    private ProfilePictureModel picture;

    public OpenChatViewHolder(View itemView) {
        super(itemView);
        view = itemView;
    }

    public void initialize(UserModel userModel, String latestMessage) {
        this.userModel = userModel;

        TextView cName_tv = view.findViewById(R.id.openchat_name_textview);
        cName_tv.setText(userModel.getFirstname() + " " + userModel.getName());
        TextView cLatest_tv = view.findViewById(R.id.openchat_latest_textview);
        cLatest_tv.setText(latestMessage);
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
