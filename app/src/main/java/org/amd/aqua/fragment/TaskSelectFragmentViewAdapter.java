package org.amd.aqua.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.amd.aqua.R;
import org.amd.aqua.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link FragmentActionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TaskSelectFragmentViewAdapter extends RecyclerView.Adapter<TaskSelectFragmentViewAdapter.ViewHolder> {

    private Fragment mParent;
    private final List<Task> mValues;
    private final FragmentActionListener mListener;
    private final List<Task> selections = new ArrayList<>();

    public TaskSelectFragmentViewAdapter(Fragment parent, List<Task> items, FragmentActionListener listener) {
        this.mParent = parent;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task_select_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Task task = mValues.get(position);
        holder.mItem = task;
        holder.mImageView.setImageResource(task.iconResourceId);
        holder.mNameView.setText(task.taskName);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mTaskLayout.setSelected(!holder.mTaskLayout.isSelected());
                if (holder.mTaskLayout.isSelected()) {
                    selections.add(holder.mItem);
                } else {
                    selections.remove(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public List<Task> getSelections() {
        return selections;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final View mTaskLayout;
        public final ImageView mImageView;
        public final TextView mNameView;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTaskLayout = (View) view.findViewById(R.id.taskLayout);
            mImageView = (ImageView) view.findViewById(R.id.imageView);
            mNameView = (TextView) view.findViewById(R.id.nameView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
