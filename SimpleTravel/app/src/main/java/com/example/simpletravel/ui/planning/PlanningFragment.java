package com.example.simpletravel.ui.planning;

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

import com.example.simpletravel.R;
import com.example.simpletravel.databinding.FragmentPlanningBinding;
import com.example.simpletravel.databinding.FragmentPlanningBinding;
import com.example.simpletravel.ui.search.MainSearchFragment;
import com.example.simpletravel.ui.search.SearchItemFragment;

public class PlanningFragment extends Fragment {

    private PlanningViewModel notificationsViewModel;
    private FragmentPlanningBinding binding;
    private  View root;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.txt_Trip_Planning){

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.planning_frameLayout, new TripPlanningFragment());
                transaction.addToBackStack(null);
                transaction.commit();

            }

            if(view.getId() == R.id.txt_Saved_Planning){

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.planning_frameLayout, new SaveItemFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }

        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(PlanningViewModel.class);

        binding = FragmentPlanningBinding.inflate(inflater, container, false);
         root = binding.getRoot();
        PlanningConstructor();//function create constructor
        InitFragment();//Initialization first Fragment

        return root;
    }

    private void InitFragment() {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.planning_frameLayout, new TripPlanningFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //create variable
    private TextView Trip, Saved, Order;

    private void PlanningConstructor() {
        //Planning Fragment
        Trip = root.findViewById(R.id.txt_Trip_Planning);
        Trip.setOnClickListener(onClickListener);

        Saved = root.findViewById(R.id.txt_Saved_Planning);
        Saved.setOnClickListener(onClickListener);

        Order = root.findViewById(R.id.txt_Order_Planning);
        Order.setOnClickListener(onClickListener);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}