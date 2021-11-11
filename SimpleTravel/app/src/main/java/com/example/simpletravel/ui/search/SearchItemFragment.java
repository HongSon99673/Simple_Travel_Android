package com.example.simpletravel.ui.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpletravel.R;
import com.example.simpletravel.adapter.ItemSearchAdapter;
import com.example.simpletravel.model.IdServices;
import com.example.simpletravel.model.IdUsers;
import com.example.simpletravel.model.Services;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchItemFragment extends Fragment  {

    public static final String TAG = SearchItemFragment.class.getName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchItemFragment() {
        // Required empty public constructor
    }

    public static SearchItemFragment getInstance(){
        return  new SearchItemFragment();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchItemFragment newInstance(String param1, String param2) {
        SearchItemFragment fragment = new SearchItemFragment();
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
    private View view;
    private TextView Cancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        view = inflater.inflate(R.layout.fragment_search_item, container, false);

        ItemSearchControll();

        Cancel = view.findViewById(R.id.itemsearch_txt_Cancel);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });
        return view;
    }

    //create variable function
    private AutoCompleteTextView autoCompleteTextView;
    private ItemSearchAdapter itemSearchAdapter;
    private SearchViewModel searchViewModel;

    private void ItemSearchControll() {

        autoCompleteTextView = view.findViewById(R.id.item_search_act_Search);// find id AutoCompleteTextView
        searchViewModel.getServices().observe(getViewLifecycleOwner(), new Observer<List<Services>>() {
            @Override
            public void onChanged(List<Services> services) {
                itemSearchAdapter = new ItemSearchAdapter(getActivity(), R.layout.item_fragment_item_search, services);
                autoCompleteTextView.setAdapter(itemSearchAdapter);
                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        //send id service to Fragment Details
                        int idService = itemSearchAdapter.getItem(i).getID();
                        IdServices.IdService = idService;

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout_search, new DetailsSearchFragment());
                        transaction.addToBackStack(DetailsSearchFragment.TAG1);
                        transaction.commit();
                        Toast.makeText(getActivity(), itemSearchAdapter.getItem(i).toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}