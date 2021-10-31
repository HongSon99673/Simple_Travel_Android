package com.example.simpletravel.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.R;
import com.example.simpletravel.adapter.VinicityLocationAdapter;
import com.example.simpletravel.databinding.FragmentSearchBinding;
import com.example.simpletravel.databinding.FragmentSearchBinding;
import com.example.simpletravel.model.Services;

import java.util.List;

public class SearchFragment extends Fragment {

    private SearchViewModel homeViewModel;
    private FragmentSearchBinding binding;
    private List<Services> list;
    private VinicityLocationAdapter vinicityLocationAdapter;
    private RecyclerView rcv_VinicityLocation_Search;
    private View root;

    //event OnclickListener
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.txt_Search_Search){
                Fragment itemFragment = new ItemSearchFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                transaction.replace(R.id.nav_host_fragment_activity_main, itemFragment).commit();
            }

        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        rcv_VinicityLocation_Search = root.findViewById(R.id.rcv_VinicityLocation_Search);
        VinicityLocationController();

        SearchControll();

        return root;
    }

    //Create constructor
    private TextView txtSearch;
    private void SearchControll() {
        txtSearch = root.findViewById(R.id.txt_Search_Search);
        txtSearch.setOnClickListener(onClickListener);

    }

    private void VinicityLocationController() {

        rcv_VinicityLocation_Search = binding.rcvVinicityLocationSearch;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        rcv_VinicityLocation_Search.setLayoutManager(layoutManager);
        homeViewModel.getServices().observe(getViewLifecycleOwner(), new Observer<List<Services>>() {
            @Override
            public void onChanged(List<Services> services) {
                vinicityLocationAdapter = new VinicityLocationAdapter(services);
                rcv_VinicityLocation_Search.setAdapter(vinicityLocationAdapter);
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}