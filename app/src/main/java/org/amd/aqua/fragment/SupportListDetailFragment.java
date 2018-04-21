package org.amd.aqua.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import org.amd.aqua.R;
import org.amd.aqua.SupportItemCompleteAcitivity;
import org.amd.aqua.model.SupportContentManager;
import org.amd.aqua.model.SupportItem;
import org.amd.aqua.model.Task;
import org.amd.aqua.model.UserManager;
import org.amd.aqua.util.DateTimeUtil;
import org.amd.aqua.util.ResourceUtil;
import org.amd.aqua.util.StringUtil;
import org.amd.aqua.util.TaskManager;
import org.amd.aqua.util.ViewUtil;

import static org.amd.aqua.SupportItemCompleteAcitivity.EXTRA_SUPPORT_ITEM;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ItemListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 * <p>You activity (or fragment) needs to implement {@link SupportListDetailFragment.Listener}.</p>
 */
public class SupportListDetailFragment extends BottomSheetDialogFragment {

    private static final String ARG_SUPPORT_ITEM = "support_item";
    private Listener mListener;

    public static SupportListDetailFragment newInstance(SupportItem item) {
        final SupportListDetailFragment fragment = new SupportListDetailFragment();
        final Bundle args = new Bundle();
        args.putSerializable(ARG_SUPPORT_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support_list_detail, container, false);
        final SupportItem item = (SupportItem) getArguments().getSerializable(ARG_SUPPORT_ITEM);

        final Context context = view.getContext();
        ResourceUtil constants = ResourceUtil.getInstance(context);

        TaskManager taskManager = TaskManager.getInstance(context);
        Task task = taskManager.getTask(item.taskId);
        if (task == null) {
            return view;
        }
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(task.iconResourceId);

        TextView taskText = view.findViewById(R.id.taskText);
        taskText.setText(task.taskName);

        TableRow messageRow = view.findViewById(R.id.messageRow);
        TextView messageText = view.findViewById(R.id.messageText);
        if (StringUtil.isEmpty(item.message)) {
            messageRow.setVisibility(View.GONE);
        } else {
            messageRow.setVisibility(View.VISIBLE);
            messageText.setText(item.message);
        }

        TextView requestantText = view.findViewById(R.id.requestantText);
        requestantText.setText( UserManager.getInstance().getUserName(item.requestantId));

        TextView periodText = view.findViewById(R.id.periodText);
        periodText.setText(DateTimeUtil.getMDHm(item.startDate) + "～" + DateTimeUtil.getMDHm(item.limitDate));

        TextView pointText = view.findViewById(R.id.pointText);
        pointText.setText( context.getString( R.string.text_point, task.point));

        TextView statusText = view.findViewById( R.id.statusText);
        statusText.setText(constants.getStatusName(item.status));
        ViewUtil.setStatusColor(statusText, item.status);

        Button actionButton = view.findViewById(R.id.actionButton);

        if (item.status == TaskManager.STATUS_SUPPORTING) {
            actionButton.setText(R.string.text_finish);
        }
        if (item.status == TaskManager.STATUS_DONE) {
            actionButton.setVisibility(View.INVISIBLE);
        }
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.status == TaskManager.STATUS_SUPPORTING) {
                    Intent intent = new Intent(getActivity(), SupportItemCompleteAcitivity.class);
                    intent.putExtra(EXTRA_SUPPORT_ITEM, item);
                    getActivity().startActivity( intent);
                } else {
                    ViewUtil.showShortToast(context, R.string.message_undertaked);
                    item.status = SupportContentManager.STATUS_SUPPORTING;
                    mListener.doSupport();
                }
                dismiss();
            }
        });

        Button closeButton = view.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface Listener {
        void doSupport();
    }
}
