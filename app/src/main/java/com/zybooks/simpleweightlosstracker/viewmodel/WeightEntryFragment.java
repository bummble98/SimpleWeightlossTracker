package com.zybooks.simpleweightlosstracker.viewmodel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.zybooks.simpleweightlosstracker.R;
import com.zybooks.simpleweightlosstracker.databinding.FragmentWeightEntryBinding;

public class WeightEntryFragment extends Fragment {

    private FragmentWeightEntryBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentWeightEntryBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(WeightEntryFragment.this);
            navController.navigate(R.id.action_SecondFragment_to_FirstFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}