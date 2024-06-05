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

    public LiveData<List<MiniCursoModel>> getMinicursosByDate(String date) {
        return miniCursosDao.getMinicursosByDate(date);
    }

    public LiveData<List<MiniCursoModel>> getScheduleMinicursos(){
        return miniCursosDao.getAllMiniCursosWhereIsScheduleIsTrue();
    }

    public LiveData<MiniCursoModel> getMinicursoById(int id) {
        Log.d("id", "" + id);
        return miniCursosDao.getMinicursosById(id);
    }



    public LiveData<List<MiniCursoModel>> getMinicursos() {
refreshMinicursos();
return miniCursosDao.getMinicursos();
    }


    private void refreshMinicursos() {
        executor.execute(() -> {
            if (miniCursosDao.getCount() == 0) {
                Log.d("api", "refresh minicursos da api necessario");

                semocApiService.getMinicursos().enqueue(new Callback<List<MiniCursoModel>>() {
                    @Override
                    public void onResponse(Call<List<MiniCursoModel>> call, Response<List<MiniCursoModel>> response) {
                        if (response.isSuccessful()) {
                            new Thread(() -> {
                                miniCursosDao.insert(response.body());
                                Log.d("response DB", response.toString());
                            }).start();
                        } else {
                            Log.d("response DB error", response.toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<List<MiniCursoModel>> call, Throwable t) {
                        // fe em Deus que nao vai rolar nenhuma falha, tira ponto n mario pf😎
                    }
                });


            } else {
                Log.d("api", "refresh n necessario de minicursos "+ miniCursosDao.getCount());
            }
        });


    }
}
