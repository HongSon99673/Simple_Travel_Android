package com.example.simpletravel.ui.discovery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.simpletravel.R;
import com.example.simpletravel.adapter.HistoryAdapter;
import com.example.simpletravel.adapter.HotelAdapter;
import com.example.simpletravel.databinding.FragmentDiscoveryBinding;
import com.example.simpletravel.model.Services;


import java.util.List;


public class DiscoveryFragment extends Fragment {

    private FragmentDiscoveryBinding binding;
    private DiscoveryViewModel discoveryViewModel;
    private RecyclerView rcv_History_View_Discovery;
    private RecyclerView rcv_HotelSmall_View_Discovery;
    private HistoryAdapter historyAdapter;
    private HotelAdapter hotelAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {

        discoveryViewModel =
                new ViewModelProvider(this).get(DiscoveryViewModel.class);

        binding = FragmentDiscoveryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rcv_History_View_Discovery = root.findViewById(R.id.rcv_History_View_Discovery);
        HitoryController();

        rcv_HotelSmall_View_Discovery = root.findViewById(R.id.rcv_HotelSmall_View_Discovery);
        HotelController();


         return root;

    }

    private void HotelController() {

        rcv_HotelSmall_View_Discovery = binding.rcvHotelSmallViewDiscovery;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        rcv_HotelSmall_View_Discovery.setLayoutManager(layoutManager);

        discoveryViewModel.getServices().observe(getViewLifecycleOwner(), new Observer<List<Services>>() {
            @Override
            public void onChanged(List<Services> services) {
                hotelAdapter = new HotelAdapter(services);
                rcv_HotelSmall_View_Discovery.setAdapter(hotelAdapter);
            }
        });
    }

    private void HitoryController() {
        rcv_History_View_Discovery = binding.rcvHistoryViewDiscovery;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        rcv_History_View_Discovery.setLayoutManager(layoutManager);

        discoveryViewModel.getServices().observe(getViewLifecycleOwner(), new Observer<List<Services>>() {
            @Override
            public void onChanged(List<Services> services) {
                historyAdapter = new HistoryAdapter(services);
                rcv_History_View_Discovery.setAdapter(historyAdapter);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}