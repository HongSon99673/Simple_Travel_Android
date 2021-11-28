package com.example.simpletravel.adapter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.R;
import com.example.simpletravel.model.SavedItem;
import com.example.simpletravel.model.Temp.IdServices;
import com.example.simpletravel.model.Temp.IdTrip;
import com.example.simpletravel.model.Temp.IdUsers;
import com.example.simpletravel.ui.search.DetailsSearchFragment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SavedItemAdapter extends RecyclerView.Adapter<SavedItemAdapter.ListSavedItem> {
    private List<SavedItem> savedItemList;
    private Connection connection;
    private JDBCControllers jdbcControllers;
    private Statement statement;
    private View view;

    public SavedItemAdapter(List<SavedItem> savedItemList) {
        this.savedItemList = savedItemList;
    }

    @NonNull
    @Override
    public ListSavedItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saveitem_fragment_planning,
                parent, false);

        return new ListSavedItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSavedItem holder, int position) {
        SavedItem savedItem = savedItemList.get(position);
        if (savedItem == null) {
            return;
        }

        holder.NameType.setText(savedItem.getNameType());
        holder.NameService.setText(savedItem.getNameService());
//        holder.Rating.setText(String.valueOf(savedItem.getRating()));
        if (savedItem.getRating() == 1) {
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if (savedItem.getRating() == 2) {
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if (savedItem.getRating() == 3) {
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if (savedItem.getRating() == 4) {
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star4.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if (savedItem.getRating() == 5) {
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star4.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star5.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        holder.Summary.setText(savedItem.getSummary());
        holder.TimOpen.setText(savedItem.getTimeOpen());
        holder.NamePlane.setText(savedItem.getNamePlan());
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IdServices.IdService = savedItem.getIdService();//set Id service
                OpenCreateTripDialog(Gravity.BOTTOM);//show dialog delete

            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IdServices.IdService = savedItem.getIdService();//set Id Service
                FragmentActivity activity = (FragmentActivity) view.getContext();
                Fragment myFragment = new DetailsSearchFragment();
                activity.getSupportFragmentManager().beginTransaction().add(
                        R.id.planning_frameLayout_Main, myFragment).addToBackStack(DetailsSearchFragment.TAG1).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (savedItemList != null) {
            return savedItemList.size();
        }
        return 0;
    }

    public class ListSavedItem extends RecyclerView.ViewHolder {
        private TextView NameType, NameService, Rating, Summary, TimOpen, NamePlane, Delete;
        private ImageView Star1, Star2, Star3, Star4, Star5;
        private LinearLayout linearLayout;

        public ListSavedItem(@NonNull View itemView) {
            super(itemView);
            NameType = itemView.findViewById(R.id.item_savedtitem_txt_NameType);
            NameService = itemView.findViewById(R.id.item_savedtitem_txt_NameService);
            Star1 = itemView.findViewById(R.id.saveitem_star_1);
            Star2 = itemView.findViewById(R.id.saveitem_star_2);
            Star3 = itemView.findViewById(R.id.saveitem_star_3);
            Star4 = itemView.findViewById(R.id.saveitem_star_4);
            Star5 = itemView.findViewById(R.id.saveitem_star_5);
            Summary = itemView.findViewById(R.id.item_savedtitem_txt_Summary);
            TimOpen = itemView.findViewById(R.id.item_savedtitem_txt_TimeOpen);
            NamePlane = itemView.findViewById(R.id.item_savedtitem_txt_NamePlan);
            linearLayout = itemView.findViewById(R.id.planning_saveitem_Layout);
            Delete = itemView.findViewById(R.id.planning_item_Delete);
        }
    }

    private void OpenCreateTripDialog(int gravity) {
        final Dialog dialog = new Dialog(view.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirm);

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

        Button No = dialog.findViewById(R.id.dialog_Confirm_No);
        Button Yes = dialog.findViewById(R.id.dialog_Confirm_Yes);

        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                            connection = jdbcControllers.ConnectionData();
                            Log.e("Log", "True");
                            statement = connection.createStatement();

                            String sql = "delete from DetailsPlanning where IdService = '" + IdServices.IdService + "' " +
                                    "and IdPlan ='" + IdTrip.IdTrips + "'";
                            ResultSet resultSet = statement.executeQuery(sql);
                            resultSet.close();
                            statement.close();
                            connection.close();

                        } catch (Exception ex) {
                            Log.e("Log", ex.getMessage());
                        }
                    }
                }).start();
                notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
