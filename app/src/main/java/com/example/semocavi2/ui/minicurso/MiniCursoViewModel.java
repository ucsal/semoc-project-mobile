package com.example.semocavi2.ui.minicurso;

import androidx.lifecycle.ViewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.semocavi2.models.MiniCursoModel;
import com.example.semocavi2.service.SemocApiService;
import com.example.semocavi2.database.SemocAppDB;
import com.example.semocavi2.dao.MiniCursosDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MiniCursoViewModel extends ViewModel {

    private SemocApiService semocApiService;
    private MiniCursosDao miniCursosDao;
    private LiveData<List<MiniCursoModel>> minicursosLiveData;

    public MiniCursoViewModel(SemocApiService semocApiService, SemocAppDB database) {
        this.semocApiService = semocApiService;
        this.miniCursosDao = database.minicursoDao();
        this.minicursosLiveData = miniCursosDao.getMinicursos();
        loadMinicursos(); // Carrega os minicursos ao instanciar o ViewModel
    }

    public LiveData<List<MiniCursoModel>> getMinicursos() {
        return minicursosLiveData;
    }

    public void loadMinicursos() {
        semocApiService.getMinicursos().enqueue(new Callback<List<MiniCursoModel>>() {
            @Override
            public void onResponse(Call<List<MiniCursoModel>> call, Response<List<MiniCursoModel>> response) {
                if (response.isSuccessful()) {
                    // Salva os minicursos no banco de dados Room
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            miniCursosDao.insert(response.body());
                        }
                    }).start();
                } else {
                    // Trate erros de resposta da API aqui
                }
            }

            @Override
            public void onFailure(Call<List<MiniCursoModel>> call, Throwable t) {
                // Trate falhas de conexão aqui, se acontecer eu vou de oldekandalaray do meu sac
            }
        });
    }


}
