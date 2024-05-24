package com.example.semocavi2.ui.minicurso;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.semocavi2.R;
import com.example.semocavi2.client.RetrofitClient;
import com.example.semocavi2.database.SemocAppDB;
import com.example.semocavi2.models.MiniCursoModel;
import com.example.semocavi2.service.SemocApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MiniCursoFragment extends Fragment {
    private SemocAppDB dataBase;
    private SemocApiService semocApiService;
    private MiniCursoViewModel mViewModel;

    public static MiniCursoFragment newInstance() {
        return new MiniCursoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mini_curso, container, false);

        dataBase = SemocAppDB.getInstance(requireContext());
        semocApiService = RetrofitClient.getClient().create(SemocApiService.class);
        getAndSaveMinicursos();

        return view;
    }

    private void getAndSaveMinicursos() {
        semocApiService.getMinicursos().enqueue(new Callback<List<MiniCursoModel>>() {
            @Override
            public void onResponse(Call<List<MiniCursoModel>> call, Response<List<MiniCursoModel>> response) {
                if (response.isSuccessful()) {
                    List<MiniCursoModel> minicursos = response.body();
                    if (minicursos != null && !minicursos.isEmpty()) {
                        new Thread(() -> {
                            dataBase.minicursoDao().insert(minicursos);
                            Log.d("Database", "Minicursos salvos no banco de dados");
                        }).start();
                    }
                } else {
                    Log.e("API Error", "Erro na resposta da API: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<MiniCursoModel>> call, Throwable t) {
                Log.e("API Failure", "Falha na requisição: " + t.getMessage());
            }
        });
    }
}
