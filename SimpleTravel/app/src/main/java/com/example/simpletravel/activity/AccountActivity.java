package com.example.simpletravel.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.MainActivity;
import com.example.simpletravel.R;
import com.example.simpletravel.model.IdUsers;
import com.example.simpletravel.model.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    // create variable
    private ImageView img;
    private TextView Name,Email,LiveNow,Introduce, Phone, Back ;
    private Button Logout, Update;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //event click button Logout then layout activity Login
            if(view.getId() == R.id.account_btn_Logout){
                signOut();
            }
            //event update information account
            if(view.getId() == R.id.account_btn_Update){
                DialogSaveChange(view);
            }
            if(view.getId() == R.id.account_txt_Back){
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        AccountConstructor();
        InformationGoogle();
        UpdateInformation();

    }

    //create varable
    private JDBCControllers jdbcControllers;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private int IdUser ;

    private void UpdateInformation() {
        IdUser = IdUsers.IdUser;
        try {
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            Log.e("Log", "Connect Data True");
            String sql = "select * from  Users where IdUser = '" + IdUser + "'";//check email exits
            PreparedStatement ps = connection.prepareStatement(sql);
            Log.e("query", sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                //send data in activity account
                Users users = new Users(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7));

                if(users != null){

                    // get data update information user
                    Name.setText( users.getUserName());
                    Email.setText(users.getEmail());
                    LiveNow.setText(users.getAddress());
                    Introduce.setText( users.getIntroduce());
                    Phone.setText( users.getPhone());
                }
                connection.close();//close connect to data
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            Log.e("Error:", e.getMessage());
        }
    }

    private void AccountConstructor() {
        //function Logout
        img = findViewById(R.id.account_imgProfilePic);
        Name = findViewById(R.id.txtName);
        Email = findViewById(R.id.txtEmail);
        LiveNow = findViewById(R.id.account_txt_LiveNow);
        Introduce = findViewById(R.id.account_txt_Introduce);
        Phone = findViewById(R.id.account_txt_Phone);
        Back = findViewById(R.id.account_txt_Back);
        Back.setOnClickListener(onClickListener);

        Logout = findViewById(R.id.account_btn_Logout);
        Logout.setOnClickListener(onClickListener);

        //constructor function Dialog Save Change
        Update = findViewById(R.id.account_btn_Update);
        Update.setOnClickListener(onClickListener);

    }

    //dialog update information account
    //create variable use in dialog save change
    private Button SaveChange, Cancel;
    private String   username,  livenow, introduce, contact;
    private void DialogSaveChange(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setCancelable(false);
        builder.setTitle(R.string.title_update);
        final View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_account, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.show();
        final EditText editTextUsername = dialogView.findViewById(R.id.account_txt_Name);
        final EditText editTextLiveNow= dialogView.findViewById(R.id.account_txt_LiveNow);
        final EditText editTextIntroduce = dialogView.findViewById(R.id.account_txt_Introduce);
        final EditText editTextContact = dialogView.findViewById(R.id.account_txt_Contact);
        //update text in dialog
        editTextUsername.setText(Name.getText());
        editTextLiveNow.setText(LiveNow.getText());
        editTextIntroduce.setText(Introduce.getText());
        editTextContact.setText(Phone.getText());
        //show du
        Button buttonSaveChange = dialogView.findViewById(R.id.account_btn_SaveChange);
        buttonSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = editTextUsername.getText().toString().trim();
                livenow = editTextLiveNow.getText().toString().trim();
                introduce = editTextIntroduce.getText().toString().trim();
                contact = editTextContact.getText().toString().trim();
                //update data user edit
                try {
                    jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                    connection = jdbcControllers.ConnectionData();
                    Log.e("Log", "Connect Data True");
                    String sql = "Update Users set Name = N'"+username+"', Address = N'" + livenow +"', " +
                            "Introduce = N'"+ introduce +"', Phone = '"+ contact + "' Where IdUser = '" + IdUsers.IdUser +"'";//check id user
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.executeUpdate();
                    ps.close();
                    Log.e("query", sql);
                    Log.e("Log", "Update true");
                    UpdateInformation();//update reset layout Account

                } catch (SQLException e) {
                    Log.e("Error:", e.getMessage());
                    Toast.makeText(getApplicationContext(),"Update false",Toast.LENGTH_LONG).show();
                }

                Toast.makeText(getApplicationContext(),"Update true",Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
            }
        });
        Button buttonCancel = dialogView.findViewById(R.id.account_btn_Cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    //Show information login with Google
    GoogleSignInClient mGoogleSignInClient;
    private void InformationGoogle() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
//            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Name.setText(personName);
            Email.setText(personEmail);
            Glide.with(this).load(String.valueOf(personPhoto)).into(img);
        }

    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AccountActivity.this,"sign out", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AccountActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }
    //
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
}