package com.deuce.me.matura.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deuce.me.matura.R;
import com.deuce.me.matura.activities.UserprofileActivity;
import com.deuce.me.matura.models.ProfilePictureModel;
import com.deuce.me.matura.models.UserModel;
import com.deuce.me.matura.util.JSONtoInfo;
import com.deuce.me.matura.util.tempFileGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by ingli on 12.07.2018.
 */

public class searchresult_recycleradapter extends RecyclerView.Adapter<searchresult_recycleradapter.ViewHolder> {

    private JSONArray rawDataset;
    private UserModel[] mDataset;
    private String clientInfo;
    private Context mContext;
    private Bitmap placeholderBitmap;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public UserModel info;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void setInfo(UserModel info) {
            this.info = info;
        }

        public UserModel getInfo() {
            return this.info;
        }
    }

    public searchresult_recycleradapter(JSONArray Dataset, String clientInfo, Context Context) throws JSONException {
        this.rawDataset = Dataset;
        this.mContext = Context;
        this.clientInfo = clientInfo;
        this.mDataset = new UserModel[rawDataset.length()];
        JSONtoInfo jsn = new JSONtoInfo(mContext);
        for(int n = 0; n < Dataset.length(); n++) {
            mDataset[n] = jsn.createNewItem(rawDataset.getJSONObject(n));
            System.out.println("ZAG!");
        }

        this.placeholderBitmap = new ProfilePictureModel(mContext,new File(new tempFileGenerator().getTempFilePath(mContext, null))).getImageBitmap();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_list_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        System.out.println("KARMA!");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setInfo(mDataset[position]);
        System.out.println("SWUGG!");
        TextView name_tv = holder.itemView.findViewById(R.id.result_name_textview);
        TextView school_yob_tv = holder.itemView.findViewById(R.id.result_school_textview);

        ImageView pb_imageview = holder.itemView.findViewById(R.id.result_profilepicture_imageview);

        ImageView chemistry_medal = holder.itemView.findViewById(R.id.result_chemistrymedal_imageview);
        ImageView french_medal = holder.itemView.findViewById(R.id.result_frenchmedal_imageview);
        ImageView spanish_medal = holder.itemView.findViewById(R.id.result_spanishmedal_imageview);
        ImageView maths_medal = holder.itemView.findViewById(R.id.result_mathsmedal_imageview);
        ImageView physics_medal = holder.itemView.findViewById(R.id.result_physicsmedal_imageview);
        ImageView biology_medal = holder.itemView.findViewById(R.id.result_biologymedal_imageview);
        ImageView music_medal = holder.itemView.findViewById(R.id.result_musicmedal_imageview);
        ImageView german_medal = holder.itemView.findViewById(R.id.result_germanmedal_imageview);
        ImageView english_medal = holder.itemView.findViewById(R.id.result_englishmedal_imageview);

        if(!mDataset[position].isMaths()) { maths_medal.setVisibility(View.GONE); }
        if(!mDataset[position].isSpanish()) { spanish_medal.setVisibility(View.GONE); }
        if(!mDataset[position].isFrench()) { french_medal.setVisibility(View.GONE); }
        if(!mDataset[position].isBiology()) { biology_medal.setVisibility(View.GONE); }
        if(!mDataset[position].isPhysics()) { physics_medal.setVisibility(View.GONE); }
        if(!mDataset[position].isEnglish()) { english_medal.setVisibility(View.GONE); }
        if(!mDataset[position].isGerman()) { german_medal.setVisibility(View.GONE); }
        if(!mDataset[position].isMusic()) { music_medal.setVisibility(View.GONE); }
        if(!mDataset[position].isChemistry()) { chemistry_medal.setVisibility(View.GONE); }

        name_tv.setText(mDataset[position].getFirstname() + " " + mDataset[position].getName());
        school_yob_tv.setText(mDataset[position].getYearofbirth() + "");

        if(mDataset[position].getTempProfilePicturePath() != null) {
            pb_imageview.setImageBitmap(new ProfilePictureModel(mContext, new File(mDataset[position].getTempProfilePicturePath())).getImageBitmap());
        } else {
            pb_imageview.setImageBitmap(placeholderBitmap);
        }
        //holder.itemView.setOnClickListener(null);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UserprofileActivity.class);
                holder.getInfo().setTempProfilePicturePath("0");

                try {
                    holder.getInfo().updateJSON();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                intent.putExtra("profileInfo", holder.getInfo().getJSON());
                intent.putExtra("clientInfo", clientInfo);
                try {
                    intent.putExtra("results", new JSONObject().put("results", rawDataset).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        System.out.println("LENGTH:: " + mDataset.length);
        return mDataset.length;
    }

    public void refresh(int start, String[] paths) {
        for(int n = 0; n < paths.length; n++) {
            mDataset[start + n].setTempProfilePicturePath(paths[n]);
        }
    }


}
