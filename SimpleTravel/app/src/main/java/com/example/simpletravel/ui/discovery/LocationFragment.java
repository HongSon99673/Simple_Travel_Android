package com.example.simpletravel.ui.discovery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simpletravel.R;
import com.example.simpletravel.adapter.ItemLocationAdapter;
import com.example.simpletravel.model.Location;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.my_interface.IClickItemService;
import com.example.simpletravel.viewmodel.LocationViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback, IClickItemService {

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
            if (view.getId() == R.id.search_txt_Back_DetailSearch) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        }
    };
    //create variable
    private boolean isPermissionGranted;
    private MapView mapView;
    private Marker marker;

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
    private IClickItemService iClickItemService = this;

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

        //show gg map in fragment
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);

        return view;
    }

    //create variable
    private TextView Back;
    private TextView Name, Title;

    private void ContructorLocation() {
        Back = view.findViewById(R.id.search_txt_Back_DetailSearch);
        Back.setOnClickListener(onClickListener);

        Name = view.findViewById(R.id.discovery_location_NameService);
        Title = view.findViewById(R.id.discovery_location_Title);

        mapView = view.findViewById(R.id.discovery_google_Map);


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

    //
    public IClickItemService getIClickItemService() {
        return iClickItemService;
    }

    @Override
    public void onClickItem(Location location) {
        Name.setText(location.getNameLocation());
        Title.setText(location.getNameLocation());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        locationViewModel.getFood().observe(getViewLifecycleOwner(), new Observer<List<Services>>() {
            @Override
            public void onChanged(List<Services> services) {
                for (int i = 0; i < services.size(); i++) {
                    Services s = (Services) services.get(i);
                    //create marker
                    marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(s.getLatitude(), s.getLongitude()))
                            .title(s.getName())
                            .snippet(s.getAddress())
                    );
                    //Zoom
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(s.getLatitude(), s.getLongitude()), 15));
                }
            }
        });

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
}