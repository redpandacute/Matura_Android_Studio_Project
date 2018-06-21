package com.deuce.me.matura;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flo on 28.12.2017.
 */

public class register_request extends StringRequest {
    private static final String register_URL = "http://ef-informatik.umbach.ch/students/hirtzf/PHP/register_php.php";
    private Map<String, String> params;

    public register_request(String username, String name, String firstname, String school, int yearofbirth, String email, String password, String salt, Response.Listener<String> listener) {
        super(Method.POST, register_URL, listener, null /*Errorlistener*/);
        params = new HashMap<>();
        params.put("user_username", username);
        params.put("user_name", name);
        params.put("user_firstname", firstname);
        params.put("user_school", school);
        params.put("user_yearofbirth", yearofbirth + "");
        params.put("user_email", email);

        params.put("hash_password", password);
        params.put("hash_salt", salt);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
