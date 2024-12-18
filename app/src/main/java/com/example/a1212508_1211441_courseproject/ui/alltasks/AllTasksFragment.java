package com.example.a1212508_1211441_courseproject.ui.alltasks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1212508_1211441_courseproject.DataBaseHelper;
import com.example.a1212508_1211441_courseproject.TaskAdapter;
import com.example.a1212508_1211441_courseproject.EditTaskActivity;
import com.example.a1212508_1211441_courseproject.TaskModel;
import com.example.a1212508_1211441_courseproject.R;

import java.util.List;

public class AllTasksFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<TaskModel> allTasksList;
    private DataBaseHelper dbHelper;

    public AllTasksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_tasks, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerViewAllTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DataBaseHelper(getContext());

        // Retrieve the logged-in user's email from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String loggedInUserEmail = sharedPreferences.getString("email", null);

        if (loggedInUserEmail == null) {
            Toast.makeText(getContext(), "Error: User not logged in", Toast.LENGTH_SHORT).show();
            return rootView; // Return early if no user is logged in
        }

        // Fetch tasks for the logged-in user, sorted by due date (SQL query handles sorting)
        allTasksList = dbHelper.getAllTasksSortedByDate(loggedInUserEmail);

        // Initialize the adapter and set it to the RecyclerView
        taskAdapter = new TaskAdapter(allTasksList, new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(TaskModel task) {
                // Handle task click: show delete and edit options
                showTaskOptions(task);
            }
        });
        recyclerView.setAdapter(taskAdapter);

        return rootView;
    }

    private void showTaskOptions(TaskModel task) {
        // Create an AlertDialog to display task details and options to Delete or Edit
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Set task details as message in the dialog
        builder.setTitle("Task Details")
                .setMessage("Title: " + task.getTitle() + "\n" +
                        "Description: " + task.getDescription() + "\n" +
                        "Due Date: " + task.getDueDate() + "\n" +
                        "Priority: " + task.getPriority() + "\n" +
                        "Status: " + task.getStatus()
                )
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Call the edit function (start EditTaskActivity with the task data)
                        editTask(task);
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Call the delete function
                        deleteTask(task);
                    }
                })
                .setNeutralButton("Cancel", null)  // Close the dialog
                .show();
    }

    private void editTask(TaskModel task) {
        // Create an Intent to start EditTaskActivity
        Intent intent = new Intent(getContext(), EditTaskActivity.class);

        // Pass task data to the EditTaskActivity
        intent.putExtra("taskId", task.getId());
        intent.putExtra("taskTitle", task.getTitle());
        intent.putExtra("taskDescription", task.getDescription());
        intent.putExtra("taskDueDate", task.getDueDate());
        intent.putExtra("taskPriority", task.getPriority());
        intent.putExtra("taskStatus", task.getStatus());

        // Start EditTaskActivity
        startActivity(intent);
    }

    private void deleteTask(TaskModel task) {
        // Delete the task from the database
        boolean isDeleted = dbHelper.deleteTask(task.getId());

        if (isDeleted) {
            Toast.makeText(getContext(), "Task deleted successfully", Toast.LENGTH_SHORT).show();
            // Update the RecyclerView by removing the task
            allTasksList.remove(task);
            taskAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "Failed to delete task", Toast.LENGTH_SHORT).show();
        }
    }
}
