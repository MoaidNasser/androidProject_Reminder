package com.example.a1212508_1211441_courseproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a1212508_1211441_courseproject.DataBaseHelper;
import com.example.a1212508_1211441_courseproject.TaskModel;

public class EditTaskActivity extends AppCompatActivity {

    private EditText editTaskTitle, editTaskDescription, editTaskDueDate;
    private Spinner editTaskPriority, editTaskStatus;
    private DataBaseHelper dbHelper;
    private TaskModel task;

    // Arrays for priority and status
    private String[] priorityArray = {"Low", "Medium", "High"};
    private String[] statusArray = {"Pending", "In Progress", "Completed"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // Initialize views
        editTaskTitle = findViewById(R.id.editTaskTitle);
        editTaskDescription = findViewById(R.id.editTaskDescription);
        editTaskDueDate = findViewById(R.id.editTaskDueDate);
        editTaskPriority = findViewById(R.id.editTaskPriority);
        editTaskStatus = findViewById(R.id.editTaskStatus);

        dbHelper = new DataBaseHelper(this);

        // Set priority and status arrays in spinners
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, priorityArray);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTaskPriority.setAdapter(priorityAdapter);

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusArray);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTaskStatus.setAdapter(statusAdapter);

        // Retrieve the task from the Intent
        task = (TaskModel) getIntent().getSerializableExtra("task");

        // Pre-fill fields with task data if task exists
        if (task != null) {
            editTaskTitle.setText(task.getTitle());
            editTaskDescription.setText(task.getDescription());
            editTaskDueDate.setText(task.getDueDate());

            // Set the selected priority and status based on the task
            int priorityPosition = getPriorityPosition(task.getPriority());
            editTaskPriority.setSelection(priorityPosition);

            int statusPosition = getStatusPosition(task.getStatus());
            editTaskStatus.setSelection(statusPosition);
        }

        // Save button functionality
        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTaskChanges();
            }
        });
    }

    private void saveTaskChanges() {
        String title = editTaskTitle.getText().toString();
        String description = editTaskDescription.getText().toString();
        String dueDate = editTaskDueDate.getText().toString();
        String priority = editTaskPriority.getSelectedItem().toString();
        String status = editTaskStatus.getSelectedItem().toString();



        // Update task data only for the modified fields
        if (!title.isEmpty()) task.setTitle(title);
        if (!description.isEmpty()) task.setDescription(description);
        if (!dueDate.isEmpty()) task.setDueDate(dueDate);
        task.setPriority(priority);
        task.setStatus(status);

        // Update task in the database
        boolean isUpdated = dbHelper.updateTask(task);

        if (isUpdated) {
            Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity after saving
        } else {
            Toast.makeText(this, "Failed to update task", Toast.LENGTH_SHORT).show();
        }
    }

    private int getPriorityPosition(String priority) {
        for (int i = 0; i < priorityArray.length; i++) {
            if (priorityArray[i].equals(priority)) {
                return i;
            }
        }
        return 0; // Default to the first item (Low)
    }

    private int getStatusPosition(String status) {
        for (int i = 0; i < statusArray.length; i++) {
            if (statusArray[i].equals(status)) {
                return i;
            }
        }
        return 0; // Default to the first item (Pending)
    }
}
