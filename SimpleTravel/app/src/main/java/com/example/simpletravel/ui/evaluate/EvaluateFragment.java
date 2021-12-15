package com.example.simpletravel.ui.evaluate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.simpletravel.R;
import com.example.simpletravel.databinding.FragmentEvaluateBinding;
import com.example.simpletravel.viewmodel.EvaluateViewModel;


public class EvaluateFragment extends Fragment {

    private FragmentEvaluateBinding binding;
    private Button Evaluate, Images;
    private View root;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.evaluate_btn_Evaluate){
                //replace fragment main evaluate is equal to fragment search item rating
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.evaluate_frameLayout, new SearchItemRatingFragment());
                transaction.addToBackStack(SearchItemRatingFragment.TAG1);
                transaction.commit();
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

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