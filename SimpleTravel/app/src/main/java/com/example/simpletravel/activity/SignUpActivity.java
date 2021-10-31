package com.example.simpletravel.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpletravel.MainActivity;
import com.example.simpletravel.R;

public class SignUpActivity extends AppCompatActivity {

    private TextView Back,EmailAlert, PasswordAlert, ShowPassWord;
    private EditText txtEmail, txtPassword;
    private Button  SignIn, SignUp;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.txt_back_SignUp_Email){
                Intent intent = new Intent(SignUpActivity.this, EmailActivity.class);
                startActivity(intent);
                finish();
            }
            if(view.getId() == R.id.btn_Signup_SignUp_Email ){

                if(checkEmail(true)){
                    SignUp.setBackgroundColor(getResources().getColor(R.color.INK_Brow));
                    Intent intent = new Intent(SignUpActivity.this, EmailActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            if(view.getId() == R.id.btn_SignIn_SignUp_Email){
                SignIn.setBackgroundColor(getResources().getColor(R.color.INK_Brow));
                Intent intent = new Intent(SignUpActivity.this, EmailActivity.class);
                startActivity(intent);
                finish();
            }
            if(view.getId() == R.id.txt_ShowPassWord_SignUp_Email){
                if(txtPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    //Show Password
                    ShowPassWord.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_eye_white, 0, 0, 0);
                    txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    //Hide Password
                    ShowPassWord.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_visit_main, 0, 0, 0);
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }

        }
    };

    //check account sign up
    private String emailPattern = "[a-z-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    private Boolean checkEmail( boolean isValid ) {
         isValid = true;
        //check email valid
        if(txtEmail.getText().toString().isEmpty()) {
            EmailAlert.setText("Địa chỉ email không để trống. ");
            EmailAlert.setTextColor(getResources().getColor(R.color.INK_RED));
            Toast.makeText(getApplicationContext(),"empty email address",Toast.LENGTH_SHORT);
            isValid = false;
        }else {
            if (txtEmail.getText().toString().matches(emailPattern)) {
                EmailAlert.setText("Địa chỉ email chính xác.");
                EmailAlert.setTextColor(getResources().getColor(R.color.INK_GREEN));
                Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT);
               isValid = true;
            } else {
                EmailAlert.setText("Địa chỉ email sai. Ví dụ: abc123@gmail.com");
                EmailAlert.setTextColor(getResources().getColor(R.color.INK_RED));
                Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT);
                isValid = false;
            }
        }

        //check password valid
        if (txtPassword.getText().toString().isEmpty()) {
            PasswordAlert.setText("Mật khẩu trống không để trống.");
            PasswordAlert.setTextColor(getResources().getColor(R.color.INK_RED));
            Toast.makeText(getApplicationContext(),"empty password valid or invalid password",Toast.LENGTH_SHORT);
            isValid = false;
        } else {
                PasswordAlert.setText("Mật khẩu chính xác");
                PasswordAlert.setTextColor(getResources().getColor(R.color.INK_RED));
                Toast.makeText(getApplicationContext(),"Invalid password", Toast.LENGTH_SHORT);
                isValid = true;
            }

        return isValid;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_email);

        SignUpConTroll();
    }

    private void SignUpConTroll() {

        Back = findViewById(R.id.txt_back_SignUp_Email);
        Back.setOnClickListener(onClickListener);


        SignIn = findViewById(R.id.btn_SignIn_SignUp_Email);
        SignIn.setOnClickListener(onClickListener);

        SignUp = findViewById(R.id.btn_Signup_SignUp_Email);
        SignUp.setOnClickListener(onClickListener);

        txtEmail = findViewById(R.id.txt_AddressEmail_SignUp_Email);
        txtPassword = findViewById(R.id.txt_Password_SignUp_Email);
        EmailAlert = findViewById(R.id.txt_AddressEmailAlert_SignUp_Email);
        PasswordAlert = findViewById(R.id.txt_PasswordAlert_SignUp_Email);


        ShowPassWord = findViewById(R.id.txt_ShowPassWord_SignUp_Email);
        ShowPassWord.setOnClickListener(onClickListener);
    }


}