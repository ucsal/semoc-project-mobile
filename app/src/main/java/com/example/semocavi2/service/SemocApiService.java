package com.example.semocavi2.service;

import com.example.semocavi2.models.MiniCursoModel;
import com.example.semocavi2.ui.minicurso.MiniCursoViewModel;
import com.example.semocavi2.ui.palestra.PalestraViewModel;
import com.example.semocavi2.ui.pessoa.PessoaViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SemocApiService {
    @GET("minicursos.json")
    Call<List<MiniCursoModel>> getMinicursos();

//    @GET("palestras.json")
//    Call<List<PalestraViewModel>> getPalestras();
//
//    @GET("pessoas.json")
//    Call<List<PessoaViewModel>> getPessoas();
}
