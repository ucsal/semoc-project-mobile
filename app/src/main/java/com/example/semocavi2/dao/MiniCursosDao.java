package com.example.semocavi2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.semocavi2.models.MiniCursoModel;
import com.example.semocavi2.models.PalestraModel;

import java.util.List;

@Dao
public interface MiniCursosDao {


        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(List<MiniCursoModel> minicursos);

        @Query("SELECT * FROM tb_mini_cursos")
        LiveData<List<MiniCursoModel>> getMinicursos();

        //verificar o numero de registros na tabela, isso aqui que verificaria se ia precisar dar um refresh no banco
        @Query("SELECT COUNT(*) FROM tb_mini_cursos")
        int getCount();

        @Query("SELECT * FROM tb_mini_cursos WHERE data = :data")
        LiveData<List<MiniCursoModel>> getMinicursosByDate(String data);


        @Query("SELECT * FROM tb_mini_cursos WHERE isScheduled = 1")
        LiveData<List<MiniCursoModel>>getAllMiniCursosWhereIsScheduleIsTrue();


        @Query("UPDATE tb_mini_cursos SET isScheduled = 1 WHERE id = :id")
        void updateIsScheduled(int id);




        @Query("SELECT * FROM tb_mini_cursos WHERE id = :id")
        LiveData<MiniCursoModel> getMinicursosById(int id);


}
