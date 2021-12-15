package com.example.simpletravel.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpletravel.JDBC.JDBCControllers;

import com.example.simpletravel.R;
import com.example.simpletravel.model.Temp.IdUsers;
import com.example.simpletravel.model.Users;
import com.example.simpletravel.security.AESUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpEmailActivity extends AppCompatActivity {

    private TextView Back, EmailAlert, PasswordAlert, ShowPassWord, ConfirmAlert, ConfirmShowPW;
    private EditText txtEmail, txtPassword, txtConfirm;
    private Button SignIn, SignUp;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.txt_back_SignUp_Email) {
                Intent intent = new Intent(SignUpEmailActivity.this, LoginEmailActivity.class);
                startActivity(intent);
                finish();
            }
            if (view.getId() == R.id.btn_Signup_SignUp_Email) {
                //check variable in
                if (checkEmail(true) && AccountExists(true)) {
                    SignUpControll(); // connect database and send data
                    Log.e("Log", "Send data true");
                    Intent intent = new Intent(SignUpEmailActivity.this, LoginEmailActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
                Log.e("Log", "Send data fail");

            }
            //event click icon eye password
            if (view.getId() == R.id.btn_SignIn_SignUp_Email) {
                Intent intent = new Intent(SignUpEmailActivity.this, LoginEmailActivity.class);
                startActivity(intent);
                finish();
            }
            if (view.getId() == R.id.txt_ShowPassWord_SignUp_Email) {
                if (txtPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    //Show Password
                    ShowPassWord.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_eye_white, 0, 0, 0);
                    txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //Hide Password
                    ShowPassWord.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_visit_main, 0, 0, 0);
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
            //event click icon eye confirm password
            if (view.getId() == R.id.txt_ConfirmShowPassWord_SignUp_Email) {
                if (txtConfirm.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    //Show Password
                    ConfirmShowPW.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_eye_white, 0, 0, 0);
                    txtConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //Hide Password
                    ConfirmShowPW.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_visit_main, 0, 0, 0);
                    txtConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }

        }
    };

    //check account sign up
    private String emailPattern = "[a-z-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

    private Boolean checkEmail(boolean isValid) {
        isValid = true;
        //check email valid
        if (txtEmail.getText().toString().isEmpty()) {
            EmailAlert.setText("Địa chỉ email không để trống. ");
            EmailAlert.setTextColor(getResources().getColor(R.color.INK_RED));
            Toast.makeText(getApplicationContext(), "empty email address", Toast.LENGTH_SHORT);
            isValid = false;
        } else {
            if (txtEmail.getText().toString().matches(emailPattern)) {
                EmailAlert.setText("Địa chỉ email chính xác.");
                EmailAlert.setTextColor(getResources().getColor(R.color.INK_GREEN));
                Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT);
            } else {
                EmailAlert.setText("Địa chỉ email sai. Ví dụ: abc123@gmail.com");
                EmailAlert.setTextColor(getResources().getColor(R.color.INK_RED));
                Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT);
                isValid = false;
            }
        }

        //check password valid
        if (txtPassword.getText().toString().isEmpty()) {//check pass is null
            PasswordAlert.setText("Mật khẩu không để trống.");
            PasswordAlert.setTextColor(getResources().getColor(R.color.INK_RED));
            Toast.makeText(getApplicationContext(), "empty password valid or invalid password", Toast.LENGTH_SHORT);
            isValid = false;
        } else {
            if (txtPassword.getText().toString().matches(passwordPattern)) {//compare password == passwordPartern
                PasswordAlert.setText("Password đúng định dạng.");
                PasswordAlert.setTextColor(getResources().getColor(R.color.INK_GREEN));
                Toast.makeText(getApplicationContext(), "valid password address", Toast.LENGTH_SHORT);
            } else {
                PasswordAlert.setText("Password sai định dạng. Ví dụ: Abc@123");//pass fail format
                PasswordAlert.setTextColor(getResources().getColor(R.color.INK_RED));
                Toast.makeText(getApplicationContext(), "Invalid password address", Toast.LENGTH_SHORT);
                isValid = false;
            }
        }
        //check confirm password
        if (txtConfirm.getText().toString().isEmpty()) {//check pass is null
            ConfirmAlert.setText("Xác nhận mật khẩu không để trống.");
            ConfirmAlert.setTextColor(getResources().getColor(R.color.INK_RED));
            Toast.makeText(getApplicationContext(), "empty password valid or invalid password", Toast.LENGTH_SHORT);
            isValid = false;
        } else {
            if (txtConfirm.getText().toString().equals(txtPassword.getText().toString())) {
                ConfirmAlert.setText("Xác nhận mật khẩu chính xác.");
                ConfirmAlert.setTextColor(getResources().getColor(R.color.INK_GREEN));
                Toast.makeText(getApplicationContext(), "valid password address", Toast.LENGTH_SHORT);
            } else {
                ConfirmAlert.setText("Xác nhận sai. Vui lòng kiểm tra lại.");
                ConfirmAlert.setTextColor(getResources().getColor(R.color.INK_RED));
                Toast.makeText(getApplicationContext(), "Invalid password address", Toast.LENGTH_SHORT);
                isValid = false;
            }
        }
        return isValid;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_email);
        SignUpConstructor();
        Encrypt();
    }

    //Send data give SQL
    private JDBCControllers jdbcControllers;
    private Connection connection;
    private PreparedStatement preparedStatement;

    private void SignUpControll() {
        String Email = txtEmail.getText().toString();
        String PassWord = txtPassword.getText().toString();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                    connection = jdbcControllers.ConnectionData();
                    Log.e("Log", "Connect true");
                    String sql = "Insert into Users " +
                            " ( Name, Gmail, Password, Address) values " + "('','" + Email + "','" + Encrypt() + "','')";
                    PreparedStatement preparedStatement = connection
                            .prepareStatement(sql);

                    preparedStatement.executeUpdate();
                    preparedStatement.close();

                } catch (Exception ex) {
                    Log.e("Log", ex.toString());
                }
            }
        });
        thread.start();
    }

    //check email exits
    private Boolean AccountExists(Boolean isCheck) {
        isCheck = true;
        try {
            String Email = txtEmail.getText().toString();//get email
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            String sql = "select  Gmail from  Users where Gmail = '" + Email + "'";//check email exits
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String passcode = rs.getString("Gmail");
                //send data in activity account
                Users users = new Users(0, "", rs.getString("Gmail"), "",
                        "", "", "", "", "");
                // send IdUser for DiscoveryViewModel check History

                connection.close();//close connect to data
                rs.close();
                ps.close();
                //check email in database
                if (passcode == null) {
                    Log.e("Email not exits", "True");
                    EmailAlert.setText("");
                } else {
                    Log.e("Email not exits", "True");
                    EmailAlert.setText("Email đã tồn tại");
                    EmailAlert.setTextColor(getResources().getColor(R.color.INK_RED));
                    isCheck = false;
                }

            }

        } catch (SQLException e) {
            Log.e("Error:", e.getMessage());
            isCheck = false;

        }
        return isCheck;
    }

    //Encrypt password into the database
    private String Encrypt() {
        String encrypted = "";
        String sourceStr = txtPassword.getText().toString();
        try {
            encrypted = AESUtils.encrypt(sourceStr);
            Log.d("TEST", "encrypted:" + encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    //create constructor
    private void SignUpConstructor() {

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

        txtConfirm = findViewById(R.id.txt_ConfirmPassword_SignUp_Email);
        ConfirmAlert = findViewById(R.id.txt_ConfirmPasswordAlert_SignUp_Email);
        ConfirmShowPW = findViewById(R.id.txt_ConfirmShowPassWord_SignUp_Email);
        ConfirmShowPW.setOnClickListener(onClickListener);
    }
}