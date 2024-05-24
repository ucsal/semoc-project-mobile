package com.example.semocavi2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.semocavi2.models.MiniCursoModel;
import com.example.semocavi2.ui.minicurso.MiniCursoViewModel;

import java.util.List;

@Dao
public interface MiniCursosDao {


        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(List<MiniCursoModel> minicursos);

        @Query("SELECT * FROM tb_mini_cursos")
        LiveData<List<MiniCursoModel>> getMinicursos();



}