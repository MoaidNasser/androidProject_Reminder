package com.example.a1212508_1211441_courseproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<String> taskList;

    public TaskAdapter(List<String> taskList) {
        this.taskList = taskList;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate your item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        // Bind data to views
        String task = taskList.get(position);
        holder.taskTitle.setText(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // ViewHolder class
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.taskTitle);
        }
    }
}
