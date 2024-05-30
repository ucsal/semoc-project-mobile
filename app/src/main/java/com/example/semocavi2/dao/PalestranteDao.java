package com.example.semocavi2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.semocavi2.models.PalestranteModel;

import java.util.List;

@Dao
public interface PalestranteDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<PalestranteModel> palestrantes);


    @Query("SELECT * FROM tb_palestrantes")
    LiveData<List<PalestranteModel>> getPalestrantes();

    @Query("SELECT COUNT(*) FROM tb_palestrantes")
    int getCount();

    @Query("SELECT * FROM tb_palestrantes WHERE id = :id")
    LiveData<PalestranteModel> getPalestrantesById(int id);

}
