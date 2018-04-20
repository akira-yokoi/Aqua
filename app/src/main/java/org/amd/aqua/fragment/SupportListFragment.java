package org.amd.aqua.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import org.amd.aqua.R;
import org.amd.aqua.model.SupportContentManager;
import org.amd.aqua.model.SupportItem;

import java.util.ArrayList;
import java.util.List;

import static org.amd.aqua.model.SupportContentManager.MODE_ALL;
import static org.amd.aqua.model.SupportContentManager.MODE_REQUEST;
import static org.amd.aqua.model.SupportContentManager.MODE_SUPPORT;
import static org.amd.aqua.model.SupportContentManager.STATUS_ALL;
import static org.amd.aqua.model.SupportContentManager.STATUS_LOOKING;
import static org.amd.aqua.model.SupportContentManager.STATUS_SUPPORTING;
import static org.amd.aqua.model.SupportContentManager.STATUS_COMPLETE;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link FragmentActionListener}
 * interface.
 */
public class SupportListFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private static final String ARG_MODE = "mode";
    private static final String ARG_STATUS = "status";

    private FragmentActionListener mListener;

    private RecyclerView mRecyclerView;

    private SupportListFragmentViewAdapter mAdapter;

    private int mode = MODE_ALL;

    private int status = STATUS_ALL;

    private boolean initialized = false;

    private Menu menu = null;

    private RadioButton allStatusRadio = null;
    private RadioButton statusLookingRadio = null;
    private RadioButton statusSupportingRadio = null;
    private RadioButton statusCompleteRadio = null;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SupportListFragment() {
    }

    public static SupportListFragment newInstance(int mode, int status) {
        SupportListFragment fragment = new SupportListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MODE, mode);
        args.putInt(ARG_STATUS, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.mode = getArguments().getInt(ARG_MODE);
            this.status = getArguments().getInt(ARG_STATUS);
        }
    }

    public void setMode(int mode) {
        this.mode = mode;

        if (mode == MODE_ALL) {
            getActivity().setTitle("リスト（全て）");
            menu.findItem(R.id.filter_all).setChecked(true);
        }
        if (mode == MODE_REQUEST) {
            getActivity().setTitle("リスト（依頼分）");
            menu.findItem(R.id.filter_request).setChecked(true);
        }
        if (mode == MODE_SUPPORT) {
            getActivity().setTitle("リスト（担当分）");
            menu.findItem(R.id.filter_support).setChecked(true);
        }
    }

    public void setStatus(int status) {
        this.status = status;

        if (status == STATUS_ALL) {
            allStatusRadio.setChecked(true);
        }
        if (status == STATUS_LOOKING) {
            statusLookingRadio.setChecked(true);
        }
        if (status == STATUS_SUPPORTING) {
            statusSupportingRadio.setChecked(true);
        }
        if (status == STATUS_COMPLETE) {
            statusCompleteRadio.setChecked(true);
        }
    }

    public void filter() {
        SupportContentManager scm = SupportContentManager.getInstance( getContext());
        List<SupportItem> items = scm.getItems( mode, status, null, null);
        mAdapter.setItems( items);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support_list, container, false);

        allStatusRadio = view.findViewById(R.id.allStatusRadio);
        statusLookingRadio = view.findViewById(R.id.statusLookingRadio);
        statusSupportingRadio = view.findViewById(R.id.statusSupportingRadio);
        statusCompleteRadio = view.findViewById(R.id.statusCompleteRadio);

        allStatusRadio.setOnCheckedChangeListener(this);
        statusLookingRadio.setOnCheckedChangeListener(this);
        statusSupportingRadio.setOnCheckedChangeListener(this);
        statusCompleteRadio.setOnCheckedChangeListener(this);

        List<SupportItem> items = new ArrayList<>();

        mRecyclerView = view.findViewById(R.id.list);
        setHasOptionsMenu(true);
        // Set the adapter
        mAdapter = new SupportListFragmentViewAdapter(this, items, mListener);
        Context context = view.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main_menu, menu);
        this.menu = menu;

        setMode( mode);
        setStatus( status);
        filter();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActionListener) {
            mListener = (FragmentActionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentActionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView == allStatusRadio) {
                this.status = STATUS_ALL;
            }
            if (buttonView == statusLookingRadio) {
                this.status = STATUS_LOOKING;
            }
            if (buttonView == statusSupportingRadio) {
                this.status = STATUS_SUPPORTING;
            }
            if (buttonView == statusCompleteRadio) {
                this.status = STATUS_COMPLETE;
            }

            filter();
        }
    }
}
