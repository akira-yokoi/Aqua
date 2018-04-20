package org.amd.aqua.model;

import android.content.Context;

import org.amd.aqua.util.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class SupportContentManager {

    public static final int MODE_ALL = 0;
    public static final int MODE_SUPPORT = 1;
    public static final int MODE_REQUEST = 2;

    public static final int STATUS_ALL = 0;
    public static final int STATUS_LOOKING = 1;
    public static final int STATUS_SUPPORTING = 2;
    public static final int STATUS_COMPLETE = 3;

    private static SupportContentManager instance = null;

    private static final int COUNT = 25;



    private List<SupportItem> items = new ArrayList<SupportItem>();

    public static SupportContentManager getInstance(Context context) {
        if (instance == null) {
            instance = new SupportContentManager(context);
        }
        return instance;
    }

    private SupportContentManager(Context context) {
        // SupportItemの初期化
        for (int i = 1; i <= COUNT; i++) {
            items.add(createDummyItem(i));
        }
    }

    public List<SupportItem> getItems(int mode, int status, Integer requestantId, Integer supporterId) {
        List<SupportItem> results = new ArrayList<SupportItem>();
        for (SupportItem item : items) {
            switch ((status)) {
                case STATUS_ALL:
                    results.add(item);
                    break;
                default:
                    if (status == item.status) {
                        results.add(item);
                    }
                    break;
            }
        }
        return results;
    }

    private static SupportItem createDummyItem(int position) {
        SupportItem item = new SupportItem();
        item.supportId = position;
        item.taskId = 1000;
        if (position < 6) {
            item.taskId = 1000 + position * 10;
        }
        if (position > 10) {
            item.taskId = 2000 + (position - 10) * 10;
        }
        item.requestantId = 0;
        item.supporterId = 0;

        if (position % 3 == 0) {
            item.status = STATUS_LOOKING;
        }
        if (position % 3 == 1) {
            item.status = STATUS_SUPPORTING;
        }
        if (position % 3 == 2) {
            item.status = STATUS_COMPLETE;
        }

        item.message = "お掃除をお願いします";
        item.createDate = DateTimeUtil.getDate(2018, 4, 16, 10, 0);
        item.startDate = DateTimeUtil.getDate(2018, 4, 16, 22, 15 + position);
        item.limitDate = DateTimeUtil.getDate(2018, 4, 18, 22, 15 + position);
        return item;
    }
}
