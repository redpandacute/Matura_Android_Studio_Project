package com.deuce.me.matura.interfaces;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.deuce.me.matura.activities.MainActivity;

/**
 * Created by ingli on 16.08.2018.
 */

public interface LoaderFragment {
    public boolean getReadyState();
    public void setReadyState(boolean readyState);
    public Activity getActivity();
}
