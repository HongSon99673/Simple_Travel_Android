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
import com.example.simpletravel.MainActivity;
import com.example.simpletravel.R;
import com.example.simpletravel.model.IdUsers;
import com.example.simpletravel.model.Users;
import com.example.simpletravel.security.AESUtils;
import com.example.simpletravel.ui.discovery.DiscoveryViewModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginEmailActivity extends AppCompatActivity {

    private static final int REQUEST = 10;
    //create variable
    private TextView Back, ShowPassWord;
    private Button SignIn, SignUp;

    //catch event OnclickListener
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.txt_back_Email) {
                Intent intent = new Intent(LoginEmailActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            //Login in App
            if (view.getId() == R.id.btn_SignIn_Email) {
                //check database user
                if (checkEmail(true) && LoginController(true)) {
                    //check user don't exits
                    Intent intent = new Intent(LoginEmailActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            if (view.getId() == R.id.btn_SignUp_Email) {
                Intent intent = new Intent(LoginEmailActivity.this, SignUpEmailActivity.class);
                startActivity(intent);
                finish();
            }
            if (view.getId() == R.id.txt_ShowPassWord_Email) {
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

        }
    };

    //Create variable
    private String emailPattern = "[a-z-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
    private TextView EmailAlert, PasswordAlert;
    private EditText txtEmail, txtPassword;

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
                EmailAlert.setText("Địa chỉ email chưa đúng.");
                EmailAlert.setTextColor(getResources().getColor(R.color.INK_RED));
                Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT);
                isValid = false;
            }
        }

        //check password valid
        if (txtPassword.getText().toString().isEmpty()) {
            PasswordAlert.setText("Mật khẩu không để trống.");
            PasswordAlert.setTextColor(getResources().getColor(R.color.INK_RED));
            Toast.makeText(getApplicationContext(), "empty password valid or invalid password", Toast.LENGTH_SHORT);
            isValid = false;
        } else {
            if (txtPassword.getText().toString().matches(passwordPattern)) {
                PasswordAlert.setText("Mật khẩu chính xác");
                PasswordAlert.setTextColor(getResources().getColor(R.color.INK_GREEN));
                Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT);
            } else {
                PasswordAlert.setText("Mật khẩu sai định dạng ");
                PasswordAlert.setTextColor(getResources().getColor(R.color.INK_RED));
                isValid = false;
            }
        }

        return isValid;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        LoginConstructor(); //create event
    }

    //create varable
    private JDBCControllers jdbcControllers;
    private Connection connection;
    private PreparedStatement preparedStatement;


    private Boolean LoginController(boolean isLogin) {
        isLogin = true;
        String Email = txtEmail.getText().toString();
        String PassWord = txtPassword.getText().toString();
        try {
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            Log.e("Log", "Connect Data True");
            String sql = "select IdUser, Gmail, Password from  Users where Gmail = '" + Email + "'";//check email exits
            PreparedStatement ps = connection.prepareStatement(sql);
            Log.e("query", sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String passcode = rs.getString("Password");
                //send data in activity account
                Users users = new Users(rs.getInt("IdUser"), "", rs.getString("Gmail"), rs.getString("Password"),
                       "", "", "", "","");
                // send IdUser for DiscoveryViewModel check History
                int idUser = users.getIdUser();
                IdUsers.IdUser = idUser;//set IdUser

                connection.close();//close connect to data
                rs.close();
                ps.close();
                //check password in database and password in txtPassword
                if (passcode != null && !passcode.trim().equals("") && passcode.equals(Encrypt())) {
                    Log.e("Log", "Password true");

                } else {
                    Log.e("Log", "Password fail");
                    PasswordAlert.setText("Mật khẩu không chính xác ");
                    PasswordAlert.setTextColor(getResources().getColor(R.color.INK_RED));
                    isLogin = false;
                }

            } else {
                Log.e("Log", "User does not exists.");
                EmailAlert.setText("Tài khoản không tồn tại");
                EmailAlert.setTextColor(getResources().getColor(R.color.INK_RED));
                PasswordAlert.setText("");
                isLogin = false;
            }

        } catch (SQLException e) {
            Log.e("Error:", e.getMessage());
            EmailAlert.setText("Tài khoản không tồn tại");
            EmailAlert.setTextColor(getResources().getColor(R.color.INK_RED));
            isLogin = false;

        }
        return isLogin;

    }

    //parse password to SHA
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

    private void LoginConstructor() {

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