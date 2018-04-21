package org.amd.aqua.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.amd.aqua.R;
import org.amd.aqua.model.SupportItem;
import org.amd.aqua.model.Task;
import org.amd.aqua.model.UserManager;
import org.amd.aqua.util.DateTimeUtil;
import org.amd.aqua.util.ResourceUtil;
import org.amd.aqua.util.TaskManager;
import org.amd.aqua.util.ViewUtil;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link SupportItem} and makes a call to the
 * specified {@link FragmentActionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SupportListFragmentViewAdapter extends RecyclerView.Adapter<SupportListFragmentViewAdapter.ViewHolder> {

    private Fragment mParent;
    private List<SupportItem> mValues;
    private FragmentActionListener mListener;

    public SupportListFragmentViewAdapter(Fragment parent, List<SupportItem> items, FragmentActionListener listener) {
        mParent = parent;
        mValues = items;
        mListener = listener;
    }

    public void setItems(List<SupportItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_support_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        Context context = holder.mContentView.getContext();
        ResourceUtil constants = ResourceUtil.getInstance(context);

        TaskManager taskManager = TaskManager.getInstance(context);

        SupportItem item = mValues.get(position);

        // 作業内容
        int taskId = mValues.get(position).taskId;
        Task task = taskManager.getTask(taskId);
        if (task == null) {
            return;
        }
        holder.mContentView.setText(task.taskName);

        // アイコン
        holder.mImagetView.setImageResource(task.iconResourceId);

        int status = mValues.get(position).status;
        holder.mStatusView.setText(constants.getStatusName(status));
        ViewUtil.setStatusColor(holder.mStatusView, status);

        holder.mMessageView.setText(item.message);
        holder.mRequestantView.setText( UserManager.getInstance().getUserName(item.requestantId));
        holder.mPointView.setText( context.getString( R.string.text_point, task.point));

        holder.mRequestDateView.setText(DateTimeUtil.getMDHm(item.startDate) + "～" + DateTimeUtil.getMDHm(item.limitDate));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onSelect(mParent, holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImagetView;
        public final TextView mContentView;
        public final TextView mStatusView;
        public final TextView mPointView;
        public final TextView mMessageView;
        public final TextView mRequestantView;
        public final TextView mRequestDateView;

        public SupportItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImagetView = (ImageView) view.findViewById(R.id.imageView);
            mStatusView = (TextView) view.findViewById(R.id.status);
            mContentView = (TextView) view.findViewById(R.id.content);
            mMessageView = (TextView) view.findViewById(R.id.message);
            mPointView = (TextView) view.findViewById(R.id.pointText);
            mRequestantView = (TextView) view.findViewById(R.id.requestant);
            mRequestDateView = (TextView) view.findViewById(R.id.requestDate);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
