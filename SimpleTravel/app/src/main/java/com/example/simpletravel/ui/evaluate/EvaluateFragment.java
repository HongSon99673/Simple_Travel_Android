package com.example.simpletravel.ui.evaluate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.simpletravel.databinding.FragmentEvaluateBinding;


public class EvaluateFragment extends Fragment {

    private EvaluateViewModel notificationsViewModel;
    private FragmentEvaluateBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(EvaluateViewModel.class);

        binding = FragmentEvaluateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textEvaluate;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}