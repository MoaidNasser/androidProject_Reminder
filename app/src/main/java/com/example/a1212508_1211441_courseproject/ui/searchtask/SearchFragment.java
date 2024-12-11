package com.example.a1212508_1211441_courseproject.ui.searchtask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.a1212508_1211441_courseproject.R;


public class SearchFragment extends Fragment {

    private EditText editTextStartDate, editTextEndDate;
    private Button buttonSearch;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        editTextStartDate = rootView.findViewById(R.id.editTextStartDate);
        editTextEndDate = rootView.findViewById(R.id.editTextEndDate);
        buttonSearch = rootView.findViewById(R.id.buttonSearch);

        buttonSearch.setOnClickListener(v -> {
            String startDate = editTextStartDate.getText().toString();
            String endDate = editTextEndDate.getText().toString();

            // Add logic to search tasks based on date range
            Toast.makeText(getContext(), "Searching tasks...", Toast.LENGTH_SHORT).show();
        });

        return rootView;
    }
}
