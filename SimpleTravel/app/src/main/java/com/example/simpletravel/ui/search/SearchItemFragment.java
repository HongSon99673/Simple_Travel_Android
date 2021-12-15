package com.example.simpletravel.ui.search;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simpletravel.R;
import com.example.simpletravel.adapter.ItemSearchAdapter;
import com.example.simpletravel.adapter.RecentlyRearchAdapter;
import com.example.simpletravel.asynctask.search.ListServiceAsyncTask;
import com.example.simpletravel.asynctask.search.RecentlyAsyncTask;
import com.example.simpletravel.viewmodel.DiscoveryViewModel;
import com.example.simpletravel.viewmodel.SearchViewModel;

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
        view = inflater.inflate(R.layout.fragment_search_item, container, false);

        ItemSearchControll();
        RecentlyList();//show list service recently
        CancelControll();//back to fragment Main Search
        ShowGooglemapRecently();//show all service in location here user
        return view;
    }

    //create variable
    private TextView Recently;

    private void ShowGooglemapRecently() {
        Recently = view.findViewById(R.id.search_txt_Recently);
        Recently.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GoogleMapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CancelControll() {
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
    }

    //craete variable
    private ListView listView;
    private RecentlyRearchAdapter recentlyRearchAdapter;
    private DiscoveryViewModel discoveryViewModel;

    private void RecentlyList() {
        //create thread new handle list view
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView = view.findViewById(R.id.search_itemsearch_LV);
                        new RecentlyAsyncTask(getContext(),listView).execute();
                    }
                });
            }
        }).start();
    }

    //create variable function
    private AutoCompleteTextView autoCompleteTextView;
    private ItemSearchAdapter itemSearchAdapter;
    private SearchViewModel searchViewModel;

    private void ItemSearchControll() {
        //find id autocomplete not exits
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        autoCompleteTextView = view.findViewById(R.id.item_search_act_Search);
                        new ListServiceAsyncTask(getContext(),autoCompleteTextView).execute();
                    }
                });
            }
        }).start();
    }
}