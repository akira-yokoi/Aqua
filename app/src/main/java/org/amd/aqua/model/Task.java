package org.amd.aqua.model;

/**
 * Created by Akira on 2018/04/16.
 */

public class Task implements ImageSpinnerData {
    public int taskGroupCode;
    public String taskGroupName;
    public int taskCode;
    public String taskName;
    public int point;
    public int iconResourceId;

    @Override
    public String getText() {
        return taskName;
    }

    @Override
    public int getIconResourceId() {
        return iconResourceId;
    }
}
