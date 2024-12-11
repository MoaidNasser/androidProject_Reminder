package com.example.a1212508_1211441_courseproject.ui.todaytask;

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


public class TodayFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<String> taskList;

    public TodayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_today, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerViewToday);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sample data for today tasks
        taskList = new ArrayList<>();
        taskList.add("Task 1 for Today");
        taskList.add("Task 2 for Today");

        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);

        return rootView;
    }
}
