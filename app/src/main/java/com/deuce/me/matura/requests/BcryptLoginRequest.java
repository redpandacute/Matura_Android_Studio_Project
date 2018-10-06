package com.deuce.me.matura.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ingli on 04.10.2018.
 */

public class BcryptLoginRequest extends StringRequest{

    private static final String login_URL = "http://ef-informatik.umbach.ch/students/hirtzf/PHP/login_bcrypt.php";
    private Map<String, String> params;

    public BcryptLoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Method.POST, login_URL, listener, null /*Errorlistener*/);
        params = new HashMap<>();
        params.put("user_username", username);
        params.put("user_password", password);
    }

    @Override
    public Map<String, String> getParams() { System.out.println("params:" + params); return params; }
}
