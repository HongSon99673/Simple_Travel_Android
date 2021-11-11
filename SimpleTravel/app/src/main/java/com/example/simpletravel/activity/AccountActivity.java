package com.example.simpletravel.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.simpletravel.R;
import com.example.simpletravel.model.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class AccountActivity extends AppCompatActivity {
    // create variable
    private ImageView img;
    private TextView Name,Email,LiveNow,Introduce, Phone ;
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

    private void UpdateInformation() {

        Users users = (Users) getIntent().getExtras().get("User");
        // get data update information user
        Name.setText("Tên: "+ users.getUserName());
        Email.setText("Email: "+ users.getEmail());
        LiveNow.setText("Nơi sống hiện tại: "+ users.getAddress());
        Introduce.setText("Giới thiệu: "+ users.getIntroduce());
        Phone.setText("Liên hệ: "+ users.getPhone());
    }

    private void AccountConstructor() {
        //function Logout
        img = findViewById(R.id.account_imgProfilePic);
        Name = findViewById(R.id.txtName);
        Email = findViewById(R.id.txtEmail);
        LiveNow = findViewById(R.id.account_txt_LiveNow);
        Introduce = findViewById(R.id.account_txt_Introduce);
        Phone = findViewById(R.id.account_txt_Phone);

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
        //show du
        Button buttonSaveChange = dialogView.findViewById(R.id.account_btn_SaveChange);
        buttonSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = editTextUsername.getText().toString().trim();
                livenow = editTextLiveNow.getText().toString().trim();
                introduce = editTextIntroduce.getText().toString().trim();
                contact = editTextContact.getText().toString().trim();
//                if (username.equalsIgnoreCase("pmk") && password.equalsIgnoreCase("lab")) {
//                    Toast.makeText(getApplicationContext(), R.string.valid, Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), R.string.invalid, Toast.LENGTH_SHORT).show();
//                }
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