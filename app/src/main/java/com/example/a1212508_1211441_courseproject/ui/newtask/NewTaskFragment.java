package com.example.a1212508_1211441_courseproject.ui.newtask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.a1212508_1211441_courseproject.R;


public class NewTaskFragment extends Fragment {

    private EditText editTextTaskTitle, editTextTaskDescription, editTextDueDate;
    private Spinner spinnerPriority;
    private CheckBox checkBoxReminder;
    private Button buttonSaveTask;

    public NewTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_task, container, false);

        editTextTaskTitle = rootView.findViewById(R.id.editTextTaskTitle);
        editTextTaskDescription = rootView.findViewById(R.id.editTextTaskDescription);
        editTextDueDate = rootView.findViewById(R.id.editTextDueDate);
        spinnerPriority = rootView.findViewById(R.id.spinnerPriority);
        checkBoxReminder = rootView.findViewById(R.id.checkBoxReminder);
        buttonSaveTask = rootView.findViewById(R.id.buttonSaveTask);

        buttonSaveTask.setOnClickListener(v -> {
            // Logic to save the task
            String taskTitle = editTextTaskTitle.getText().toString();
            String taskDescription = editTextTaskDescription.getText().toString();
            String dueDate = editTextDueDate.getText().toString();
            boolean reminder = checkBoxReminder.isChecked();

            // Add logic to save the task to database or memory
            Toast.makeText(getContext(), "Task saved", Toast.LENGTH_SHORT).show();
        });

        return rootView;
    }
}
