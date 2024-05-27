package com.example.semocavi2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.semocavi2.models.PalestraModel;

import java.util.List;

public interface PalestraDao {

    // esse cara vai dar um replace nos caras igual( eu acho), se n a gente ajeita dps, n tiira ponto mario pf
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<PalestraModel> palestras);

    @Query("SELECT * FROM tb_palestra")
    LiveData<List<PalestraModel>> getPalestras();

    //verificar o numero de registros na tabela para saber se tem coisa no banco ou n
    @Query("SELECT COUNT(*) FROM tb_palestra")
    int getCount();




}
