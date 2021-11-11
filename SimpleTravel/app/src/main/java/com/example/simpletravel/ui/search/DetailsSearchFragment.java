package com.example.simpletravel.ui.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simpletravel.R;
import com.example.simpletravel.adapter.CommentAdapter;
import com.example.simpletravel.adapter.PhotoAdapter;
import com.example.simpletravel.model.Comment;
import com.example.simpletravel.model.Photo;
import com.example.simpletravel.model.Services;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsSearchFragment extends Fragment {

    public static final String TAG1 = DetailsSearchFragment.class.getName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailsSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsSearchFragment newInstance(String param1, String param2) {
        DetailsSearchFragment fragment = new DetailsSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //create variable
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private View view;
    private DetailSearchViewModel detailSearchViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.search_txt_Back_DetailSearch){
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        detailSearchViewModel =
                new ViewModelProvider(this).get(DetailSearchViewModel.class);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details_search, container, false);
        DetailSearchConstructor();
        PhotoControll();//Viewpager photo in layout details
        ShowDetailItemSearch();
        ShowDeatailComment();
        return view;
    }

    //show all comment in service
    //create variable function
    private CommentAdapter commentAdapter;
    private ListView listViewComment;
    private void ShowDeatailComment() {

        detailSearchViewModel.getConmment().observe(getViewLifecycleOwner(), new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {

                commentAdapter = new CommentAdapter(comments);
                listViewComment.setAdapter(commentAdapter);
            }
        });

    }

    //show detail item choose
    //create variable
    public void ShowDetailItemSearch() {

        detailSearchViewModel.getServices().observe(getViewLifecycleOwner(), new Observer<List<Services>>() {
            @Override
            public void onChanged(List<Services> servicesList) {

                NameService.setText(servicesList.get(0).getName());
                Rating.setText(String.valueOf(servicesList.get(0).getRatings()));
                Quantity.setText(String.valueOf(servicesList.get(0).getQuantity() + " " + "đánh giá"));
                Summary.setText(servicesList.get(0).getSummary());
                URL.setText(servicesList.get(0).getURL());
                Phone.setText(servicesList.get(0).getPhone());
                NameStatus.setText(servicesList.get(0).getNameStatus());
                TimeOpen.setText(servicesList.get(0).getOpenTime());
                SuggestTime.setText(String.valueOf(servicesList.get(0).getSuggestTime() + " " + "giờ"));
                Address.setText(servicesList.get(0).getAddress());
            }
        });

    }

    //create variable
    private TextView Back, NameService, Rating, Quantity, Summary, URL, Phone, TimeOpen, NameStatus, SuggestTime, Address ;

    private void DetailSearchConstructor() {
        Back = view.findViewById(R.id.search_txt_Back_DetailSearch);
        Back.setOnClickListener(onClickListener);

        NameService = view.findViewById(R.id.search_details_NameService);
        Rating = view.findViewById(R.id.search_details_Rating);
        Quantity = view.findViewById(R.id.search_details_Quantity);
        Summary = view.findViewById(R.id.search_details_Summary);
        URL = view.findViewById(R.id.search_details_URL);
        Phone = view.findViewById(R.id.search_details_Phone);
        NameStatus= view.findViewById(R.id.search_details_NameStatus);
        TimeOpen= view.findViewById(R.id.search_details_TimeOpen);
        SuggestTime= view.findViewById(R.id.search_details_SuggestTime);
        Address= view.findViewById(R.id.search_details_Address);

        listViewComment = view.findViewById(R.id.search_lv_Rating);


    }

    private void PhotoControll() {
        viewPager = view.findViewById(R.id.search_viewpager);
//        circleIndicator = view.findViewById(R.id.search_Circle_Indicator);

        photoAdapter = new PhotoAdapter(getActivity(), getListPhoto());
        viewPager.setAdapter(photoAdapter);

//        circleIndicator.setViewPager(viewPager);
//        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }

    private List<Photo> getListPhoto(){
        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo(R.drawable.img));
        photos.add(new Photo(R.drawable.img));
        photos.add(new Photo(R.drawable.img));

        return photos;
    }

}