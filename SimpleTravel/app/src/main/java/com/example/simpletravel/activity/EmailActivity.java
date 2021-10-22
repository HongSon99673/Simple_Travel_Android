package com.example.simpletravel.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.simpletravel.MainActivity;
import com.example.simpletravel.R;

public class EmailActivity extends AppCompatActivity {

    //create variable
    private TextView Back;
    private Button SignIn;
    private Button SignUp;

    //catch event OnclickListener
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.txt_back_Login){
                Intent intent = new Intent(EmailActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            if(view.getId() == R.id.btn_SignIn_Email){
                Intent intent = new Intent(EmailActivity.this, MainActivity.class);
                startActivity(intent);
            }

            if(view.getId() == R.id.btn_SignUp_Email){
                Intent intent = new Intent(EmailActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        LoginDetails(); //create event
    }

    private void LoginDetails() {

        Back = findViewById(R.id.txt_back_Login);
        Back.setOnClickListener(onClickListener);


        SignIn = findViewById(R.id.btn_SignIn_Email);
        SignIn.setOnClickListener(onClickListener);

        SignUp = findViewById(R.id.btn_SignUp_Email);
        SignUp.setOnClickListener(onClickListener);
    }
}