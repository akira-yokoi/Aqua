package org.amd.aqua.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.amd.aqua.R;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link FragmentActionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    private static final int PLUS_ONE_REQUEST_CODE = 0;

    private FragmentActionListener mListener;

    private Button supporterLookingButton = null;
    private Button supporterSupportingButton = null;
    private Button supporterCompletedButton = null;

    private Button requestantLookingButton = null;
    private Button requestantSupportingButton = null;
    private Button requestantCompletedButton = null;

    public HomeFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        supporterLookingButton = view.findViewById( R.id.supporterlookingButton);
        supporterSupportingButton = view.findViewById( R.id.supporterSupportingButton);
        supporterCompletedButton = view.findViewById( R.id.supporterCompletedButton);

        requestantLookingButton = view.findViewById( R.id.requestantLookingButton);
        requestantSupportingButton = view.findViewById( R.id.requestantSupportingButton);
        requestantCompletedButton = view.findViewById( R.id.requestantCompletedButton);


        supporterLookingButton.setOnClickListener(this);
        supporterSupportingButton.setOnClickListener(this);
        supporterCompletedButton.setOnClickListener(this);

        requestantLookingButton.setOnClickListener(this);
        requestantSupportingButton.setOnClickListener(this);
        requestantCompletedButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActionListener) {
            mListener = (FragmentActionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        mListener.onAction( this, v.getId());
    }
}
