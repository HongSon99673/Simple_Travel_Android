package com.example.simpletravel.ui.planning;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.R;
import com.example.simpletravel.adapter.TripAdapter;
import com.example.simpletravel.model.Temp.IdUsers;
import com.example.simpletravel.model.Trip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TripPlanningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TripPlanningFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TripPlanningFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TripPlanningFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TripPlanningFragment newInstance(String param1, String param2) {
        TripPlanningFragment fragment = new TripPlanningFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //create variable
    private View view;
    private Button CreateTrip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        planningViewModel = new ViewModelProvider(this).get(PlanningViewModel.class);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_trip_planning, container, false);

        UpdateListTrip(); //update list trip in fragment TripPlanning

        CreateTrip = view.findViewById(R.id.btn_CreateTrip_Planning);
        CreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCreateTripDialog(Gravity.BOTTOM);
                //update Fragment TripPlanning
                UpdateListTrip();

            }
        });



        return view;
    }

    //create function handle dialog
    //Send data give SQL
    private JDBCControllers jdbcControllers;
    private Connection connection;

    private void OpenCreateTripDialog(int gravity){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_create_trip);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = gravity;
        window.setAttributes(windowAttribute);

        //Gravity is bottom then dialog close
        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        EditText nameTrip = dialog.findViewById(R.id.dialog_txt_NameTrip);
        Button createTrip = dialog.findViewById(R.id.dialog_btn_CreateTrip);
        createTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String NameTrip = nameTrip.getText().toString();
                int IdUser = IdUsers.IdUser;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                            connection = jdbcControllers.ConnectionData();
                            Log.e("Log", "Connect true");
                            String sql = "Insert into Planning " +
                                    " ( NamePlan,IdUser) values " + "('" + NameTrip + "','" + IdUser+ "')";
                            PreparedStatement preparedStatement = connection
                                    .prepareStatement(sql);
                            preparedStatement.executeUpdate();
                            preparedStatement.close();
                            Toast.makeText(getActivity(), "Send true", Toast.LENGTH_LONG).show();

                            dialog.dismiss();//close dialog
                        } catch (Exception ex) {
                            Log.e("Log", ex.toString());
                        }
                    }
                }).start();
            }
        });
        dialog.show();
    }


    //create variable
    private RecyclerView recyclerView;
    private TripAdapter tripAdapter;
    private PlanningViewModel planningViewModel;

    private void UpdateListTrip() {

        recyclerView = view.findViewById(R.id.planning_lv_Trip);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        planningViewModel.getTrip().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                tripAdapter = new TripAdapter(trips);
                recyclerView.setAdapter(tripAdapter);
            }
        });
    }

}