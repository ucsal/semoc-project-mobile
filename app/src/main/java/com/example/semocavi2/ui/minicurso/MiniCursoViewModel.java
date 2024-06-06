package com.example.semocavi2.ui.minicurso;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.semocavi2.models.MiniCursoModel;
import com.example.semocavi2.repo.MiniCursoRepository;
import java.util.List;

public class MiniCursoViewModel extends ViewModel {

    private MiniCursoRepository repository;
    private LiveData<List<MiniCursoModel>> minicursosLiveData;

    public MiniCursoViewModel(MiniCursoRepository repository) {
        this.repository = repository;
        this.minicursosLiveData = repository.getMinicursos();
    }


    public LiveData<List<MiniCursoModel>> getScheduleMiniCursos(){
        return repository.getScheduleMinicursos();
    }

    public void setScheduleEvent(int id){
        repository.setScheduleEvent(id);
    }


    public LiveData<List<MiniCursoModel>> getMinicursos() {
        return minicursosLiveData;
    }

    public LiveData<MiniCursoModel> getMinicusosById(int id){
        return repository.getMinicursoById(id);
    }

    public LiveData<List<MiniCursoModel>> getMinicursosByDate(String date) {
        return repository.getMinicursosByDate(date);
    }
}
