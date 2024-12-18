package com.example.a1212508_1211441_courseproject;

import android.annotation.SuppressLint;
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
    // Your database and table initialization code here

    public List<TaskModel> getAllTasksSortedByDate(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<TaskModel> tasks = new ArrayList<>();

        // SQL query to fetch tasks sorted by due date (chronologically)
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Tasks WHERE email = ? ORDER BY due_date_time ASC",
                new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range") String dueDateTime = cursor.getString(cursor.getColumnIndex("due_date_time"));
                @SuppressLint("Range") String priority = cursor.getString(cursor.getColumnIndex("priority"));
                @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("status"));
                @SuppressLint("Range") int reminder = cursor.getInt(cursor.getColumnIndex("reminder"));

                TaskModel task = new TaskModel(id, title, description, dueDateTime, priority, status, reminder);
                tasks.add(task);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return tasks;
    }


    public boolean updateTask(TaskModel task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", task.getTitle());
        values.put("description", task.getDescription());
        values.put("due_date", task.getDueDate());
        values.put("priority", task.getPriority());

        int rowsAffected = db.update("Tasks", values, "id = ?", new String[]{String.valueOf(task.getId())});
        db.close();
        return rowsAffected > 0;  // Return true if at least one row was updated
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



    public Cursor getTasksByUserEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Tasks WHERE email = ? ORDER BY due_date_time", new String[]{email});
    }
}
