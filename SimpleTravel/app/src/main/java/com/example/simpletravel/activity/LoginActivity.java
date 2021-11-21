package com.example.simpletravel.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.MainActivity;
import com.example.simpletravel.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout Email,Google;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //create event click button Email
            if(view.getId() == R.id.btn_Email_Login){
                Email.setBackgroundColor(getResources().getColor(R.color.INK_grey));
                Intent intent = new Intent(LoginActivity.this, LoginEmailActivity.class);
                startActivity(intent);
                finish();
            }
            //create event click button Google
            if(view.getId() == R.id.btn_Google_Login){
                Google.setBackgroundColor(getResources().getColor(R.color.INK_grey));
                signIn();
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login();
        LoginEmail();
    }
    //Create variable
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;

    private void LoginEmail() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

    //Login Email
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    //Send data give SQL
    private JDBCControllers jdbcControllers;
    private Connection connection;

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
//                            connection = jdbcControllers.ConnectionData();
//                            Log.e("Log", "Connect true");
//                            String sql = "Insert into Users " +
//                                    " ( Name, Gmail, Avatar) values " + "('"+personName +"','" + personEmail + "','')";
//                            PreparedStatement preparedStatement = connection
//                                    .prepareStatement(sql);
//
//                            preparedStatement.executeUpdate();
//                            preparedStatement.close();
//                            connection.close();//close connect database
//                        } catch (Exception ex) {
//                            Log.e("Log", ex.toString());
//                        }
//                    }
//                }).start();
            }
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

            // Signed in successfully, show authenticated UI.
//            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d( "message" , e.toString());
//            updateUI(null);
        }
    }


    private void updateUI(GoogleSignInAccount account) {
        if(account==null)
        {
            Toast.makeText(this,"please sign in",Toast.LENGTH_SHORT).show();
        }
        else {

            Toast.makeText(this,"you are signed in", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }
    //constructor
    private void Login() {
        Google = findViewById(R.id.btn_Google_Login);
        Google.setOnClickListener(onClickListener);

        Email = findViewById(R.id.btn_Email_Login);
        Email.setOnClickListener(onClickListener);

    }
}