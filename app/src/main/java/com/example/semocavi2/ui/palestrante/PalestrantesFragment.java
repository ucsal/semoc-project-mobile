package com.example.semocavi2.ui.palestrante;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.semocavi2.R;

public class PalestrantesFragment extends Fragment {

    private PalestrantesViewModel mViewModel;

    public static PalestrantesFragment newInstance() {
        return new PalestrantesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_palestrantes, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PalestrantesViewModel.class);
        // TODO: Use the ViewModel
    }

}