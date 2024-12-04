package com.example.sistema_hospitalario.dao.sqliteDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.sistema_hospitalario.dao.PatientDAO;
import com.example.sistema_hospitalario.dao.listeners.OnPatientReceivedListener;
import com.example.sistema_hospitalario.dao.listeners.OnPatientsReceivedListener;
import com.example.sistema_hospitalario.dto.PatientDTO;
import com.example.sistema_hospitalario.manager.DataBaseManager;

import java.util.ArrayList;
import java.util.List;

    public class SqlitePatientDAO implements PatientDAO {

        private final DataBaseManager db;

        public SqlitePatientDAO(Context context) { db = DataBaseManager.getInstance(context); }

        @Override
        public void createPatient(PatientDTO patientDTO) {
            ContentValues values = new ContentValues();
            values.put("firstName", patientDTO.getFirstName());
            values.put("lastName", patientDTO.getLastName());
            values.put("dni", patientDTO.getDni());
            values.put("email", patientDTO.getEmail());
            values.put("user_medical_code", patientDTO.getUser_medical_code());
            values.put("dateOfBirth", patientDTO.getDateOfBirth());
            values.put("gender", patientDTO.getGender());
            values.put("phone", patientDTO.getPhone());
            values.put("address", patientDTO.getAddress());
            values.put("doctor_email", patientDTO.getDoctor_email());

            db.getWritableDatabase().insert("patient", null, values);
        }

        @Override
        public void updatePatient(String email, PatientDTO patientDTO) {
            ContentValues values = new ContentValues();

            if (patientDTO.getFirstName() != null && !patientDTO.getFirstName().isEmpty()) {
                values.put("firstName", patientDTO.getFirstName());
            }

            if (patientDTO.getLastName() != null && !patientDTO.getLastName().isEmpty()) {
                values.put("lastName", patientDTO.getLastName());
            }

            if (patientDTO.getDni() != null && !patientDTO.getDni().isEmpty()) {
                values.put("dni", patientDTO.getDni());
            }

            if (patientDTO.getEmail() != null && !patientDTO.getEmail().isEmpty()) {
                values.put("email", patientDTO.getEmail());
            }

            if (patientDTO.getUser_medical_code() != null) {
                values.put("user_medical_code", patientDTO.getUser_medical_code());
            }

            if (patientDTO.getDateOfBirth() != null && !patientDTO.getDateOfBirth().isEmpty()) {
                values.put("dateOfBirth", patientDTO.getDateOfBirth());
            }

            if (patientDTO.getGender() != null && !patientDTO.getGender().isEmpty()) {
                values.put("gender", patientDTO.getGender());
            }

            if (patientDTO.getPhone() != null && !patientDTO.getPhone().isEmpty()) {
                values.put("phone", patientDTO.getPhone());
            }

            if (patientDTO.getAddress() != null && !patientDTO.getAddress().isEmpty()) {
                values.put("address", patientDTO.getAddress());
            }

            if (patientDTO.getDoctor_email() != null) {
                values.put("doctor_email", patientDTO.getDoctor_email());
            }

            String whereClause = "email=?";
            String[] whereArgs = { email };

            db.getWritableDatabase().update("patient", values, whereClause, whereArgs);
        }

        @Override
        public void deletePatient(String email) {
            String whereClause = "email=?";
            String[] whereArgs = { email };

            db.getWritableDatabase().delete("patient", whereClause, whereArgs);
        }

        @Override
        public void getPatient(String email, OnPatientReceivedListener listener) {
            String query = "SELECT * FROM patient WHERE email=?";
            String[] whereArgs = { email };

            Cursor cursor;
            PatientDTO patient = null;
            try {
                cursor = db.getReadableDatabase().rawQuery(query, whereArgs);
                if (cursor != null && cursor.moveToFirst()) {
                    patient = new PatientDTO(
                        cursor.getString(cursor.getColumnIndexOrThrow("firstName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("lastName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("user_medical_code")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dateOfBirth")),
                        cursor.getString(cursor.getColumnIndexOrThrow("gender")),
                        cursor.getString(cursor.getColumnIndexOrThrow("phone")),
                        cursor.getString(cursor.getColumnIndexOrThrow("address")),
                        cursor.getString(cursor.getColumnIndexOrThrow("doctor_email"))
                    );
                    cursor.close();
                }
            } catch (Exception e) {
                listener.onError(e);
                return;
            }

            listener.onPatientReceived(patient);
        }

        @Override
        public void getAllPatientsByDoctorEmail(String email, OnPatientsReceivedListener listener) {
            String query = "SELECT * FROM patient WHERE doctor_email=?";
            String[] whereArgs = { email };
            List<PatientDTO> patients = new ArrayList<>();

            Cursor cursor;

            try {
                cursor = db.getReadableDatabase().rawQuery(query, whereArgs);

                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        PatientDTO patient = new PatientDTO(
                                cursor.getString(cursor.getColumnIndexOrThrow("firstName")),
                                cursor.getString(cursor.getColumnIndexOrThrow("lastName")),
                                cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                                cursor.getString(cursor.getColumnIndexOrThrow("email")),
                                cursor.getInt(cursor.getColumnIndexOrThrow("user_medical_code")),
                                cursor.getString(cursor.getColumnIndexOrThrow("dateOfBirth")),
                                cursor.getString(cursor.getColumnIndexOrThrow("gender")),
                                cursor.getString(cursor.getColumnIndexOrThrow("phone")),
                                cursor.getString(cursor.getColumnIndexOrThrow("address")),
                                cursor.getString(cursor.getColumnIndexOrThrow("doctor_email"))
                        );
                        patients.add(patient);
                    }
                    cursor.close();
                }
            } catch (Exception e) {
                listener.onError(e);
            }

            listener.onPatientsReceived(patients);
        }

    }

