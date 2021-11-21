package com.example.simpletravel.ui.evaluate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.R;
import com.example.simpletravel.adapter.PhotoCommentAdapter;
import com.example.simpletravel.model.IdServices;
import com.example.simpletravel.model.IdUsers;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class EvaluateActivity extends AppCompatActivity {

    private ImageView Star1, Star2, Star3, Star4, Star5;
    private TextView Rating, Bussiness, Couple, Alone, Friend, FamilySmall, FamilyBig;
    private TextView ChooseImage, TypeAlert, TitleAlert, SummaryAlert, Alert;
    private EditText Summary,Title;
    private Button Send;
    private RecyclerView recyclerView;
    private PhotoCommentAdapter photoCommentAdapter;
    private int Star = 0 ;
    private String Type = "";
    private String TextSummary = "";
    private String TextTitle = "";
    private String imageString= "";

    //connect data base
    private JDBCControllers jdbcControllers;
    private Connection connection;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //set event click star 1
            if(view.getId() == R.id.evaluate_star_1){
                Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star2.setImageResource(R.drawable.baseline_star_outline_black_48);
                Star3.setImageResource(R.drawable.baseline_star_outline_black_48);
                Star4.setImageResource(R.drawable.baseline_star_outline_black_48);
                Star5.setImageResource(R.drawable.baseline_star_outline_black_48);
                //set Star
                Star = 1;
                //set title rating
                Rating.setText(R.string.title_VeryBad);
                Rating.setTextSize(20);
                Rating.setTextColor(getResources().getColor(R.color.black));
            }
            //set event click star 2
            if(view.getId() == R.id.evaluate_star_2){
                Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star3.setImageResource(R.drawable.baseline_star_outline_black_48);
                Star4.setImageResource(R.drawable.baseline_star_outline_black_48);
                Star5.setImageResource(R.drawable.baseline_star_outline_black_48);
                //set Star
                Star = 2;
                //set title rating
                Rating.setText(R.string.title_Bad);
                Rating.setTextSize(20);
                Rating.setTextColor(getResources().getColor(R.color.black));
            }
            //set event click star 2
            if(view.getId() == R.id.evaluate_star_3){
                Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star4.setImageResource(R.drawable.baseline_star_outline_black_48);
                Star5.setImageResource(R.drawable.baseline_star_outline_black_48);
                //set Star
                Star = 3;
                //set title rating
                Rating.setText(R.string.title_Medium);
                Rating.setTextSize(20);
                Rating.setTextColor(getResources().getColor(R.color.black));
            }
            //set event click star 2
            if(view.getId() == R.id.evaluate_star_4){
                Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star4.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star5.setImageResource(R.drawable.baseline_star_outline_black_48);
                //set Star
                Star = 4;
                //set title rating
                Rating.setText(R.string.title_Rating_Good);
                Rating.setTextSize(20);
                Rating.setTextColor(getResources().getColor(R.color.black));
            }
            //set event click star 2
            if(view.getId() == R.id.evaluate_star_5){
                Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star4.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star5.setImageResource(R.drawable.outline_star_purple500_black_48);
                //set Star
                Star = 5;
                //set title rating
                Rating.setText(R.string.title_Rating_VeryGood);
                Rating.setTextSize(20);
                Rating.setTextColor(getResources().getColor(R.color.black));
            }
            //set event click textview bussiness
            if(view.getId() == R.id.evaluate_txt_Bussiness){
                Bussiness.setBackground(getResources().getDrawable(R.drawable.ic_shape_frames_boders));
                Couple.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Alone.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Friend.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilyBig.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilySmall.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                //set Type
                Type = Bussiness.getText().toString();
            }
            //set event click textview Couple
            if(view.getId() == R.id.evaluate_txt_Couple){
                Bussiness.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Couple.setBackground(getResources().getDrawable(R.drawable.ic_shape_frames_boders));
                Alone.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Friend.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilyBig.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilySmall.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                //set Type
                Type = Couple.getText().toString();
            }
            //set event click textview Alone
            if(view.getId() == R.id.evaluate_txt_Alone){
                Bussiness.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Couple.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Alone.setBackground(getResources().getDrawable(R.drawable.ic_shape_frames_boders));
                Friend.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilyBig.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilySmall.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                //set Type
                Type = Alone.getText().toString();
            }
            //set event click textview Friend
            if(view.getId() == R.id.evaluate_txt_Friend){
                Bussiness.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Couple.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Alone.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Friend.setBackground(getResources().getDrawable(R.drawable.ic_shape_frames_boders));
                FamilyBig.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilySmall.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                //set Type
                Type = Friend.getText().toString();
            }
            //set event click textview FamilySmall
            if(view.getId() == R.id.evaluate_txt_FamilySmall){
                Bussiness.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Couple.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Alone.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Friend.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilyBig.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilySmall.setBackground(getResources().getDrawable(R.drawable.ic_shape_frames_boders));
                //set Type
                Type = FamilySmall.getText().toString();
            }
            //set event click textview Family Big
            if(view.getId() == R.id.evaluate_txt_FamilyBig){
                Bussiness.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Couple.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Alone.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Friend.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilyBig.setBackground(getResources().getDrawable(R.drawable.ic_shape_frames_boders));
                FamilySmall.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                //set Type
                Type = FamilyBig.getText().toString();

            }
            //set event click text  view Camera
            if (view.getId() == R.id.evaluate_txt_ChooseImages){
                requestPermission();
            }
            //set event click button Send
            if(view.getId() == R.id.evaluate_btn_Send_Comment){
                if (CheckData() == true){
                    Alert.setText("");
                    //get date now
                    String currentDate = new SimpleDateFormat("'ngày 'dd, 'tháng 'MM, 'năm 'yyyy", Locale.getDefault()).format(new Date());
                    //create Thread new and send data from app to SQL
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                                connection = jdbcControllers.ConnectionData();
                                Log.e("Log", "Connect true");
                                String sql = "insert into Ratings (IdService, IdUser, Ratings, Summary, Type, Title, Time, Images) " +
                                        "values ('"+ IdServices.IdService +"','"+ IdUsers.IdUser +"','"+ Star +"'," +
                                        "N'"+ TextSummary +"',N'"+ Type +"',N'"+ TextTitle+"',N'"+ currentDate+"','"+imageString+"')";
                                PreparedStatement preparedStatement = connection
                                        .prepareStatement(sql);

                                preparedStatement.executeUpdate();
                                preparedStatement.close();
                                connection.close();

                            } catch (Exception ex) {
                                Log.e("Log", ex.toString());
                            }
                        }
                    }).start();

                } else {
                    Alert.setText("Vui lòng kiểm tra lại thông tin đã nhập");
                    Alert.setTextColor(getResources().getColor(R.color.INK_RED));
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        EvaluateActivityContructor();
        ListImagesControll();//constructor list images

    }
    //check data
    private Boolean CheckData(){
        boolean isCheck = true;
        //check Star
        if(Star == 0){
            Rating.setText("Vui lòng chọn mức xếp hạng");
            Rating.setTextColor(getResources().getColor(R.color.INK_RED));
            Rating.setTextSize(15);
            isCheck = false;
        }
        //check Type
        if (Type.equals("")){
            TypeAlert.setText("Vui lòng chọn loại hình chuyến đi");
            TypeAlert.setTextColor(getResources().getColor(R.color.INK_RED));
            isCheck = false;
        } else {
            TypeAlert.setText("");
        }
        //check Summary
        TextSummary = Summary.getText().toString();
        if(TextSummary.isEmpty() || Summary.length() < 100){
            SummaryAlert.setText("Đánh giá phải dài ít nhất 100 ký tự");
            SummaryAlert.setTextColor(getResources().getColor(R.color.INK_RED));
            isCheck = false;
        } else {
            SummaryAlert.setText("");
        }
        //check Title
        TextTitle = Title.getText().toString();
        if (TextTitle.isEmpty() || Title.length() < 20){
            TitleAlert.setText("Vui lòng tạo tiêu đề cho đánh giá của bạn");
            TitleAlert.setTextColor(getResources().getColor(R.color.INK_RED));
            isCheck = false;
        } else {
            TitleAlert.setText("");
        }
        return isCheck;
    }

    private void ListImagesControll() {
        photoCommentAdapter = new PhotoCommentAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2,
                LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setFocusable(false);
        recyclerView.setAdapter(photoCommentAdapter);
    }

    private void requestPermission(){

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openBottomPicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(EvaluateActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }
    private void openBottomPicker(){
        TedBottomPicker.with(EvaluateActivity.this)
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Done")
                .setEmptySelectionText("No Select")
//                .setSelectedUriList(selectedUriList)
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        // here is selected image uri list
                        if(uriList != null && !uriList.isEmpty()){
                            photoCommentAdapter.setMlistPhoto(uriList);
                            //encode image to base64 string
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), photoCommentAdapter.getItemCount());
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageBytes = baos.toByteArray();
                            imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        }
                    }
                });
    }

    private void EvaluateActivityContructor() {
        Star1 = findViewById(R.id.evaluate_star_1);
        Star1.setOnClickListener(onClickListener);

        Star2 = findViewById(R.id.evaluate_star_2);
        Star2.setOnClickListener(onClickListener);

        Star3 = findViewById(R.id.evaluate_star_3);
        Star3.setOnClickListener(onClickListener);

        Star4 = findViewById(R.id.evaluate_star_4);
        Star4.setOnClickListener(onClickListener);

        Star5 = findViewById(R.id.evaluate_star_5);
        Star5.setOnClickListener(onClickListener);

        Rating = findViewById(R.id.evaluate_txt_Rating);

        Bussiness = findViewById(R.id.evaluate_txt_Bussiness);
        Bussiness.setOnClickListener(onClickListener);

        Couple = findViewById(R.id.evaluate_txt_Couple);
        Couple.setOnClickListener(onClickListener);

        Alone = findViewById(R.id.evaluate_txt_Alone);
        Alone.setOnClickListener(onClickListener);

        Friend = findViewById(R.id.evaluate_txt_Friend);
        Friend.setOnClickListener(onClickListener);

        FamilySmall = findViewById(R.id.evaluate_txt_FamilySmall);
        FamilySmall.setOnClickListener(onClickListener);

        FamilyBig = findViewById(R.id.evaluate_txt_FamilyBig);
        FamilyBig.setOnClickListener(onClickListener);

        Summary = findViewById(R.id.evaluate_edit_Summary);
        Title = findViewById(R.id.evaluate_edit_Title);

        ChooseImage = findViewById(R.id.evaluate_txt_ChooseImages);
        ChooseImage.setOnClickListener(onClickListener);
        recyclerView = findViewById(R.id.evaluate_rcv_ListPhoto);

        TypeAlert = findViewById(R.id.evaluate_txt_Type_Alert);
        SummaryAlert = findViewById(R.id.evaluate_txt_Summary_Alert);
        TitleAlert = findViewById(R.id.evaluate_txt_Title_Alert);

        Send = findViewById(R.id.evaluate_btn_Send_Comment);
        Send.setOnClickListener(onClickListener);
        Alert = findViewById(R.id.evaluate_txt_Alert);
    }
}