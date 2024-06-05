package com.example.semocavi2.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.semocavi2.dao.PalestraDao;
import com.example.semocavi2.models.PalestraModel;
import com.example.semocavi2.service.SemocApiService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PalestraRepository {

    private SemocApiService semocApiService;
    private PalestraDao palestraDao;
    private ExecutorService executor;

    public PalestraRepository(SemocApiService semocApiService, PalestraDao palestraDao) {
        this.semocApiService = semocApiService;
        this.palestraDao = palestraDao;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<PalestraModel>> getSchedulePalestra(){
        return palestraDao.getAllPalestraWhereIsScheduleIsTrue();
    }

public LiveData<List<PalestraModel>>getPalestraByData(String data){
        return palestraDao.getPalestraByData(data);
}
    public LiveData<PalestraModel> getPalestraById(int id) {
        return palestraDao.getPalestraById(id);
    }

    public LiveData<List<PalestraModel>> getPalestras() {
        refreshPalestrasDb();
        Log.d("api", "refresh n necessario");
        return palestraDao.getPalestras();
    }

    private void refreshPalestrasDb() {

        executor.execute(() -> {
            if (palestraDao.getCount() == 0) {
                Log.d("api", "refresh palestras da api necessario");

                semocApiService.getPalestras().enqueue(new Callback<List<PalestraModel>>() {
                    @Override
                    public void onResponse(Call<List<PalestraModel>> call, Response<List<PalestraModel>> response) {
                        if (response.isSuccessful()) {
                            new Thread(() -> {
                                palestraDao.insert(response.body());
                            }).start();
                        } else {
                            Log.d("response DB error", response.toString());
                            Log.d("api", "refresh minicursos da api");

                        }
                    }

                    @Override
                    public void onFailure(Call<List<PalestraModel>> call, Throwable t) {
                        Log.d("response repository", "failure to call palestras on repo" + t);
                    }
                });
            } else {
                Log.d("api", "refresh n necessario de palestrantes" + palestraDao.getCount());

            }
        });


    }

}
