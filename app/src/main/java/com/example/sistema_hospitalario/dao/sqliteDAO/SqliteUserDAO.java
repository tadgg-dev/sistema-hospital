package com.example.sistema_hospitalario.dao.sqliteDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.sistema_hospitalario.dao.listeners.OnUsersReceivedListener;
import com.example.sistema_hospitalario.manager.DataBaseManager;
import com.example.sistema_hospitalario.dao.UserDAO;
import com.example.sistema_hospitalario.dao.listeners.OnUserReceivedListener;
import com.example.sistema_hospitalario.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class SqliteUserDAO implements UserDAO {

    private static final String TAG = "USER DAO";
    private DataBaseManager db;

    public SqliteUserDAO(Context context) {
        db = DataBaseManager.getInstance(context);
    }

    @Override
    public void createUser(UserDTO userDTO) {
        ContentValues values = new ContentValues();
        values.put("firstName", userDTO.getFirstName());
        values.put("lastName", userDTO.getLastName());
        values.put("dni", userDTO.getDni());
        values.put("userName", userDTO.getUserName());
        values.put("email", userDTO.getEmail());
        values.put("password", userDTO.getPassword());
        values.put("medical_license", userDTO.getMedical_license());

        db.getWritableDatabase().insert("user", null, values);
    }

    @Override
    public void updateUser(String email, UserDTO userDTO) {
        ContentValues values = new ContentValues();

        if (userDTO.getFirstName() != null && !userDTO.getFirstName().isEmpty()) {
            values.put("firstName", userDTO.getFirstName());
        }

        if (userDTO.getLastName() != null && !userDTO.getLastName().isEmpty()) {
            values.put("lastName", userDTO.getLastName());
        }

        if (userDTO.getDni() != null && !userDTO.getDni().isEmpty()) {
            values.put("dni", userDTO.getDni());
        }

        if (userDTO.getUserName() != null && !userDTO.getUserName().isEmpty()) {
            values.put("userName", userDTO.getUserName());
        }

        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
            values.put("email", userDTO.getEmail());
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            values.put("password", userDTO.getPassword());
        }

        if (userDTO.getMedical_license() != 0) {
            values.put("medical_license", userDTO.getMedical_license());
        }

        String whereClause = "email=?";
        String[] whereArgs = { email };

        db.getWritableDatabase().update("user", values, whereClause, whereArgs);
    }


    @Override
    public void deleteUser(String email) {
        String whereClause = "email=?";
        String[] whereArgs = { email };

        db.getWritableDatabase().delete("user", whereClause, whereArgs);
    }

    @Override
    public void getUser(String email, OnUserReceivedListener listener) {
        String query = "SELECT * FROM user WHERE email=?";
        String[] whereArgs = { email };

        Cursor cursor = null;
        UserDTO user = null;
        try {
            cursor = db.getReadableDatabase().rawQuery(query, whereArgs);
            if (cursor != null && cursor.moveToFirst()) {
                user = new UserDTO(
                        cursor.getString(cursor.getColumnIndexOrThrow("firstName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("lastName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("userName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("password")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("medical_license"))
                );
                cursor.close();
            }
        } catch (Exception e) {
            listener.onError(e);
            return;
        }

        listener.onUserReceived(user);
    }


    @Override
    public void getAllUsers(OnUsersReceivedListener listener) {
        String query = "SELECT * FROM user";
        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);
        List<UserDTO> users = new ArrayList<>();

        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    UserDTO user = new UserDTO(
                        cursor.getString(cursor.getColumnIndexOrThrow("firstName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("lastName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("userName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("password")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("medical_license"))
                    );
                    users.add(user);
                }
                cursor.close();
            }
        } catch (Exception e) {
            listener.onError(e);
            return;
        }

        listener.onUsersReceived(users);
    }
}
