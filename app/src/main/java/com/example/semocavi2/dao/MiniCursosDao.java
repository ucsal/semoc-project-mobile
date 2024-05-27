package com.example.semocavi2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.semocavi2.models.MiniCursoModel;

import java.util.List;

@Dao
public interface MiniCursosDao {


        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(List<MiniCursoModel> minicursos);

        @Query("SELECT * FROM tb_mini_cursos")
        LiveData<List<MiniCursoModel>> getMinicursos();

        //verificar o numero de registros na tabela
        @Query("SELECT COUNT(*) FROM tb_mini_cursos")
        int getCount();



}
