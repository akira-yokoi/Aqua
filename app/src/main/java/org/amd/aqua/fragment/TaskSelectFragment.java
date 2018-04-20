package org.amd.aqua.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.amd.aqua.R;
import org.amd.aqua.model.Task;
import org.amd.aqua.util.TaskManager;

import java.util.List;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     TaskSelectFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class TaskSelectFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_TASK_GROUP_ID = "task_group_id";

    private int mColumnCount = 1;
    private int mTaskGroupId = 100;
    private FragmentActionListener mListener;
    private RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskSelectFragment() {
    }

    public static TaskSelectFragment newInstance(int taskGroupId, int columnCount) {
        TaskSelectFragment fragment = new TaskSelectFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_TASK_GROUP_ID, taskGroupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mTaskGroupId = getArguments().getInt(ARG_TASK_GROUP_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_select, container, false);
        mRecyclerView = view.findViewById(R.id.list);
        Button okButton = view.findViewById(R.id.okButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);
        okButton.setOnClickListener( this);
        cancelButton.setOnClickListener( this);

        // Set the adapter
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        TaskManager manager = TaskManager.getInstance(context);
        List<Task> taskList = manager.getTaskList(mTaskGroupId);
        TaskSelectFragmentViewAdapter adapter = new TaskSelectFragmentViewAdapter(this, taskList, mListener);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancelButton) {
            mListener.onSelect(this, null);
            dismiss();
        }
        if (v.getId() == R.id.okButton) {
            TaskSelectFragmentViewAdapter adapter = (TaskSelectFragmentViewAdapter) mRecyclerView.getAdapter();
            mListener.onSelect(this, adapter.getSelections());
            dismiss();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActionListener) {
            mListener = (FragmentActionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
