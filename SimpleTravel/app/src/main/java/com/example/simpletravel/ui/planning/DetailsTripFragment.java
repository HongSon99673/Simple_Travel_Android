package com.example.simpletravel.ui.planning;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.R;
import com.example.simpletravel.adapter.SavedItemAdapter;
import com.example.simpletravel.asynctask.planning.DetailTripAsyncTask;
import com.example.simpletravel.model.SavedItem;
import com.example.simpletravel.model.Temp.IdTrip;
import com.example.simpletravel.model.Temp.IdUsers;
import com.example.simpletravel.viewmodel.PlanningViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsTripFragment extends Fragment implements OnMapReadyCallback {

    public static final String TAG = DetailsTripFragment.class.getName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailsTripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsTripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsTripFragment newInstance(String param1, String param2) {
        DetailsTripFragment fragment = new DetailsTripFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //event click text view back
            if (view.getId() == R.id.detailstrip_txt_Back) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
            //event click text view delete
            if (view.getId() == R.id.detailstrip_txt_Delete) {
                DeleteTrip(Gravity.BOTTOM);
            }
            //event click text view edit
            if (view.getId() == R.id.detailstrip_txt_Edit) {
                EditTrip(Gravity.BOTTOM);
            }
        }
    };

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
    private MapView mapView;
    private Marker marker;
    private PlanningViewModel planningViewModel;
    private Connection connection;
    private JDBCControllers jdbcControllers;
    private Statement statement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details_trip, container, false);
        ContructorSeatlsTrip();
        //show gg map in fragment
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
        DetailsListService();//list all service in Trip this

        return view;
    }

    //create variable
    private RecyclerView recyclerView;
    private SavedItemAdapter savedItemAdapter;

    private void DetailsListService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView = view.findViewById(R.id.detailstrip_rcv_List_Service);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
                        recyclerView.addItemDecoration(itemDecoration);
                        new DetailTripAsyncTask(getContext(), recyclerView).execute();
                    }
                });
            }
        }).start();
    }

    //create variable
    private TextView Back, Delate, Edit, NameTrip, Information, Title;

    private void ContructorSeatlsTrip() {
        Back = view.findViewById(R.id.detailstrip_txt_Back);
        Back.setOnClickListener(onClickListener);

        mapView = view.findViewById(R.id.google_map_Trip);

        //set Title
        Title =  view.findViewById(R.id.detailstrip_txt_Title);
        Title.setText(IdTrip.NameTrip);

        //set name Trip
        NameTrip = view.findViewById(R.id.detailstrip_txt_NamTrip);
        NameTrip.setText(IdTrip.NameTrip);

        //set information
        Information = view.findViewById(R.id.detailstrip_txt_InforUser);
        Information.setText("Của " + IdUsers.NameUser + " - " + String.valueOf(IdTrip.Save) + " mục");

        Delate = view.findViewById(R.id.detailstrip_txt_Delete);
        Delate.setOnClickListener(onClickListener);

        Edit = view.findViewById(R.id.detailstrip_txt_Edit);
        Edit.setOnClickListener(onClickListener);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //create thread new call coordinates to data
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<SavedItem> savedItemList;
                try {
                    savedItemList = new ArrayList<>();
                    jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                    connection = jdbcControllers.ConnectionData();
                    Log.e("Coordinates", "True");
                    statement = connection.createStatement();
                    String sql = "select s.NameService,s.Address, s.Latitude, s.Longitude\n" +
                            "                    from Planning p, Services s, DetailsPlanning d\n" +
                            "                    where s.IdService = d.IdService and p.IdPlan = d.IdPlan and p.IdPlan = '" + IdTrip.IdTrips + "'";
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()) {

                        savedItemList.add(new SavedItem(0, "", resultSet.getString(1), 0, resultSet.getString(2), "",
                                "", 0, resultSet.getDouble(3), resultSet.getDouble(4), ""));
                    }
                    //close connect data
                    resultSet.close();
                    statement.close();
                    connection.close();
                    //show up list location on gg map
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < savedItemList.size(); i++) {
                                SavedItem s = savedItemList.get(i);
                                //create marker
                                marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(s.getLatitude(), s.getLongitude()))
                                        .title(s.getNameService())
                                        .snippet(s.getSummary())
                                );
                                //Zoom
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(s.getLatitude(), s.getLongitude()), 10));
                            }
                        }
                    });
                } catch (Exception ex) {
                    Log.e("Coordinates", ex.getMessage());
                }
            }
        }).start();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void DeleteTrip(int gravity) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirm);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = gravity;
        window.setAttributes(windowAttribute);

        //Gravity is bottom then dialog close
        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        Button No = dialog.findViewById(R.id.dialog_Confirm_No);
        Button Yes = dialog.findViewById(R.id.dialog_Confirm_Yes);

        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                    connection = jdbcControllers.ConnectionData();
                    Log.e("Delete Trip", "True");
                    statement = connection.createStatement();
                    String sql = "delete from DetailsPlanning where IdPlan = " + IdTrip.IdTrips + "";
                    String sql1 = "delete from Planning where IdPlan = " + IdTrip.IdTrips + "";
                    statement.executeUpdate(sql);
                    statement.executeUpdate(sql1);
                    //close connect to data base
                    statement.close();
                    connection.close();
                    Toast.makeText(getContext(), "Xóa chuyến đi thành công !", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();//close dialog
                    //back fragment trip
                    if (getFragmentManager() != null) {
                        getFragmentManager().popBackStack();
                    }
                } catch (Exception ex) {
                    Log.e("Delete Trip", ex.getMessage());
                }
            }
        });
        dialog.show();
    }

    private void EditTrip(int gravity) {

        final Dialog dialog1 = new Dialog(getActivity());
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_edit_trip);

        Window window = dialog1.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = gravity;
        window.setAttributes(windowAttribute);

        //Gravity is bottom then dialog close
        if (Gravity.BOTTOM == gravity) {
            dialog1.setCancelable(true);
        } else {
            dialog1.setCancelable(false);
        }

        Button No = dialog1.findViewById(R.id.dialog_Edit_No);
        Button Yes = dialog1.findViewById(R.id.dialog_Edit_Yes);
        EditText Trip = dialog1.findViewById(R.id.dialog_edit_Trip);
        TextView alert = dialog1.findViewById(R.id.dialog_edit_TripAlert);
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Trip.getText().toString().isEmpty()){
                    alert.setText("Tên chuyến đi không để trống");
                    alert.setTextColor(getResources().getColor(R.color.INK_RED));
                } else {
                    try {
                        jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                        connection = jdbcControllers.ConnectionData();
                        Log.e("Edit Trip", "True");
                        statement = connection.createStatement();
                        String sql = "update Planning set NamePlan = N'" + Trip.getText() + "' where IdPlan = " + IdTrip.IdTrips + "";
                        statement.executeUpdate(sql);
                        statement.close();
                        connection.close();
                        Toast.makeText(getContext(), "Sửa tên chuyến đi thành công !", Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();//close dialog
                        //back fragment trip
                        if (getFragmentManager() != null) {
                            getFragmentManager().popBackStack();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), "Sửa tên chuyến đi thất bại!", Toast.LENGTH_SHORT).show();
                        Log.e("Edit Trip", ex.getMessage());
                        dialog1.dismiss();

                    }
                }
            }
        });
        dialog1.show();
    }
}