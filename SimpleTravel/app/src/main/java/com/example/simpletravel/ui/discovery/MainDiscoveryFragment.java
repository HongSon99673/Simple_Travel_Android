package com.example.simpletravel.ui.discovery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.R;
import com.example.simpletravel.activity.AccountActivity;
import com.example.simpletravel.asynctask.discovery.HistoryAllAsyncTask;
import com.example.simpletravel.asynctask.discovery.HistoryAsyncTask;
import com.example.simpletravel.asynctask.discovery.LocationAsyncTask;
import com.example.simpletravel.model.Location;
import com.example.simpletravel.model.Temp.IdUsers;
import com.example.simpletravel.model.Temp.LocationTemp;
import com.example.simpletravel.viewmodel.DiscoveryViewModel;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainDiscoveryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainDiscoveryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainDiscoveryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainDiscoveryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainDiscoveryFragment newInstance(String param1, String param2) {
        MainDiscoveryFragment fragment = new MainDiscoveryFragment();
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
    private RecyclerView rcv_History_View_Discovery, rcv_HotelSmall_View_Discovery,
            rcv_ViewTravel_Discovery;
    private CircleImageView imgAvatar;
    private View root;
    private TextView LookAll, BonusView;
    private Button Bonus;
    private ImageView background;


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.img_Avatar) {
                startActivity(new Intent(getActivity(), AccountActivity.class));
            }
            if (view.getId() == R.id.discovery_txt_LookALl){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                rcv_History_View_Discovery.setLayoutManager(layoutManager);
                                new HistoryAllAsyncTask(getContext(),rcv_History_View_Discovery).execute();
                            }
                        });
                    }
                }).start();
            }
            //show bonus view
            if (view.getId() == R.id.btn_Bonus_Discovery){
                //show detail Location
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.discovery_frameLayout_Main, new LocationFragment());
                transaction.addToBackStack(LocationFragment.TAG1);
                transaction.commit();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_main_discovery, container, false);

        rcv_History_View_Discovery = root.findViewById(R.id.rcv_History_View_Discovery);
        HistoryController();

//        rcv_HotelSmall_View_Discovery = root.findViewById(R.id.rcv_HotelSmall_View_Discovery);
//        HotelController();

        rcv_ViewTravel_Discovery = root.findViewById(R.id.rcv_ViewTravel_Discovery);
        ViewTravelController();

        BonusView();//show inform bonus
        ViewConstructor();//function constructor
        return root;
    }

    private void ViewConstructor() {
        imgAvatar = root.findViewById(R.id.img_Avatar);
        imgAvatar.setOnClickListener(onClickListener);

        LookAll = root.findViewById(R.id.discovery_txt_LookALl);
        LookAll.setOnClickListener(onClickListener);
        LookAll.setPaintFlags(LookAll.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        BonusView = root.findViewById(R.id.txt_Bonus_Discovery);
        Bonus = root.findViewById(R.id.btn_Bonus_Discovery);
        Bonus.setOnClickListener(onClickListener);
        background = root.findViewById(R.id.discovery_LL_Background);
    }

    private void ViewTravelController() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        rcv_ViewTravel_Discovery.setLayoutManager(layoutManager);
                        new LocationAsyncTask(getContext(), rcv_ViewTravel_Discovery).execute();
                    }
                });
            }
        }).start();
    }
//    private void HotelController() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//                        rcv_HotelSmall_View_Discovery.setLayoutManager(layoutManager);
//                        new LocationAsyncTask(getContext(), rcv_HotelSmall_View_Discovery).execute();
//                    }
//                });
//            }
//        }).start();
//    }
    private void HistoryController() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        rcv_History_View_Discovery.setLayoutManager(layoutManager);
                        new HistoryAsyncTask(getContext(),rcv_History_View_Discovery).execute();
                    }
                });
            }
        }).start();
    }

    //show location
    private JDBCControllers jdbcControllers;
    private Connection connection;
    private List<Location> list;

    private void BonusView(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    list = new ArrayList<>();
                    jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                    connection = jdbcControllers.ConnectionData();
                    Log.e("HistoryView", "True");
                    Statement statement = connection.createStatement();
                    String sql = "select top(1) L.IdLocation, L.NameLocation, L.Images \n" +
                            "from HistoryServices H, Services S, Location L \n" +
                            "where H.IdService = S.IdService and L.IdLocation = S.IdLocation and H.IdUser = '"+ IdUsers.IdUser+"'\n" +
                            "group by L.IdLocation, L.NameLocation,H.IdHS, L.Images \n" +
                            "order by H.IdHS desc";
                    ResultSet rs = statement.executeQuery(sql);
                    while (rs.next()) {
                        list.add(new Location(rs.getInt("IdLocation"), rs.getString("NameLocation"),"","",
                                rs.getString("Images")));
                    }
                    rs.close();
                    statement.close();
                    connection.close();
                    //show inform bonus view
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i <list.size(); i++){
                                Location location = list.get(i);
                                com.example.simpletravel.model.Temp.IdLocation.IdLocations = location.getIdLocation(); //set idLocation
                                //send data to fragment detail location
                                LocationTemp.location = location;
                                BonusView.setText(location.getNameLocation());//set textview name location
                                byte[] decodedString = Base64.decode(String.valueOf(location.getImageLocation()), Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                background.setImageBitmap(decodedByte);
                            }
                        }
                    });
                } catch (Exception ex) {
                    Log.e("HistoryView", ex.getMessage());
                }
            }
        }).start();
    }
}