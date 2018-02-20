package com.deuce.me.matura;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flo on 18.02.2018.
 */

public class result_iteminfo {


    public result_item createNewItem(JSONObject json) {

        try {

            System.out.println(json);


            String user_name = json.getString("user_name");
            String user_firstname = json.getString("user_firstname");
            //String user_school = current_res_user.get("user_school"); TODO: SCHOOOL!!!!!!!!!
            String user_username = json.getString("user_username");
            String user_description = json.getString("user_description");

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


            result_item item = new result_item(json.getInt("user_id"), user_username, user_name, user_firstname, user_yearofbirth, user_description, subj_french, subj_spanish, subj_music, subj_english, subj_chemistry, subj_biology, subj_maths, subj_german, subj_physics);
            return item;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}