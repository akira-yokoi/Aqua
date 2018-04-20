package org.amd.aqua.model;

/**
 * Created by Akira on 2018/04/18.
 */

public class TaskGroup implements ImageSpinnerData {
    public int taskGroupCode;
    public String taskGroupName;
    public int iconResourceId;

    public TaskGroup(int taskGroupCode, String taskGroupName, int iconResourceId) {
        this.taskGroupCode = taskGroupCode;
        this.taskGroupName = taskGroupName;
        this.iconResourceId = iconResourceId;
    }

    @Override
    public String getText() {
        return taskGroupName;
    }

    @Override
    public int getIconResourceId() {
        return iconResourceId;
    }
}
