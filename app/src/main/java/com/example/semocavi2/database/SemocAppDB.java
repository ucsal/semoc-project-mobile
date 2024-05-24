package com.example.semocavi2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.semocavi2.dao.MiniCursosDao;
import com.example.semocavi2.models.MiniCursoModel;
import com.example.semocavi2.ui.minicurso.MiniCursoViewModel;


@Database(entities = {MiniCursoModel.class}, version = 5, exportSchema = false)
public abstract class SemocAppDB extends RoomDatabase {
    private static volatile SemocAppDB INSTANCE;

    public abstract MiniCursosDao minicursoDao();

    public static SemocAppDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SemocAppDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    SemocAppDB.class, "semoc_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}