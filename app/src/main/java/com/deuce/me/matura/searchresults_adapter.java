
package com.deuce.me.matura;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;


/**
 * Created by Flo on 18.02.2018.
*/

public class searchresults_adapter extends BaseAdapter {

    private Context mContext;
    private JSONArray results;

    public searchresults_adapter(Context mContext, JSONArray results) {
        this.mContext = mContext;
        this.results = results;
    }

    @Override
    public int getCount() {
        return results.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            //return new result_iteminfo().createNewItem(results.getJSONObject(i));
            return new result_iteminfo().createNewItem(results.getJSONObject(i));
        } catch (JSONException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public item_view getView(int i, View view, ViewGroup viewGroup) {

        //View item = View.inflate(mContext, R.layout.result_list_content, null);

        XmlPullParser parser = mContext.getResources().getLayout(R.layout.result_list_content);

        /*
        try {
            parser.next();
            parser.nextTag();
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        AttributeSet attr = Xml.asAttributeSet(parser);

        item_view item = new item_view(mContext, attr);

        //View itemm =  View.inflate(mContext, R.layout.result_list_content, null);
        //itemm = new item_view(mContext);

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


        try {
            item.setInfo(new result_iteminfo().createNewItem(results.getJSONObject(i)));

            if(!item.getInfo().isMaths()) { maths_medal.setVisibility(View.GONE); }
            if(!item.getInfo().isSpanish()) { spanish_medal.setVisibility(View.GONE); }
            if(!item.getInfo().isFrench()) { french_medal.setVisibility(View.GONE); }
            if(!item.getInfo().isBiology()) { biology_medal.setVisibility(View.GONE); }
            if(!item.getInfo().isPhysics()) { physics_medal.setVisibility(View.GONE); }
            if(!item.getInfo().isEnglish()) { english_medal.setVisibility(View.GONE); }
            if(!item.getInfo().isGerman()) { german_medal.setVisibility(View.GONE); }
            if(!item.getInfo().isMusic()) { music_medal.setVisibility(View.GONE); }
            if(!item.getInfo().isChemistry()) { chemistry_medal.setVisibility(View.GONE); }

            name_tv.setText(item.getInfo().getFirstname() + " " + item.getInfo().getName());
            school_yob_tv.setText(item.getInfo().getYearofbirth() + "");

        } catch (JSONException e) { e.printStackTrace(); }

        return item;
    }

    class onClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
        }
    }
}
