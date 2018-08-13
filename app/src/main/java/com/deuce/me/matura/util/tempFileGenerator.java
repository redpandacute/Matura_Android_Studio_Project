package com.deuce.me.matura.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.deuce.me.matura.R;
import com.deuce.me.matura.models.ProfilePictureModel;

/**
 * Created by ingli on 10.07.2018.
 */

public class tempFileGenerator {

    public String getTempFilePath(Context mContext, String blob) {
        if(!blob.equals("0") && !blob.equals("") && blob != null) {
            try {
                //System.out.println("blob: " + blob);
                ProfilePictureModel picture = new ProfilePictureModel(mContext, blob);
                return picture.getPath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Bitmap PBDummy = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.de_medal);

        ProfilePictureModel picture = new ProfilePictureModel(mContext, PBDummy);

        return picture.getPath();
    }
}
