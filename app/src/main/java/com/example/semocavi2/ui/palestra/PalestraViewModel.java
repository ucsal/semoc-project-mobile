package com.example.semocavi2.ui.palestra;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.semocavi2.models.PalestraModel;
import com.example.semocavi2.repo.PalestraRepository;

import java.io.Closeable;
import java.util.List;

public class PalestraViewModel extends ViewModel {
    private PalestraRepository palestraRepository;


    private LiveData<List<PalestraModel>> palestrasLiveData;

    public PalestraViewModel(PalestraRepository palestraRepository) {
        this.palestraRepository = palestraRepository;

        this.palestrasLiveData = palestraRepository.getPalestras();

    }

    public LiveData<List<PalestraModel>> getPalestras(){
        return  palestrasLiveData;
    }

}