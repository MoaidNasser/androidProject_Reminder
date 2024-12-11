package com.example.a1212508_1211441_courseproject.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.a1212508_1211441_courseproject.R;

public class ProfileFragment extends Fragment {

    private TextView textViewEmail;
    private EditText editTextEmail, editTextPassword;
    private Button buttonUpdateProfile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        textViewEmail = rootView.findViewById(R.id.textViewEmail);
        editTextEmail = rootView.findViewById(R.id.editTextEmail);
        editTextPassword = rootView.findViewById(R.id.editTextPassword);
        buttonUpdateProfile = rootView.findViewById(R.id.buttonUpdateProfile);

        buttonUpdateProfile.setOnClickListener(v -> {
            // Logic to update profile
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            // Update profile in database or shared preferences
            Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_SHORT).show();
        });

        return rootView;
    }
}
