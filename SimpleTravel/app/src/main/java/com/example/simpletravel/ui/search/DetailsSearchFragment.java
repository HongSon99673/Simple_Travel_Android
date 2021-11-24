package com.example.simpletravel.ui.search;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.R;
import com.example.simpletravel.adapter.CommentAdapter;
import com.example.simpletravel.adapter.DialogListTripAdapter;
import com.example.simpletravel.adapter.PhotoAdapter;
import com.example.simpletravel.model.Comment;
import com.example.simpletravel.model.Temp.IdUsers;
import com.example.simpletravel.model.ListTrip;
import com.example.simpletravel.model.Photo;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.ui.evaluate.EvaluateActivity;
import com.example.simpletravel.ui.planning.PlanningViewModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsSearchFragment extends Fragment  {

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
    private PlanningViewModel planningViewModel;

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
            if (view.getId() == R.id.search_txt_Back_DetailSearch) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
            if (view.getId() == R.id.search_txt_Choose_DetailSearch) {
                DialogSaveServiceinTrip(Gravity.BOTTOM);
//                DialogSaveServiceinTrip1();
            }
            if (view.getId() == R.id.search_btn_Rating){
                Intent intent = new Intent(getActivity(), EvaluateActivity.class);
                startActivity(intent);
            }
            if (view.getId() == R.id.search_details_URL){
                //call wed site Services
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(URL.getText().toString()));
                startActivity(i);
            }
            if (view.getId() == R.id.search_details_Phone){
                //call phone
                Intent in = new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse("tel:" + Phone.getText().toString()));
                startActivity(in);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        detailSearchViewModel =
                new ViewModelProvider(this).get(DetailSearchViewModel.class);
        planningViewModel =
                new ViewModelProvider(this).get(PlanningViewModel.class);

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


    //show dialog choose service in trip
    private TextView CreateTrip;
    private Button Completed;
    private CheckBox checkBox;
    private DialogListTripAdapter dialogListTripAdapter;
    private PreparedStatement preparedStatement;

    private void DialogSaveServiceinTrip(int gravity) {
        final Dialog dialog = new Dialog(getActivity());
        View view1 = getLayoutInflater().inflate(R.layout.dialog_list_trip, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view1);

        View view2 = getLayoutInflater().inflate(R.layout.item_dialog_listview_trip, null);
        checkBox = view2.findViewById(R.id.item_dialoglistrip_rb_Choose);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = gravity;
        window.setAttributes(windowAttribute);

        //Gravity is bottom then dialog close
        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        dialog.show();

        //show list view trip
        ListView listView = view1.findViewById(R.id.dialoglistrip_lv_Trip);
        planningViewModel.getListTrip().observe(getViewLifecycleOwner(), new Observer<List<ListTrip>>() {
            @Override
            public void onChanged(List<ListTrip> listTrips) {
                dialogListTripAdapter = new DialogListTripAdapter(getActivity(), R.layout.item_dialog_listview_trip, listTrips);
                listView.setAdapter(dialogListTripAdapter);
                listView.setClickable(true);
                int lenght = listTrips.size();//get size list ListTrip
                //event click button completed
                Completed = dialog.findViewById(R.id.dialog_list_btn_Completed);
                Completed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //checkbox is true then when click button perfect add Service in Trip this
                        for (int i =0; i< lenght; i++ ){
                            ListTrip listTrip = listTrips.get(i);
                            if (listTrip.isChecked()) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                                            connection = jdbcControllers.ConnectionData();
                                            Log.e("Log", "Connect true");
                                            String sql = "insert into DetailsPlanning (IdPlan, IdService) values ('" + listTrip.getIdTrip() +
                                                    "','" + IdTemp + "')";
                                            preparedStatement = connection
                                                    .prepareStatement(sql);
                                            preparedStatement.executeUpdate();
                                            connection.close();
                                            preparedStatement.close();
                                        } catch (Exception ex) {
                                            Log.e("Log", ex.toString());
                                        }
                                    }
                                }).start();
                                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                        Toast.makeText(getActivity(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();//close dialog
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        checkBox.setChecked(true);
//                        IdPlan = dialogListTripAdapter.getItem(i).getIdTrip();
                    }
                });
            }
        });


        //event click text Create Trip
        CreateTrip = dialog.findViewById(R.id.dialog_list_txt_Create_Trip);
        CreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCreateTripDialog(Gravity.BOTTOM);
            }
        });
    }

    //show dialog Create Trip
    //create function handle dialog
    //Send data give SQL
    private JDBCControllers jdbcControllers;
    private Connection connection;

    private void OpenCreateTripDialog(int gravity) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_create_trip);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = gravity;
        window.setAttributes(windowAttribute);

        //Gravity is bottom then dialog close
        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        EditText nameTrip = dialog.findViewById(R.id.dialog_txt_NameTrip);
        TextView aLert = dialog.findViewById(R.id.dialoglisttrip_txt_NameTrip_Alert);
        Button createTrip = dialog.findViewById(R.id.dialog_btn_CreateTrip);

        createTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aLert.getText().toString().isEmpty()) {
                    aLert.setText("Tên chuyến đi không để trống");
                    aLert.setTextColor(getResources().getColor(R.color.INK_RED));
                } else {
                    aLert.setText("");
                    String NameTrip = nameTrip.getText().toString();
                    int IdUser = IdUsers.IdUser;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                                connection = jdbcControllers.ConnectionData();
                                Log.e("Log", "Connect true");
                                String sql = "Insert into Planning " +
                                        " ( NamePlan,IdUser) values " + "('" + NameTrip + "','" + IdUser + "')";
                                PreparedStatement preparedStatement = connection
                                        .prepareStatement(sql);
                                preparedStatement.executeUpdate();
                                preparedStatement.close();
                                connection.close();
                            } catch (Exception ex) {
                                Log.e("Log", ex.toString());
                            }
                        }
                    }).start();
                    Toast.makeText(getActivity(), "Chuyến đi đã tạo", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();//close dialog
                }
            }
        });
        dialog.show();
    }

    //show detail item choose
    //create variable
    private int IdTemp;

    public void ShowDetailItemSearch() {

        detailSearchViewModel.getServices().observe(getViewLifecycleOwner(), new Observer<List<Services>>() {
            @Override
            public void onChanged(List<Services> servicesList) {
                IdTemp = servicesList.get(0).getID();

                NameService.setText(servicesList.get(0).getName());
                if (servicesList.get(0).getRatings() == 1) {
                    Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
                }
                if (servicesList.get(0).getRatings() == 2) {
                    Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
                    Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
                }
                if (servicesList.get(0).getRatings() == 3) {
                    Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
                    Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
                    Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
                }
                if (servicesList.get(0).getRatings() == 4) {
                    Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
                    Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
                    Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
                    Star4.setImageResource(R.drawable.outline_star_purple500_black_48);
                }
                if (servicesList.get(0).getRatings() == 4) {
                    Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
                    Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
                    Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
                    Star4.setImageResource(R.drawable.outline_star_purple500_black_48);
                    Star5.setImageResource(R.drawable.outline_star_purple500_black_48);
                }
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
    private TextView Back, NameService, Rating, Quantity, Summary, URL, Phone, TimeOpen, NameStatus, SuggestTime, Address;
    private TextView Choose;
    private ImageView Star1, Star2, Star3, Star4, Star5;
    private Button Ratings;

    private void DetailSearchConstructor() {
        Back = view.findViewById(R.id.search_txt_Back_DetailSearch);
        Back.setOnClickListener(onClickListener);

        NameService = view.findViewById(R.id.search_details_NameService);
        Star1 = view.findViewById(R.id.details_star_1);
        Star2 = view.findViewById(R.id.details_star_2);
        Star3 = view.findViewById(R.id.details_star_3);
        Star4 = view.findViewById(R.id.details_star_4);
        Star5 = view.findViewById(R.id.details_star_5);
        Quantity = view.findViewById(R.id.search_details_Quantity);
        Summary = view.findViewById(R.id.search_details_Summary);
        URL = view.findViewById(R.id.search_details_URL);
        URL.setOnClickListener(onClickListener);
        Phone = view.findViewById(R.id.search_details_Phone);
        Phone.setOnClickListener(onClickListener);
        NameStatus = view.findViewById(R.id.search_details_NameStatus);
        TimeOpen = view.findViewById(R.id.search_details_TimeOpen);
        SuggestTime = view.findViewById(R.id.search_details_SuggestTime);
        Address = view.findViewById(R.id.search_details_Address);

        listViewComment = view.findViewById(R.id.search_lv_Rating);

        Choose = view.findViewById(R.id.search_txt_Choose_DetailSearch);
        Choose.setOnClickListener(onClickListener);

        Ratings = view.findViewById(R.id.search_btn_Rating);
        Ratings.setOnClickListener(onClickListener);


    }

    private void PhotoControll() {
        viewPager = view.findViewById(R.id.search_viewpager);
//        circleIndicator = view.findViewById(R.id.search_Circle_Indicator);

        photoAdapter = new PhotoAdapter(getActivity(), getListPhoto());
        viewPager.setAdapter(photoAdapter);

//        circleIndicator.setViewPager(viewPager);
//        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }

    private List<Photo> getListPhoto() {
        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo(R.drawable.avartar1));
        photos.add(new Photo(R.drawable.avartar1));
        photos.add(new Photo(R.drawable.avartar1));

        return photos;
    }

}