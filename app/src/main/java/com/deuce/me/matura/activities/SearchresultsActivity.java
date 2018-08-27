package com.deuce.me.matura.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.deuce.me.matura.R;
import com.deuce.me.matura.adapter.searchresult_recycleradapter;
import com.deuce.me.matura.requests.SmallProfilePicturesRequest;
import com.deuce.me.matura.util.TempFileGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Flo on 11.02.2018.
 */

public class SearchresultsActivity extends AppCompatActivity {

    private Bundle extrasBundle;
    private searchresult_recycleradapter recAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private int firstVisibleItemPosition, childCount;
    private static final int heapsize = 20;
    private boolean isLoading = false;
    private int loop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlist_activity);

        Toolbar toolbar = findViewById(R.id.searchresult_toolbar);
        toolbar.setTitle(R.string.searchresults_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //ListView listview = findViewById(R.id.searchlistact_listview_listview);
        extrasBundle = getIntent().getExtras();

        try {
            JSONArray json_arr = (new JSONObject(extrasBundle.getString("results"))).getJSONArray("results");

            recyclerView = findViewById(R.id.searchlistact_listview_reclistview);
            layoutManager = new LinearLayoutManager(getBaseContext());
            recyclerView.setLayoutManager(layoutManager);
            recAdapter = new searchresult_recycleradapter(json_arr, extrasBundle.getString("clientInfo"), getBaseContext());
            recyclerView.setAdapter(recAdapter);
            recyclerView.addOnScrollListener(new onScrollListener());

            if(json_arr.length() != 0) {
                if (json_arr.length() >= heapsize) {
                    getProfilePictures(0, heapsize);
                    firstVisibleItemPosition = 0;
                } else {
                    getProfilePictures(0, json_arr.length());
                    firstVisibleItemPosition  = 0;
                }
            }
            //listview.setAdapter(new searchresults_adapter(getApplicationContext(), json_arr));
            //listview.setOnItemClickListener(new onItemClickListener());
            //System.out.println(listview.getOnItemClickListener().toString());
        } catch (JSONException e) { e.printStackTrace(); }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(SearchresultsActivity.this, SearchfilterActivity.class);
                intent.putExtra("clientInfo", extrasBundle.getString("clientInfo"));
                startActivity(intent);
                System.out.println("::BACK BUTTON::");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    private class onItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            try {
                JSONArray json_arr = (new JSONObject(extrasBundle.getString("results"))).getJSONArray("results");
                Intent showuser_intent = new Intent(SearchresultsActivity.this, UserprofileActivity.class);
                showuser_intent.putExtra("profileInfo", json_arr.getJSONObject(i).toString());
                showuser_intent.putExtra("clientInfo", extrasBundle.getString("clientInfo"));
                showuser_intent.putExtra("results", extrasBundle.getString("results"));
                startActivity(showuser_intent);

            } catch (JSONException e) { e.printStackTrace(); }
        }
    }
    */

    private void getProfilePictures(int start, int amount) {
        int[] ids = new int[amount];
        isLoading = true;
        try {
            JSONArray json_arr = (new JSONObject(extrasBundle.getString("results"))).getJSONArray("results");

            for(int n = 0; n < amount; n++) {
                ids[n] = json_arr.getJSONObject(n+start).getInt("user_id");
            }

            SmallProfilePicturesRequest request = new SmallProfilePicturesRequest(ids, new onPictureResponseListener());
            RequestQueue queue = Volley.newRequestQueue(SearchresultsActivity.this);
            System.out.println("MAKING SMALLIMAGES REQUEST");
            queue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class onPictureResponseListener implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {
            try {
                System.out.println(response);
                JSONObject jsn = new JSONObject(response);
                if(jsn.getBoolean("success")) {
                    JSONArray data = jsn.getJSONArray("data");
                    String[] paths = new String[data.length()];
                    TempFileGenerator gen = new TempFileGenerator();
                    for(int n = 0; n < data.length(); n++) {
                        paths[n] = gen.getTempFilePath(getBaseContext(), data.getJSONObject(n).getString("blob_profilepicture_small"));
                    }
                    recAdapter.refresh(firstVisibleItemPosition + loop, paths);
                    isLoading = false;
                    recAdapter.notifyDataSetChanged();
                } else {
                    System.out.println("::ERROR WITH REQUESTING IDS:: ");
                    System.out.println(response);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class onScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int totalItemCount = layoutManager.getItemCount();

            firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            childCount = layoutManager.getChildCount();

            for(loop = 0; loop < childCount; loop++) {
                searchresult_recycleradapter.ViewHolder holder = (searchresult_recycleradapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(firstVisibleItemPosition + loop);
                if(holder.getInfo().getTempProfilePicturePath() == null && !isLoading) {
                    if(totalItemCount - firstVisibleItemPosition - 1 >= heapsize) {
                        int amount = heapsize;
                        int start = firstVisibleItemPosition + loop;
                        getProfilePictures(start, amount);
                        break;
                    } else {
                        int amount = totalItemCount - firstVisibleItemPosition - loop;
                        int start = firstVisibleItemPosition + loop;
                        getProfilePictures(start, amount);
                        break;
                    }
                }
            }
            // Load more if we have reach the end to the recyclerView
            //if (recyclerView.getAdapter().getItemViewType(2) && firstVisibleItemPosition >= 0) {
                //loadMoreItems();
            //}
        }

    }
}