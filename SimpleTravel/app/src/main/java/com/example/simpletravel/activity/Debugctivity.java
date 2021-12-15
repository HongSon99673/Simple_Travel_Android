package com.example.simpletravel.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.simpletravel.MainActivity;
import com.example.simpletravel.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;

public class Debugctivity extends AppCompatActivity {
    private ArrayList<Intent> intents;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debugctivity);
//        importIntentList();
//
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, intents);
//
//        ListView debugListView = findViewById(R.id.debug_ListView);
//        debugListView.setAdapter(adapter);
//
//        debugListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity((Intent) intents.get(position));
//
//            }
//        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //tde tam sau sửa
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                // Build a GoogleSignInClient with the options specified by gso.
                mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                if (acct == null){
                    Intent intent = new Intent(Debugctivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else{
                    Intent intent = new Intent(Debugctivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 2000);
    }


//    private void importIntentList() {
//        intents = new ArrayList<>();
//
//        Intent Home = new Intent(this, MainActivity.class);
//        intents.add(Home);
//        Intent Login = new Intent(this, LoginActivity.class);
//        intents.add(Login);
//        Intent SignUp = new Intent(this, SignUpEmailActivity.class);
//        intents.add(SignUp);
//        Intent Ready = new Intent(this, LoginEmailActivity.class);
//        intents.add(Ready);
//    }
}