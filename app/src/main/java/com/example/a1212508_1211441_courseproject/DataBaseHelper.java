package com.example.a1212508_1211441_courseproject;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TaskManager.db";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // User table
        db.execSQL("CREATE TABLE Users(" +
                "email TEXT PRIMARY KEY, " +
                "first_name TEXT, " +
                "last_name TEXT, " +
                "password TEXT)");

        // Tasks table
        db.execSQL("CREATE TABLE Tasks(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT, " +
                "title TEXT, " +
                "description TEXT, " +
                "due_date TEXT, " +
                "priority TEXT, " +
                "status TEXT DEFAULT 'Pending', " +
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

    // Add Task
    public boolean addTask(String email, String title, String description, String dueDate, String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("title", title);
        values.put("description", description);
        values.put("due_date", dueDate);
        values.put("priority", priority);
        long result = db.insert("Tasks", null, values);
        return result != -1;
    }

    // Fetch Tasks
    public Cursor getTasks(String email, String statusFilter) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Tasks WHERE email = ? AND status = ? ORDER BY due_date", new String[]{email, statusFilter});
    }

    // Update Task Status
    public boolean updateTaskStatus(int taskId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", status);
        int rowsAffected = db.update("Tasks", values, "id = ?", new String[]{String.valueOf(taskId)});
        return rowsAffected > 0;
    }

    // Delete Task
    public boolean deleteTask(int taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("Tasks", "id = ?", new String[]{String.valueOf(taskId)});
        return rowsDeleted > 0;
    }
}
