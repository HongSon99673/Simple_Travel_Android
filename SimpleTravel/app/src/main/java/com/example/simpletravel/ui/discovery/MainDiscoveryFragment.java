package com.example.simpletravel.ui.discovery;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simpletravel.R;
import com.example.simpletravel.activity.AccountActivity;
import com.example.simpletravel.adapter.HistoryAdapter;
import com.example.simpletravel.adapter.HotelLocationAdapter;
import com.example.simpletravel.adapter.ViewTravelAdapter;
import com.example.simpletravel.model.Location;
import com.example.simpletravel.model.Services;

import java.sql.SQLException;
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
    private DiscoveryViewModel discoveryViewModel;
    private RecyclerView rcv_History_View_Discovery, rcv_HotelSmall_View_Discovery,
            rcv_ViewTravel_Discovery;
    private HistoryAdapter historyAdapter;
    private HotelLocationAdapter hotelAdapter;
    private ViewTravelAdapter viewTravelAdapter;
    private CircleImageView imgAvatar;
    private View root;


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.img_Avatar) {
                startActivity(new Intent(getActivity(), AccountActivity.class));
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //star thread get data
        try {
            discoveryViewModel = new DiscoveryViewModel();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        new Thread(discoveryViewModel).start();
//        discoveryViewModel = new ViewModelProvider(this).get(DiscoveryViewModel.class);

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_main_discovery, container, false);

        rcv_History_View_Discovery = root.findViewById(R.id.rcv_History_View_Discovery);
        HistoryController();

        rcv_HotelSmall_View_Discovery = root.findViewById(R.id.rcv_HotelSmall_View_Discovery);
        HotelController();

        rcv_ViewTravel_Discovery = root.findViewById(R.id.rcv_ViewTravel_Discovery);
        ViewTravelController();

        ViewConstructor();
        return root;
    }

    private void ViewConstructor() {
        imgAvatar = root.findViewById(R.id.img_Avatar);
        imgAvatar.setOnClickListener(onClickListener);
    }

    private void ViewTravelController() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rcv_ViewTravel_Discovery.setLayoutManager(layoutManager);
        discoveryViewModel.getLocations().observe(getViewLifecycleOwner(), new Observer<List<Location>>() {
            @Override
            public void onChanged(List<Location> locations) {
                viewTravelAdapter = new ViewTravelAdapter(locations);
                rcv_ViewTravel_Discovery.setAdapter(viewTravelAdapter);
            }
        });
    }


    private void HotelController() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                rcv_HotelSmall_View_Discovery.setLayoutManager(layoutManager);
                discoveryViewModel.getLocations().observe(getViewLifecycleOwner(), new Observer<List<Location>>() {
                    @Override
                    public void onChanged(List<Location> locations) {
                        hotelAdapter = new HotelLocationAdapter(locations);
                        rcv_HotelSmall_View_Discovery.setAdapter(hotelAdapter);
                    }
                });
            }
        });
    }

    private void HistoryController() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                rcv_History_View_Discovery.setLayoutManager(layoutManager);
                discoveryViewModel.getServices().observe(getViewLifecycleOwner(), new Observer<List<Services>>() {
                    @Override
                    public void onChanged(List<Services> services) {
                        historyAdapter = new HistoryAdapter(getActivity(),services);
                        rcv_History_View_Discovery.setAdapter(historyAdapter);
                    }
                });
            }
        });
    }
}