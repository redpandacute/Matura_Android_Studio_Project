package com.deuce.me.matura;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by ingli on 10.07.2018.
 */

public class tempFileGenerator {

    public String getTempFilePath(Context mContext, String blob) {
        if(!blob.equals("0") && !blob.equals("") && blob != null) {
            try {
                //System.out.println("blob: " + blob);
                profilePicture picture = new profilePicture(mContext, blob);
                return picture.getPath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Bitmap PBDummy = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.de_medal);

        profilePicture picture = new profilePicture(mContext, PBDummy);

        return picture.getPath();
    }
}
