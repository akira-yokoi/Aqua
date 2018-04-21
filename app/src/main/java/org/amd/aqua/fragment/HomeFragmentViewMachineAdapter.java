package org.amd.aqua.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.amd.aqua.R;
import org.amd.aqua.model.Machine;
import org.amd.aqua.model.Task;
import org.amd.aqua.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentViewMachineAdapter extends RecyclerView.Adapter<HomeFragmentViewMachineAdapter.ViewHolder> {
    private Fragment mParent;
    private int mColumnCnt;
    private final List<Machine> mValues;
    private final FragmentActionListener mListener;
    private final List<Task> selections = new ArrayList<>();

    public HomeFragmentViewMachineAdapter(Fragment parent, FragmentActionListener listener, int columnCnt) {
        this.mParent = parent;
        mValues = new ArrayList<>();
        mListener = listener;
        mColumnCnt = columnCnt;
    }

    public HomeFragmentViewMachineAdapter(Fragment parent, List<Machine> machineList, FragmentActionListener listener, int columnCnt) {
        this.mParent = parent;
        mValues = machineList;
        mListener = listener;
        mColumnCnt = columnCnt;
    }

    public void setItems(List<Machine> items) {
        this.mValues.clear();
        this.mValues.addAll(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (mColumnCnt == 2) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_home_machine_item_col2, parent, false);
        }
        if (mColumnCnt == 3) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_home_machine_item_col3, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Machine machineInfo = mValues.get(position);
        holder.mItem = machineInfo;
        holder.mNameView.setText(machineInfo.name);
        holder.mNameView.setTextColor(Color.DKGRAY);

        if (mColumnCnt == 2) {
            Context context = holder.mStatusView.getContext();
            String statusCode = machineInfo.statusCode;
            if (StringUtil.equals(statusCode, "T") || StringUtil.isEmpty( statusCode)) {
                int stopColor = context.getResources().getColor(R.color.colorPrimary);
                holder.mStatusView.setTextColor(stopColor);
                holder.mStatusView.setText(context.getString(R.string.text_status_stop, machineInfo.statusName));
            } else {
                holder.mStatusView.setTextColor(Color.DKGRAY);
                String statusName = machineInfo.statusName.replace("運転", "");
                holder.mStatusView.setText(context.getString(R.string.text_status_running, statusName, machineInfo.remainingTime));
            }
        }
        holder.mImageView.setImageBitmap(machineInfo.image);
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
        public final TextView mStatusView;
        public Machine mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTaskLayout = (View) view.findViewById(R.id.machineLayout);
            mImageView = (ImageView) view.findViewById(R.id.imageView);
            mNameView = (TextView) view.findViewById(R.id.nameView);
            mStatusView = (TextView) view.findViewById(R.id.statusView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
