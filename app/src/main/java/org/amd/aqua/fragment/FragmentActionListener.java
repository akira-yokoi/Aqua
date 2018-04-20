package org.amd.aqua.fragment;


import android.support.v4.app.Fragment;

/**
 * Created by Akira on 2018/04/17.
 */

public interface FragmentActionListener {
    public void onAction(Fragment fragment, int actionId);

    public void onSelect(Fragment fragment, Object item);
}
