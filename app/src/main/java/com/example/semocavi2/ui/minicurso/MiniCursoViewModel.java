package com.example.semocavi2.ui.minicurso;

import androidx.lifecycle.LiveData;
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

    private MiniCursosDao miniCursosDao;
    private LiveData<List<MiniCursoModel>> minicursosLiveData;

    public MiniCursoViewModel(SemocApiService semocApiService, SemocAppDB database) {
        this.miniCursosDao = database.minicursoDao();
        this.minicursosLiveData = miniCursosDao.getMinicursos();
        loadMinicursos(semocApiService);
    }

    public LiveData<List<MiniCursoModel>> getMinicursos() {
        return minicursosLiveData;
    }

    private void loadMinicursos(SemocApiService semocApiService) {
        semocApiService.getMinicursos().enqueue(new Callback<List<MiniCursoModel>>() {
            @Override
            public void onResponse(Call<List<MiniCursoModel>> call, Response<List<MiniCursoModel>> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        miniCursosDao.insert(response.body());
                    }).start();
                } else {
                    // Trate erros de resposta da API aqui
                }
            }

            @Override
            public void onFailure(Call<List<MiniCursoModel>> call, Throwable t) {
                // Trate falhas de conexão aqui
            }
        });
    }
}
