package com.deuce.me.matura;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flo on 11.02.2018.
 */

public class searchresults_activity extends AppCompatActivity {


    Integer[] results;
    Map<String, String> current_res_user;
    Map<String, Integer> current_res_subj;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlist_activity);

        ListView listview = findViewById(R.id.searchlistact_listview_listview);
        Bundle extras_bundle = getIntent().getExtras();
        results = (Integer[]) extras_bundle.get("results");

        custom_adapter custom_adapter = new custom_adapter();
        listview.setAdapter(custom_adapter);
    }


    class custom_adapter extends BaseAdapter {


        @Override
        public int getCount() {

            return results.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View item = getLayoutInflater().inflate(R.layout.result_list_content, null);

            TextView name_tv = item.findViewById(R.id.result_name_textview);
            TextView school_yob_tv = item.findViewById(R.id.result_school_yob_textview);

            ImageView pb_imageview = item.findViewById(R.id.result_profilepicture_imageview);

            ImageView chemistry_medal = item.findViewById(R.id.result_chemistrymedal_imageview);
            ImageView french_medal = item.findViewById(R.id.result_frenchmedal_imageview);
            ImageView spanish_medal = item.findViewById(R.id.result_spanishmedal_imageview);
            ImageView maths_medal = item.findViewById(R.id.result_mathsmedal_imageview);
            ImageView physics_medal = item.findViewById(R.id.result_physicsmedal_imageview);
            ImageView biology_medal = item.findViewById(R.id.result_biologymedal_imageview);
            ImageView music_medal = item.findViewById(R.id.result_musicmedal_imageview);
            ImageView german_medal = item.findViewById(R.id.result_germanmedal_imageview);
            ImageView english_medal = item.findViewById(R.id.result_englishmedal_imageview);

            //Making request -----------------------------------------------------------------------
            id_request id_request = new id_request(results[i], new onResponseListener());
            RequestQueue request_queue = Volley.newRequestQueue(searchresults_activity.this); //Request Queue
            request_queue.add(id_request);

            //--------------------------------------------------------------------------------------

            String user_name = current_res_user.get("user_name");
            String user_firstname = current_res_user.get("user_firstname");
            //String user_school = current_res_user.get("user_school"); TODO: SCHOOOL!!!!!!!!!
            String user_username = current_res_user.get("user_username");
            String user_description = current_res_user.get("user_description");

            boolean subj_maths = 0 != current_res_subj.get("subj_maths");
            boolean subj_german = 0 != current_res_subj.get("subj_german");
            boolean subj_french = 0 != current_res_subj.get("subj_french");
            boolean subj_spanish = 0 != current_res_subj.get("subj_spanish");
            boolean subj_physics = 0 != current_res_subj.get("subj_physics");
            boolean subj_chemistry = 0 != current_res_subj.get("subj_chemistry");
            boolean subj_biology = 0 != current_res_subj.get("subj_biology");
            boolean subj_music = 0 != current_res_subj.get("subj_music");
            boolean subj_english = 0 != current_res_subj.get("subj_english");

            int user_yearofbirth = Integer.parseInt(current_res_user.get("user_yearofbirth"));
            int user_id = Integer.parseInt(current_res_user.get("user_id"));

            if(!subj_maths) { maths_medal.setVisibility(View.GONE); }
            if(!subj_spanish) { spanish_medal.setVisibility(View.GONE); }
            if(!subj_french) { french_medal.setVisibility(View.GONE); }
            if(!subj_biology) { biology_medal.setVisibility(View.GONE); }
            if(!subj_physics) { physics_medal.setVisibility(View.GONE); }
            if(!subj_english) { english_medal.setVisibility(View.GONE); }
            if(!subj_german) { german_medal.setVisibility(View.GONE); }
            if(!subj_music) { music_medal.setVisibility(View.GONE); }
            if(!subj_chemistry) { chemistry_medal.setVisibility(View.GONE); }



            name_tv.setText(user_firstname + " " + user_name);
            school_yob_tv.setText(user_yearofbirth);


            return item;
        }
    }

    private class onResponseListener implements Response.Listener<String> {


        @Override
        public void onResponse(String response) {

            try {

                System.out.println("my_response" + response);
                JSONObject json_response = new JSONObject(response);
                boolean success = json_response.getBoolean("success");

                System.out.println(success);

                if (success) {

                    current_res_user.clear();
                    current_res_subj.clear();

                    //Could be implemented into the extra directly
                    //getting UserData from Response
                    current_res_user.put("user_username", json_response.getString("user_username"));
                    current_res_user.put("user_name", json_response.getString("user_name"));
                    current_res_user.put("user_firstname", json_response.getString("user_firstname"));
                    //current_res_user.put("user_school", json_response.getString("user_school"));
                    current_res_user.put("user_email", json_response.getString("user_email"));
                    current_res_user.put("user_description", json_response.getString("user_description"));
                    current_res_user.put("user_yearofbirth", json_response.getString("user_yearofbirth"));
                    current_res_user.put("user_id", json_response.getString("user_id"));


                    current_res_subj.put("subj_maths", json_response.getInt("subj_maths"));
                    current_res_subj.put("subj_spanish", json_response.getInt("subj_spanish"));
                    current_res_subj.put("subj_german", json_response.getInt("subj_german"));
                    current_res_subj.put("subj_english", json_response.getInt("subj_english"));
                    current_res_subj.put("subj_physics", json_response.getInt("subj_physics"));
                    current_res_subj.put("subj_music", json_response.getInt("subj_music"));
                    current_res_subj.put("subj_chemistry", json_response.getInt("subj_chemistry"));
                    current_res_subj.put("subj_french", json_response.getInt("subj_french"));
                    current_res_subj.put("subj_biology", json_response.getInt("subj_biology"));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
