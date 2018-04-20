package org.amd.aqua.util;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import org.amd.aqua.R;

/**
 * Created by Akira on 2018/04/10.
 */

public class ViewUtil {
    public static void showShortToast(Context context, int resourceId) {
        String message = context.getResources().getString(resourceId);
        showShortToast(context, message);
    }

    public static void showShortToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showLongToast(Context context, int resourceId) {
        String message = context.getResources().getString(resourceId);
        showLongToast(context, message);
    }

    public static void showLongToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void setStatusColor(TextView textView, int status) {
        int colorResourceId = R.color.colorPrimary;
        switch ((status)) {
            case TaskManager.STATUS_SUPPORTING:
                colorResourceId = R.color.colorGreen;
                break;
            case TaskManager.STATUS_DONE:
                colorResourceId = R.color.colorDarkGlay;
                break;
        }
        textView.setTextColor( textView.getResources().getColor( colorResourceId));
    }
}
