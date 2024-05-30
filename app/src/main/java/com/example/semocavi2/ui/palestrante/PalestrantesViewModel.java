package com.example.semocavi2.ui.palestrante;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.semocavi2.models.PalestranteModel;
import com.example.semocavi2.repo.PalestranteRepository;

import java.util.List;

public class PalestrantesViewModel extends ViewModel {

    private PalestranteRepository repository;
    private LiveData<List<PalestranteModel>> palestrantesLiveDate;


    public PalestrantesViewModel(PalestranteRepository repository) {
        this.repository = repository;
        this.palestrantesLiveDate = repository.getPalestrante();
    }

    // esse cara nao vai ser usado
    public LiveData<List<PalestranteModel>> getPalestrantes(){
        return palestrantesLiveDate;

    }
    public LiveData<PalestranteModel>getPalestraById(int id){
        Log.d("id", "o id" + id);
        return repository.getPalestranteById(id);
    }



}