package com.example.simpletravel.ui.planning;

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
import com.example.simpletravel.adapter.SavedItemAdapter;
import com.example.simpletravel.model.SavedItem;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaveItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaveItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SaveItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaveItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SaveItemFragment newInstance(String param1, String param2) {
        SaveItemFragment fragment = new SaveItemFragment();
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
    private PlanningViewModel planningViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        planningViewModel = new ViewModelProvider(this).get(PlanningViewModel.class);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_save_item, container, false);

        UpdateSavedItem();

        return view;
    }
    //create variable
    private SavedItemAdapter savedItemAdapter;
    private RecyclerView recyclerView;

    private void UpdateSavedItem() {
        recyclerView = view.findViewById(R.id.planning_lv_SaveItemFragment);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        planningViewModel.getSavedItem().observe(getViewLifecycleOwner(), new Observer<List<SavedItem>>() {
            @Override
            public void onChanged(List<SavedItem> savedItems) {
                savedItemAdapter = new SavedItemAdapter(savedItems);
                recyclerView.setAdapter(savedItemAdapter );
            }
        });

    }
}