package com.example.simpletravel.ui.evaluate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.simpletravel.R;
import com.example.simpletravel.databinding.FragmentEvaluateBinding;

import java.io.InputStream;


public class EvaluateFragment extends Fragment {

    private EvaluateViewModel notificationsViewModel;
    private FragmentEvaluateBinding binding;
    private Button Evaluate, Images;
    private View root;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.evaluate_btn_Evaluate){
                Intent intent = new Intent(getActivity(), EvaluateActivity.class);
                startActivity(intent);
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(EvaluateViewModel.class);

        binding = FragmentEvaluateBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        EvaluateConstructor();
        return root;
    }

    private void EvaluateConstructor() {
        Evaluate = root.findViewById(R.id.evaluate_btn_Evaluate);
        Evaluate.setOnClickListener(onClickListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}