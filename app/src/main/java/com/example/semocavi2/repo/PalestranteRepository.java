package com.example.semocavi2.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.semocavi2.dao.PalestranteDao;
import com.example.semocavi2.models.PalestranteModel;
import com.example.semocavi2.service.SemocApiService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PalestranteRepository {

    private SemocApiService semocApiService;
    private PalestranteDao palestranteDao;

    private ExecutorService executor;

    public PalestranteRepository(SemocApiService semocApiService, PalestranteDao palestranteDao) {
        this.semocApiService = semocApiService;
        this.palestranteDao = palestranteDao;
        this.executor = Executors.newSingleThreadExecutor();

    }


    private void refreshPalestrantes(){
        executor.execute(() -> {
            // acho que ta ok, deve funcionar para o projeto
            if (palestranteDao.getCount() == 0){
                Log.d("api", "refresh palestrantes da api necessario");
                semocApiService.getPessoas().enqueue(new Callback<List<PalestranteModel>>() {
                    @Override
                    public void onResponse(Call<List<PalestranteModel>> call, Response<List<PalestranteModel>> response) {
                        if (response.isSuccessful()){
                            new Thread(() -> {
                                palestranteDao.insert(response.body());
                                Log.d("response repository", response.toString());

                            }).start();
                        }else {
                            Log.d("response DB error", response.toString());

                        }
                    }

                    @Override
                    public void onFailure(Call<List<PalestranteModel>> call, Throwable t) {
                        Log.d("response repository", "failure to call palestrantes on repo"+ t);
                    }
                });

            }else {
                Log.d("api", "refresh n necessario de palestrantes" + palestranteDao.getCount());
            }
        });


        // coloco um callback de pessoas em fila, dps crio uma thread para lidar com a insercao dos dados vindos do body da resposta no banco de dados
    }
    public LiveData<List<PalestranteModel>> getPalestrante(){
        refreshPalestrantes();
    return palestranteDao.getPalestrantes();
    }
    public LiveData<PalestranteModel> getPalestranteById(int id){
        refreshPalestrantes();
        Log.d("id", "" + id);
        return palestranteDao.getPalestrantesById(id);

    }
}
