package com.example.a1212508_1211441_courseproject.ui.alltasks;

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

        // Fetch tasks for the logged-in user
        String loggedInUserEmail = "user@example.com"; // Replace with actual logged-in user email
        allTasksList = dbHelper.getAllTasks(loggedInUserEmail);

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
        // Show a simple toast or dialog with options to Edit or Delete
        Toast.makeText(getContext(), "Options: Edit or Delete task with ID " + task.getId(), Toast.LENGTH_SHORT).show();
        // You can implement your Edit and Delete logic here, for example:
        // - Edit: Open a new fragment or activity to edit the task
        // - Delete: Remove the task from the database and update the RecyclerView
    }
}
