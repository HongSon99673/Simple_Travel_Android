package com.example.simpletravel.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class EmailActivity extends AppCompatActivity {

    //create variable
    private TextView Back, ShowPassWord;
    private Button SignIn, SignUp;

    //catch event OnclickListener
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.txt_back_Email){
                Intent intent = new Intent(EmailActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            if(view.getId() == R.id.btn_SignIn_Email){
                if(checkEmail(true)){
                    SignIn.setBackgroundColor(getResources().getColor(R.color.INK_Brow));
                    Intent intent = new Intent(EmailActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            if(view.getId() == R.id.btn_SignUp_Email){
                SignUp.setBackgroundColor(getResources().getColor(R.color.INK_Brow));
                Intent intent = new Intent(EmailActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
            if(view.getId() == R.id.txt_ShowPassWord_Email){
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

    //Create variable
    private String emailPattern = "[a-z-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private TextView EmailAlert, PasswordAlert;
    private EditText txtEmail, txtPassword;

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
                EmailAlert.setText("Địa chỉ email chưa đúng.");
                EmailAlert.setTextColor(getResources().getColor(R.color.INK_RED));
                Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT);
                isValid = false;
            }
        }

        //check password valid
        if (txtPassword.getText().toString().isEmpty()) {
            PasswordAlert.setText("Mật khẩu không để trống.");
            PasswordAlert.setTextColor(getResources().getColor(R.color.INK_RED));
            Toast.makeText(getApplicationContext(),"empty password valid or invalid password",Toast.LENGTH_SHORT);
            isValid = false;
        } else {
            PasswordAlert.setText("Mật khẩu chính xác");
            PasswordAlert.setTextColor(getResources().getColor(R.color.INK_GREEN));
            Toast.makeText(getApplicationContext(),"Invalid password", Toast.LENGTH_SHORT);
            isValid = true;
        }

        return isValid;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        LoginDetails(); //create event
    }

    private void LoginDetails() {

        Back = findViewById(R.id.txt_back_Email);
        Back.setOnClickListener(onClickListener);


        SignIn = findViewById(R.id.btn_SignIn_Email);
        SignIn.setOnClickListener(onClickListener);

        SignUp = findViewById(R.id.btn_SignUp_Email);
        SignUp.setOnClickListener(onClickListener);

        txtEmail = findViewById(R.id.txt_AddressEmail_Email);
        txtEmail.setOnClickListener(onClickListener);

        txtPassword = findViewById(R.id.txt_Password_Email);
        SignUp.setOnClickListener(onClickListener);

        EmailAlert = findViewById(R.id.txt_AddressEmailAlert_Email);
        EmailAlert.setOnClickListener(onClickListener);

        PasswordAlert = findViewById(R.id.txt_PasswordAlert_Email);
        PasswordAlert.setOnClickListener(onClickListener);

        ShowPassWord = findViewById(R.id.txt_ShowPassWord_Email);
        ShowPassWord.setOnClickListener(onClickListener);

    }
}