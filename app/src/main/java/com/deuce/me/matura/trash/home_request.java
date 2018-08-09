package com.deuce.me.matura.trash;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flo on 05.03.2018.
 */

public class home_request extends StringRequest{

    private static final String login_URL = "http://ef-informatik.umbach.ch/students/hirtzf/PHP/home_php.php";
    private Map<String, String> params;

    public home_request(int id, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, login_URL, listener, null /*Errorlistener*/);
        params = new HashMap<>();
        params.put("user_id", id + "");
        params.put("user_password", password);
    }

    @Override
    public Map<String, String> getParams() { System.out.println("params:" + params); return params; }

}
