package com.deuce.me.matura;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flo on 13.02.2018.
 */

public class id_request extends StringRequest {

    private static final String search_URL = "http://ef-informatik.umbach.ch/students/hirtzf/PHP/id_request.php";
    private Map<String, String> params;

    public id_request (int id, Response.Listener<String> listener) {
        super(Method.POST, search_URL, listener, null /*Errorlistener*/);
        params = new HashMap<>();
        params.put("user_id", id + "");
    }

    @Override
    public Map<String, String> getParams() { return params; }
}
