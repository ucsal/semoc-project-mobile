package com.example.semocavi2.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.semocavi2.dao.MiniCursosDao;
import com.example.semocavi2.models.MiniCursoModel;
import com.example.semocavi2.service.SemocApiService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MiniCursoRepository {
    private SemocApiService semocApiService;
    private MiniCursosDao miniCursosDao;
    private ExecutorService executor;


    public MiniCursoRepository(SemocApiService semocApiService, MiniCursosDao miniCursosDao) {
        this.semocApiService = semocApiService;
        this.miniCursosDao = miniCursosDao;
        this.executor = Executors.newSingleThreadExecutor();

    }

    // meio falho isso aqui, mas apenas para efeito da atividade irei manter
    private void refreshMinicursosIfNecessary() {
        executor.execute(() -> {
            int count = miniCursosDao.getCount();
            if (count == 0) {
                refreshMinicursos();
            }
        });
    }

    public LiveData<List<MiniCursoModel>> getMinicursos() {
        refreshMinicursosIfNecessary();
        Log.d("api", "refresh n necessario");
        return miniCursosDao.getMinicursos();
    }

    private void refreshMinicursos() {
        Log.d("api", "refresh minicursos da api");

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
                // Trate falhas de conexão aqui, vai dar n, com fe em God
            }
        });
    }
}