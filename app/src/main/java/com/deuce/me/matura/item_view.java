package com.deuce.me.matura;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Flo on 21.02.2018.
 */

public class item_view extends View implements View.OnClickListener {

    result_item info;

    public item_view(Context context, AttributeSet attrs) {
        super(context, attrs);
       // this.info = info;
    }

    public result_item getInfo() {
        return info;
    }

    public void setInfo(result_item info) {
        this.info = info;
    }

    @Override
    public void onClick(View view) {

    }
}
