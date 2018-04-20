package org.amd.aqua.util;

import android.content.Context;

import org.amd.aqua.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Akira on 2018/04/15.
 */

public class ResourceUtil {
    private static ResourceUtil instance = null;

    private Map<Integer, String> statusNameMap = new HashMap<Integer, String>();

    public static ResourceUtil getInstance(Context context) {
        if (instance == null) {
            instance = new ResourceUtil(context);
        }
        return instance;
    }

    private ResourceUtil(Context context) {
        // ステータス
        {
            String[] statusArray = context.getResources().getStringArray(R.array.status_map);
            for (String status : statusArray) {
                addMap(statusNameMap, status);
            }
        }
    }

    public static void addMap(Map<Integer, String> map, String keyValue) {
        String[] keyValueArray = keyValue.split(":");
        map.put(Integer.valueOf(keyValueArray[0]), keyValueArray[1]);
    }

    public String getStatusName(Integer status) {
        return statusNameMap.get(status);
    }
}
