package com.deuce.me.matura.trash;

import android.content.Context;
import android.view.View;

import com.deuce.me.matura.models.UserModel;

/**
 * Created by Flo on 21.02.2018.
 */

public class item_view extends View implements View.OnClickListener {

    UserModel info;

    public item_view(Context context) {
        super(context);
       // this.info = info;
    }

    public UserModel getInfo() {
        return info;
    }

    public void setInfo(UserModel info) {
        this.info = info;
    }

    @Override
    public void onClick(View view) {

    }
}
