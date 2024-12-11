package com.example.a1212508_1211441_courseproject.ui.alltasks;

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


public class AllTasksFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<String> allTasksList;

    public AllTasksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_all_tasks, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerViewAllTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sample data for all tasks
        allTasksList = new ArrayList<>();
        allTasksList.add("All Task 1");
        allTasksList.add("All Task 2");

        taskAdapter = new TaskAdapter(allTasksList);
        recyclerView.setAdapter(taskAdapter);

        return rootView;
    }
}
