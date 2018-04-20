package org.amd.aqua.util;

import android.content.Context;

import org.amd.aqua.R;
import org.amd.aqua.model.ImageSpinnerData;
import org.amd.aqua.model.Task;
import org.amd.aqua.model.TaskGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Akira on 2018/04/16.
 */

public class TaskManager {
    public static final int TASK_SUPPORT_LAUNDRY = 10;
    public static final int TASK_PACKAGE = 20;
    public static final int TASK_DELIVERY = 30;
    public static final int TASK_CLEANUP_SHOP = 40;
    public static final int TASK_CLEANUP_MACHINE = 50;

    public static final int STATUS_LOOKING = 1;
    public static final int STATUS_SUPPORTING = 2;
    public static final int STATUS_DONE = 3;

    private static TaskManager instance = null;

    private Map<Integer, String> taskGroupCodeNameMap = new HashMap<>();

    private Map<Integer, Integer> iconGroupResourceMap = new HashMap<>();

    private List<TaskGroup> taskGroups = new ArrayList<>();

    private Map<Integer, Task> taskMap = new HashMap<>();

    private Map<Integer, List<Task>> taskGroupMap = new HashMap<>();

    public static final int TASK_GROUP_LAUNDRY = 100;

    public static final int TASK_GROUP_CLEAN = 200;

    public static TaskManager getInstance(Context context) {
        if (instance == null) {
            instance = new TaskManager(context);
        }
        return instance;
    }

    private TaskManager(Context context) {
        // タスクグループ
        {
            // TaskGroupの初期化
            String[] groupArray = context.getResources().getStringArray(R.array.task_group_map);
            for (String group : groupArray) {
                String[] keyValueArray = group.split(":");
                int taskGroupId = Integer.valueOf(keyValueArray[0]);
                if (taskGroupId == TASK_GROUP_LAUNDRY) {
                    taskGroups.add(new TaskGroup(taskGroupId, keyValueArray[1], R.drawable.flat_washing_machine));
                }
                if (taskGroupId == TASK_GROUP_CLEAN) {
                    taskGroups.add(new TaskGroup(taskGroupId, keyValueArray[1], R.drawable.flat_clean));
                }
            }
        }

        // タスクアイコン
        {
            iconGroupResourceMap.put(10, R.drawable.flat_washing_machine);
            iconGroupResourceMap.put(20, R.drawable.flat_shopping_bag);
            iconGroupResourceMap.put(30, R.drawable.flat_delivery);
            iconGroupResourceMap.put(40, R.drawable.flat_clean);
            iconGroupResourceMap.put(40, R.drawable.flat_trashbox);
        }

        // タスク
        {
            String[] c2cTaskArray = context.getResources().getStringArray(R.array.c2c_support_task_map);
            for (String task : c2cTaskArray) {
                addTask(task);
            }
            String[] b2cTaskArray = context.getResources().getStringArray(R.array.b2c_support_task_map);
            for (String task : b2cTaskArray) {
                addTask(task);
            }
        }
    }

    private void addTask(String keyValue) {
        String[] keyValueArray = keyValue.split(":");
        int taskGroupCode = Integer.valueOf(keyValueArray[0]);
        int taskCode = Integer.valueOf(keyValueArray[1]);
        String taskName = keyValueArray[2];
        int point = Integer.valueOf( keyValueArray[3]);
        int iconGroupCode = Integer.valueOf(keyValueArray[4]);

        Task task = new Task();
        task.taskGroupCode = taskGroupCode;
        task.taskGroupName = taskGroupCodeNameMap.get(taskGroupCode);
        task.taskCode = taskCode;
        task.taskName = taskName;
        task.point = point;
        task.iconResourceId = iconGroupResourceMap.get(iconGroupCode);

        taskMap.put(task.taskCode, task);

        List<Task> taskList = taskGroupMap.get(task.taskGroupCode);
        if (taskList == null) {
            taskList = new ArrayList<>();
            taskGroupMap.put(taskGroupCode, taskList);
        }
        taskList.add(task);
    }

    public Task getTask(int taskCode) {
        return taskMap.get(taskCode);
    }

    public List<Task> getTaskList(int taskGroupCode) {
        return taskGroupMap.get(taskGroupCode);
    }

    public List<TaskGroup> getTaskGrouplist() {
        return taskGroups;
    }

    public List<ImageSpinnerData> getSpinnerTaskGroups() {
        List<ImageSpinnerData> results = new ArrayList<>();
        results.addAll( taskGroups);
        return results;
    }

    public List<ImageSpinnerData> getSpinnerTasks(int taskGroupCode) {
        List<ImageSpinnerData> results = new ArrayList<>();
        results.addAll( taskGroupMap.get(taskGroupCode));
        return results;
    }
}
