package com.example.semocavi2.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.semocavi2.dao.PalestraDao;
import com.example.semocavi2.models.PalestraModel;
import com.example.semocavi2.service.SemocApiService;

import java.util.List;
import java.util.concurrent.ExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PalestraRepository {

    private SemocApiService semocApiService;
    private PalestraDao palestraDao;
    private ExecutorService executor;

    public PalestraRepository(SemocApiService semocApiService, PalestraDao palestraDao, ExecutorService executorService) {
        this.semocApiService = semocApiService;
        this.palestraDao = palestraDao;
        this.executor = executorService;
    }

    // meio falho isso aqui, mas apenas para efeito da atividade irei manter
    private void refreshPalestrasIfNecessary() {
        executor.execute(() -> {
            int count = palestraDao.getCount();
            if (count == 0) {
                refreshPalestrasDb();
            }
        });
    }

    public LiveData<List<PalestraModel>> getPalestras() {
        refreshPalestrasIfNecessary();
        Log.d("api", "refresh n necessario");
        return palestraDao.getPalestras();
    }

    private void refreshPalestrasDb() {
        Log.d("api", "refresh minicursos da api");

        semocApiService.getPalestras().enqueue(new Callback<List<PalestraModel>>() {
            @Override
            public void onResponse(Call<List<PalestraModel>> call, Response<List<PalestraModel>> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        palestraDao.insert(response.body());
                    }).start();
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<PalestraModel>> call, Throwable t) {
                // Trate falhas de conexão aqui, vai dar n, com fe em God
            }
        });
    }

}
