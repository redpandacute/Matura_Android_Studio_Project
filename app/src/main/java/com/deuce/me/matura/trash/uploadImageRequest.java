package com.deuce.me.matura.trash;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ingli on 18.06.2018.
 */

public class uploadImageRequest extends StringRequest {

    private static final String URL = "";
    private Map<String, String> params;

    public uploadImageRequest(int id,
                              String username,
                              String password,
                              String imageBase64,
                              Response.Listener<String> listener
    ) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("user_id", id + "");
        params.put("user_username", username);
        params.put("user_password", password);
        params.put("encoded_base64", imageBase64);
    }
}
