package com.example.simpletravel.ui.search;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.R;
import com.example.simpletravel.adapter.ItemSearchAdapter;
import com.example.simpletravel.adapter.RecentlyRearchAdapter;
import com.example.simpletravel.model.Temp.IdServices;
import com.example.simpletravel.model.Temp.IdUsers;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.ui.discovery.DiscoveryViewModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

        discoveryViewModel =
                new ViewModelProvider(this).get(DiscoveryViewModel.class);
        view = inflater.inflate(R.layout.fragment_search_item, container, false);

        ItemSearchControll();
        RecentlyList();

        //event text view cancel
        Cancel = view.findViewById(R.id.itemsearch_txt_Cancel);
        Cancel.setPaintFlags(Cancel.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
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

    //craete variable
    private ListView listView;
    private RecentlyRearchAdapter recentlyRearchAdapter;
    private DiscoveryViewModel discoveryViewModel;

    private void RecentlyList() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView = view.findViewById(R.id.search_itemsearch_LV);
                discoveryViewModel.getServices().observe(getViewLifecycleOwner(), new Observer<List<Services>>() {
                    @Override
                    public void onChanged(List<Services> services) {
                        recentlyRearchAdapter = new RecentlyRearchAdapter(getActivity(),R.layout.item_evaluate_listview, services);
                        listView.setAdapter(recentlyRearchAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                IdServices.IdService = recentlyRearchAdapter.getItem(i).getID();//set Idservice
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.frameLayout_search, new DetailsSearchFragment());
                                transaction.addToBackStack(DetailsSearchFragment.TAG1);
                                transaction.commit();

                            }
                        });
                    }
                });
            }
        });
    }

    //create variable function
    private AutoCompleteTextView autoCompleteTextView;
    private ItemSearchAdapter itemSearchAdapter;
    private SearchViewModel searchViewModel;

    //Send data give SQL
    private JDBCControllers jdbcControllers;
    private Connection connection;
    private PreparedStatement preparedStatement;

    private void ItemSearchControll() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
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
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                                            connection = jdbcControllers.ConnectionData();
                                            Log.e("Log", "Connect true");
                                            String sql = "Insert into HistoryServices (IdService, IdUser) values " +
                                                    "('"+ idService + "','"+ IdUsers.IdUser+"')";
                                            PreparedStatement preparedStatement = connection
                                                    .prepareStatement(sql);
                                            preparedStatement.executeUpdate();
                                            preparedStatement.close();
                                        } catch (Exception ex) {
                                            Log.e("Log", ex.toString());
                                        }
                                    }
                                });
                                thread.start();

                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.frameLayout_search, new DetailsSearchFragment());
                                transaction.addToBackStack(DetailsSearchFragment.TAG1);
                                transaction.commit();
                            }
                        });
                    }
                });
            }
        });
    }

}