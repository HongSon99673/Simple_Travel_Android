package com.example.simpletravel.ui.evaluate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpletravel.R;
import com.example.simpletravel.adapter.PhotoCommentAdapter;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.ArrayList;
import java.util.List;
import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class EvaluateActivity extends AppCompatActivity {

    private ImageView Star1, Star2, Star3, Star4, Star5;
    private TextView Rating, Bussiness, Couple, Alone, Friend, FamilySmall, FamilyBig;
    private TextView ChooseImage;
    private RecyclerView recyclerView;
    private PhotoCommentAdapter photoCommentAdapter;

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
                //set title rating
                Rating.setText(R.string.title_VeryBad);
            }
            //set event click star 2
            if(view.getId() == R.id.evaluate_star_2){
                Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star3.setImageResource(R.drawable.baseline_star_outline_black_48);
                Star4.setImageResource(R.drawable.baseline_star_outline_black_48);
                Star5.setImageResource(R.drawable.baseline_star_outline_black_48);
                //set title rating
                Rating.setText(R.string.title_Bad);
            }
            //set event click star 2
            if(view.getId() == R.id.evaluate_star_3){
                Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star4.setImageResource(R.drawable.baseline_star_outline_black_48);
                Star5.setImageResource(R.drawable.baseline_star_outline_black_48);
                //set title rating
                Rating.setText(R.string.title_Medium);
            }
            //set event click star 2
            if(view.getId() == R.id.evaluate_star_4){
                Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star4.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star5.setImageResource(R.drawable.baseline_star_outline_black_48);
                //set title rating
                Rating.setText(R.string.title_Rating_Good);
            }
            //set event click star 2
            if(view.getId() == R.id.evaluate_star_5){
                Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star4.setImageResource(R.drawable.outline_star_purple500_black_48);
                Star5.setImageResource(R.drawable.outline_star_purple500_black_48);
                //set title rating
                Rating.setText(R.string.title_Rating_VeryGood);
            }
            //set event click textview bussiness
            if(view.getId() == R.id.evaluate_txt_Bussiness){
                Bussiness.setBackground(getResources().getDrawable(R.drawable.ic_shape_frames_boders));
                Couple.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Alone.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Friend.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilyBig.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilySmall.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
            }
            //set event click textview Couple
            if(view.getId() == R.id.evaluate_txt_Couple){
                Bussiness.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Couple.setBackground(getResources().getDrawable(R.drawable.ic_shape_frames_boders));
                Alone.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Friend.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilyBig.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilySmall.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
            }
            //set event click textview Alone
            if(view.getId() == R.id.evaluate_txt_Alone){
                Bussiness.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Couple.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Alone.setBackground(getResources().getDrawable(R.drawable.ic_shape_frames_boders));
                Friend.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilyBig.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilySmall.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
            }
            //set event click textview Friend
            if(view.getId() == R.id.evaluate_txt_Friend){
                Bussiness.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Couple.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Alone.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Friend.setBackground(getResources().getDrawable(R.drawable.ic_shape_frames_boders));
                FamilyBig.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilySmall.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
            }
            //set event click textview FamilySmall
            if(view.getId() == R.id.evaluate_txt_FamilySmall){
                Bussiness.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Couple.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Alone.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Friend.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilyBig.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilySmall.setBackground(getResources().getDrawable(R.drawable.ic_shape_frames_boders));
            }
            //set event click textview Family Big
            if(view.getId() == R.id.evaluate_txt_FamilyBig){
                Bussiness.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Couple.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Alone.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                Friend.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
                FamilyBig.setBackground(getResources().getDrawable(R.drawable.ic_shape_frames_boders));
                FamilySmall.setBackground(getResources().getDrawable(R.drawable.ic_shape_round));
            }
            //set event click text  view Camera
            if (view.getId() == R.id.evaluate_txt_ChooseImages){
                requestPermission();
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

        ChooseImage = findViewById(R.id.evaluate_txt_ChooseImages);
        ChooseImage.setOnClickListener(onClickListener);
        recyclerView = findViewById(R.id.evaluate_rcv_ListPhoto);
    }
}