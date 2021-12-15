package com.example.simpletravel.ui.discovery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.R;
import com.example.simpletravel.databinding.FragmentDiscoveryBinding;
import com.example.simpletravel.model.Location;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.model.Temp.IdUsers;
import com.example.simpletravel.model.Users;
import com.example.simpletravel.ui.search.MainSearchFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class DiscoveryFragment extends Fragment {

    private FragmentDiscoveryBinding binding;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDiscoveryBinding.inflate(inflater, container, false);
         root = binding.getRoot();

        InsertUser();
        InitFragment();
         return root;

    }

    //create fragment first
    private void InitFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.discovery_frameLayout_Main, new MainDiscoveryFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //save information user
    //Send data give SQL
    private JDBCControllers jdbcControllers;
    private Connection connection;
    private GoogleSignInClient mGoogleSignInClient;
    private String imageString;

    private void InsertUser(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
//            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            //Add User if account not exist
            if (AccountExists(true)){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                            connection = jdbcControllers.ConnectionData();
                            Log.e("Gmail", "True");
                            String sql = "Insert into Users " +
                                    " ( Name, Gmail) values " + "(N'" + personName + "',N'" + personEmail + "')";
                            PreparedStatement preparedStatement = connection
                                    .prepareStatement(sql);

                            preparedStatement.executeUpdate();
                            preparedStatement.close();
                            connection.close();//close connect database
                        } catch (Exception ex) {
                            Log.e("Gmail", ex.toString());
                        }
                    }
                }).start();
            }
        }
    }
    //check email exits
    private Boolean AccountExists(Boolean isCheck) {
        isCheck = true;
        try {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            // Build a GoogleSignInClient with the options specified by gso.
            mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
            String Email = acct.getEmail();//get email
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            String sql = "select  IdUser, Gmail, Name from  Users where Gmail = '" + Email + "'";//check email exits
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String passcode = rs.getString("Gmail");
                //send data in activity account
                Users users = new Users(rs.getInt("IdUser"), rs.getString("Name"), rs.getString("Gmail"), "",
                        "", "", "", "", "");
                // send IdUser for DiscoveryViewModel check History
                IdUsers.IdUser = users.getIdUser();
                IdUsers.NameUser = users.getUserName();
                connection.close();//close connect to data
                rs.close();
                ps.close();
                //check email in database
                if (passcode == null) {
                    Log.e("Email not exits", "True");
                } else {
                    Log.e("Email exits", "True");
                    isCheck = false;
                }

            }

        } catch (SQLException e) {
            Log.e("Error:", e.getMessage());
            isCheck = false;

        }
        return isCheck;
    }

}