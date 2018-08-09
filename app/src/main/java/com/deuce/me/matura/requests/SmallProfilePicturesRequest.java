package com.deuce.me.matura.requests;

import android.os.Build;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ingli on 12.07.2018.
 */

public class SmallProfilePicturesRequest extends StringRequest {

    private static final String URL = "http://ef-informatik.umbach.ch/students/hirtzf/PHP/smallimages_php_v2.php";
    private Map<String, String> params;

    public SmallProfilePicturesRequest(int[] ids, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();

        JSONArray jsnArray = new JSONArray();
        for(int n = 0; n < ids.length; n++) {
            jsnArray.put(ids[n]);
        }
        params.put("id_array", jsnArray.toString());
    }

    @Override
    public Map<String, String> getParams() { return params; }
}
