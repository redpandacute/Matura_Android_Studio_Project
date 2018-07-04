package com.deuce.me.matura;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ingli on 21.06.2018.
 */

public class saltRequest extends StringRequest {

    private static final String URL = "http://ef-informatik.umbach.ch/students/hirtzf/PHP/salt_php.php";
    private Map<String, String> params;

    public saltRequest(String username, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("user_username", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
