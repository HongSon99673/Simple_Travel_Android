package com.example.simpletravel.ui.discovery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simpletravel.R;
import com.example.simpletravel.adapter.HistoryAdapter;
import com.example.simpletravel.adapter.ItemLocationAdapter;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.ui.search.DetailsSearchFragment;
import com.example.simpletravel.viewmodel.LocationViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG1 = LocationFragment.class.getName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationFragment newInstance(String param1, String param2) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    //event onclickListener
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.search_txt_Back_DetailSearch){
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
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
    //create variable function On create View
    private View view;
    private LocationViewModel locationViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //star thread get data is location
        locationViewModel = new LocationViewModel();
        new Thread(locationViewModel).start();
//        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_location, container, false);
        ContructorLocation();
        PlayController();//handle recycle view play
        StayController();//handle recycle view stay
        FoodController();//handle recycle view food
        return view;
    }

    //create variable
    private TextView Back;
    private void ContructorLocation() {
        Back = view.findViewById(R.id.search_txt_Back_DetailSearch);
        Back.setOnClickListener(onClickListener);

    }

    //create variable
    private RecyclerView rcv_Stay;
    private ItemLocationAdapter itemLocationAdapter2;

    private void StayController() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rcv_Stay = view.findViewById(R.id.discovery_location_rcv_Stay);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                rcv_Stay.setLayoutManager(layoutManager);
                locationViewModel.getStay().observe(getViewLifecycleOwner(), new Observer<List<Services>>() {
                    @Override
                    public void onChanged(List<Services> services) {
                        itemLocationAdapter2 = new ItemLocationAdapter(services);
                        rcv_Stay.setAdapter(itemLocationAdapter2);
                    }
                });
            }
        });
    }

    //create variable
    private RecyclerView rcv_Play;
    private ItemLocationAdapter itemLocationAdapter;

    private void PlayController() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rcv_Play = view.findViewById(R.id.discovery_location_rcv_Play);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                rcv_Play.setLayoutManager(layoutManager);
                locationViewModel.getPlay().observe(getViewLifecycleOwner(), new Observer<List<Services>>() {
                    @Override
                    public void onChanged(List<Services> services) {
                        itemLocationAdapter = new ItemLocationAdapter(services);
                        rcv_Play.setAdapter(itemLocationAdapter);
                    }
                });
            }
        });
    }
    //create variable
    private ItemLocationAdapter itemLocationAdapter1;
    private RecyclerView rcv_Food;

    private void FoodController() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rcv_Food = view.findViewById(R.id.discovery_location_rcv_Food);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                rcv_Food.setLayoutManager(layoutManager);
                locationViewModel.getFood().observe(getViewLifecycleOwner(), new Observer<List<Services>>() {
                    @Override
                    public void onChanged(List<Services> services) {
                        itemLocationAdapter1 = new ItemLocationAdapter(services);
                        rcv_Food.setAdapter(itemLocationAdapter1);
                    }
                });
            }
        });
    }
}