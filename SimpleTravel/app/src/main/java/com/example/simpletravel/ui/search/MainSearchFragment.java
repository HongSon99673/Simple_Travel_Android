package com.example.simpletravel.ui.search;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.R;
import com.example.simpletravel.adapter.LocationAdapter;
import com.example.simpletravel.asynctask.search.LoveLocationAsyncTask;
import com.example.simpletravel.asynctask.search.VicinityAllAsyncTask;
import com.example.simpletravel.asynctask.search.VicinityAsyncTask;
import com.example.simpletravel.model.Location;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.model.Temp.IdLocation;
import com.example.simpletravel.model.Temp.IdUsers;
import com.example.simpletravel.model.Temp.LocationTemp;
import com.example.simpletravel.ui.discovery.LocationFragment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainSearchFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainSearchFragment newInstance(String param1, String param2) {
        MainSearchFragment fragment = new MainSearchFragment();
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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.txt_Search_Search){
                GotoSearchFragment();
            }
            //show all list view recently
            if (view.getId() == R.id.txt_AllList_Search){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                rcv_VicinityLocation_Search.setLayoutManager(layoutManager);
                                new VicinityAllAsyncTask(getContext(), rcv_VicinityLocation_Search).execute();
                            }
                        });
                    }
                }).start();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        view = inflater.inflate(R.layout.fragment_main_search, container, false);
        SearchControll();//function constructor
        VinicityLocationController();//function show list vinicity Location
        ShowLoveLocation();

        return view;
    }
    //show
    private LocationAdapter locationAdapter;
    private GridView gridView;

    private void ShowLoveLocation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               getActivity().runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       //show top 4 service have love best
                       new LoveLocationAsyncTask(getContext(), gridView).execute();
                   }
               });
            }
        }).start();
    }

    //Create constructor
    private TextView txtSearch, txtViewAll;
    private RecyclerView rcv_VicinityLocation_Search;

    private void SearchControll() {
        txtSearch = view.findViewById(R.id.txt_Search_Search);
        txtSearch.setOnClickListener(onClickListener);

        txtViewAll = view.findViewById(R.id.txt_AllList_Search);
        txtViewAll.setOnClickListener(onClickListener);
        txtViewAll.setPaintFlags(txtViewAll.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        rcv_VicinityLocation_Search = view.findViewById(R.id.search_rv_Vicinity);//find id recycle view vicinity
        gridView = view.findViewById(R.id.search_gv_LoveLocation);//find id grid view
    }
    public void GotoSearchFragment(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout_search, new SearchItemFragment());
        transaction.addToBackStack(SearchItemFragment.TAG);
        transaction.commit();

    }

    private void VinicityLocationController() {
       new Thread(new Runnable() {
           @Override
           public void run() {
               getActivity().runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                       rcv_VicinityLocation_Search.setLayoutManager(layoutManager);
                       new VicinityAsyncTask(getContext(), rcv_VicinityLocation_Search).execute();
                   }
               });
           }
       }).start();
    }
}