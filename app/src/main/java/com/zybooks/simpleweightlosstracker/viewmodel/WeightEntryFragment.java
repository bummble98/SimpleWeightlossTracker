package com.zybooks.simpleweightlosstracker.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.zybooks.simpleweightlosstracker.R;
import com.zybooks.simpleweightlosstracker.databinding.FragmentWeightEntryBinding;
import com.zybooks.simpleweightlosstracker.model.Weight;
import com.zybooks.simpleweightlosstracker.repo.WeightLogRepository;

import java.util.Locale;
import java.util.Objects;

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
        Context mainActivityContext = requireActivity();
        WeightLogRepository repository = WeightLogRepository.getInstance(mainActivityContext);
        MainActivity mainActivity = (MainActivity) requireActivity();
        binding.buttonSecond.setOnClickListener(v -> {
            TextInputEditText weightEntryField = view.findViewById(R.id.weight_entry_field);
            //Check if weight entry field is empty
            if (!Objects.requireNonNull(weightEntryField.getText()).toString().trim().isEmpty()) {
                DatePicker datePicker = view.findViewById(R.id.datePicker);
                //Collect weight data
                String date = String.format(Locale.getDefault(), "%04d-%02d-%02d",
                        datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
                Integer weight = Integer.parseInt(Objects.requireNonNull(weightEntryField.getText()).toString());
                //Add weight to repository
                repository.addWeight(new Weight(date, weight, mainActivity.getUsername()));
                //Return to main fragment
                NavController navController = NavHostFragment.findNavController(WeightEntryFragment.this);
                navController.navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
            else {
                Toast.makeText(mainActivity, "Must include weight", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}