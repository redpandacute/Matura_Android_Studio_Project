package com.deuce.me.matura.requests;

import android.app.VoiceInteractor;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ingli on 17.07.2018.
 */

public class BigProfilePictureRequest extends StringRequest {

    private static final String URL = "http://ef-informatik.umbach.ch/students/hirtzf/PHP/profilepicture_big_php.php";
    private Map<String, String> params;

    public BigProfilePictureRequest(int id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("user_id", id + "");
    }

    @Override
    public Map<String, String> getParams() { return params; }


}
