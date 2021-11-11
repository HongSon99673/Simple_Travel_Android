package com.example.simpletravel.ui.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.simpletravel.R;
import com.example.simpletravel.adapter.VinicityLocationAdapter;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.ui.discovery.DiscoveryFragment;


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

    //
    private List<Services> list;
    private VinicityLocationAdapter vinicityLocationAdapter;
    private View view;
    private SearchViewModel searchViewModel;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.txt_Search_Search){
                GotoSearchFragment();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        view = inflater.inflate(R.layout.fragment_main_search, container, false);
        VinicityLocationController();
        SearchControll();
        return view;
    }


    //Create constructor
    private TextView txtSearch;
    private RecyclerView rcv_VicinityLocation_Search;

    private void SearchControll() {
        txtSearch = view.findViewById(R.id.txt_Search_Search);
        txtSearch.setOnClickListener(onClickListener);

        rcv_VicinityLocation_Search = view.findViewById(R.id.rcv_VicinityLocation_Search);

    }
    public void GotoSearchFragment(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout_search, new SearchItemFragment());
        transaction.addToBackStack(SearchItemFragment.TAG);
        transaction.commit();

    }


    private void VinicityLocationController() {

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        rcv_VicinityLocation_Search.setLayoutManager(layoutManager);
//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
//        rcv_VicinityLocation_Search.addItemDecoration(itemDecoration);

        searchViewModel.getServices().observe(getViewLifecycleOwner(), new Observer<List<Services>>() {
            @Override
            public void onChanged(List<Services> services) {
                vinicityLocationAdapter = new VinicityLocationAdapter(services);
                rcv_VicinityLocation_Search.setAdapter(vinicityLocationAdapter);
            }
        });

    }
}