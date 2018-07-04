package com.deuce.me.matura;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Flo on 18.02.2018.
 */

public class JSONtoInfo {

    private Context context;

    public JSONtoInfo(Context context) {
        this.context = context;
    }


    public userInfo createNewItem(JSONObject json) {

        try {

            System.out.println(json);


            String user_name = json.getString("user_name");
            String user_firstname = json.getString("user_firstname");
            //String user_school = current_res_user.get("user_school"); TODO: SCHOOOL!!!!!!!!!
            String user_username = json.getString("user_username");

            String user_description = "No description";
            try {
                user_description = json.getString("user_description");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("NOTE: No description");
            }

            int user_yearofbirth = json.getInt("user_yearofbirth");


            boolean subj_maths = 0 != json.getInt("subj_maths");
            boolean subj_german = 0 != json.getInt("subj_german");
            boolean subj_french = 0 != json.getInt("subj_french");
            boolean subj_spanish = 0 != json.getInt("subj_spanish");
            boolean subj_physics = 0 != json.getInt("subj_physics");
            boolean subj_chemistry = 0 != json.getInt("subj_chemistry");
            boolean subj_biology = 0 != json.getInt("subj_biology");
            boolean subj_music = 0 != json.getInt("subj_music");
            boolean subj_english = 0 != json.getInt("subj_english");

            String encodedProfilePicture = null;

            if(json.getString("blob_profilepicture").equals("0")) {
                Bitmap PBDummy = BitmapFactory.decodeResource(context.getResources(), R.drawable.bi_medal);

                profilePicture PB = new profilePicture(context, PBDummy);
                encodedProfilePicture = PB.getBASE64();
            } else {
                encodedProfilePicture = json.getString("blob_profilepicture");
            }



            userInfo item = null;
            if(json.getString("hash_password") == null) {
                item = new userInfo(
                        json.getInt("user_id"), user_username, user_name, user_firstname, user_yearofbirth, user_description,
                        subj_french, subj_spanish, subj_music, subj_english, subj_chemistry, subj_biology, subj_maths, subj_german, subj_physics,
                        encodedProfilePicture, json.toString());
            } else {

                String user_email = json.getString("user_email");
                String hash_password = json.getString("hash_password");
                String hash_salt = json.getString("hash_salt");

                item = new userInfo(
                        json.getInt("user_id"), user_username, user_name, user_firstname, user_yearofbirth, user_description, user_email,
                        subj_french, subj_spanish, subj_music, subj_english, subj_chemistry, subj_biology, subj_maths, subj_german, subj_physics,
                        hash_password, hash_salt,
                        encodedProfilePicture, json.toString());
            }

            return item;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}