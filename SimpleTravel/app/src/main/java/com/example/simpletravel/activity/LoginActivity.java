package com.example.simpletravel.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.simpletravel.MainActivity;
import com.example.simpletravel.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private Button Email, Facebook,Google, Instagram;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //create event click button Email
            if(view.getId() == R.id.btn_Email_Login){
                Email.setBackgroundColor(getResources().getColor(R.color.email));
                Intent intent = new Intent(LoginActivity.this, EmailActivity.class);
                startActivity(intent);
                finish();
            }
            //create event click button Google
            if(view.getId() == R.id.btn_Google_Login){
                Google.setBackgroundColor(getResources().getColor(R.color.google));
                Intent intent = new Intent(LoginActivity.this, EmailActivity.class);
                startActivity(intent);
                finish();
            }
            //create event click button Facebook
            if(view.getId() == R.id.btn_Facebook_Login){
                Facebook.setBackgroundColor(getResources().getColor(R.color.facebook));
                Intent intent = new Intent(LoginActivity.this, EmailActivity.class);
                startActivity(intent);
                finish();
            }
            //create event click button Instagram
            if(view.getId() == R.id.btn_Instagram_Login){
                Instagram.setBackgroundColor(getResources().getColor(R.color.Instagram));
                Intent intent = new Intent(LoginActivity.this, EmailActivity.class);
                startActivity(intent);
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
//        updateUI(account);
        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.btn_Email_Login);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
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
    private void Login() {

//        Email = findViewById(R.id.btn_Email_Login);
//        Email.setOnClickListener(onClickListener);

        Facebook = findViewById(R.id.btn_Facebook_Login);
        Facebook.setOnClickListener(onClickListener);

        Google = findViewById(R.id.btn_Google_Login);
        Google.setOnClickListener(onClickListener);

        Instagram = findViewById(R.id.btn_Instagram_Login);
        Instagram.setOnClickListener(onClickListener);


    }
}