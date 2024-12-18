package com.example.a1212508_1211441_courseproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TaskManager.db";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users table
        db.execSQL("CREATE TABLE Users(" +
                "email TEXT PRIMARY KEY, " +
                "first_name TEXT, " +
                "last_name TEXT, " +
                "password TEXT)");

        // Tasks table with updated schema
        db.execSQL("CREATE TABLE Tasks(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT, " +
                "title TEXT, " +
                "description TEXT, " +
                "due_date_time TEXT, " + // Due date and time
                "priority TEXT, " +      // Priority level
                "status TEXT DEFAULT 'Pending', " + // Completion status
                "reminder INTEGER DEFAULT 0, " +    // Reminder flag: 0 = off, 1 = on
                "FOREIGN KEY(email) REFERENCES Users(email))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Tasks");
        onCreate(db);
    }


    // Register User
    public boolean registerUser(String email, String firstName, String lastName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("first_name", firstName);
        values.put("last_name", lastName);
        values.put("password", password);
        long result = db.insert("Users", null, values);
        return result != -1;
    }

    // Authenticate User
    public boolean authenticateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE email = ? AND password = ?", new String[]{email, password});
        boolean isAuthenticated = cursor.getCount() > 0;
        cursor.close();
        return isAuthenticated;
    }

    // Retrieve all users
    public Cursor getUsername(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Users WHERE email = ? ", new String[]{email});
    }



    /**
     * Add a new task to the database.
     */
    public boolean addTask(String userEmail, String title, String description, String dueDateTime, String priority,String status, int reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("email", userEmail);
        values.put("title", title);
        values.put("description", description);
        values.put("due_date_time", dueDateTime);
        values.put("priority", priority);
        values.put("status", status); // Default status
        values.put("reminder", reminder); // Reminder flag

        long result = db.insert("Tasks", null, values);
        db.close();
        return result != -1; // Return true if insertion is successful
    }

    /**
     * Retrieve all tasks for a specific user.
     */
// In your DataBaseHelper class
    public List<TaskModel> getAllTasks(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<TaskModel> tasks = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM Tasks WHERE email = ? ORDER BY due_date_time", new String[]{email});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Safely retrieve column indices
                int idColumnIndex = cursor.getColumnIndex("id");
                int titleColumnIndex = cursor.getColumnIndex("title");
                int descriptionColumnIndex = cursor.getColumnIndex("description");
                int dueDateTimeColumnIndex = cursor.getColumnIndex("due_date_time");
                int priorityColumnIndex = cursor.getColumnIndex("priority");
                int statusColumnIndex = cursor.getColumnIndex("status");
                int reminderColumnIndex = cursor.getColumnIndex("reminder");

                // Make sure column indices are valid
                if (idColumnIndex != -1 && titleColumnIndex != -1 && descriptionColumnIndex != -1 &&
                        dueDateTimeColumnIndex != -1 && priorityColumnIndex != -1 && statusColumnIndex != -1 &&
                        reminderColumnIndex != -1) {

                    int id = cursor.getInt(idColumnIndex);
                    String title = cursor.getString(titleColumnIndex);
                    String description = cursor.getString(descriptionColumnIndex);
                    String dueDateTime = cursor.getString(dueDateTimeColumnIndex);
                    String priority = cursor.getString(priorityColumnIndex);
                    String status = cursor.getString(statusColumnIndex);
                    int reminder = cursor.getInt(reminderColumnIndex);

                    // Create TaskModel and add to the list
                    TaskModel task = new TaskModel(id, title, description, dueDateTime, priority, status, reminder);
                    tasks.add(task);
                } else {
                    // Log or handle the case where the column doesn't exist
                    Log.e("Database", "One or more columns are missing.");
                }

            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return tasks;
    }


    /**
     * Update the completion status of a task.
     */
    public boolean updateTaskStatus(int taskId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", newStatus);

        int rowsUpdated = db.update("Tasks", values, "id = ?", new String[]{String.valueOf(taskId)});
        db.close();
        return rowsUpdated > 0;
    }

    /**
     * Update task details such as title, description, due date, priority, and reminder.
     */
    public boolean updateTaskDetails(int taskId, String title, String description, String dueDateTime, String priority, int reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("title", title);
        values.put("description", description);
        values.put("due_date_time", dueDateTime);
        values.put("priority", priority);
        values.put("reminder", reminder);

        int rowsUpdated = db.update("Tasks", values, "id = ?", new String[]{String.valueOf(taskId)});
        db.close();
        return rowsUpdated > 0;
    }

    /**
     * Delete a task from the database.
     */
    public boolean deleteTask(int taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("Tasks", "id = ?", new String[]{String.valueOf(taskId)});
        db.close();
        return rowsDeleted > 0;
    }

    /**
     * Retrieve a task by its ID.
     */
    public Cursor getTaskById(int taskId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Tasks WHERE id = ?", new String[]{String.valueOf(taskId)});
    }

    /**
     * Retrieve all tasks for a specific user, ordered by due date.
     */
    public Cursor getTasksByUserEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Tasks WHERE email = ? ORDER BY due_date_time", new String[]{email});
    }
}
