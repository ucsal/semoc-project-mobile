package com.example.semocavi2.ui.palestra;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.semocavi2.models.PalestraModel;
import com.example.semocavi2.repo.PalestraRepository;

import java.util.List;

public class PalestraViewModel extends ViewModel {
    private PalestraRepository palestraRepository;

    private LiveData<List<PalestraModel>> palestrasLiveData;

    public PalestraViewModel(PalestraRepository palestraRepository) {
        this.palestraRepository = palestraRepository;
        this.palestrasLiveData = palestraRepository.getPalestras();
    }

    public LiveData<List<PalestraModel>> getSchedulePalestras() {
        return palestraRepository.getSchedulePalestra();
    }

    public LiveData<List<PalestraModel>> getPalestrasByData(String data) {
        return palestraRepository.getPalestraByData(data);
    }

    public void setScheduleEvent(int id){
        palestraRepository.setScheduleEvent(id);
    }

    public LiveData<PalestraModel> getPalestraById(int id) {
        return palestraRepository.getPalestraById(id);

    }

    public LiveData<List<PalestraModel>> getPalestras() {
        return palestrasLiveData;
    }

}