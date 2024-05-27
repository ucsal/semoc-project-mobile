package com.example.semocavi2.service;

import com.example.semocavi2.models.MiniCursoModel;
import com.example.semocavi2.models.PalestraModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SemocApiService {
    @GET("/minicursos.json")
    Call<List<MiniCursoModel>> getMinicursos();

    @GET("palestras.json")
    Call<List<PalestraModel>> getPalestras();
//
//    @GET("pessoas.json")
//    Call<List<PessoaViewModel>> getPessoas();
}
