package org.amd.aqua;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.amd.aqua.fragment.DatePickerDialogFragment;
import org.amd.aqua.fragment.FragmentActionListener;
import org.amd.aqua.fragment.TaskSelectFragment;
import org.amd.aqua.fragment.TimePickerDialogFragment;
import org.amd.aqua.model.Task;
import org.amd.aqua.util.DateTimeUtil;
import org.amd.aqua.util.TaskManager;
import org.amd.aqua.util.ViewUtil;
import org.amd.aqua.widget.ImageSpinnerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SupportItemEditAcitivity extends AppCompatActivity implements FragmentActionListener {

    private Spinner taskGroupSpinner;

    private ImageSpinnerAdapter taskGroupAdapter;

    private Spinner taskSpinner;

    private ImageSpinnerAdapter taskAdapter;

    private List<ImageButton> groupButtons = new ArrayList<>();

    private List<Task> tasks = new ArrayList<>();

    private ListView taskList = null;

    private TaskListAdapter adapter = null;

    private Button dateButton = null;

    private Button hourButton = null;

    private Button saveButton = null;

    private Button closeButton = null;

    private Date limitDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_item_edit);

        setTitle(R.string.title_new_support);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        {
            GroupButtonListener listener = new GroupButtonListener();
            ImageButton laundryButton = (ImageButton) findViewById(R.id.laundryButton);
            laundryButton.setTag(TaskManager.TASK_GROUP_LAUNDRY);

            ImageButton cleanButton = (ImageButton) findViewById(R.id.cleanButton);
            cleanButton.setTag(TaskManager.TASK_GROUP_LAUNDRY);

            laundryButton.setOnClickListener(listener);
            cleanButton.setOnClickListener(listener);
            groupButtons.add(laundryButton);
            groupButtons.add(cleanButton);
        }

        {
            taskList = (ListView) findViewById(R.id.taskList);
            adapter = new TaskListAdapter(this, android.R.layout.simple_list_item_1);
            taskList.setAdapter(adapter);
        }

        {
            SelectionChangeListener selectionChangeListener = new SelectionChangeListener();
            Button manButton = (Button) findViewById(R.id.manButton);
            Button womanButton = (Button) findViewById(R.id.womanButton);
            Button youngButton = (Button) findViewById(R.id.youngButton);
            Button middleButton = (Button) findViewById(R.id.middleButton);
            Button seniorButton = (Button) findViewById(R.id.seniorButton);

            manButton.setOnClickListener(selectionChangeListener);
            womanButton.setOnClickListener(selectionChangeListener);
            youngButton.setOnClickListener(selectionChangeListener);
            middleButton.setOnClickListener(selectionChangeListener);
            seniorButton.setOnClickListener(selectionChangeListener);
        }

        {
            limitDate = DateTimeUtil.adjustHour(new Date(), 3);
            dateButton = (Button) findViewById(R.id.dateButton);
            dateButton.setText(DateTimeUtil.getYYYYMD(limitDate));
            dateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialogFragment datePicker = DatePickerDialogFragment.newInstance(limitDate);
                    datePicker.show(getSupportFragmentManager(), "datePicker");
                }
            });

            hourButton = (Button) findViewById(R.id.hourButton);
            hourButton.setText(DateTimeUtil.getHmm(limitDate));
            hourButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerDialogFragment timePicker = TimePickerDialogFragment.newInstance(limitDate);
                    timePicker.show(getSupportFragmentManager(), "timePicker");
                }
            });
        }

        {
            saveButton = (Button) findViewById(R.id.saveButton);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewUtil.showShortToast(SupportItemEditAcitivity.this, R.string.message_requested);
                    finish();
                }
            });
            closeButton = (Button) findViewById(R.id.closeButton);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

    }

    public void onAction(Fragment fragment, int actionId) {
    }

    public void onSelect(Fragment fragment, Object item) {
        if (fragment instanceof TaskSelectFragment) {
            tasks.clear();
            adapter.clear();

            if (item != null) {
                tasks.addAll((List<Task>) item);
                adapter.addAll(tasks);
            }
            adapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(taskList);

            if (tasks.isEmpty()) {
                for (View groupButton : groupButtons) {
                    groupButton.setSelected(false);
                }
            }
        }
        if (fragment instanceof DatePickerDialogFragment) {
            List<Integer> results = (List<Integer>) item;
            Integer year = results.get(0);
            Integer month = results.get(1);
            Integer day = results.get(2);

            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.setTime(limitDate);
            cal.set(year, month, day);
            limitDate = cal.getTime();
            dateButton.setText(DateTimeUtil.getYYYYMD(limitDate));
        }
        if (fragment instanceof TimePickerDialogFragment) {
            List<Integer> results = (List<Integer>) item;
            Integer hour = results.get(0);
            Integer min = results.get(1);

            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.setTime(limitDate);
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, min);
            limitDate = cal.getTime();
            hourButton.setText(DateTimeUtil.getHmm(limitDate));
        }
    }

    private class GroupButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            for (ImageButton button : groupButtons) {
                if (v == button) {
                    button.setSelected(!button.isSelected());
                } else {
                    button.setSelected(false);
                }
            }

            if (v.isSelected()) {
                int groupId = (int) v.getTag();
                TaskSelectFragment bottomSheetDialog = TaskSelectFragment.newInstance(groupId, 2);
                bottomSheetDialog.show(getSupportFragmentManager(), bottomSheetDialog.getTag());
            } else {
                tasks.clear();
                adapter.clear();
                adapter.notifyDataSetChanged();
            }
        }
    }

    private class SelectionChangeListener implements View.OnClickListener {
        public void onClick(View v) {
            v.setSelected(!v.isSelected());
        }
    }

    private class TaskListAdapter extends ArrayAdapter<Task> {
        public TaskListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // ビューを受け取る
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_support_item_content_task_list_adapter, parent, false);

            Task task = getItem(position);

            ImageView imageView = view.findViewById(R.id.imageView);
            TextView nameView = view.findViewById(R.id.nameView);
            TextView pointView = view.findViewById(R.id.pointView);

            imageView.setImageResource(task.iconResourceId);
            nameView.setText(task.taskName);
            pointView.setText(getString(R.string.text_point, task.point));
            return view;
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
