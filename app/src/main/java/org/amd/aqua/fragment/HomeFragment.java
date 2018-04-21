package org.amd.aqua.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.amd.aqua.R;
import org.amd.aqua.model.Machine;
import org.amd.aqua.model.MachineManager;
import org.amd.aqua.model.User;
import org.amd.aqua.task.AquaApiTask;
import org.amd.aqua.util.PreferenceUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link FragmentActionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_COLUMN_COUNT = "column-count";

    private static final int PLUS_ONE_REQUEST_CODE = 0;

    private String ownerId = null;
    private String shopId = null;

    private ProgressBar progressBar;

    private FragmentActionListener mListener;

    private View rootView = null;

    private LinearLayout contentsLayout;

    private RecyclerView recyclerView = null;

    private HomeFragmentViewMachineAdapter adapter = null;

    private List<Machine> machineList = new ArrayList<>();

    private Map<String, Machine> machineMap = new HashMap<>();

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

        ownerId = PreferenceUtil.getStringPref(getActivity(), User.KEY_OWNER_ID, null);
        shopId = PreferenceUtil.getStringPref(getActivity(), User.KEY_SHOP_ID, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = rootView.findViewById(R.id.progressBar);
        contentsLayout = rootView.findViewById(R.id.contentsLayout);
        recyclerView = rootView.findViewById(R.id.list);
        // Set the adapter
        Context context = rootView.getContext();
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        adapter = new HomeFragmentViewMachineAdapter(this, mListener, 2);
        recyclerView.setAdapter(adapter);

        MachineManager manager = MachineManager.getInstance();
        List<Machine> machineList = manager.getMachines();
        if (manager.getMachines().isEmpty()) {
            showProgress(true);
            GetOperatingStatusTask task = new GetOperatingStatusTask(getActivity());
            task.execute("operatingstatus?ANKOWNERID=" + ownerId + "&ANKSHOPID=" + shopId + "&ANKMACHINENUM=");
        } else {
            showProgress(false);
            adapter.setItems(machineList);
            adapter.notifyDataSetChanged();
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView pointText = rootView.findViewById( R.id.pointText);
        TextView lankText = rootView.findViewById( R.id.lankText);

        String point = PreferenceUtil.getStringPref(getActivity(), User.KEY_NUM_POINT, "0");
        pointText.setText( point);
        lankText.setText( getText( R.string.text_lank_gold));
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
        mListener.onAction(this, v.getId());
    }

    public class GetOperatingStatusTask extends AquaApiTask {

        GetOperatingStatusTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void onPostExecute(String response) {
            machineList = new ArrayList<>();
            try {
                JSONObject json = new JSONObject(response);
                JSONArray members = json.getJSONArray("DataModel");
                for (int i = 0; i < members.length(); i++) {
                    JSONObject obj = members.getJSONObject(i);

                    final String no = obj.getString("ANKMACHINENUM");
                    final String name = obj.getString("KNJMACHNAME");
                    final String statusCode = obj.getString("ANKOPESTATUSCODE");
                    final String statusName = obj.getString("KNJOPESTATUS");
                    final String remainingTime = obj.getString("NUMREMAININGTIME");
                    final String process = obj.getString("ANKPROCESS");

                    Machine machine = new Machine();
                    machine.no = no;
                    machine.name = name;
                    machine.statusCode = statusCode;
                    machine.statusName = statusName;
                    machine.remainingTime = remainingTime;
                    machine.process = process;

                    machineList.add(machine);
                    machineMap.put(no, machine);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            GetMachineImageTask task = new GetMachineImageTask(getActivity());
            task.execute("machineimage?ANKOWNERID=" + ownerId + "&ANKSHOPID=" + shopId + "&ANKMACHINENUM=");
        }

        @Override
        protected void onCancelled() {
//            showProgress(false);
        }
    }


    public class GetMachineImageTask extends AquaApiTask {
        GetMachineImageTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void onPostExecute(String response) {
            try {
                JSONObject json = new JSONObject(response);
                JSONArray members = json.getJSONArray("DataModel");
                for (int i = 0; i < members.length(); i++) {
                    JSONObject obj = members.getJSONObject(i);

                    final String no = obj.getString("ANKMACHINENUM");
                    final String imageData = obj.getString("BINMACHINEIMAGE");

                    String image = imageData.replace("data:image/jpeg;base64,", "");
                    byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    Machine status = machineMap.get(no);
                    status.image = decodedByte;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            {
                Machine machine = new Machine();
                machine.name = "ケルヒャー\n高圧洗浄機";
                machine.image = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.item1);
                machine.statusName = "貸出可";
                machineList.add( machine);
            }

            {
                Machine machine = new Machine();
                machine.name = "AQUA\n店舗用クリーナー";
                machine.image = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.item2);
                machine.statusName = "貸出可";
                machineList.add( machine);
            }

            {
                Machine machine = new Machine();
                machine.name = "AQUA\nAXEL CLEAN";
                machine.image = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.item3);
                machine.statusName = "貸出可";
                machineList.add( machine);
            }

            MachineManager.getInstance().setMachines(machineList);
            adapter.setItems(machineList);
            adapter.notifyDataSetChanged();
            showProgress(false);
            if (mListener != null) {
                mListener.onAction(HomeFragment.this, 0);
            }
        }

        @Override
        protected void onCancelled() {
            showProgress(false);
        }
    }

    private void showProgress(boolean showProgress) {
        if (showProgress) {
            progressBar.setVisibility(View.VISIBLE);
            contentsLayout.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            contentsLayout.setVisibility(View.VISIBLE);
        }
    }
}
