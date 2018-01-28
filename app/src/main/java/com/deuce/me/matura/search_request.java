package com.deuce.me.matura;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flo on 28.01.2018.
 */

public class search_request extends StringRequest{

    private static final String search_URL = "";
    private Map<String, String> params;

    public search_request(String name /*  *PLACEHOLDER*, String school*/, Map<String, Boolean> map, Response.Listener<String> listener) {
        super(Method.POST, search_URL, listener, null /*Errorlistener*/);
        params = new HashMap<>();
        params.put("user_name", name);

        //TODO IMPLEMENT THE SCHOOL SPINNER STUFF FLO !!
        //params.put("user_school", school);

        params.put("subj_german", map.get("subj_german") + "");
        params.put("subj_spanish", map.get("subj_spanish") + "");
        params.put("subj_english", map.get("subj_english") + "");
        params.put("subj_french",map.get("subj_french") + "");
        params.put("subj_biology",map.get("subj_biology") + "");
        params.put("subj_maths", map.get("subj_maths") + "");
        params.put("subj_chemistry", map.get("subj_chemistry") + "");
        params.put("subj_music", map.get("subj_music") + "");
        params.put("subj_physics", map.get("subj_physics") + "");
    }

    @Override
    public Map<String, String> getParams() { return params; }
}
