// @emanuel3queijos
package com.example.semocavi2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.semocavi2.dao.MiniCursosDao;
import com.example.semocavi2.dao.PalestraDao;
import com.example.semocavi2.models.MiniCursoModel;
import com.example.semocavi2.models.PalestraModel;

// pra funfar a parte de room corretamente, e necessario colocar as model classes como entidades do banco, pq se n o arquivo generado automaticamente .impl n ajeita tudo e ai vc teria que forcara aentidade na mao, e isso nao e legal

@Database(entities = {MiniCursoModel.class, PalestraModel.class}, version = 5, exportSchema = false)
public abstract class SemocAppDB extends RoomDatabase {
    private static volatile SemocAppDB INSTANCE;

    public abstract MiniCursosDao minicursoDao();
    public abstract PalestraDao palestraDao();


    public static SemocAppDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SemocAppDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    SemocAppDB.class, "semoc_database").allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
