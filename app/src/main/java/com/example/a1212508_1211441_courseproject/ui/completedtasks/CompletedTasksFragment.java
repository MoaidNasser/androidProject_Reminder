package com.example.a1212508_1211441_courseproject.ui.completedtasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.a1212508_1211441_courseproject.R;
import com.example.a1212508_1211441_courseproject.TaskAdapter;
import com.example.a1212508_1211441_courseproject.TaskModel;

public class CompletedTasksFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<TaskModel> completedTasksList; // List of TaskModel objects

    public CompletedTasksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_completed_tasks, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerViewCompletedTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sample data for completed tasks (create TaskModel objects instead of strings)
        completedTasksList = new ArrayList<>();
        completedTasksList.add(new TaskModel(1, "Completed Task 1", "Task description 1", "2024-12-17", "High", "Completed", 1));
        completedTasksList.add(new TaskModel(2, "Completed Task 2", "Task description 2", "2024-12-18", "Medium", "Completed", 0));

        // Pass the list of TaskModel to the TaskAdapter
        taskAdapter = new TaskAdapter(completedTasksList, new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(TaskModel task) {
                // Handle task click (e.g., show task details, edit, or delete)
            }
        });

        recyclerView.setAdapter(taskAdapter);

        return rootView;
    }
}
