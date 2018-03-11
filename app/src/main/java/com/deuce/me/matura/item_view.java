package com.deuce.me.matura;

import android.content.Context;
import android.view.View;

/**
 * Created by Flo on 21.02.2018.
 */

public class item_view extends View implements View.OnClickListener {

    userInfo info;

    public item_view(Context context) {
        super(context);
       // this.info = info;
    }

    public userInfo getInfo() {
        return info;
    }

    public void setInfo(userInfo info) {
        this.info = info;
    }

    @Override
    public void onClick(View view) {

    }
}
