package com.example.semocavi2.service;

import com.example.semocavi2.models.MiniCurso;
import com.example.semocavi2.models.Palestra;
import com.example.semocavi2.models.Pessoa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SemocApiService {
    @GET("minicursos.json")
    Call<List<MiniCurso>> getMinicursos();

    @GET("palestras.json")
    Call<List<Palestra>> getPalestras();

    @GET("pessoas.json")
    Call<List<Pessoa>> getPessoas();
}
