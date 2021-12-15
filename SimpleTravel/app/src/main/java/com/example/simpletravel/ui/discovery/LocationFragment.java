package com.example.simpletravel.ui.discovery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpletravel.R;
import com.example.simpletravel.adapter.ItemLocationAdapter;
import com.example.simpletravel.asynctask.discovery.CoordinatesAsyncTask;
import com.example.simpletravel.asynctask.discovery.ItemFoodAllAsyncTask;
import com.example.simpletravel.asynctask.discovery.ItemFoodAsyncTask;
import com.example.simpletravel.asynctask.discovery.ItemPlayAllAsyncTask;
import com.example.simpletravel.asynctask.discovery.ItemPlayAsyncTask;
import com.example.simpletravel.asynctask.discovery.ItemStayAllAsyncTask;
import com.example.simpletravel.asynctask.discovery.ItemStayAsyncTask;
import com.example.simpletravel.asynctask.discovery.LocationAsyncTask;
import com.example.simpletravel.model.Location;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.model.Temp.LocationTemp;
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
public class LocationFragment extends Fragment implements OnMapReadyCallback{

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
            //view play all
            if (view.getId() == R.id.discovery_txt_PlayAll){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                rcv_Play.setLayoutManager(layoutManager);
                                new ItemPlayAllAsyncTask(getContext(),rcv_Play).execute();
                            }
                        });
                    }
                }).start();
            }
            //view stay all
            if (view.getId() == R.id.discovery_txt_StayAll){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                rcv_Stay.setLayoutManager(layoutManager);
                                new ItemStayAllAsyncTask(getContext(), rcv_Stay).execute();
                            }
                        });
                    }
                }).start();
            }
            //view food all
            if (view.getId() == R.id.discovery_txt_FoodAll){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                rcv_Food.setLayoutManager(layoutManager);
                                new ItemFoodAllAsyncTask(getContext(), rcv_Food).execute();
                            }
                        });
                    }
                }).start();
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
        SetInformation();
        //show gg map in fragment
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);

        PlayController();//handle recycle view play
        StayController();//handle recycle view stay
        FoodController();//handle recycle view food

        return view;
    }

    //create variable
    private TextView Back, PlayAll, StayAll, FoodAll;
    private TextView Name, Title;
    private ImageView imageView;

    private void ContructorLocation() {
        Back = view.findViewById(R.id.search_txt_Back_DetailSearch);
        Back.setOnClickListener(onClickListener);
        Name = view.findViewById(R.id.discovery_location_NameService);
        Title = view.findViewById(R.id.discovery_location_Title);
        imageView = view.findViewById(R.id.discovery_location_Avatar);
        mapView = view.findViewById(R.id.discovery_google_Map);
        rcv_Play = view.findViewById(R.id.discovery_location_rcv_Play);
        rcv_Stay = view.findViewById(R.id.discovery_location_rcv_Stay);
        rcv_Food = view.findViewById(R.id.discovery_location_rcv_Food);

        PlayAll = view.findViewById(R.id.discovery_txt_PlayAll);
        PlayAll.setPaintFlags(PlayAll.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        PlayAll.setOnClickListener(onClickListener);

        StayAll = view.findViewById(R.id.discovery_txt_StayAll);
        StayAll .setPaintFlags(StayAll .getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        StayAll.setOnClickListener(onClickListener);

        FoodAll = view.findViewById(R.id.discovery_txt_FoodAll);
        FoodAll.setPaintFlags(FoodAll.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        FoodAll.setOnClickListener(onClickListener);

    }

    //create variable
    private RecyclerView rcv_Stay;
    private ItemLocationAdapter itemLocationAdapter2;

    private void StayController() {
       new Thread(new Runnable() {
           @Override
           public void run() {
               getActivity().runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                       rcv_Stay.setLayoutManager(layoutManager);
                       new ItemStayAsyncTask(getContext(), rcv_Stay).execute();
                   }
               });
           }
       }).start();
    }

    //create variable
    private RecyclerView rcv_Play;
    private ItemLocationAdapter itemLocationAdapter;

    private void PlayController() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        rcv_Play.setLayoutManager(layoutManager);
                        new ItemPlayAsyncTask(getContext(),rcv_Play).execute();
                    }
                });
            }
        }).start();

    }

    //create variable
    private ItemLocationAdapter itemLocationAdapter1;
    private RecyclerView rcv_Food;

    private void FoodController() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        rcv_Food.setLayoutManager(layoutManager);
                        new ItemFoodAsyncTask(getContext(), rcv_Food).execute();
                    }
                });
            }
        }).start();
    }

    //get data from fragment Discovery
    private void SetInformation(){
        //set images avatar
        byte[] decodedString = Base64.decode(String.valueOf(LocationTemp.location.getImageLocation()), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte );
        //set Name location
        Name.setText(LocationTemp.location.getNameLocation());
        //set title name
        Title.setText(LocationTemp.location.getNameLocation());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        locationViewModel.getCoordinates().observe(getViewLifecycleOwner(), new Observer<List<Services>>() {
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
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(s.getLatitude(), s.getLongitude()), 10));
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