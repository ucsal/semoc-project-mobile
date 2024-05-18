package com.example.semocavi2.ui.minicurso;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.semocavi2.R;

public class MiniCursoFragment extends Fragment {

    private MiniCursoViewModel mViewModel;

    public static MiniCursoFragment newInstance() {
        return new MiniCursoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mini_curso, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MiniCursoViewModel.class);
        // TODO: Use the ViewModel
    }

}