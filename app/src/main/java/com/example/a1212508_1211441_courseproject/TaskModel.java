package com.example.a1212508_1211441_courseproject;

public class TaskModel {

    private int id;
    private String title;
    private String description;
    private String dueDate;
    private String priority;
    private String status;
    private int reminder;

    // Constructor to accept all required fields
    public TaskModel(int id, String title, String description, String dueDate, String priority, String status, int reminder) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.reminder = reminder;
    }

    // Getters for all fields
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public int getReminder() {
        return reminder;
    }
}
