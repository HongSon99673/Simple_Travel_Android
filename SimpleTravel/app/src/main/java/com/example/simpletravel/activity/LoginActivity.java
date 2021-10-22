package com.example.simpletravel.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.simpletravel.R;

public class LoginActivity extends AppCompatActivity {

    private Button Email;
    private Button Facebook;
    private Button Google;
    private Button Instagram;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.btn_Email_Login){
                Intent intent = new Intent(LoginActivity.this, EmailActivity.class);
                startActivity(intent);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginEmail();
    }

    private void LoginEmail() {

        Email = findViewById(R.id.btn_Email_Login);
        Email.setOnClickListener(onClickListener);


    }
}