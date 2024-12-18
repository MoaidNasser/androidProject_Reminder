package com.example.a1212508_1211441_courseproject.ui.newtask;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.a1212508_1211441_courseproject.DataBaseHelper;
import com.example.a1212508_1211441_courseproject.R;

public class NewTaskFragment extends Fragment {

    private EditText editTextTaskTitle, editTextTaskDescription, editTextDueDate;
    private Spinner spinnerPriority;
    private CheckBox checkBoxReminder, checkBoxCompletionStatus;
    private Button buttonSaveTask;

    private DataBaseHelper dbHelper; // Database Helper
    private String loggedInUserEmail; // Logged-in user's email

    public NewTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_task, container, false);

        // Initialize views
        editTextTaskTitle = rootView.findViewById(R.id.editTextTaskTitle);
        editTextTaskDescription = rootView.findViewById(R.id.editTextTaskDescription);
        editTextDueDate = rootView.findViewById(R.id.editTextDueDate);
        spinnerPriority = rootView.findViewById(R.id.spinnerPriority);
        checkBoxReminder = rootView.findViewById(R.id.checkBoxReminder);
        checkBoxCompletionStatus = rootView.findViewById(R.id.checkBoxCompletionStatus); // Completion Status
        buttonSaveTask = rootView.findViewById(R.id.buttonSaveTask);

        dbHelper = new DataBaseHelper(getContext()); // Initialize database helper

        // Retrieve logged-in user's email from Intent
        Intent intent = getActivity().getIntent();
        loggedInUserEmail = intent.getStringExtra("email");

        if (loggedInUserEmail == null || loggedInUserEmail.isEmpty()) {
            Toast.makeText(getContext(), "Error: User not logged in", Toast.LENGTH_SHORT).show();
            return rootView;
        }

        // Setup spinner for priority selection
        setupPrioritySpinner();

        buttonSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collect input values
                String taskTitle = editTextTaskTitle.getText().toString().trim();
                String taskDescription = editTextTaskDescription.getText().toString().trim();
                String dueDate = editTextDueDate.getText().toString().trim();
                String priority = spinnerPriority.getSelectedItem().toString();
                boolean reminder = checkBoxReminder.isChecked();
                boolean isCompleted = checkBoxCompletionStatus.isChecked();

                String status = isCompleted ? "Completed" : "Pending";

                // Input validation
                if (taskTitle.isEmpty() || taskDescription.isEmpty() || dueDate.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Append reminder note if needed
                if (reminder) {
                    taskDescription += " [Reminder Set]";
                }

                // Save task to the database with the logged-in user's email and completion status
                boolean isInserted = dbHelper.addTask(
                        loggedInUserEmail,
                        taskTitle,
                        taskDescription,
                        dueDate,
                        priority,
                        status,
                        reminder ? 1 : 0  // Set reminder to 1 if checked, otherwise 0
                );

                if (isInserted) {
                    Toast.makeText(getContext(), "Task saved successfully!", Toast.LENGTH_SHORT).show();
                    clearInputs();
                } else {
                    Toast.makeText(getContext(), "Failed to save task", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    private void setupPrioritySpinner() {
        // Define priority levels directly in the code
        String[] priorityLevels = {"High", "Medium", "Low"};

        // Set up the Spinner with an ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                priorityLevels
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(adapter);
    }

    private void clearInputs() {
        editTextTaskTitle.setText("");
        editTextTaskDescription.setText("");
        editTextDueDate.setText("");
        checkBoxReminder.setChecked(false);
        checkBoxCompletionStatus.setChecked(false);
    }
}
