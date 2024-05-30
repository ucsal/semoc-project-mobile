package com.example.semocavi2.ui.palestrante;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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
            return repository.getPalestranteById(id);
    }
    public void observePalestranteById(int id, LifecycleOwner owner, Observer<PalestranteModel> observer) {
        getPalestraById(id).observe(owner, new Observer<PalestranteModel>() {
            @Override
            public void onChanged(PalestranteModel palestrante) {
                if (palestrante.getNome() != null) {
                    observer.onChanged(palestrante);
                } else {
                    Log.d("PalestranteViewModel", "Palestrante é nulo");
                }
            }
        });
    }



}