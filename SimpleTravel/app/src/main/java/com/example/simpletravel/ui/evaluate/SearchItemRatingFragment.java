package com.example.simpletravel.ui.evaluate;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simpletravel.R;
import com.example.simpletravel.adapter.HistoryAdapter;
import com.example.simpletravel.adapter.ItemEvaluateAdapter;
import com.example.simpletravel.adapter.ItemLocationAdapter;
import com.example.simpletravel.adapter.RecentlyRearchAdapter;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.model.Temp.IdServices;
import com.example.simpletravel.ui.discovery.DiscoveryViewModel;
import com.example.simpletravel.ui.search.SearchViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchItemRatingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchItemRatingFragment extends Fragment {

    public static final String TAG1 = SearchItemRatingFragment.class.getName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchItemRatingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchItemRatingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchItemRatingFragment newInstance(String param1, String param2) {
        SearchItemRatingFragment fragment = new SearchItemRatingFragment();
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
    private DiscoveryViewModel discoveryViewModel;
    private EvaluateViewModel evaluateViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        discoveryViewModel = new ViewModelProvider(this).get(DiscoveryViewModel.class);
        evaluateViewModel = new EvaluateViewModel();
        new Thread(evaluateViewModel).start();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_item_rating, container, false);
        EvaluateLVControll();//update history see
        RecentlyService();//list view histtory service
        BackEvaluate();//back fragment Evaluate
        return view;
    }

    //create variable
    private TextView Back;
    private void BackEvaluate() {
        Back = view.findViewById(R.id.evaluate_txt_Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });

    }

    //create variable
    private AutoCompleteTextView autoCompleteTextView;
    private ItemEvaluateAdapter itemEvaluateAdapter;

    private void EvaluateLVControll() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                autoCompleteTextView = view.findViewById(R.id.evaluate_ATC_Search);
                evaluateViewModel.getSearch().observe(getViewLifecycleOwner(), new Observer<List<Services>>() {
                    @Override
                    public void onChanged(List<Services> services) {
                        itemEvaluateAdapter = new ItemEvaluateAdapter(getActivity(), R.layout.item_evaluate_listview, services);
                        autoCompleteTextView.setAdapter(itemEvaluateAdapter);
                        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                IdServices.IdService = itemEvaluateAdapter.getItem(i).getID();//set Id Service
                                Intent intent = new Intent(getActivity(), EvaluateActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("location",itemEvaluateAdapter.getItem(i).getID());
                                bundle.putString("location1",itemEvaluateAdapter.getItem(i).getName());
                                bundle.putString("location2",itemEvaluateAdapter.getItem(i).getImages());
                                bundle.putString("location3",itemEvaluateAdapter.getItem(i).getAddress());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }

    //create variable
    private ListView listView;
    private RecentlyRearchAdapter recentlyRearchAdapter;

    private void RecentlyService(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView = view.findViewById(R.id.evaluate_item_lv_Recently);
                discoveryViewModel.getServices().observe(getViewLifecycleOwner(), new Observer<List<Services>>() {
                    @Override
                    public void onChanged(List<Services> services) {
                        recentlyRearchAdapter = new RecentlyRearchAdapter(getActivity(),R.layout.item_evaluate_listview, services);
                        listView.setAdapter(recentlyRearchAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                IdServices.IdService = recentlyRearchAdapter.getItem(i).getID();//set Idservice
                                Intent intent = new Intent(getActivity(), EvaluateActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("location",recentlyRearchAdapter.getItem(i).getID());
                                bundle.putString("location1",recentlyRearchAdapter.getItem(i).getName());
                                bundle.putString("location2",recentlyRearchAdapter.getItem(i).getImages());
                                bundle.putString("location3",recentlyRearchAdapter.getItem(i).getAddress());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }
}