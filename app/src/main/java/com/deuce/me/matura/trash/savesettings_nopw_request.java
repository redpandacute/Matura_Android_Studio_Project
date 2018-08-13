package com.deuce.me.matura.trash;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flo on 04.01.2018.
 */

public class savesettings_nopw_request extends StringRequest{
    private static final String savesettings_nopw_URL = "http://ef-informatik.umbach.ch/students/hirtzf/PHP/savesettings_nopw.php";
    private Map<String, String> params;

    public savesettings_nopw_request(int id, String firstname, String name, String email, String school, String description, boolean german , boolean spanish, boolean english, boolean french, boolean biology, boolean chemistry, boolean music, boolean maths, boolean physics, Response.Listener<String> listener) {

        super(Method.POST, savesettings_nopw_URL, listener, null /*Errorlistener*/);

        params = new HashMap<>();
        params.put("user_id", id + "");
        params.put("user_name", name);
        params.put("user_firstname", firstname);
        params.put("user_school", school);
        params.put("user_description", description);
        params.put("user_email", email);

        params.put("subj_german",  ((german) ? 1 : 0) + "");
        params.put("subj_spanish", ((spanish) ? 1 : 0) + "");
        params.put("subj_english", ((english) ? 1 : 0) + "");
        params.put("subj_french", ((french) ? 1 : 0) + "");
        params.put("subj_biology", ((biology) ? 1 : 0) + "");
        params.put("subj_chemistry", ((chemistry) ? 1 : 0) + "");
        params.put("subj_music", ((music) ? 1 : 0) + "");
        params.put("subj_maths", ((maths) ? 1 : 0) + "");
        params.put("subj_physics", ((physics) ? 1 : 0) + "");
    }

    @Override
    public Map<String, String> getParams() { return params; }
}
