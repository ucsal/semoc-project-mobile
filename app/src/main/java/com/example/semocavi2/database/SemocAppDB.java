// @emanuel3queijos
package com.example.semocavi2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.semocavi2.dao.MiniCursosDao;
import com.example.semocavi2.dao.PalestraDao;
import com.example.semocavi2.dao.PalestranteDao;
import com.example.semocavi2.models.MiniCursoModel;
import com.example.semocavi2.models.PalestraModel;
import com.example.semocavi2.models.PalestranteModel;

// pra funfar a parte de room corretamente, e necessario colocar as model classes como entidades do banco, pq se n o arquivo generado automaticamente .impl n ajeita tudo e ai vc teria que forcara aentidade na mao, e isso nao e legal

@Database(entities = {MiniCursoModel.class, PalestraModel.class, PalestranteModel.class}, version = 5, exportSchema = false)
public abstract class SemocAppDB extends RoomDatabase {
    private static volatile SemocAppDB INSTANCE;

    public abstract MiniCursosDao minicursoDao();
    public abstract PalestraDao palestraDao();

    public abstract PalestranteDao palestranteDao();

    public static SemocAppDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SemocAppDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    SemocAppDB.class, "semoc_database").allowMainThreadQueries().addMigrations()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    // precisa configurar certinho para dar certo a atuializacao do banco
//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE tb_mini_cursos ADD COLUMN new_column TEXT");
//            database.execSQL("ALTER TABLE tb_palestrantes ADD COLUMN new_column TEXT");
//        }
//    };
//
//
//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            // Code to run on first creation of the database
//        }
//
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//            // Code to run every time the database is opened
//            int currentVersion = db.getVersion();
//            Log.d("DatabaseVersion", "Current version: " + currentVersion);
//            // You can handle version check logic here if needed
//        }
//    };


}
